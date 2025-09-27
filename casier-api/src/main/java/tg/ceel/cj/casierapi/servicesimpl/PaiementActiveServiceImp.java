package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.PaiementActiveDto;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.*;
import tg.ceel.cj.casierapi.services.PaiementActiveService;
import tg.ceel.cj.casierapi.utils.ModelPaiementActive;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaiementActiveServiceImp implements PaiementActiveService {

    private final DemandeRepository demandeRepository;
    private final EntityMapper entityMapper;
    private final PayementRepository payementRepository;
    private  final FloozTransactionRepository floozTransactionRepository;
    private final TmoneyRequestRepository tyTmoneyRequestRepository;
    Logger logger = LoggerFactory.getLogger(DemandeServiceImpl.class);
    private  final UtilisateurCasierRepository utilisateurCasierRepository;

    private final PaiementActiveRepository paiementActiveRepository;

    public PaiementActiveServiceImp(DemandeRepository demandeRepository, EntityMapper entityMapper, PayementRepository payementRepository, FloozTransactionRepository floozTransactionRepository, TmoneyRequestRepository tyTmoneyRequestRepository, UtilisateurCasierRepository utilisateurCasierRepository, PaiementActiveRepository paiementActiveRepository) {
        this.demandeRepository = demandeRepository;
        this.entityMapper = entityMapper;
        this.payementRepository = payementRepository;
        this.floozTransactionRepository = floozTransactionRepository;
        this.tyTmoneyRequestRepository = tyTmoneyRequestRepository;
        this.utilisateurCasierRepository = utilisateurCasierRepository;
        this.paiementActiveRepository = paiementActiveRepository;
    }

    @Override
    public List<PaiementActiveDto> getAll() {
        List<PaiementActive>  list = this.paiementActiveRepository.findAll(Sort.by("id").descending());
        return list.stream().map(d -> entityMapper.paiementActiceToPaiementActiveDto(d)).collect(Collectors.toList());
    }

    @Override
    public PaiementActiveDto save(PaiementActiveDto paiementActiveDto) {
        PaiementActive paiementActive = this.entityMapper.paiementActiveDtoToPaiementActive(paiementActiveDto);
        try {
            String nomPrenomsUtilisateur = this.getCurrentUser().getPersonneInfo().getNom()+"  "+this.getCurrentUser().getPersonneInfo().getPrenom();
            paiementActive.setUserName(nomPrenomsUtilisateur);
            paiementActive.setCreatedBy(this.getCurrentUser().getPersonneInfo().getId());
            Demande demande = this.demandeRepository.findByNumeroDemande(paiementActiveDto.getNumeroDemande());
            if (demande == null){
                throw new Exception("Aucune demande ne correspond à ce numéro");
            }else {
                Payement payement = this.payementRepository.findById(demande.getPayement().getId()).orElse(null);
                if (payement == null){
                    throw new Exception("Aucun paiement trouvé");
                }
                payement.setRegler(Boolean.TRUE);
                this.payementRepository.save(payement);
            }
            return  this.entityMapper.paiementActiceToPaiementActiveDto(this.paiementActiveRepository.save(paiementActive));
        }catch (Exception e){
            logger.error("Erreur interne"+ e);
            return  null;
        }
    }

    @Override
    public PaiementActiveDto findById(Integer id) {
        return null;
    }

    @Override
    public ModelPaiementActive activerPaiement(String numeroDemande) {
         ModelPaiementActive modelPaiementActive = new ModelPaiementActive();

        Demande  demande = this.demandeRepository.findByNumeroDemande(numeroDemande);
        try {
        if (demande == null){
            throw new Exception("Aucune demande ne correspond à ce numéro");
        }
        else {
            modelPaiementActive.setNumeroDemande(demande.getNumeroDemande());
            modelPaiementActive.setPointRetrait(demande.getPointRetrait().getId());
            String demandeur = demande.getNom()+"  "+demande.getPrenom();
            modelPaiementActive.setDemandeur(demandeur);

            Payement payement =  this.payementRepository.findById(demande.getPayement().getId()).orElse(null);
            if (payement == null){
                throw new Exception("Aucun paiement n'est enregistré sous ce numéro");
            }
            else {
                modelPaiementActive.setDatePaiement(payement.getDatePayement());
                modelPaiementActive.setNumePaiement(payement.getNumero());
                modelPaiementActive.setModePaiement(payement.getCanalPayement().getCanalPayement());
                modelPaiementActive.setRegler(payement.getRegler());
            }

            if (payement.getCanalPayement().getCanalPayement() != null){
            if (payement.getCanalPayement().getCanalPayement().equals("FLOOZ")){
                modelPaiementActive.setNumeroTransaction(this.floozTransactionRepository.findByDemandeId(demande.getId()).getOpFloozRefid());
            }else {
                TmoneyRequest tmoneyRequest = this.tyTmoneyRequestRepository.findByDemandeId(demande.getId());
                modelPaiementActive.setNumeroTransaction(tmoneyRequest.getPurchaseref());
            }
            }
        }

        return modelPaiementActive;

    }catch (Exception e) {
        e.printStackTrace();
        logger.error("Erreur interne", e);
        return null;
    }
    }

    private UtilisateurCasier getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UtilisateurCasier utilisateurCasier = utilisateurCasierRepository.getFromUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException(String.format("Aucun utilisateur trouvé avec le nom %s", auth.getName())));
        return utilisateurCasier;
    }
}
