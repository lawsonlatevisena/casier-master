package tg.ceel.cj.casierapi.servicesimpl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.*;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.fnc.ReportManager;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.models.PaymentStatByMonthModel;
import tg.ceel.cj.casierapi.poste.LaPosteApi;
import tg.ceel.cj.casierapi.repositories.*;
import tg.ceel.cj.casierapi.services.PaiementService;
import tg.ceel.cj.casierapi.services.VariableService;
import tg.ceel.cj.casierapi.utils.CasierConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaiementServiceImpl implements PaiementService {
    @Value("${file.server.path}")
    private String fileServerPath;
    @Value("${tg.armoirie}")
    private String armoirieTogo;
    @Value("${stat.template.base}")
    private String statBaseTemplate;
    Logger logger = LoggerFactory.getLogger(PaiementServiceImpl.class);
    private final EntityMapper entityMapper;
    private final PayementRepository payementRepository;
    private final PaiementDtoService paiementDtoService;
    private final DemandeRepository demandeRepository;
    private final TmoneyNotificationRepository tmoneyNotificationRepository;
    private  final TmoneyRequestRepository tmoneyRequestRepository;
    private final VariableService variableService;
    private final FloozTransactionRepository floozTransactionRepository;
    private final LaPosteApi laPosteApi;
    private final PointRetraitRepository pointRetraitRepository;

    public PaiementServiceImpl(EntityMapper entityMapper, PayementRepository payementRepository, PaiementDtoService paiementDtoService, DemandeRepository demandeRepository, TmoneyNotificationRepository tmoneyNotificationRepository, TmoneyRequestRepository tmoneyRequestRepository, VariableService variableService, FloozTransactionRepository floozTransactionRepository, LaPosteApi laPosteApi, PointRetraitRepository pointRetraitRepository) {
        this.entityMapper = entityMapper;
        this.payementRepository = payementRepository;
        this.paiementDtoService = paiementDtoService;
        this.demandeRepository = demandeRepository;
        this.tmoneyNotificationRepository = tmoneyNotificationRepository;
        this.tmoneyRequestRepository = tmoneyRequestRepository;
        this.variableService = variableService;
        this.floozTransactionRepository = floozTransactionRepository;
        this.laPosteApi = laPosteApi;
        this.pointRetraitRepository = pointRetraitRepository;
    }

    @Override
    public PaiementDto savePaiement(PaiementDto dto) {
        try {
            if (dto == null) {
                this.logger.error("Le corps de l'objet paiement n'est pas indiqué");
                throw new Exception("L'objet paiement est null");
            }
            if (dto.getNumeroDemande() == null) {
                this.logger.error("Le numéro de la demande n'est pas indiqué");
                throw new Exception("Le numéro de la demande n'est pas indiqué");
            }
            Demande demande = demandeRepository.findByNumeroDemande(dto.getNumeroDemande());
            if (demande == null) {
                this.logger.error("Le numéro de demande indiqué ne correspond à aucune demande");
                throw new Exception("Le numéro de demande indiqué ne correspond à aucune demande");
            }
            Payement payement = paiementDtoService.paiementDtoToPaiement(dto);
            if (StringUtils.containsIgnoreCase(dto.getMoyenPaiement(), "TMONEY")) {
                payement.setCanalPayement(CanalPayement.TMONEY);
            } else {
                if (StringUtils.containsIgnoreCase(dto.getMoyenPaiement(), "FLOOZ")) {
                    payement.setCanalPayement(CanalPayement.FLOOZ);
                } else {
                    if (StringUtils.containsIgnoreCase(dto.getMoyenPaiement(), "visa") || StringUtils.containsIgnoreCase(dto.getMoyenPaiement(), "BANK")) {
                        payement.setCanalPayement(CanalPayement.CARTE_BANCAIRE);
                    }
                }
            }
            payement.setDateCreation(new Date());
            payement.setModePayement(ModePayement.ATD);
            payement = payementRepository.save(payement);
            demande.setPayement(payement);
            demande.setDisponible(false);
            demande.setValider(false);
            demande.setRetirer(false);
            demande.setEtape1Valider(false);
            demande.setEtape2Valider(false);
            demande.setEtape3Valider(false);
            demandeRepository.save(demande);
            PaiementDto paiementDto = paiementDtoService.paiementToPaiementDto(payement);
            paiementDto.setNombreCopie(dto.getNombreCopie());
            paiementDto.setNumeroDemande(dto.getNumeroDemande());
            paiementDto.setMessage(dto.getMessage());
            paiementDto.setStatut(dto.getMessage());
            return paiementDto;
        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TmoneyNotificationDto> AllTMoney() {
        try {
            List<TmoneyNotification> list = tmoneyNotificationRepository.findAll().subList(0, 100);
            return list.stream().map(entityMapper::tmoneyNotificationToTmoneyNotificationDto).collect(Collectors.toList());
        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public List<FloozTransactionDto> AllFlooz() {
        try {
            List<FloozTransaction> list = floozTransactionRepository.findByOpStatusEquals(0).subList(0, 100);
            return list.stream().map(entityMapper::floozTransactionToTFloozTransactionDto).collect(Collectors.toList());
        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public ResponseEntity<?> payerTmoney(TmoneyNotificationDto dto) {
        try {
            if (dto == null) {
                this.logger.error("Le corps de l'objet paiement n'est pas indiqué");
                return new ResponseEntity<>("Le corps de l'objet paiement n'est pas indiqué", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumeroDemande() == null) {
                this.logger.error("Le numéro de la demande n'est pas indiqué");
                return new ResponseEntity<>(String.format("Le numéro de la demande n'est pas indiqué"), HttpStatus.BAD_REQUEST);
            }
            Demande demande = demandeRepository.findByNumeroDemande(dto.getNumeroDemande());
            if (demande == null) {
                this.logger.error("Le numéro de demande indiqué ne correspond à aucune demande");
                return new ResponseEntity<>(String.format("Le numéro de demande %s ne correspond à aucune demande", dto.getNumeroDemande()), HttpStatus.BAD_REQUEST);
            }
            if (demande == null) {
                this.logger.error("Le numéro de demande indiqué ne correspond à aucune demande");
                return new ResponseEntity<>(String.format("Le numéro de demande %s ne correspond à aucune demande", dto.getNumeroDemande()), HttpStatus.BAD_REQUEST);
            }
            int nbrCopie = demande.getNombreCopie();
            double montant = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX) * nbrCopie;
            if (dto.getAmount().doubleValue() != montant) {
                return new ResponseEntity<>(String.format("Le montant indiqué (%s) ne correspond pas au montant attendu (%s)",dto.getAmount(),montant), HttpStatus.BAD_REQUEST);
            }
            TmoneyNotification tmoneyNotification = entityMapper.tmoneyNotificationDtoToTmoneyNotification(dto);
            tmoneyNotification.setDatecreation(new Date());
            tmoneyNotification = tmoneyNotificationRepository.save(tmoneyNotification);
            if (tmoneyNotification.getStatus().equalsIgnoreCase("OK")) {
                Payement payement = paiementDtoService.tmoneyNotificationnToPaiement(tmoneyNotification);
                payement.setRegler(true);
                payement.setDatePayement(new Date());
                payement = payementRepository.save(payement);
                demande.setPayement(payement);
                demande.setDisponible(false);
                demande.setValider(false);
                demande.setRetirer(false);
                demande.setEtape1Valider(false);
                demande.setEtape2Valider(false);
                demande.setEtape3Valider(false);
                demandeRepository.save(demande);
                this.sendNotification(demande);
                return new ResponseEntity<>(dto,HttpStatus.CREATED);
            }else {
                    return new ResponseEntity<>(String.format("La demande numéro %s ne peut pas être activée cas la transaction a le statut %s",dto.getNumeroDemande(),dto.getStatus()), HttpStatus.BAD_REQUEST);

            }

        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<?> payerTmoney(TmoneyRequestDto dto) {

        try {
            Demande demande = demandeRepository.findByNumeroDemande(dto.getDemande().getNumeroDemande());
            if (demande==null){
                return  new ResponseEntity<>(String.format("Aucune demande trouvée avec le numéro %s ",dto.getDemande().getNumeroDemande()),HttpStatus.NOT_FOUND);
            }
            TmoneyRequest tmoneyRequest = TmoneyRequest.builder()
                    .accepturl(dto.getAccepturl())
                    .amount(dto.getAmount())
                    .brand(dto.getBrand())
                    .cancelurl(dto.getCancelurl())
                    .currency(dto.getCurrency())
                    .dateDemande(dto.getDateDemande())
                    .declineurl(dto.getDeclineurl())
                    .description(dto.getDescription())
                    .merchantid(dto.getMerchantid())
                    .phonenumber(dto.getPhonenumber())
                    .purchaseref(dto.getPurchaseref())
                    .traiter(false)
                    .sessionid(dto.getDemande().getSessionId())
                    .demande(demande)
                    .build();
            return new ResponseEntity<>(tmoneyRequestRepository.save(tmoneyRequest),HttpStatus.CREATED);
        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<?> payerTmoney(FloozTransactionDto dto) {
         try {
            if (dto == null) {
                this.logger.error("Le corps de l'objet paiement n'est pas indiqué");
                return new ResponseEntity<>("Le corps de l'objet paiement n'est pas indiqué", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumeroDemande() == null) {
                this.logger.error("Le numéro de la demande n'est pas indiqué");
                return new ResponseEntity<>(String.format("Le numéro de la demande n'est pas indiqué"), HttpStatus.BAD_REQUEST);
            }
            Demande demande = demandeRepository.findByNumeroDemande(dto.getNumeroDemande());
            if (demande == null) {
                this.logger.error("Le numéro de demande indiqué ne correspond à aucune demande");
                return new ResponseEntity<>(String.format("Le numéro de demande %s ne correspond à aucune demande", dto.getNumeroDemande()), HttpStatus.BAD_REQUEST);
            }
            if (demande == null) {
                this.logger.error("Le numéro de demande indiqué ne correspond à aucune demande");
                return new ResponseEntity<>(String.format("Le numéro de demande %s ne correspond à aucune demande", dto.getNumeroDemande()), HttpStatus.BAD_REQUEST);
            }
            int nbrCopie = demande.getNombreCopie();
            double montant = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX) * nbrCopie;
            if (Double.valueOf(dto.getIopAmount()) != montant) {
                return new ResponseEntity<>(String.format("Le montant indiqué (%s) ne correspond pas au montant attendu (%s)",Double.valueOf(dto.getIopAmount()),montant), HttpStatus.BAD_REQUEST);
            }
            FloozTransaction floozTransaction = entityMapper.floozTransactionDtoToTFloozTransaction(dto);
             //floozTransaction.setDatecreation(LocalDateTime.now());
             floozTransaction.setDemande(demande);
             floozTransaction = floozTransactionRepository.save(floozTransaction);
            if (dto.getOpStatus()==0) {
                Payement payement = paiementDtoService.floozTransactionToPayement(floozTransaction);
                payement.setRegler(true);
                payement.setDatePayement(new Date());
                payement = payementRepository.save(payement);
                demande.setPayement(payement);
                demande.setDisponible(false);
                demande.setValider(false);
                demande.setRetirer(false);
                demande.setEtape1Valider(false);
                demande.setEtape2Valider(false);
                demande.setEtape3Valider(false);
                demandeRepository.save(demande);
                this.sendNotification(demande);
                return new ResponseEntity<>(dto,HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(String.format("La demande numéro %s ne peut pas être activée cas la transaction a le statut %s",dto.getNumeroDemande(),dto.getOpStatus()), HttpStatus.BAD_REQUEST);

            }

        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PaymentStatByMonthModel> getStatistiqueByMonth(Integer year) {
        try {
            return payementRepository.getStatistiqueByMonth(year);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public String printOnDefaultPrinter(Integer year) throws Exception {
        return null;
    }

    @Override
    public InputStream exporterStatistiqueByMonth(Integer year, String format) throws IOException, JRException {
        List<PaymentStatByMonthModel> paymentStatByMonthList = this.getStatistiqueByMonth(year);
        JRBeanCollectionDataSource paymentStatByMonthDataSource = new JRBeanCollectionDataSource(paymentStatByMonthList);
        HashMap params = new HashMap();
        params.put("ANNEE", year);
        params.put("PAIEMENT_STATISTIQUE_BY_MONTH_DATA_SOURCE", paymentStatByMonthDataSource);
        ReportManager reportManager = new ReportManager(params, statBaseTemplate+"/Paiement_stat_by_month.jrxml");
        return exporter(reportManager, format);
    }
    public InputStream exporter(ReportManager reportManager,String format) throws JRException, IOException {
        InputStream inputStream = null;
        String formatUpperCase = format.toUpperCase();
        switch (formatUpperCase) {
            case "PDF":
                inputStream = reportManager.exportFromJREmptyDataSourceToInputStream(ReportManager.ReportFormat.PDF);
                break;
            case "DOCX":
                inputStream = reportManager.exportFromJREmptyDataSourceToInputStream(ReportManager.ReportFormat.DOCX);
                break;
            case "XLSX":
                inputStream = reportManager.exportFromJREmptyDataSourceToInputStream(ReportManager.ReportFormat.XLSX);
                break;
            default:
                throw new IllegalArgumentException("Format non pris en charge : " + formatUpperCase);
        }

        return inputStream;

    }
    @Async("asyncExecutor")
    public void sendNotification(Demande demande) {
        try {
            demande.setNombreTentative(1);
            laPosteApi.sendTrackingRequest(demande);
          //  laPosteApi.sendEtabliRequest(demande, token);
        } catch (Exception e) {
            this.logger.error("Erreur lors de l'envoie à la poste ", e);
            e.printStackTrace();
        }
    }


}
