package tg.ceel.cj.casierapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tg.ceel.cj.casierapi.dto.Traitement;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.models.*;
import tg.ceel.cj.casierapi.poste.LaPosteApi;
import tg.ceel.cj.casierapi.repositories.*;
import tg.ceel.cj.casierapi.utils.CasierConstants;
import tg.ceel.cj.casierapi.utils.CasierUtils;

import java.io.IOException;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@Transactional
public class DemandeAtdServiceImpl implements DemandeAtdService {
    Logger logger = LoggerFactory.getLogger(DemandeAtdServiceImpl.class);
    @Value("${ftp.api.base_url}")
    private String ftpApiBaseUrl;
    private final LaPosteApi laPosteApi;
    private final EntityMapper entityMapper;
    private final TypePieceRepository typePieceRepository;
    private final UserB2Repository userB2Repository;
    private final ServiceDemandeurB2Repository serviceDemandeurB2Repository;
    private final EntiteDemandeurB2Repository entiteDemandeurB2Repository;
    private final CategorieDemandeurB2Repository categorieDemandeurB2Repository;
    private final UtilisateurCasierRepository utilisateurCasierRepository;
    private final SexeRepository sexeRepository;
    private final PersonneInfoRepository personneInfoRepository;
    private final ProfessionRepository professionRepository;
    private final PrefectureRepository prefectureRepository;
    private final PointRetraitRepository pointRetraitRepository;
    private final SituationMatrimonialeRepository situationMatrimonialeRepository;
    private final LocaliteRepository localiteRepository;
    private final PaysRepository paysRepository;
    private final VariableService variableService;
    private final DemandeRepository demandeRepository;
    private final CompteurService compteurService;
    private final TypeDemandeRepository typeDemandeRepository;
    private final PaiementAtDRepository paiementAtDRepository;
    private final PayementRepository payementRepository;
    private final TypePersonneMoraleRepository typePersonneMoraleRepository;
    private final LogService logService;


    public DemandeAtdServiceImpl(LaPosteApi laPosteApi, EntityMapper entityMapper, TypePieceRepository typePieceRepository, UserB2Repository userB2Repository, ServiceDemandeurB2Repository serviceDemandeurB2Repository, EntiteDemandeurB2Repository entiteDemandeurB2Repository, CategorieDemandeurB2Repository categorieDemandeurB2Repository, UtilisateurCasierRepository utilisateurCasierRepository, SexeRepository sexeRepository, PersonneInfoRepository personneInfoRepository, ProfessionRepository professionRepository, PrefectureRepository prefectureRepository, PointRetraitRepository pointRetraitRepository, SituationMatrimonialeRepository situationMatrimonialeRepository, LocaliteRepository localiteRepository, PaysRepository paysRepository, VariableService variableService, DemandeRepository demandeRepository, CompteurService compteurService, TypeDemandeRepository typeDemandeRepository, PaiementAtDRepository paiementAtDRepository, PayementRepository payementRepository, TypePersonneMoraleRepository typePersonneMoraleRepository, LogService logService) {
        this.laPosteApi = laPosteApi;
        this.entityMapper = entityMapper;
        this.typePieceRepository = typePieceRepository;
        this.userB2Repository = userB2Repository;
        this.serviceDemandeurB2Repository = serviceDemandeurB2Repository;
        this.entiteDemandeurB2Repository = entiteDemandeurB2Repository;
        this.categorieDemandeurB2Repository = categorieDemandeurB2Repository;
        this.utilisateurCasierRepository = utilisateurCasierRepository;
        this.sexeRepository = sexeRepository;
        this.personneInfoRepository = personneInfoRepository;
        this.professionRepository = professionRepository;
        this.prefectureRepository = prefectureRepository;
        this.pointRetraitRepository = pointRetraitRepository;
        this.situationMatrimonialeRepository = situationMatrimonialeRepository;
        this.localiteRepository = localiteRepository;
        this.paysRepository = paysRepository;
        this.variableService = variableService;
        this.demandeRepository = demandeRepository;
        this.compteurService = compteurService;
        this.typeDemandeRepository = typeDemandeRepository;
        this.paiementAtDRepository = paiementAtDRepository;
        this.payementRepository = payementRepository;
        this.typePersonneMoraleRepository = typePersonneMoraleRepository;
        this.logService = logService;
    }
    private final Executor executor = Executors.newFixedThreadPool(5);

    public CompletableFuture<Pays> getPaysAsync(String id) {
        return CompletableFuture.supplyAsync(() -> paysRepository.findById(id).orElse(null), executor);
    }

    public CompletableFuture<Sexe> getSexeAsync(String  id) {
        return CompletableFuture.supplyAsync(() -> sexeRepository.findById(id).orElse(null), executor);
    }

    public CompletableFuture<PointRetrait> getPointRetraitAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> pointRetraitRepository.findById(id).orElse(null), executor);
    }

    public CompletableFuture<Prefecture> getPrefectureAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> prefectureRepository.findById(Math.toIntExact(id)).orElse(null), executor);
    }

    public CompletableFuture<SituationMatrimoniale> getSituationMatrimonialeAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> situationMatrimonialeRepository.findById(Math.toIntExact(id)).orElse(null), executor);
    }
@Override
public ResponseEntity<?> saveDemandeNew(DemandeModele modele, MultipartFile multipartFile) {
        try {
            DemandeAtd demandeAtd = modele.getDemande();

            if (demandeRepository.countByRecord(demandeAtd.getRecord()) > 0) {
                Demande existing = demandeRepository.findByRecord(demandeAtd.getRecord());
                demandeAtd.setNumero_demande(existing.getNumeroDemande());
                return new ResponseEntity<>(demandeAtd, HttpStatus.CREATED);
            }

            Demande demande = creerDemandeDeBase(demandeAtd);

            CompletableFuture<Pays> paysNaissanceFuture = getPaysAsync(demandeAtd.getPays_naissance_demandeur());
            CompletableFuture<Pays> paysNationaliteFuture = getPaysAsync(demandeAtd.getPays_nationalite_demandeur());
            CompletableFuture<Pays> paysResidenceFuture = getPaysAsync(demandeAtd.getPays_residence_demandeur());
            CompletableFuture<PointRetrait> pointRetraitFuture = getPointRetraitAsync(
                    demandeAtd.getId_point_retrait_demande() != null ? demandeAtd.getId_point_retrait_demande().longValue() : null);
            CompletableFuture<Prefecture> prefectureFuture = getPrefectureAsync(Long.valueOf(demandeAtd.getId_prefecture_naissance_demandeur()));
            CompletableFuture<SituationMatrimoniale> situationFuture = getSituationMatrimonialeAsync(Long.valueOf(demandeAtd.getId_situation_matrimoniale_demandeur()));
            CompletableFuture<Sexe> sexeFuture = getSexeAsync(demandeAtd.getSexe_demandeur());

            CompletableFuture.allOf(paysNaissanceFuture, paysNationaliteFuture, paysResidenceFuture,
                    pointRetraitFuture, prefectureFuture, situationFuture, sexeFuture).join();

            demande.setPaysNaissance(paysNaissanceFuture.get());
            demande.setPaysNationalite(paysNationaliteFuture.get());
            demande.setPaysResidence(paysResidenceFuture.get());
            demande.setPointRetrait(pointRetraitFuture.get());
            demande.setPrefectureNaissance(prefectureFuture.get());
            demande.setSituationMatrimoniale(situationFuture.get());
            demande.setSexe(sexeFuture.get());
            demande = setProfession(demandeAtd, demande);

            demande = setBureauPoste(demandeAtd, demande);
            Payement p=traiterPaiement(modele).get();

            if ("CJE".equalsIgnoreCase(demande.getTypeDemande())) {
                demande.setTypeDemande("ANC");
                demande.setPointRetrait(pointRetraitRepository.findById(34L).orElse(null));
            }
            demande.setPayement(p);
            demande= demandeRepository.save(demande);
            String nomFichier = handleUpload(demande, multipartFile);
            demande.setNomFichier(nomFichier);

            if (demandeAtd.getId_bureau_poste_retrait_demande() != null && demande.getTrackingCode() != null) {
                this.sendNotification(demande);
            }
            logDemande(demande);

            return new ResponseEntity<>(demandeAtd, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Async("taskExecutor")
    public CompletableFuture<Payement> traiterPaiement(DemandeModele modele) {
        Paiement paiement = paiementAtDRepository.save(modele.getPaiement());
        Payement payement = atdPaiementToPayement(paiement);
        payement.setRegler(true);

        String gateway = paiement.getGateway().toLowerCase();
        if (gateway.contains("tmoney.tg")) {
            payement.setCanalPayement(CanalPayement.TMONEY);
        } else if (gateway.contains("moov-money.tg")) {
            payement.setCanalPayement(CanalPayement.FLOOZ);
        } else if (gateway.contains("cyber-source") || gateway.contains("bank")) {
            payement.setCanalPayement(CanalPayement.CARTE_BANCAIRE);
        } else {
            throw new IllegalArgumentException("Le mode de paiement utilisé n'est pas pris en compte");
        }

        payement.setDateCreation(new Date());
        payement.setModePayement(ModePayement.ATD);
        Payement savedPayement = payementRepository.save(payement);

        return CompletableFuture.completedFuture(savedPayement);
    }

    private Demande creerDemandeDeBase(DemandeAtd atd) throws JsonProcessingException {
        return Demande.builder()
                .typePiece(getTypePiece(atd.getId_type_piece_demande()))
                .dateDemande(new Date())
                .email(atd.getEmail_demandeur())
                .lieuNaissance(atd.getLieu_naissance_demandeur())
                .lieuResidence(atd.getLieu_residence_demandeur())
                .nom(atd.getNom_demandeur())
                .prenom(atd.getPrenom_demandeur())
                .nombreCopie(atd.getNombre_copie_demande())
                .dateNaissance(atd.getDate_naissance_demandeur())
                .record(atd.getRecord())
                .feedbackTaskId(atd.getFeedbackTaskId())
                .nomPere(atd.getNom_pere_demandeur())
                .prenomPere(atd.getPrenom_pere_demandeur())
                .nomMere(atd.getNom_mere_demandeur())
                .prenomMere(atd.getPrenom_mere_demandeur())
                .numeroFeuillet(atd.getNumero_feuillet_naissance())
                .numeroActe(atd.getNumero_acte_naissance())
                .nombreEnfant(atd.getNombre_enfant_demandeur())
                .numeroPasseport(atd.getNumero_passeport_demandeur())
                .numeroRegistre(atd.getNumero_registre_naissance())
                .order(atd.getOrder())
                .objet(new ObjectMapper().writeValueAsString(atd))
                .process(atd.getProcess())
                .typeDemande(atd.getType_demande())
                .telephone(atd.getTelephone_demandeur())
                .step(atd.getStep())
                .etape1Valider(false).etape2Valider(false).etape3Valider(false)
                .valider(false).retirer(false).signee(false)
                .trackingNotificationSuccess(false).trackingDeliverySuccess(false)
                .disponible(false).invalidee(false).tracked(false)
                .build();
    }

    private String handleUpload(Demande demande, MultipartFile file) {
        String nomFichier = demande.getId() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        MetaData metaData = MetaData.builder()
                .submittedFileName(demande.getId().toString())
                .racine(variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER))
                .build();
        CasierUtils.transfert(file, metaData, ftpApiBaseUrl + "storages/save");
        return nomFichier;
    }

    private void logDemande(Demande demande) throws JsonProcessingException {
        UtilisateurCasier utilisateur = getCurrentUser();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        logService.save(String.format("Enregistrement de la demande %s ", demande.getNumeroDemande()),
                mapper.writeValueAsString(demande), null, utilisateur.getPersonneInfo().getUser(), demande);
    }
    @Override
    public DemandeAtd saveDemande(DemandeAtd demandeAtd, MultipartFile multipartFile) {
        try {
            String codeTypePiece = demandeAtd.getId_type_piece_demande();
            TypePiece typePiece = getTypePiece(codeTypePiece);
            if (demandeRepository.findByRecord(demandeAtd.getRecord()) != null) {
                return demandeAtd;
            }
            System.err.println("date naissance ==  " + demandeAtd.getDate_naissance_demandeur());
            Demande demande = Demande.builder().typePiece(typePiece).dateDemande(new Date()).typeDemande(demandeAtd.getType_demande()).email(demandeAtd.getEmail_demandeur()).lieuNaissance(demandeAtd.getLieu_naissance_demandeur()).lieuResidence(demandeAtd.getLieu_residence_demandeur()).nom(demandeAtd.getNom_demandeur()).prenom(demandeAtd.getPrenom_demandeur()).nombreCopie(demandeAtd.getNombre_copie_demande()).dateNaissance(demandeAtd.getDate_naissance_demandeur()).record(demandeAtd.getRecord()).feedbackTaskId(demandeAtd.getFeedbackTaskId()).nomPere(demandeAtd.getNom_pere_demandeur()).prenomPere(demandeAtd.getPrenom_pere_demandeur()).nomMere(demandeAtd.getNom_mere_demandeur()).prenomMere(demandeAtd.getPrenom_mere_demandeur()).numeroFeuillet(demandeAtd.getNumero_feuillet_naissance()).numeroActe(demandeAtd.getNumero_acte_naissance()).nombreEnfant(demandeAtd.getNombre_enfant_demandeur()).numeroPasseport(demandeAtd.getNumero_passeport_demandeur()).numeroRegistre(demandeAtd.getNumero_registre_naissance()).order(demandeAtd.getOrder()).process(demandeAtd.getProcess()).telephone(demandeAtd.getTelephone_demandeur()).step(demandeAtd.getStep()).date_arivee_togo(demandeAtd.getDate_arivee_togo()).build();
            demande.setObjet(new ObjectMapper().writeValueAsString(demandeAtd));
            demande.setEtape1Valider(false);
            demande.setEtape2Valider(false);
            demande.setEtape3Valider(false);
            demande.setRetirer(false);
            demande.setValider(false);
            demande.setInvalidee(false);
            demande.setSignee(false);
            demande.setTracked(false);
            demande.setTrackingDeliverySuccess(false);
            demande.setTrackingNotificationSuccess(false);
            demande = setDateArriveeAutogo(demande);
            checkTypeDemande(demandeAtd, demande);
            demande = construireDemande(demandeAtd, demande);

            if (demandeAtd.getPays_naissance_demandeur() != null) {
                demande.setPaysNaissance(paysRepository.findById(demandeAtd.getPays_naissance_demandeur()).orElse(null));
            }

            if (demandeAtd.getPays_nationalite_demandeur() != null) {
                demande.setPaysNationalite(paysRepository.findById(demandeAtd.getPays_nationalite_demandeur()).orElse(null));
            }

            if (demandeAtd.getPays_residence_demandeur() != null) {
                demande.setPaysResidence(paysRepository.findById(demandeAtd.getPays_residence_demandeur()).orElse(null));
            }

            if (demandeAtd.getId_point_retrait_demande() != null) {
                demande.setPointRetrait(pointRetraitRepository.findById(demandeAtd.getId_point_retrait_demande().longValue()).orElse(null));
            }

            if (demandeAtd.getId_prefecture_naissance_demandeur() != null) {
                demande.setPrefectureNaissance(prefectureRepository.findById(demandeAtd.getId_prefecture_naissance_demandeur()).orElse(null));
            }

            if (demandeAtd.getId_situation_matrimoniale_demandeur() != null) {
                demande.setSituationMatrimoniale(situationMatrimonialeRepository.findById(demandeAtd.getId_situation_matrimoniale_demandeur()).orElse(null));
            }
            if (demandeAtd.getSexe_demandeur() != null) {
                demande.setSexe(sexeRepository.findById(demandeAtd.getSexe_demandeur()).orElse(null));
            }
          /*  if (dto.getCategorieSocioProfessionnelleId()!=null){
                demande.setCategorieSocioProfessionnelle(categorieSocioProfessionnelleRepository.findById(dto.getCategorieSocioProfessionnelleId()).orElse(null));
            }*/
            demande =setProfession(demandeAtd, demande);

            double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);
            if (demandeAtd.getId_bureau_poste_retrait_demande() != null) {
                demande.setBureauPosteRetrait(pointRetraitRepository.findById(demandeAtd.getId_bureau_poste_retrait_demande().longValue()).orElse(null));
            } else {
                demande.setBureauPosteRetrait(null);
            }
            demande.setDateDemande(new Date());
            demande.setAnneeDemande(Year.now().getValue());
            demande.setProvenance(Provenance.ATD);
            demande.setDisponible(false);
            demande.setTraitee(false);
            demande = setNumero(demande);
            //
            demandeAtd.setNumero_demande(demande.getNumeroDemande());

            if (demandeAtd.getRetrait_par_poste_demande() == true && demandeAtd.getId_bureau_poste_retrait_demande() != null) {
                demande.setTrackingCode(UUID.randomUUID() + "");
            }
            if (demande.getTypeDemande().equalsIgnoreCase("CJE")) {
                demande.setTypeDemande("ANC");
                demande.setPointRetrait(pointRetraitRepository.findById(34L).orElse(null));
            }
            demande = demandeRepository.save(demande);
            String UPLOAD_FOLDER = variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER);
            try {
                CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER);
            } catch (SecurityException se) {
                return null;
            }
            String nomFichier = demande.getId() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String uploadedFileLocation = UPLOAD_FOLDER + nomFichier;
            try {
                CasierUtils.saveToFile(multipartFile.getInputStream(), uploadedFileLocation);
                demande.setNomFichier(nomFichier);
                // Todo voir pour paiement
                demandeRepository.save(demande);
                if (demandeAtd.getId_bureau_poste_retrait_demande() != null && demande.getTrackingCode() != null) {
                    this.sendNotification(demande);
                }
            } catch (IOException e) {
                logger.error("Erreur interne", e);
                return null;
            }
            UtilisateurCasier utilisateur = getCurrentUser();
            logService.save(String.format("Modification de la demande %s ", demande.getNumeroDemande()), new ObjectMapper().writeValueAsString(demande), null, utilisateur.getPersonneInfo().getUser(), demande);
            return demandeAtd;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private Demande setNumero(Demande demande) {
        try{
            String num = compteurService.nextFormated(demande);
            Long count = demandeRepository.countByNumeroDemande(num);
            while (count>0){
                 num = compteurService.nextFormated(demande);
                 count = demandeRepository.countByNumeroDemande(num);
            }
            demande.setNumeroDemande(num);
            return demande;
        }catch (Exception e) {
            logger.error("Erreur interne", e);
            return demande;
        }
    }

    @Override
    public ResponseEntity<?> saveDemande(DemandeModele modele, MultipartFile multipartFile) {
        try {
            DemandeAtd demandeAtd = modele.getDemande();
            if (demandeRepository.countByRecord(demandeAtd.getRecord()) > 0) {
                Demande demande = demandeRepository.findByRecord(demandeAtd.getRecord());
                demandeAtd.setNumero_demande(demande.getNumeroDemande());
                return new ResponseEntity<>(demandeAtd, HttpStatus.CREATED);
            }
            logger.error("Envoyé:  " + demandeAtd);
            String codeTypePiece = demandeAtd.getId_type_piece_demande();
            TypePiece typePiece = getTypePiece(codeTypePiece);
            Demande demande = Demande.builder().typePiece(typePiece).dateDemande(new Date()).typeDemande(demandeAtd.getType_demande()).email(demandeAtd.getEmail_demandeur()).lieuNaissance(demandeAtd.getLieu_naissance_demandeur()).lieuResidence(demandeAtd.getLieu_residence_demandeur()).nom(demandeAtd.getNom_demandeur()).prenom(demandeAtd.getPrenom_demandeur()).nombreCopie(demandeAtd.getNombre_copie_demande()).dateNaissance(demandeAtd.getDate_naissance_demandeur()).record(demandeAtd.getRecord()).feedbackTaskId(demandeAtd.getFeedbackTaskId()).nomPere(demandeAtd.getNom_pere_demandeur()).prenomPere(demandeAtd.getPrenom_pere_demandeur()).nomMere(demandeAtd.getNom_mere_demandeur()).prenomMere(demandeAtd.getPrenom_mere_demandeur()).numeroFeuillet(demandeAtd.getNumero_feuillet_naissance()).numeroActe(demandeAtd.getNumero_acte_naissance()).nombreEnfant(demandeAtd.getNombre_enfant_demandeur()).numeroPasseport(demandeAtd.getNumero_passeport_demandeur()).numeroRegistre(demandeAtd.getNumero_registre_naissance()).order(demandeAtd.getOrder()).process(demandeAtd.getProcess()).telephone(demandeAtd.getTelephone_demandeur()).step(demandeAtd.getStep()).build();
            demande.setObjet(new ObjectMapper().writeValueAsString(demandeAtd));
            demande.setEtape1Valider(false);
            demande.setEtape2Valider(false);
            demande.setEtape3Valider(false);
            demande.setRetirer(false);
            demande.setValider(false);
            demande.setInvalidee(false);
            demande.setSignee(false);
            demande.setTracked(false);
            demande.setTrackingDeliverySuccess(false);
            demande.setTrackingNotificationSuccess(false);
            demande = setDateArriveeAutogo(demande);
            checkTypeDemande(demandeAtd, demande);
            demande = construireDemande(demandeAtd, demande);

            if (demandeAtd.getPays_naissance_demandeur() != null) {
                demande.setPaysNaissance(paysRepository.findById(demandeAtd.getPays_naissance_demandeur()).orElse(null));
            }

            if (demandeAtd.getPays_nationalite_demandeur() != null) {
                demande.setPaysNationalite(paysRepository.findById(demandeAtd.getPays_nationalite_demandeur()).orElse(null));
            }

            if (demandeAtd.getPays_residence_demandeur() != null) {
                demande.setPaysResidence(paysRepository.findById(demandeAtd.getPays_residence_demandeur()).orElse(null));
            }

            if (demandeAtd.getId_point_retrait_demande() != null) {
                demande.setPointRetrait(pointRetraitRepository.findById(demandeAtd.getId_point_retrait_demande().longValue()).orElse(null));
            }

            if (demandeAtd.getId_prefecture_naissance_demandeur() != null) {
                demande.setPrefectureNaissance(prefectureRepository.findById(demandeAtd.getId_prefecture_naissance_demandeur()).orElse(null));
            }

            if (demandeAtd.getId_situation_matrimoniale_demandeur() != null) {
                demande.setSituationMatrimoniale(situationMatrimonialeRepository.findById(demandeAtd.getId_situation_matrimoniale_demandeur()).orElse(null));
            }
            if (demandeAtd.getSexe_demandeur() != null) {
                demande.setSexe(sexeRepository.findById(demandeAtd.getSexe_demandeur()).orElse(null));
            }

            demande =setProfession(demandeAtd, demande);

          //  double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);
            demande = setBureauPoste(demandeAtd, demande);
            String UPLOAD_FOLDER = variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER);
            try {
                // CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER);
            } catch (SecurityException se) {
                return null;
            }
            // TODO modifier la manière de déposer les pièces
            String nomFichier = demande.getId() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            // String uploadedFileLocation = UPLOAD_FOLDER + nomFichier;
            MetaData metaData = MetaData.builder().folder(null).submittedFileName(String.valueOf(demande.getId())).racine(UPLOAD_FOLDER).build();
            CasierUtils.transfert(multipartFile, metaData, this.ftpApiBaseUrl + "storages/save");
            try {
                // CasierUtils.saveToFile(multipartFile.getInputStream(), uploadedFileLocation);
                demande.setNomFichier(nomFichier);
                Paiement paiement = paiementAtDRepository.save(modele.getPaiement());
                Payement payement = atdPaiementToPayement(paiement);
                payement.setRegler(true);
                if (StringUtils.containsIgnoreCase(paiement.getGateway(), "tmoney.tg")) {
                    payement.setCanalPayement(CanalPayement.TMONEY);
                } else {
                    if (StringUtils.containsIgnoreCase(paiement.getGateway(), "moov-money.tg")) {
                        payement.setCanalPayement(CanalPayement.FLOOZ);
                    } else {
                        if (StringUtils.containsIgnoreCase(paiement.getGateway(), "cyber-source") || StringUtils.containsIgnoreCase(paiement.getGateway(), "BANK")) {
                            payement.setCanalPayement(CanalPayement.CARTE_BANCAIRE);
                        } else {
                            logger.error("Erreur interne", "Le mode de paiement utilisé n'est pas prise en compte");
                            return new ResponseEntity<>("Le mode de paiement utilisé n'est pas prise en compte", HttpStatus.PRECONDITION_FAILED);
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
                if (demande.getTypeDemande().equalsIgnoreCase("CJE")) {
                    demande.setTypeDemande("ANC");
                    demande.setPointRetrait(pointRetraitRepository.findById(34L).orElse(null));
                }
                demandeRepository.save(demande);
                if (demandeAtd.getId_bureau_poste_retrait_demande() != null && demande.getTrackingCode() != null) {
                    this.sendNotification(demande);
                }
                UtilisateurCasier utilisateur = getCurrentUser();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                logService.save(String.format("Enregistrement de la demande %s ", demande.getNumeroDemande()), mapper.writeValueAsString(demande), null, utilisateur.getPersonneInfo().getUser(), demande);
            } catch (IOException e) {
                logger.error("Erreur interne", e);
                return null;
            }
            return new ResponseEntity<>(demandeAtd, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    private Demande setDateArriveeAutogo(Demande demande) {
        if (demande.getDate_arivee_togo() == null) {
            demande.setDate_arivee_togo(demande.getDateNaissance());
        }
        return demande;
    }

    @Override
    public ResponseEntity<?> saveDemande(DemandeModele demandeAtd) {
        return null;
    }

    @Override
    public ResponseEntity<?> saveDemandePM(DemandeModele modele, MultipartFile multipartFile) {
        try {
            DemandeAtd demandeAtd = modele.getDemande();

            String codeTypePiece = demandeAtd.getId_type_piece_demande();
            TypePiece typePiece = getTypePiece(codeTypePiece);
            Demande demande = Demande.builder()
                    .typePiece(typePiece)
                    .dateDemande(new Date())
                    .typeDemande(demandeAtd.getType_demande())
                    .email(demandeAtd.getEmail_demandeur())
                    .siege(demandeAtd.getSiege())
                    .denomination(demandeAtd.getDenomination())
                    .nif(demandeAtd.getNif())
                    .numeroRccm(demandeAtd.getNumero_rccm())
                    .numero_piece_personne_morale(demandeAtd.getNumero_piece_personne_morale())
                    .refExistenceLegale(demandeAtd.getRef_existence_legale())
                    .nombreCopie(demandeAtd.getNombre_copie_demande())
                    .record(demandeAtd.getRecord())
                    .feedbackTaskId(demandeAtd.getFeedbackTaskId())
                    .nom_complet_dirigeant(demandeAtd.getNom_complet_dirigeant())
                    .indicatif_demandeur(demandeAtd.getIndicatif_mobile_demandeur_personne_morale())
                    .indicatif_telephone_dirigeant(demandeAtd.getIndicatif_telephone_dirigeant())
                    .telephone_dirigeant(demandeAtd.getTelephone_dirigeant())
                    .localite_residence_dirigeant(demandeAtd.getLocalite_residence_dirigeant())
                    .order(demandeAtd.getOrder()).process(demandeAtd.getProcess())
                    .titre_dirigeant(demandeAtd.getTitre_dirigeant())
                    .step(demandeAtd.getStep())
                    .typePersonneMorale(getTypePersonneMorale(demandeAtd))
                    .build();
            demande.setObjet(new ObjectMapper().writeValueAsString(demandeAtd));
            demande.setEtape1Valider(false);
            demande.setEtape2Valider(false);
            demande.setEtape3Valider(false);
            demande.setRetirer(false);
            demande.setValider(false);
            demande.setInvalidee(false);
            demande.setSignee(false);
            demande.setTracked(false);
            demande.setTrackingDeliverySuccess(false);
            demande.setTrackingNotificationSuccess(false);
            checkTypeDemande(demandeAtd, demande);
            demande = construireDemande(demandeAtd, demande);
            if (demandeAtd.getMobile_demandeur_personne_morale() != null) {
                demande.setTelephone(demandeAtd.getMobile_demandeur_personne_morale());
            }
            if (demandeAtd.getId_point_retrait_demande() != null) {
                demande.setPointRetrait(pointRetraitRepository.findById(demandeAtd.getId_point_retrait_demande().longValue()).orElse(null));
            }
            demande.setPaysResidence(paysRepository.findById("228").orElse(null));

            if (demandeAtd.getId_point_retrait_demande() != null) {
                demande.setPointRetrait(pointRetraitRepository.findById(demandeAtd.getId_point_retrait_demande().longValue()).orElse(null));
            }
            if (demandeAtd.getPays_siege() != null) {
                demande.setPaysResidence(paysRepository.findById(demandeAtd.getPays_siege()).orElse(null));
            }

            if (demandeAtd.getId_point_retrait_demande() != null) {
                demande.setPointRetrait(pointRetraitRepository.findById(demandeAtd.getId_point_retrait_demande().longValue()).orElse(null));
            }
            demande.setTypeDemande("M3");
            demande = setBureauPoste(demandeAtd, demande);
            String UPLOAD_FOLDER = variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER);
            try {
                CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER);
            } catch (SecurityException se) {
                return null;
            }
            String nomFichier = demande.getId() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String uploadedFileLocation = UPLOAD_FOLDER + nomFichier;
            try {
                CasierUtils.saveToFile(multipartFile.getInputStream(), uploadedFileLocation);
                demande.setNomFichier(nomFichier);
                Paiement paiement = paiementAtDRepository.save(modele.getPaiement());
                Payement payement = atdPaiementToPayement(paiement);
                payement.setRegler(true);
                if (StringUtils.containsIgnoreCase(paiement.getGateway(), "tmoney.tg")) {
                    payement.setCanalPayement(CanalPayement.TMONEY);
                } else {
                    if (StringUtils.containsIgnoreCase(paiement.getGateway(), "moov-money.tg")) {
                        payement.setCanalPayement(CanalPayement.FLOOZ);
                    } else {
                        if (StringUtils.containsIgnoreCase(paiement.getGateway(), "cyber-source") || StringUtils.containsIgnoreCase(paiement.getGateway(), "BANK")) {
                            payement.setCanalPayement(CanalPayement.CARTE_BANCAIRE);
                        } else {
                            logger.error("Erreur interne", "Le mode de paiement utilisé n'est pas prise en compte");
                            return new ResponseEntity<>("Le mode de paiement utilisé n'est pas prise en compte", HttpStatus.PRECONDITION_FAILED);
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
                if (demandeAtd.getId_bureau_poste_retrait_demande() != null && demande.getTrackingCode() != null) {
                    this.sendNotification(demande);
                }
                UtilisateurCasier utilisateur = getCurrentUser();
                logService.save(String.format("Enregistrement de la demande %s ", demande.getNumeroDemande()), new ObjectMapper().writeValueAsString(demande), null, utilisateur.getPersonneInfo().getUser(), demande);
            } catch (IOException e) {
                logger.error("Erreur interne", e);
                return null;
            }
            return new ResponseEntity<>(demandeAtd, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @Async("asyncExecutor")
    public void sendNotification(Demande demande) {
        try {
            demande.setNombreTentative(1);
            laPosteApi.sendTrackingRequest(demande);
        } catch (Exception e) {
            this.logger.error("Erreur lors de l'envoie à la poste ", e);
            e.printStackTrace();
        }
    }

    private Demande construireDemande(DemandeAtd demandeAtd, Demande demande) {
        try {
            TypePiece typePiece = typePieceRepository.findByCode(demandeAtd.getId_type_piece_demande()).orElse(null);
            switch (typePiece.getId()) {
                case 1: {
                    demande.setNumeroActe(demandeAtd.getNumero_acte_naissance());
                    demande.setNumeroFeuillet(demandeAtd.getNumero_feuillet_naissance());
                    demande.setNumeroRegistre(demandeAtd.getNumero_registre_naissance());
                    demande.setEtatCivil(demandeAtd.getEtat_civil_naissance());
                    demande.setAnnee(CasierUtils.getYearOfDate(demandeAtd.getDate_naissance_demandeur()) + "");
                    break;
                }
                case 4: {
                    if (demandeAtd.getNumero_passeport_demandeur() != null) {
                        demande.setNumeroPasseport(demandeAtd.getNumero_passeport_demandeur());
                        demande.setNumeroCarte(demandeAtd.getNumero_passeport_demandeur());
                    } else if (demandeAtd.getNumero_passeport() != null) {
                        demande.setNumeroPasseport(demandeAtd.getNumero_passeport());
                        demande.setNumeroCarte(demandeAtd.getNumero_passeport());
                    }
                    break;
                }
                case 5: {
                    demande.setNumeroActe(demandeAtd.getNumero_carte_sejour());
                    demande.setDateDelivranceCarte(demandeAtd.getDate_delivrance_carte_sejour());
                    break;
                }
                case 6: {

                    demande.setDateNaissance(demandeAtd.getDate_naissance_demandeur());
                    demande.setNumeroJugement(demandeAtd.getNumero_jugement_sup_recons());
                    demande.setDateJugement(demandeAtd.getDate_jugement_sup_recons());
                    demande.setDateTranscriptionJugement(demandeAtd.getDate_transcription_jugement_sup_recons());
                    demande.setNumeroTranscriptionJugement(demandeAtd.getNumero_transcription_jugement_sup_recons());
                    break;
                }
                case 7: {
                    demande.setDateActeRectifie(demandeAtd.getDate_jugement_rectificatif());
                    demande.setTribunalJugement(demandeAtd.getTribunal_jugement_rectificatif());
                    demande.setNumeroJugement(demandeAtd.getNumero_jugement_rectificatif());
                    demande.setDateJugement(demandeAtd.getDate_jugement_rectificatif());
                    demande.setDateTranscriptionJugement(demandeAtd.getDate_transcription_jugement_sup_recons());
                    demande.setNumeroTranscriptionJugement(demandeAtd.getNumero_mention_jugement_rectificatif());
                    demande.setNomJugement(demandeAtd.getAncien_nom_demandeur());
                    demande.setPrenomJugement(demandeAtd.getAncien_prenom_demandeur());
                    demande.setNomPereJugement(demandeAtd.getAncien_nom_pere_demandeur());
                    demande.setPrenomPereJugement(demandeAtd.getAncien_prenom_pere_demandeur());
                    demande.setNomMereJugement(demandeAtd.getAncien_nom_mere_demandeur());
                    demande.setPrenomMereJugement(demandeAtd.getAncien_prenom_mere_demandeur());
                    demande.setDateNaissanceJugement(demandeAtd.getAncien_date_naissance_demandeur());
                    demande.setDateNaissance(demandeAtd.getDate_naissance_demandeur());
                    demande.setNumeroActeRectifie(demandeAtd.getNumero_acte_rectifie());
                    demande.setEtatCivilActeRectifie(demandeAtd.getEtat_civil_acte_rectifie());
                    break;
                }
                case 11: {
                    demande.setNumeroCarte(demandeAtd.getNumero_certificat_nationalite());
                    demande.setDateDelivranceCarte(demandeAtd.getDate_delivrance_nationalite());
                    break;
                }
                case 12:
                case 13:
                case 14: {
                    demande.setDateNaissance(CasierUtils.parseDate("dd/MM/yyyy", "01/01/2024"));
                    demande.setNom(demandeAtd.getDenomination());
                    demande.setPrenom(demandeAtd.getDenomination());
                    demande.setNomPere("N/A");
                    demande.setPrenomPere("N/A");
                    demande.setNomMere("N/A");
                    demande.setPrenomMere("N/A");
                    demande.setNumero_piece_personne_morale(demandeAtd.getNumero_piece_personne_morale());
                    break;
                }
                default: {
                    return demande;
                }
            }
            return demande;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }

    }

    @Override
    public DemandeAtd updateDemande(DemandeAtd demandeAtd, MultipartFile multipartFile) {
        try {
            Demande oldDemande = demandeRepository.findByRecord(demandeAtd.getRecord());
            if (oldDemande != null) {
                String codeTypePiece = demandeAtd.getId_type_piece_demande();
                TypePiece typePiece = getTypePiece(codeTypePiece);
                System.err.println("date naissance ==  " + demandeAtd.getDate_naissance_demandeur());
                oldDemande.setTypePiece(typePiece);
                oldDemande.setEmail(demandeAtd.getEmail_demandeur());
                oldDemande.setLieuNaissance(demandeAtd.getLieu_naissance_demandeur());
                oldDemande.setLieuResidence(demandeAtd.getLieu_residence_demandeur());
                oldDemande.setNom(demandeAtd.getNom_demandeur());
                oldDemande.setPrenom(demandeAtd.getPrenom_demandeur());
                oldDemande.setDateNaissance(demandeAtd.getDate_naissance_demandeur());
                oldDemande.setNomPere(demandeAtd.getNom_pere_demandeur());
                oldDemande.setPrenomPere(demandeAtd.getPrenom_pere_demandeur());
                oldDemande.setNomMere(demandeAtd.getNom_mere_demandeur());
                oldDemande.setPrenomMere(demandeAtd.getPrenom_mere_demandeur());
                oldDemande.setNumeroFeuillet(demandeAtd.getNumero_feuillet_naissance());
                oldDemande.setNumeroActe(demandeAtd.getNumero_acte_naissance());
                oldDemande.setNumeroRegistre(demandeAtd.getNumero_registre_naissance());
                oldDemande.setNombreEnfant(demandeAtd.getNombre_enfant_demandeur());
                oldDemande.setNumeroPasseport(demandeAtd.getNumero_passeport_demandeur());
                oldDemande.setTelephone(demandeAtd.getTelephone_demandeur());
                oldDemande.setObjet(new ObjectMapper().writeValueAsString(demandeAtd));
                oldDemande.setEtape1Valider(false);
                oldDemande.setEtape2Valider(false);
                oldDemande.setEtape3Valider(false);
                oldDemande.setRetirer(false);
                oldDemande.setValider(false);
                oldDemande.setInvalidee(false);
                oldDemande.setSignee(false);
                oldDemande.setTracked(false);
                oldDemande.setTrackingDeliverySuccess(false);
                oldDemande.setTrackingNotificationSuccess(false);
                // pour une personne morale
                oldDemande.setSiege(demandeAtd.getSiege());
                oldDemande.setDenomination(demandeAtd.getDenomination());
                oldDemande.setNif(demandeAtd.getNif());
                oldDemande.setNumeroRccm(demandeAtd.getNumero_rccm());
                oldDemande.setRefExistenceLegale(demandeAtd.getRef_existence_legale());
                oldDemande.setTypePersonneMorale(getTypePersonneMorale(demandeAtd));
                oldDemande.setNom_complet_dirigeant(demandeAtd.getNom_complet_dirigeant());
                oldDemande.setTelephone_dirigeant(demandeAtd.getTelephone_dirigeant());
                oldDemande.setNumero_piece_personne_morale(demandeAtd.getNumero_piece_personne_morale());
                oldDemande.setAdresse_dirigeant(demandeAtd.getAdresse_dirigeant());
                oldDemande.setTitre_dirigeant(demandeAtd.getTitre_dirigeant());
                oldDemande.setLocalite_residence_dirigeant(demandeAtd.getLocalite_residence_dirigeant());
                checkTypeDemande(demandeAtd, oldDemande);
                oldDemande = construireDemande(demandeAtd, oldDemande);
                if (demandeAtd.getMobile_demandeur_personne_morale() != null) {
                    oldDemande.setTelephone(demandeAtd.getMobile_demandeur_personne_morale());
                }
                if (demandeAtd.getPays_naissance_demandeur() != null) {
                    oldDemande.setPaysNaissance(paysRepository.findById(demandeAtd.getPays_naissance_demandeur()).orElse(null));
                }

                if (demandeAtd.getPays_nationalite_demandeur() != null) {
                    oldDemande.setPaysNationalite(paysRepository.findById(demandeAtd.getPays_nationalite_demandeur()).orElse(null));
                }

                if (demandeAtd.getPays_residence_demandeur() != null) {
                    oldDemande.setPaysResidence(paysRepository.findById(demandeAtd.getPays_residence_demandeur()).orElse(null));
                }

                if (demandeAtd.getId_point_retrait_demande() != null) {
                    oldDemande.setPointRetrait(pointRetraitRepository.findById(demandeAtd.getId_point_retrait_demande().longValue()).orElse(null));
                }

                if (demandeAtd.getId_prefecture_naissance_demandeur() != null) {
                    oldDemande.setPrefectureNaissance(prefectureRepository.findById(demandeAtd.getId_prefecture_naissance_demandeur()).orElse(null));
                }

                if (demandeAtd.getId_situation_matrimoniale_demandeur() != null) {
                    oldDemande.setSituationMatrimoniale(situationMatrimonialeRepository.findById(demandeAtd.getId_situation_matrimoniale_demandeur()).orElse(null));
                }
                if (demandeAtd.getSexe_demandeur() != null) {
                    oldDemande.setSexe(sexeRepository.findById(demandeAtd.getSexe_demandeur()).orElse(null));
                }
          /*  if (dto.getCategorieSocioProfessionnelleId()!=null){
                demande.setCategorieSocioProfessionnelle(categorieSocioProfessionnelleRepository.findById(dto.getCategorieSocioProfessionnelleId()).orElse(null));
            }*/
              /*  if (demandeAtd.getProfession_demandeur() != null && !demandeAtd.getProfession_demandeur().equalsIgnoreCase("1639")) {
                    oldDemande.setProfession(professionRepository.findById(Integer.valueOf(demandeAtd.getProfession_demandeur())).orElse(null));
                    oldDemande.setEmploi(oldDemande.getProfession().getLibelle());
                    oldDemande.setCategorieSocioProfessionnelle(oldDemande.getProfession().getCategorieSocioProfessionnelle());
                } else {
                    if (demandeAtd.getAutre_profession() != null) {
                        oldDemande.setProfession(professionRepository.findById(1639).orElse(null));
                        oldDemande.setEmploi(demandeAtd.getAutre_profession());
                        oldDemande.setAutreProfession(demandeAtd.getAutre_profession());
                        oldDemande.setCategorieSocioProfessionnelle(oldDemande.getProfession().getCategorieSocioProfessionnelle());

                    }
                }*/

                oldDemande =setProfession(demandeAtd, oldDemande);

                double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);
                oldDemande = setBureauPoste(demandeAtd, oldDemande);
                String UPLOAD_FOLDER = variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER);
                try {
                    CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER);
                } catch (SecurityException se) {
                    return null;
                }
                String nomFichier = oldDemande.getId() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                String uploadedFileLocation = UPLOAD_FOLDER + nomFichier;
                try {
                    CasierUtils.saveToFile(multipartFile.getInputStream(), uploadedFileLocation);
                    oldDemande.setNomFichier(nomFichier);
                    demandeRepository.save(oldDemande);
                } catch (IOException e) {
                    logger.error("Erreur interne", e);
                    return null;
                }
            }
            UtilisateurCasier utilisateur = getCurrentUser();
            logService.save(String.format("Modification de la demande %s ", oldDemande.getNumeroDemande()), new ObjectMapper().writeValueAsString(oldDemande), null, utilisateur.getPersonneInfo().getUser(), oldDemande);
            return demandeAtd;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeAtd updateDemande(DemandeAtd demandeAtd) {
        try {
            Demande oldDemande = demandeRepository.findByRecord(demandeAtd.getRecord());
            if (oldDemande != null) {
                String codeTypePiece = demandeAtd.getId_type_piece_demande();
                TypePiece typePiece = getTypePiece(codeTypePiece);
                oldDemande.setTypePiece(typePiece);
                oldDemande.setEmail(demandeAtd.getEmail_demandeur());
                oldDemande.setLieuNaissance(demandeAtd.getLieu_naissance_demandeur());
                oldDemande.setLieuResidence(demandeAtd.getLieu_residence_demandeur());
                oldDemande.setNom(demandeAtd.getNom_demandeur());
                oldDemande.setPrenom(demandeAtd.getPrenom_demandeur());
                oldDemande.setDateNaissance(demandeAtd.getDate_naissance_demandeur());
                oldDemande.setNomPere(demandeAtd.getNom_pere_demandeur());
                oldDemande.setPrenomPere(demandeAtd.getPrenom_pere_demandeur());
                oldDemande.setNomMere(demandeAtd.getNom_mere_demandeur());
                oldDemande.setPrenomMere(demandeAtd.getPrenom_mere_demandeur());
                oldDemande.setNumeroFeuillet(demandeAtd.getNumero_feuillet_naissance());
                oldDemande.setNumeroActe(demandeAtd.getNumero_acte_naissance());
                oldDemande.setNumeroRegistre(demandeAtd.getNumero_registre_naissance());
                oldDemande.setNombreEnfant(demandeAtd.getNombre_enfant_demandeur());
                oldDemande.setNumeroPasseport(demandeAtd.getNumero_passeport_demandeur());
                oldDemande.setTelephone(demandeAtd.getTelephone_demandeur());
                oldDemande.setObjet(new ObjectMapper().writeValueAsString(demandeAtd));
                oldDemande.setEtape1Valider(false);
                oldDemande.setEtape2Valider(false);
                oldDemande.setEtape3Valider(false);
                oldDemande.setRetirer(false);
                oldDemande.setValider(false);
                oldDemande.setInvalidee(false);
                oldDemande.setSignee(false);
                oldDemande.setTracked(false);
                oldDemande.setTrackingDeliverySuccess(false);
                oldDemande.setTrackingNotificationSuccess(false);
                // pour une personne morale
                oldDemande.setSiege(demandeAtd.getSiege());
                oldDemande.setDenomination(demandeAtd.getDenomination());
                oldDemande.setNif(demandeAtd.getNif());
                oldDemande.setNumeroRccm(demandeAtd.getNumero_rccm());
                oldDemande.setRefExistenceLegale(demandeAtd.getRef_existence_legale());
                oldDemande.setNom_complet_dirigeant(demandeAtd.getNom_complet_dirigeant());
                oldDemande.setTelephone_dirigeant(demandeAtd.getTelephone_dirigeant());
                oldDemande.setNumero_piece_personne_morale(demandeAtd.getNumero_piece_personne_morale());
                oldDemande.setAdresse_dirigeant(demandeAtd.getAdresse_dirigeant());
                oldDemande.setTitre_dirigeant(demandeAtd.getTitre_dirigeant());
                oldDemande.setLocalite_residence_dirigeant(demandeAtd.getLocalite_residence_dirigeant());
                if (demandeAtd.getType_personne_morale_code() != null) {
                    oldDemande.setTypePersonneMorale(getTypePersonneMorale(demandeAtd));
                }
                if (demandeAtd.getMobile_demandeur_personne_morale() != null) {
                    oldDemande.setTelephone(demandeAtd.getMobile_demandeur_personne_morale());
                }
                checkTypeDemande(demandeAtd, oldDemande);
                oldDemande = construireDemande(demandeAtd, oldDemande);

                if (demandeAtd.getPays_naissance_demandeur() != null) {
                    oldDemande.setPaysNaissance(paysRepository.findById(demandeAtd.getPays_naissance_demandeur()).orElse(null));
                }

                if (demandeAtd.getPays_nationalite_demandeur() != null) {
                    oldDemande.setPaysNationalite(paysRepository.findById(demandeAtd.getPays_nationalite_demandeur()).orElse(null));
                }

                if (demandeAtd.getPays_residence_demandeur() != null) {
                    oldDemande.setPaysResidence(paysRepository.findById(demandeAtd.getPays_residence_demandeur()).orElse(null));
                    if (oldDemande.getNumeroDemande().contains("M3")) {
                        oldDemande.setPaysResidence(paysRepository.findById(demandeAtd.getPays_siege()).orElse(null));

                    }
                }

                if (demandeAtd.getId_point_retrait_demande() != null) {
                    oldDemande.setPointRetrait(pointRetraitRepository.findById(demandeAtd.getId_point_retrait_demande().longValue()).orElse(null));
                }

                if (demandeAtd.getId_prefecture_naissance_demandeur() != null) {
                    oldDemande.setPrefectureNaissance(prefectureRepository.findById(demandeAtd.getId_prefecture_naissance_demandeur()).orElse(null));
                }

                if (demandeAtd.getId_situation_matrimoniale_demandeur() != null) {
                    oldDemande.setSituationMatrimoniale(situationMatrimonialeRepository.findById(demandeAtd.getId_situation_matrimoniale_demandeur()).orElse(null));
                }
                if (demandeAtd.getSexe_demandeur() != null) {
                    oldDemande.setSexe(sexeRepository.findById(demandeAtd.getSexe_demandeur()).orElse(null));
                }

                if (demandeAtd.getProfession_demandeur() != null && demandeAtd.getProfession_demandeur() != "1639") {
                    oldDemande.setProfession(professionRepository.findById(Integer.valueOf(demandeAtd.getProfession_demandeur())).orElse(null));
                    oldDemande.setEmploi(oldDemande.getProfession().getLibelle());
                    oldDemande.setCategorieSocioProfessionnelle(oldDemande.getProfession().getCategorieSocioProfessionnelle());
                } else {
                    if (demandeAtd.getAutre_profession() != null) {
                        oldDemande.setProfession(professionRepository.findById(1639).orElse(null));
                        oldDemande.setEmploi(demandeAtd.getAutre_profession());
                        oldDemande.setAutreProfession(demandeAtd.getAutre_profession());
                        oldDemande.setCategorieSocioProfessionnelle(oldDemande.getProfession().getCategorieSocioProfessionnelle());

                    }
                }

                oldDemande.setDateDemande(new Date());
                oldDemande.setAnneeDemande(Year.now().getValue());
                oldDemande.setProvenance(Provenance.ATD);
                oldDemande.setDisponible(false);
                oldDemande.setTraitee(false);
                demandeAtd.setNumero_demande(oldDemande.getNumeroDemande());

                oldDemande = demandeRepository.save(oldDemande);
                UtilisateurCasier utilisateur = getCurrentUser();
                logService.save(String.format("Modification de la demande %s ", oldDemande.getNumeroDemande()), new ObjectMapper().writeValueAsString(oldDemande), null, utilisateur.getPersonneInfo().getUser(), oldDemande);
            }
            return demandeAtd;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeAtd updateDemandeB1etB2(DemandeAtd demandeAtd) {
        try {
            Demande oldDemande = demandeRepository.findByRecord(demandeAtd.getRecord());
            if (oldDemande != null) {
                oldDemande.setEmail(demandeAtd.getEmail_demandeur());
                oldDemande.setLieuNaissance(demandeAtd.getLieu_naissance_demandeur());
                oldDemande.setLieuResidence(demandeAtd.getLieu_residence_demandeur());
                oldDemande.setNom(demandeAtd.getNom_demandeur());
                oldDemande.setPrenom(demandeAtd.getPrenom_demandeur());
                oldDemande.setDateNaissance(demandeAtd.getDate_naissance_demandeur());
                oldDemande.setNomPere(demandeAtd.getNom_pere_demandeur());
                oldDemande.setPrenomPere(demandeAtd.getPrenom_pere_demandeur());
                oldDemande.setNomMere(demandeAtd.getNom_mere_demandeur());
                oldDemande.setPrenomMere(demandeAtd.getPrenom_mere_demandeur());
                oldDemande.setNumeroFeuillet(demandeAtd.getNumero_feuillet_naissance());
                oldDemande.setNumeroActe(demandeAtd.getNumero_acte_naissance());
                oldDemande.setNumeroRegistre(demandeAtd.getNumero_registre_naissance());
                oldDemande.setNombreEnfant(demandeAtd.getNombre_enfant_demandeur());
                oldDemande.setNumeroPasseport(demandeAtd.getNumero_passeport_demandeur());
                //System.err.println(demandeAtd.getTelephone_demandeur());
                oldDemande.setTelephone(demandeAtd.getTelephone_demandeur());
                oldDemande.setObjet(new ObjectMapper().writeValueAsString(demandeAtd));
                oldDemande.setEtape1Valider(false);
                oldDemande.setEtape2Valider(false);
                oldDemande.setEtape3Valider(false);
                oldDemande.setRetirer(false);
                oldDemande.setValider(false);
                oldDemande.setInvalidee(false);
                oldDemande.setSignee(false);
                oldDemande.setTracked(false);
                oldDemande.setTrackingDeliverySuccess(false);
                oldDemande.setTrackingNotificationSuccess(false);
                // pour une personne morale
                oldDemande.setSiege(demandeAtd.getSiege());
                oldDemande.setDenomination(demandeAtd.getDenomination());
                oldDemande.setNif(demandeAtd.getNif());
                oldDemande.setNumeroRccm(demandeAtd.getNumero_rccm());
                oldDemande.setRefExistenceLegale(demandeAtd.getRef_existence_legale());

                oldDemande.setNom_complet_dirigeant(demandeAtd.getNom_complet_dirigeant());
                oldDemande.setTelephone_dirigeant(demandeAtd.getTelephone_dirigeant());
                oldDemande.setNumero_piece_personne_morale(demandeAtd.getNumero_piece_personne_morale());
                oldDemande.setAdresse_dirigeant(demandeAtd.getAdresse_dirigeant());
                oldDemande.setTitre_dirigeant(demandeAtd.getTitre_dirigeant());
                oldDemande.setLocalite_residence_dirigeant(demandeAtd.getLocalite_residence_dirigeant());
                if (demandeAtd.getMobile_demandeur_personne_morale() != null) {
                    oldDemande.setTelephone(demandeAtd.getMobile_demandeur_personne_morale());
                }
                if (oldDemande.getTypePersonneMorale() != null) {
                    oldDemande.setTypePersonneMorale(getTypePersonneMorale(demandeAtd));
                }
                if (demandeAtd.getPays_naissance_demandeur() != null) {
                    oldDemande.setPaysNaissance(paysRepository.findById(demandeAtd.getPays_naissance_demandeur()).orElse(null));
                }

                if (demandeAtd.getPays_nationalite_demandeur() != null) {
                    oldDemande.setPaysNationalite(paysRepository.findById(demandeAtd.getPays_nationalite_demandeur()).orElse(null));
                }

                if (demandeAtd.getPays_residence_demandeur() != null) {
                    oldDemande.setPaysResidence(paysRepository.findById(demandeAtd.getPays_residence_demandeur()).orElse(null));
                    if (oldDemande.getNumeroDemande().contains("M3")) {
                        oldDemande.setPaysResidence(paysRepository.findById(demandeAtd.getPays_siege()).orElse(null));

                    }
                }

                if (demandeAtd.getId_point_retrait_demande() != null) {
                    oldDemande.setPointRetrait(pointRetraitRepository.findById(demandeAtd.getId_point_retrait_demande().longValue()).orElse(null));
                }

                if (demandeAtd.getId_prefecture_naissance_demandeur() != null) {
                    oldDemande.setPrefectureNaissance(prefectureRepository.findById(demandeAtd.getId_prefecture_naissance_demandeur()).orElse(null));
                }

                if (demandeAtd.getId_situation_matrimoniale_demandeur() != null) {
                    oldDemande.setSituationMatrimoniale(situationMatrimonialeRepository.findById(demandeAtd.getId_situation_matrimoniale_demandeur()).orElse(null));
                }
                if (demandeAtd.getSexe_demandeur() != null) {
                    oldDemande.setSexe(sexeRepository.findById(demandeAtd.getSexe_demandeur()).orElse(null));
                }

                if (demandeAtd.getProfession_demandeur() != null && demandeAtd.getProfession_demandeur() != "1639") {
                    oldDemande.setProfession(professionRepository.findById(Integer.valueOf(demandeAtd.getProfession_demandeur())).orElse(null));
                    oldDemande.setEmploi(oldDemande.getProfession().getLibelle());
                    oldDemande.setCategorieSocioProfessionnelle(oldDemande.getProfession().getCategorieSocioProfessionnelle());
                } else {
                    if (demandeAtd.getAutre_profession() != null) {
                        oldDemande.setProfession(professionRepository.findById(1639).orElse(null));
                        oldDemande.setEmploi(demandeAtd.getAutre_profession());
                        oldDemande.setAutreProfession(demandeAtd.getAutre_profession());
                        oldDemande.setCategorieSocioProfessionnelle(oldDemande.getProfession().getCategorieSocioProfessionnelle());

                    }
                }

                oldDemande.setDateDemande(new Date());
                oldDemande.setAnneeDemande(Year.now().getValue());
                oldDemande.setProvenance(Provenance.ATD);
                oldDemande.setDisponible(false);
                oldDemande.setTraitee(false);
                demandeAtd.setNumero_demande(oldDemande.getNumeroDemande());

                oldDemande = demandeRepository.save(oldDemande);
                UtilisateurCasier utilisateur = getCurrentUser();
                logService.save(String.format("Modification de la demande %s ", oldDemande.getNumeroDemande()), new ObjectMapper().writeValueAsString(oldDemande), null, utilisateur.getPersonneInfo().getUser(), oldDemande);
            }
            return demandeAtd;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            e.printStackTrace();
            return null;
        }
    }

    private TypePersonneMorale getTypePersonneMorale(DemandeAtd demandeAtd) {
        return typePersonneMoraleRepository.findById(demandeAtd.getType_personne_morale_code()).orElse(null);
    }

    @Override
    public DemandeAtd demandeToDemandeAtd(Demande demande) {
        return null;
    }

    private TypePiece getTypePiece(String code) {
        try {
            TypePiece typePiece = typePieceRepository.findByCode(code).orElseThrow(() -> new Exception(String.format("Aucun type pièce ne correspond au code %s ", code)));
            return typePiece;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<?> validerUneDemande(DemandeAtd dto) {
        String type = dto.getType_demande();
        if (dto == null) {
            return new ResponseEntity<>("La demande envoyé ne contient aucune information", HttpStatus.BAD_REQUEST);
        }
        if (dto.getType_demande() == null) {
            return new ResponseEntity<>("Le type de demande n'est pas indiqué", HttpStatus.BAD_REQUEST);
        }
        if (type != null && type.equalsIgnoreCase("CJE")) {
            type = "ANC";
        }
        TypeDemande typeDemande = typeDemandeRepository.findByCode(type.toUpperCase());
        if (typeDemande == null) {
            return new ResponseEntity<>(String.format("Le type de demande %s n'existe pas ", type), HttpStatus.BAD_REQUEST);
        }

        if (dto.getNom_demandeur() == null) {
            return new ResponseEntity<>("Le nom du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPrenom_demandeur() == null) {
            return new ResponseEntity<>("Le prénom du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getId_point_retrait_demande() == null || dto.getId_point_retrait_demande() == 0) {
            return new ResponseEntity<>("Le point de retrait du belletin demandé est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPays_naissance_demandeur() == null) {
            return new ResponseEntity<>("Le pays de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPays_nationalite_demandeur() == null) {
            return new ResponseEntity<>("Le pays de nationalité du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPays_residence_demandeur() == null) {
            return new ResponseEntity<>("Le pays de résidence du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getRetrait_par_poste_demande()) {
            if (dto.getId_bureau_poste_retrait_demande() == null) {
                return new ResponseEntity<>("L'identifiant du bureau de poste est obligatoire", HttpStatus.BAD_REQUEST);
            }
        }
        switch (type.toUpperCase()) {
            case "CJE":
            case "ANC":
            case "B3": {
                if (dto.getDate_naissance_demandeur() == null) {
                    return new ResponseEntity<>("La date de naissaince du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getNom_pere_demandeur() == null) {
                    return new ResponseEntity<>("Le nom du père du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getPrenom_pere_demandeur() == null) {
                    return new ResponseEntity<>("Le prénom du père du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getNom_mere_demandeur() == null) {
                    return new ResponseEntity<>("Le nom de la mère du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getPrenom_mere_demandeur() == null) {
                    return new ResponseEntity<>("Le prénom de la mère du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getId_prefecture_naissance_demandeur() == null) {
                    return new ResponseEntity<>("La préfecture de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getLieu_naissance_demandeur() == null) {
                    return new ResponseEntity<>("La préfecture de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getId_type_piece_demande() == null) {
                    return new ResponseEntity<>("Le type de pièce utilisé pour la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                TypePiece typePiece = typePieceRepository.findByCode(dto.getId_type_piece_demande()).orElse(null);
                if (typePiece == null) {
                    return new ResponseEntity<>(String.format("Aucun type de pièce trouvé avec l'identifiant %s ", dto.getId_type_piece_demande()), HttpStatus.BAD_REQUEST);
                } else {
                    if (typePiece.getId() == 11) {
                        if (dto.getNumero_certificat_nationalite() == null) {
                            dto.setNumero_certificat_nationalite(dto.getNumero_carte());
                        }
                    }
                    ResponseEntity<?> response = validerTypePiece(dto, typePiece);
                    if (response != null) {
                        return response;
                    } else {
                        break;
                    }
                }
            }
            case "B1":
            case "B2": {
                if (dto.getDemandeur() == null) {
                    return new ResponseEntity<>(String.format("Le demandeur du bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getNom() == null) {
                    return new ResponseEntity<>(String.format("Le nom du demandeur du bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getPrenoms() == null) {
                    return new ResponseEntity<>(String.format("Le prénom de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getCni() == null) {
                    return new ResponseEntity<>(String.format("Le numéro de la carte d'identification de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getTel() == null) {
                    return new ResponseEntity<>(String.format("Le téléphone de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getSexe() == null) {
                    return new ResponseEntity<>(String.format("Le sexe de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                break;
            }
            default: {
                return new ResponseEntity<>(String.format("Le typde de demande %s n'existe pas ", type), HttpStatus.BAD_REQUEST);
            }
        }

        return null;
    }

    @Override
    public ResponseEntity<?> validerUneDemandeB1etB2(DemandeAtd dto) {
        String type = dto.getType_demande();
        if (dto == null) {
            return new ResponseEntity<>("La demande envoyé ne contient aucune information", HttpStatus.BAD_REQUEST);
        }
        if (dto.getType_demande() == null) {
            return new ResponseEntity<>("Le type de demande n'est pas indiqué", HttpStatus.BAD_REQUEST);
        }
        TypeDemande typeDemande = typeDemandeRepository.findByCode(type.toUpperCase());
        if (typeDemande == null) {
            return new ResponseEntity<>(String.format("Le type de demande %s n'existe pas ", type), HttpStatus.BAD_REQUEST);
        }

        if (dto.getNom_demandeur() == null) {
            return new ResponseEntity<>("Le nom du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPrenom_demandeur() == null) {
            return new ResponseEntity<>("Le prénom du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }

        if (dto.getPays_naissance_demandeur() == null) {
            return new ResponseEntity<>("Le pays de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPays_nationalite_demandeur() == null) {
            return new ResponseEntity<>("Le pays de nationalité du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPays_residence_demandeur() == null) {
            return new ResponseEntity<>("Le pays de résidence du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }

        /*if (dto.getDemandeur() == null) {
            return new ResponseEntity<>(String.format("Le demandeur du bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
        }
        if (dto.getDemandeur().getNom() == null) {
            return new ResponseEntity<>(String.format("Le nom du demandeur du bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
        }
        if (dto.getDemandeur().getPrenoms() == null) {
            return new ResponseEntity<>(String.format("Le prénom de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
        }
        if (dto.getDemandeur().getCni() == null) {
            return new ResponseEntity<>(String.format("Le numéro de la carte d'identification de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
        }
        if (dto.getDemandeur().getTel() == null) {
            return new ResponseEntity<>(String.format("Le téléphone de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
        }
        if (dto.getDemandeur().getSexe() == null) {
            return new ResponseEntity<>(String.format("Le sexe de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
        }*/

        return null;
    }

    @Override
    public ResponseEntity<?> validerUneDemandePersonneMorale(DemandeAtd dto) {
        String type = dto.getType_demande();
        System.err.println("les type de la piece est: " + dto.getId_type_piece_demande());
        if (dto == null) {
            return new ResponseEntity<>("La demande envoyé ne contient aucune information", HttpStatus.BAD_REQUEST);
        }
        if (dto.getType_demande() == null) {
            return new ResponseEntity<>("Le type de demande n'est pas indiqué", HttpStatus.BAD_REQUEST);
        }
        if (dto.getType_personne_morale_code() == null) {
            return new ResponseEntity<>("Le type de personne morale est obligatoire", HttpStatus.BAD_REQUEST);
        }

        TypeDemande typeDemande = typeDemandeRepository.findByCode(type.toUpperCase());
        if (typeDemande == null) {
            return new ResponseEntity<>(String.format("Le type de demande %s n'existe pas ", type), HttpStatus.BAD_REQUEST);
        }


        if (dto.getDenomination() == null) {
            return new ResponseEntity<>("La dénomination ou le nom commercial est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getNif() == null) {
            return new ResponseEntity<>("Le numéro d'identification fiscal est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getSiege() == null) {
            return new ResponseEntity<>("L'adresse du siège de la société est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getId_type_piece_demande() == null) {
            return new ResponseEntity<>("Le type de pièce utilisé pour la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getRetrait_par_poste_demande()) {
            if (dto.getId_bureau_poste_retrait_demande() == null) {
                return new ResponseEntity<>("L'identifiant du bureau de poste est obligatoire", HttpStatus.BAD_REQUEST);
            }
        }
        TypePiece typePiece = typePieceRepository.findByCode(dto.getId_type_piece_demande()).orElse(null);
        if (typePiece == null) {
            return new ResponseEntity<>(String.format("Aucun type de pièce trouvé avec l'identifiant %s ", dto.getId_type_piece_demande()), HttpStatus.BAD_REQUEST);
        } else {
            System.err.println(typePiece);
            ResponseEntity<?> response = validerTypePiece(dto, typePiece);
            if (response != null) {
                return response;
            } else {
                return null;
            }

        }

    }


    @Override
    public ResponseEntity<?> validerUneDemande(DemandeModele modele) {
        DemandeAtd dto = modele.getDemande();
        String type = dto.getType_demande();
        if (dto == null) {
            return new ResponseEntity<>("La demande envoyé ne contient aucune information", HttpStatus.BAD_REQUEST);
        }
        if (!this.checkMontantPaiement(modele)) {
            return new ResponseEntity<>("Le montant de paiement ne correspond pas au nombre de copie demandé ", HttpStatus.BAD_REQUEST);

        }
        if (dto.getType_demande() == null) {
            return new ResponseEntity<>("Le type de demande n'est pas indiqué", HttpStatus.BAD_REQUEST);
        }
        if (type != null && type.equalsIgnoreCase("CJE")) {
            type = "ANC";
        }
        TypeDemande typeDemande = typeDemandeRepository.findByCode(type.toUpperCase());
        if (typeDemande == null) {
            return new ResponseEntity<>(String.format("Le type de demande %s n'existe pas ", type), HttpStatus.BAD_REQUEST);
        }

        if (dto.getNom_demandeur() == null) {
            return new ResponseEntity<>("Le nom du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPrenom_demandeur() == null) {
            return new ResponseEntity<>("Le prénom du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getId_point_retrait_demande() == null || dto.getId_point_retrait_demande() == 0) {
            return new ResponseEntity<>("Le point de retrait du belletin demandé est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPays_naissance_demandeur() == null) {
            return new ResponseEntity<>("Le pays de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPays_nationalite_demandeur() == null) {
            return new ResponseEntity<>("Le pays de nationalité du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPays_residence_demandeur() == null) {
            return new ResponseEntity<>("Le pays de résidence du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getRetrait_par_poste_demande()) {
            if (dto.getId_bureau_poste_retrait_demande() == null) {
                return new ResponseEntity<>("L'identifiant du bureau de poste est obligatoire", HttpStatus.BAD_REQUEST);
            }
        }

        switch (type.toUpperCase()) {
            case "CJE":
            case "ANC":
            case "B3": {
                if (dto.getDate_naissance_demandeur() == null) {
                    return new ResponseEntity<>("La date de naissaince du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getNom_pere_demandeur() == null) {
                    return new ResponseEntity<>("Le nom du père du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getPrenom_pere_demandeur() == null) {
                    return new ResponseEntity<>("Le prénom du père du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getNom_mere_demandeur() == null) {
                    return new ResponseEntity<>("Le nom de la mère du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getPrenom_mere_demandeur() == null) {
                    return new ResponseEntity<>("Le prénom de la mère du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getId_prefecture_naissance_demandeur() == null) {
                    return new ResponseEntity<>("La préfecture de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getLieu_naissance_demandeur() == null) {
                    return new ResponseEntity<>("La préfecture de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getId_type_piece_demande() == null) {
                    return new ResponseEntity<>("Le type de pièce utilisé pour la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                TypePiece typePiece = typePieceRepository.findByCode(dto.getId_type_piece_demande()).orElse(null);
                if (typePiece == null) {
                    return new ResponseEntity<>(String.format("Aucun type de pièce trouvé avec l'identifiant %s ", dto.getId_type_piece_demande()), HttpStatus.BAD_REQUEST);
                } else {
                    if (typePiece.getId() == 11) {
                        if (dto.getNumero_certificat_nationalite() == null) {
                            dto.setNumero_certificat_nationalite(dto.getNumero_carte());
                        }
                    }
                    ResponseEntity<?> response = validerTypePiece(dto, typePiece);
                    if (response != null) {
                        return response;
                    } else {
                        break;
                    }
                }
            }
            case "B1":
            case "B2": {
                if (dto.getDemandeur() == null) {
                    return new ResponseEntity<>(String.format("Le demandeur du bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getNom() == null) {
                    return new ResponseEntity<>(String.format("Le nom du demandeur du bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getPrenoms() == null) {
                    return new ResponseEntity<>(String.format("Le prénom de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getCni() == null) {
                    return new ResponseEntity<>(String.format("Le numéro de la carte d'identification de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getTel() == null) {
                    return new ResponseEntity<>(String.format("Le téléphone de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                if (dto.getDemandeur().getSexe() == null) {
                    return new ResponseEntity<>(String.format("Le sexe de la personne qui demande le bulletin %s est  obligatoire ", type), HttpStatus.BAD_REQUEST);
                }
                break;
            }
            case "M3": {
                if (dto.getDenomination() == null) {
                    return new ResponseEntity<>("La dénomisation ou nom connerciale est obligatoire ", HttpStatus.BAD_REQUEST);

                }
            }
            default: {
                return new ResponseEntity<>(String.format("Le typde de demande %s n'existe pas ", type), HttpStatus.BAD_REQUEST);
            }
        }

        return null;
    }

    @Override
    public DemandeAtd saveDemande(DemandeAtd demandeAtd) {
        try {
            String codeTypePiece = demandeAtd.getId_type_piece_demande();
            Demande demande = Demande.builder().dateDemande(new Date()).typeDemande(demandeAtd.getType_demande().toUpperCase()).email(demandeAtd.getEmail_demandeur()).lieuNaissance(demandeAtd.getLieu_naissance_demandeur()).lieuResidence(demandeAtd.getLieu_residence_demandeur()).nom(demandeAtd.getNom_demandeur()).prenom(demandeAtd.getPrenom_demandeur()).nombreCopie(demandeAtd.getNombre_copie_demande()).dateNaissance(demandeAtd.getDate_naissance_demandeur()).record(demandeAtd.getRecord()).nomPere(demandeAtd.getNom_pere_demandeur()).feedbackTaskId(demandeAtd.getFeedbackTaskId()).prenomPere(demandeAtd.getPrenom_pere_demandeur()).nomMere(demandeAtd.getNom_mere_demandeur()).prenomMere(demandeAtd.getPrenom_mere_demandeur()).numeroFeuillet(demandeAtd.getNumero_feuillet_naissance()).numeroActe(demandeAtd.getNumero_acte_naissance()).nombreEnfant(demandeAtd.getNombre_enfant_demandeur()).numeroPasseport(demandeAtd.getNumero_passeport_demandeur()).numeroRegistre(demandeAtd.getNumero_registre_naissance()).order(demandeAtd.getOrder()).process(demandeAtd.getProcess()).telephone(demandeAtd.getTelephone_demandeur()).date_arivee_togo(demandeAtd.getDate_arivee_togo()).step(demandeAtd.getStep()).build();
            demande.setObjet(new ObjectMapper().writeValueAsString(demandeAtd));
            demande.setEtape1Valider(false);
            demande.setEtape2Valider(false);
            demande.setEtape3Valider(false);
            demande.setRetirer(false);
            demande.setValider(false);
            demande.setInvalidee(false);
            demande.setSignee(false);
            demande.setTracked(false);
            demande.setTrackingDeliverySuccess(false);
            demande.setTrackingNotificationSuccess(false);
            // checkTypeDemande(demandeAtd, demande);
          /*  if (demandeAtd.getCode_certification() == null) {
                throw new Exception("Tout demandadeur du B1 ou du B2 doit être indiqué");
            }*/
            UtilisateurCasier utilisateurCasier = getCurrentUser(); //checkTypeDemande(demandeAtd, demande);
            demande.setDemandeurB1(utilisateurCasier);
            if (!demandeAtd.getType_demande().equalsIgnoreCase("B1") && !demandeAtd.getType_demande().equalsIgnoreCase("B2")) {
                TypePiece typePiece = getTypePiece(codeTypePiece);
                demande.setTypePiece(typePiece);
                demande = construireDemande(demandeAtd, demande);
            }else {
                demande.setPersonne_destinataire(demandeAtd.getPersonne_destinataire());
                demande.setTribunal_destination(demandeAtd.getTribunal_destination());
            }

            if (demandeAtd.getPays_naissance_demandeur() != null) {
                System.err.println(demandeAtd.getPays_naissance_demandeur());
                System.err.println(demande);
                demande.setPaysNaissance(paysRepository.findById(demandeAtd.getPays_naissance_demandeur()).orElse(null));
            }

            if (demandeAtd.getPays_nationalite_demandeur() != null) {
                demande.setPaysNationalite(paysRepository.findById(demandeAtd.getPays_nationalite_demandeur()).orElse(null));
            }

            if (demandeAtd.getPays_residence_demandeur() != null) {
                demande.setPaysResidence(paysRepository.findById(demandeAtd.getPays_residence_demandeur()).orElse(null));
            }

            if (demandeAtd.getId_point_retrait_demande() != null) {
                demande.setPointRetrait(pointRetraitRepository.findById(demandeAtd.getId_point_retrait_demande().longValue()).orElse(null));
            }

            if (demandeAtd.getId_prefecture_naissance_demandeur() != null) {
                demande.setPrefectureNaissance(prefectureRepository.findById(demandeAtd.getId_prefecture_naissance_demandeur()).orElse(null));
            }

            if (demandeAtd.getId_situation_matrimoniale_demandeur() != null) {
                demande.setSituationMatrimoniale(situationMatrimonialeRepository.findById(demandeAtd.getId_situation_matrimoniale_demandeur()).orElse(null));
            }
            if (demandeAtd.getSexe_demandeur() != null) {
                demande.setSexe(sexeRepository.findById(demandeAtd.getSexe_demandeur()).orElse(null));
            }

            
            demande = setProfession(demandeAtd, demande);

            demande = setBureauPoste(demandeAtd, demande);
            demandeRepository.save(demande);
            demandeAtd.setDemandeur(this.utilisateurToUserB2(utilisateurCasier));
            return demandeAtd;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private Demande setProfession(DemandeAtd demandeAtd, Demande demande) {
        String numero=demandeAtd.getProfession_demandeur();
        Integer id=Integer.valueOf(numero);
        if (id==1639){
            demande.setProfession(professionRepository.findById(1639).orElse(null));
            demande.setAutreProfession(demandeAtd.getAutre_profession());
            demande.setEmploi(demandeAtd.getAutre_profession());
            demande.setCategorieSocioProfessionnelle(demande.getProfession().getCategorieSocioProfessionnelle());

        }else {
            demande.setProfession(professionRepository.findById(id).orElse(null));
            demande.setEmploi(demande.getProfession().getLibelle());
            demande.setCategorieSocioProfessionnelle(demande.getProfession().getCategorieSocioProfessionnelle());

        }

        return  demande;
    }

    @NotNull
    private Demande setBureauPoste(DemandeAtd demandeAtd, Demande demande) {
     try {
         if (demandeAtd.getId_bureau_poste_retrait_demande() != null) {
             demande.setBureauPosteRetrait(pointRetraitRepository.findById(demandeAtd.getId_bureau_poste_retrait_demande().longValue()).orElse(null));
             demande.setDeliveryMode("Bureau de poste");
         } else {
             demande.setBureauPosteRetrait(null);
         }
         demande.setDateDemande(new Date());
         demande.setAnneeDemande(Year.now().getValue());
         demande.setProvenance(Provenance.ATD);
         demande.setDisponible(false);
         demande.setTraitee(false);

         // demande.setNumeroDemande(compteurService.nextFormated(demande));
         demande = setNumero(demande);
         demandeAtd.setNumero_demande(demande.getNumeroDemande());
         if (demandeAtd.getRetrait_par_poste_demande() != null && demandeAtd.getRetrait_par_poste_demande() == true) {
             demande.setTrackingCode(UUID.randomUUID() + "");
         }
       //  demande = demandeRepository.save(demande);
         return demande;
     } catch (Exception e) {
         logger.error("Erreur interne", e);
         return demande;
     }
    }

    @Override
    public DemandeAtd rechercheParNumeroDemande(DemandeAtd dto) {
        try {
            Demande demande = demandeRepository.findByNumeroDemande(dto.getNumero_demande());
            if (demande == null) {
                demande = demandeRepository.findByRecord(dto.getNumero_demande());
            }
            if (demande != null) {
                demande.setFeedbackTaskId(dto.getFeedbackTaskId());
                demandeRepository.save(demande);
            }

            DemandeAtd demandeAtd = entityMapper.demandeToDemandeAdt(demande);
            return demandeAtd;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeAtd rechercheParNumeroDemande(String dto) {
        try {
            if (dto == null) {
                return null;
            }
            Demande demande = demandeRepository.findByRecord(dto);
            DemandeAtd demandeAtd = entityMapper.demandeToDemandeAdt(demande);
            return demandeAtd;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public Payement atdPaiementToPayement(Paiement paiement) {
        if (paiement == null) {
            return null;
        }
        try {
            Payement payement = Payement.builder().montant(Double.valueOf(paiement.getAccount())).transactionUUID(paiement.getTransactionuuid()).numeroTransaction(paiement.getBill()).datePayement(paiement.getTransactiondate()).dateCreation(new Date()).montantPaye(Double.valueOf(paiement.getAccount())).numero(paiement.getAccount()).modePayement(ModePayement.ATD).devisePaiement(paiement.getCurrency()).moyenPaiement(paiement.getGateway()).reponseJson(new ObjectMapper().writeValueAsString(paiement)).build();
            return payement;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public Paiement payementToAtdPaiement(Payement payement) {
        return null;
    }

    private Demandeur utilisateurToUserB2(UtilisateurCasier u) {
        if (u != null) {
            if (u.getServiceDemandeurB2() == null) {
                ServiceDemandeurB2 serviceDemandeurB2 = serviceDemandeurB2Repository.findById(2).orElse(null);
                UtilisateurCasier utilisateurCasier = utilisateurCasierRepository.getFromUsername(u.getPersonneInfo().getUser().getUsername()).orElse(null);
                if (utilisateurCasier!=null){
                    utilisateurCasier.setServiceDemandeurB2(serviceDemandeurB2);
                    utilisateurCasierRepository.save(utilisateurCasier);
                }
            }
            return Demandeur.builder().cni(u.getPersonneInfo().getUser().getUsername()).nom(u.getPersonneInfo().getNom()).prenoms(u.getPersonneInfo().getPrenom()).email(u.getPersonneInfo().getEmail()).tel(u.getPersonneInfo().getTelephone()).titre(u.getPersonneInfo().getTitre()).sexe(u.getPersonneInfo().getSexe().getCode()).build();
        }
        return null;
    }

    private UtilisateurCasier checkTypeDemande(DemandeAtd dto, Demande demande) {
        try {
            if (dto.getType_demande().equalsIgnoreCase("B1") || dto.getType_demande().equalsIgnoreCase("B2")) {
                if (dto.getCode_certification() == null) {
                    throw new Exception("Tout demandadeur du B1 ou du B2 doit être indiqué");
                } else {
                    UtilisateurCasier utilisateur = utilisateurCasierRepository.findByCode_certification(dto.getCode_certification());
                    demande.setDemandeurB1(utilisateur);
                    return utilisateur;
                }

            }
            return null;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private UtilisateurCasier createB2AndUtilisateurCasier(Demandeur demandeur) {
        try {
            UserB2 userB2 = userB2DtoToUserB2(demandeur);
            ServiceDemandeurB2 serviceDemandeurB2 = saveOrGetServiceDemandeurB2(userB2);
            userB2.setServiceDemandeurB2(serviceDemandeurB2);
            EntiteDemandeurB2 entiteDemandeurB2 = saveOrGetEntiteDemandeurB2(userB2);
            userB2.setEntiteDemandeurB2(entiteDemandeurB2);
            userB2.setValide(Boolean.FALSE);
            CategorieDemandeurB2 categorieDemandeurB2 = saveOrgetCategorieDemandeurB2(userB2);
            userB2.setCategorieDemandeurB2(categorieDemandeurB2);
            userB2.setDateDemande(new Date());
            userB2.setLogin(userB2.getCni());
            userB2.setPassword(userB2.getCni());
            userB2 = userB2Repository.save(userB2);
            User coreUser = User.builder().username(userB2.getCni()).active(false).changePassword(false).password(userB2.getCni()).build();
            PersonneInfo personneInfo = new PersonneInfo();
            personneInfo.setEmail(userB2.getEmail());
            personneInfo.setNom(userB2.getNom());
            personneInfo.setPrenom(userB2.getPrenoms());
            personneInfo.setTelephone(userB2.getTel());
            personneInfo.setUser(coreUser);
            personneInfo.setSexe(sexeRepository.findById(userB2.getSexe()).orElse(null));
            personneInfo = personneInfoRepository.save(personneInfo);
            UtilisateurCasier utilisateur = new UtilisateurCasier();
            utilisateur.setPersonneInfo(personneInfo);
            utilisateur.setServiceDemandeurB2(userB2.getServiceDemandeurB2());
            return utilisateurCasierRepository.save(utilisateur);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }

    }

    private CategorieDemandeurB2 saveOrgetCategorieDemandeurB2(UserB2 userB2) {
        try {
            if (userB2.getCategorieDemandeurB2() != null) {
                return categorieDemandeurB2Repository.findById(userB2.getCategorieDemandeurB2().getId()).orElse(null);
            } else {
                if (userB2.getAutreCategorieDemandeur() != null) {
                    CategorieDemandeurB2 categorieDemandeurB2 = categorieDemandeurB2Repository.findByLibelleEqualsIgnoreCase(userB2.getAutreCategorieDemandeur());
                    if (categorieDemandeurB2 == null) {
                        categorieDemandeurB2 = CategorieDemandeurB2.builder().libelle(userB2.getAutreCategorieDemandeur()).build();
                        return categorieDemandeurB2Repository.save(categorieDemandeurB2);
                    }
                    return categorieDemandeurB2;
                }
                return null;
            }
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private ServiceDemandeurB2 saveOrGetServiceDemandeurB2(UserB2 userB2) {
        try {
            if (userB2.getServiceDemandeurB2() != null) {
                return serviceDemandeurB2Repository.findById(userB2.getServiceDemandeurB2().getId()).orElse(null);
            } else {
                if (userB2.getAutreServiceDemandeur() != null) {
                    EntiteDemandeurB2 entiteDemandeurB2 = saveOrGetEntiteDemandeurB2(userB2);
                    ServiceDemandeurB2 serviceDemandeurB2 = serviceDemandeurB2Repository.findByLibelleEqualsIgnoreCase(userB2.getAutreServiceDemandeur());
                    if (serviceDemandeurB2 == null) {
                        serviceDemandeurB2 = ServiceDemandeurB2.builder().entiteDemandeurB2(entiteDemandeurB2).libelle(userB2.getAutreServiceDemandeur()).build();
                        return serviceDemandeurB2Repository.save(serviceDemandeurB2);
                    }
                    serviceDemandeurB2.setEntiteDemandeurB2(entiteDemandeurB2);
                    return serviceDemandeurB2;
                }
                return null;
            }
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private EntiteDemandeurB2 saveOrGetEntiteDemandeurB2(UserB2 userB2) {
        try {
            if (userB2.getEntiteDemandeurB2() != null) {
                return entiteDemandeurB2Repository.findById(userB2.getEntiteDemandeurB2().getId()).orElse(null);
            } else {
                if (userB2.getAutreEntiteDemandeur() != null) {
                    EntiteDemandeurB2 entiteDemandeurB2 = entiteDemandeurB2Repository.findByLibelleEqualsIgnoreCase(userB2.getAutreEntiteDemandeur());
                    if (entiteDemandeurB2 == null) {
                        entiteDemandeurB2 = EntiteDemandeurB2.builder().libelle(userB2.getAutreEntiteDemandeur()).build();
                        entiteDemandeurB2Repository.save(entiteDemandeurB2);
                    }
                    return entiteDemandeurB2;
                }
                return null;

            }
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    public UserB2 userB2DtoToUserB2(Demandeur dto) {
        if (dto == null) {
            return null;
        }
        UserB2 b2 = UserB2.builder().cni(dto.getCni()).autreCategorieDemandeur(dto.getAutre_categorie_demandeur()).autreServiceDemandeur(dto.getAutre_service_demandeur()).autreEntiteDemandeur(dto.getAutre_entite_demandeur()).dateDemande(new Date()).contact(dto.getContact()).email(dto.getEmail()).nom(dto.getNom()).prenoms(dto.getPrenoms()).sexe(dto.getSexe()).tel(dto.getTel()).valide(true).build();
        if (dto.getService_demandeurB2_id() != null) {
            b2.setServiceDemandeurB2(new ServiceDemandeurB2());
        }
        if (dto.getEntite_demandeurB2_id() != null) {
            b2.setEntiteDemandeurB2(new EntiteDemandeurB2(dto.getEntite_demandeurB2_id()));
        }
        if (dto.getCategorie_demandeurB2_id() != null) {
            b2.setCategorieDemandeurB2(new CategorieDemandeurB2(dto.getCategorie_demandeurB2_id()));
        }
        return b2;
    }

    private ResponseEntity<?> validerTypePiece(DemandeAtd dto, TypePiece typePiece) {
        System.err.println(typePiece.getId());
        try {
            switch (typePiece.getId()) {
                case 1: {
                    if (dto.getNumero_acte_naissance() == null) {
                        return new ResponseEntity<>("Le numéro de l'acte naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                    }
                    if (!dto.getType_demande().equalsIgnoreCase("CJE")) {
                        if (dto.getNumero_feuillet_naissance() == null) {
                            return new ResponseEntity<>("Le numéro du feuillet de l'acte naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                        }
                        if (dto.getNumero_registre_naissance() == null) {
                            return new ResponseEntity<>("Le numéro du registre de l'acte naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                        }
                        if (dto.getEtat_civil_naissance() == null) {
                            return new ResponseEntity<>("L'état civil  de l'acte naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                        }
                    }
                    break;
                }
                case 4: {
                    if (dto.getNumero_passeport_demandeur() == null && dto.getNumero_passeport() == null) {
                        return new ResponseEntity<>("Le numéro du passeport ou de la carte  du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                    }
                    if (dto.getDate_delivrance_nationalite() == null && dto.getDate_delivrance_passeport() == null) {
                        return new ResponseEntity<>("La date de délivrance du passeport  ou de la carte  du demandeur ou de la nationalité de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                    }
                    break;
                }
                case 5: {
                    if (dto.getNumero_carte_sejour() == null) {
                        return new ResponseEntity<>("Le numéro de la carte de séjour du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                    }
                    if (dto.getDate_delivrance_carte_sejour() == null) {
                        return new ResponseEntity<>("La date de délivrance  de la carte de séjour du demandeur ou de la nationalité de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                    }
                    break;
                }
                case 6:
                case 7: {
                    if (dto.getNumero_jugement_sup_recons() == null && dto.getNumero_acte_rectifie() == null) {
                        return new ResponseEntity<>("Le numéro du jugement du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                    }
                    if (dto.getTribunal_jugement_sup_recons() == null && dto.getTribunal_jugement_rectificatif() == null) {
                        return new ResponseEntity<>("Le tribunal du jugement du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                    }
                    break;
                }
                case 11: {
                    if (dto.getNumero_certificat_nationalite() == null) {
                        return new ResponseEntity<>("Le numéro du certificat de nationalité du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                    }
                    if (dto.getDate_delivrance_nationalite() == null) {
                        return new ResponseEntity<>("La date de délivrance du certificat de nationalité du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                    }
                    break;
                }
                case 12:
                case 13:
                case 14: {
                    if (dto.getNumero_piece_personne_morale() == null) {
                        return new ResponseEntity<>("Le numéro de la pièce est obligatoire", HttpStatus.BAD_REQUEST);
                    }

                    break;
                }
                default: {
                    System.err.println(" dans default");
                    return null;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
        return null;
    }

    @Override
    public Boolean checkMontantPaiement(DemandeModele modele) {
        try {
            if (modele == null || modele.getDemande() == null || modele.getPaiement() == null) {
                return false;
            }
            Integer nombreCopie = modele.getDemande().getNombre_copie_demande();
            Double montant = modele.getPaiement().getAmount();
            Double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);
            Double montantEnvoye = nombreCopie * prixDemande;
            return montant.equals(montantEnvoye);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return false;
        }
    }

    private UtilisateurCasier getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UtilisateurCasier utilisateurCasier = utilisateurCasierRepository.getFromUsername(auth.getName()).orElseThrow(() -> new RuntimeException(String.format("Aucun utilisateur trouvé avec le nom %s", auth.getName())));
        return utilisateurCasier;
    }

    @Override
    public Traitement updateOldDemande() {
        try {
            Integer nombre_traite = 0;
            String UPLOAD_FOLDER = variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER);
            List<Demande> demandes = demandeRepository.chargerLesAnncienneDemande();
            if (demandes == null) {
                return new Traitement();
            }
            Integer attendu = demandes.size();
            for (Demande demande : demandeRepository.chargerLesAnncienneDemande()) {
                String extension = CasierUtils.getExtension(UPLOAD_FOLDER, demande.getId() + "");
                if (extension != null) {
                    demande.setNomFichier(demande.getId() + "." + extension);
                    demandeRepository.save(demande);
                    nombre_traite++;
                }
            }
            return Traitement.builder().nombre_traite(nombre_traite).nombre_attendu(attendu).build();

        } catch (SecurityException se) {
            return null;
        }

    }

    @Override
    public ResponseEntity<?> raccourciSearch(RaccourciRequest raccourciRequest) {
        try {

            if (!raccourciRequest.getNames_provided()) {
                Demande demande = demandeRepository.loadDemandeTraitee(raccourciRequest.getNumero_demande());
                if (demande == null) {
                   return new ResponseEntity<>("Aucune demande aboutie trouvée avec le numéro " + raccourciRequest.getNumero_demande(), HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<>(DemandeAtd.demandeToDemandeAtd(demande),HttpStatus.OK);
                }
            }
            String nom = raccourciRequest.getNom().replaceAll(" ","");
            String prenom = raccourciRequest.getPrenom().replaceAll(" ","");
            Demande demande = this.demandeRepository.findTopByNomAndPrenomAndDateNaissanceAndTraiteeIsTrue(nom.toLowerCase(), prenom.toLowerCase(), raccourciRequest.getDate_naissance());
            if (demande == null) {
               return new ResponseEntity<>("Aucune demande aboutie trouvée avec le nom " + raccourciRequest.getNom() + " prenom " + raccourciRequest.getPrenom() + " date naissance " + raccourciRequest.getDate_naissance(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(DemandeAtd.demandeToDemandeAtd(demande),HttpStatus.OK);

        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<?> raccourciSave(DemandeModele demande) {
        try {
          DemandeAtd  demandeAtd = demande.getDemande();
            Demande oldDemande = demandeRepository.findByRecord(demandeAtd.getRecord());
            System.err.println(demandeAtd.getNombre_copie_demande());
            Demande demandeNew = new Demande();
            if (oldDemande != null) {
                BeanUtils.copyProperties(oldDemande, demandeNew, new String[]{"id", "payement", "nombreCopie", "numeroDemande"});
               demandeNew.setParentDemandeId(oldDemande.getId());
                demandeNew.setNomFichier(oldDemande.getNomFichier());
                demandeNew.setMimeType(oldDemande.getMimeType());
                demandeNew.setNombreCopie(demandeAtd.getNombre_copie_demande());
                demandeNew.setEmail(demandeAtd.getEmail_demandeur());
                demandeNew.setLieuResidence(demandeAtd.getLieu_residence_demandeur());
                demandeNew.setNombreEnfant(demandeAtd.getNombre_enfant_demandeur());
                demandeNew.setTelephone(demandeAtd.getTelephone_demandeur());
                demandeNew.setObjet(new ObjectMapper().writeValueAsString(demandeAtd));
                demandeNew.setEtape1Valider(false);
                demandeNew.setEtape2Valider(false);
                demandeNew.setEtape3Valider(false);
                demandeNew.setRetirer(false);
                demandeNew.setValider(false);
                demandeNew.setInvalidee(false);
                demandeNew.setSignee(false);
                demandeNew.setTracked(false);
                demandeNew.setTrackingDeliverySuccess(false);
                demandeNew.setTrackingNotificationSuccess(false);
                demandeNew.setRecord(demandeAtd.getRecord());
                demandeNew.setFeedbackTaskId(demandeAtd.getFeedbackTaskId());
                demandeNew.setStep(demandeAtd.getStep());
                demandeNew.setOrder(demandeAtd.getOrder());
                demandeNew.setProcess(demandeAtd.getProcess());
                // pour une personne morale
                demandeNew.setSiege(demandeAtd.getSiege());
                demandeNew.setDenomination(demandeAtd.getDenomination());
                demandeNew.setNif(demandeAtd.getNif());
                demandeNew.setNumeroRccm(demandeAtd.getNumero_rccm());
                demandeNew.setRefExistenceLegale(demandeAtd.getRef_existence_legale());
                demandeNew.setNom_complet_dirigeant(demandeAtd.getNom_complet_dirigeant());
                demandeNew.setTelephone_dirigeant(demandeAtd.getTelephone_dirigeant());
                demandeNew.setNumero_piece_personne_morale(demandeAtd.getNumero_piece_personne_morale());
                demandeNew.setAdresse_dirigeant(demandeAtd.getAdresse_dirigeant());
                demandeNew.setTitre_dirigeant(demandeAtd.getTitre_dirigeant());
                demandeNew.setLocalite_residence_dirigeant(demandeAtd.getLocalite_residence_dirigeant());
                demandeNew.setDateDemande(new Date());
                demandeNew.setAnneeDemande(Year.now().getValue());
                demandeNew.setProvenance(Provenance.ATD);
                demandeNew.setDisponible(false);
                demandeNew.setTraitee(false);
                demandeNew.setNumeroDemande(compteurService.nextFormated(demandeNew));
                demandeNew = demandeRepository.save(demandeNew);
                Paiement paiement = paiementAtDRepository.save(demande.getPaiement());
                Payement payement = atdPaiementToPayement(paiement);
                payement.setRegler(true);
                if (StringUtils.containsIgnoreCase(paiement.getGateway(), "tmoney.tg")) {
                    payement.setCanalPayement(CanalPayement.TMONEY);
                } else {
                    if (StringUtils.containsIgnoreCase(paiement.getGateway(), "moov-money.tg")) {
                        payement.setCanalPayement(CanalPayement.FLOOZ);
                    } else {
                        if (StringUtils.containsIgnoreCase(paiement.getGateway(), "cyber-source") || StringUtils.containsIgnoreCase(paiement.getGateway(), "BANK")) {
                            payement.setCanalPayement(CanalPayement.CARTE_BANCAIRE);
                        } else {
                            logger.error("Erreur interne", "Le mode de paiement utilisé n'est pas prise en compte");
                            return new ResponseEntity<>("Le mode de paiement utilisé n'est pas prise en compte", HttpStatus.PRECONDITION_FAILED);
                        }
                    }
                }
                if (demandeNew.getTypeDemande().equalsIgnoreCase("CJE")) {
                    demandeNew.setTypeDemande("ANC");
                    demandeNew.setPointRetrait(pointRetraitRepository.findById(34L).orElse(null));
                }
                demandeRepository.save(demandeNew);
                UtilisateurCasier utilisateur = getCurrentUser();
                logService.save(String.format("Modification de la demande %s ", demandeNew.getNumeroDemande()), new ObjectMapper().writeValueAsString(oldDemande), null, utilisateur.getPersonneInfo().getUser(), oldDemande);
            }
            return new ResponseEntity<>(DemandeAtd.construireDemande(demandeAtd,demandeNew), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private boolean isMatchingPieceNumber(Demande demande, String numeroPiece) {
        return (demande.getNumeroActe() != null && demande.getNumeroActe().equalsIgnoreCase(numeroPiece))
                || (demande.getNumeroCarte() != null && demande.getNumeroCarte().equalsIgnoreCase(numeroPiece))
                || (demande.getNumeroPasseport() != null && demande.getNumeroPasseport().equalsIgnoreCase(numeroPiece))
                || (demande.getNumeroJugement() != null && demande.getNumeroJugement().equals(numeroPiece));
    }
}
