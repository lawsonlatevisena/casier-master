package tg.ceel.cj.casierapi.servicesimpl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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
import tg.ceel.cj.casierapi.dto.*;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.fnc.BulletinGenerator;
import tg.ceel.cj.casierapi.fnc.Condamnation;
import tg.ceel.cj.casierapi.fnc.ReportManager;
import tg.ceel.cj.casierapi.fnc.dto.CondamnationCasier;
import tg.ceel.cj.casierapi.fnc.dto.PeineInfractionDto;
import tg.ceel.cj.casierapi.fnc.models.Casier;
import tg.ceel.cj.casierapi.fnc.models.DemandeFnc;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.models.*;
import tg.ceel.cj.casierapi.poste.LaPosteApi;
import tg.ceel.cj.casierapi.repositories.*;
import tg.ceel.cj.casierapi.security.AccountService;
import tg.ceel.cj.casierapi.services.*;
import tg.ceel.cj.casierapi.utils.CasierConstants;
import tg.ceel.cj.casierapi.utils.CasierUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import static tg.ceel.cj.casierapi.utils.ResponseCode.NOT_FOUND;
import static tg.ceel.cj.casierapi.utils.ResponseCode.SUCCESS;

@Service
@Transactional
public class DemandeServiceImpl implements DemandeService {

    @Value("${file.server.path}")
    private String fileServerPath;
    @Value("${tg.armoirie}")
    private String armoirieTogo;
    @Value("${tg.signature}")
    private String signatureImage;
    @Value("${stat.template.base}")
    private String statBaseTemplate;
    @Value("${ftp.api.base_url}")
    private String ftpApiBaseUrl;
    Logger logger = LoggerFactory.getLogger(DemandeServiceImpl.class);
    private final LogService logService;
    private final ModelSMSRepository modelSMSRepository;
    private final ServiceEnvoyeur serviceEnvoyeur;
    private final FNCService fncService;
    private final DemandeRepository demandeRepository;
    private final EntityMapper entityMapper;
    private final CompteurService compteurService;
    private final SituationMatrimonialeRepository situationMatrimonialeRepository;
    private final TypePieceRepository typePieceRepository;
    private final ProfessionRepository professionRepository;
    private final PrefectureRepository prefectureRepository;
    private final PointRetraitRepository pointRetraitRepository;
    private final LocaliteRepository localiteRepository;
    private final PaysRepository paysRepository;
    private final SexeRepository sexeRepository;
    private final VariableService variableService;
    private final UtilisateurCasierRepository utilisateurCasierRepository;
    private final BulletinGenerator bulletinGenerator;
    private final CompteurTraitementService compteurTraitementService;
    private final UserB2Repository userB2Repository;
    private final ServiceDemandeurB2Repository serviceDemandeurB2Repository;
    private final EntiteDemandeurB2Repository entiteDemandeurB2Repository;
    private final PersonneInfoRepository personneInfoRepository;
    private final CategorieDemandeurB2Repository categorieDemandeurB2Repository;
    private final TypeDemandeRepository typeDemandeRepository;
    private final UtilisateurCasierPointRetraitRepository utilisateurCasierPointRetraitRepository;
    private final AccountService accountService;
    private final LaPosteApi laPosteApi;


    public DemandeServiceImpl(LogService logService, ModelSMSRepository modelSMSRepository, ServiceEnvoyeur serviceEnvoyeur, FNCService fncService, DemandeRepository demandeRepository, EntityMapper entityMapper, CompteurService compteurService, SituationMatrimonialeRepository situationMatrimonialeRepository, PointRetraitRepository pointRetraitRepository, TypePieceRepository typePieceRepository, ProfessionRepository professionRepository, CategorieSocioProfessionnelleRepository categorieSocioProfessionnelleRepository, PrefectureRepository prefectureRepository, LocaliteRepository localiteRepository, PaysRepository paysRepository, SexeRepository sexeRepository, VariableService variableService, UtilisateurCasierRepository utilisateurCasierRepository, BulletinGenerator bulletinGenerator, CompteurTraitementService compteurTraitementService, UserB2Repository userB2Repository, ServiceDemandeurB2Repository serviceDemandeurB2Repository, EntiteDemandeurB2Repository entiteDemandeurB2Repository, PersonneInfoRepository personneInfoRepository, CategorieDemandeurB2Repository categorieDemandeurB2Repository, TypeDemandeRepository typeDemandeRepository, UtilisateurCasierPointRetraitRepository utilisateurCasierPointRetraitRepository, AccountService accountService, LaPosteApi laPosteApi) {
        this.logService = logService;
        this.modelSMSRepository = modelSMSRepository;
        this.serviceEnvoyeur = serviceEnvoyeur;
        this.fncService = fncService;


        this.demandeRepository = demandeRepository;
        this.entityMapper = entityMapper;
        this.compteurService = compteurService;
        this.situationMatrimonialeRepository = situationMatrimonialeRepository;
        this.typePieceRepository = typePieceRepository;
        this.professionRepository = professionRepository;
        this.prefectureRepository = prefectureRepository;
        this.pointRetraitRepository = pointRetraitRepository;
        this.localiteRepository = localiteRepository;
        this.paysRepository = paysRepository;
        this.sexeRepository = sexeRepository;
        this.variableService = variableService;
        this.utilisateurCasierRepository = utilisateurCasierRepository;
        this.bulletinGenerator = bulletinGenerator;
        this.compteurTraitementService = compteurTraitementService;
        this.userB2Repository = userB2Repository;
        this.serviceDemandeurB2Repository = serviceDemandeurB2Repository;
        this.entiteDemandeurB2Repository = entiteDemandeurB2Repository;
        this.personneInfoRepository = personneInfoRepository;
        this.categorieDemandeurB2Repository = categorieDemandeurB2Repository;
        this.typeDemandeRepository = typeDemandeRepository;

        this.utilisateurCasierPointRetraitRepository = utilisateurCasierPointRetraitRepository;
        this.accountService = accountService;
        this.laPosteApi = laPosteApi;
    }


    @Override
    public ResponseEntity<?> validerUneDemande(DemandeDto dto) {
        String type = dto.getTypeDemande();
        if (dto == null) {
            return new ResponseEntity<>("La demande envoyé ne contient aucune information", HttpStatus.BAD_REQUEST);
        }
        if (dto.getTypeDemande() == null) {
            return new ResponseEntity<>("Le type de demande n'est pas indiqué", HttpStatus.BAD_REQUEST);
        }
        TypeDemande typeDemande = typeDemandeRepository.findByCode(type);
        if (typeDemande == null) {
            return new ResponseEntity<>(String.format("Le type de demande %s n'existe pas ", type), HttpStatus.BAD_REQUEST);
        }

        if (dto.getNom() == null) {
            return new ResponseEntity<>("Le nom du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPrenom() == null) {
            return new ResponseEntity<>("Le prénom du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPointRetraitId() == null || dto.getPointRetraitId() == 0) {
            return new ResponseEntity<>("Le point de retrait du belletin demandé est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPaysNaissanceCode() == null) {
            return new ResponseEntity<>("Le pays de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPaysNationaliteCode() == null) {
            return new ResponseEntity<>("Le pays de nationalité du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPaysResidenceCode() == null) {
            return new ResponseEntity<>("Le pays de résidence du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
        }
        switch (type) {
            case "CJE":
            case "ANC":
            case "B3": {
                if (dto.getDateNaissance() == null) {
                    return new ResponseEntity<>("La date de naissaince du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getNomPere() == null) {
                    return new ResponseEntity<>("Le nom du père du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getPrenomPere() == null) {
                    return new ResponseEntity<>("Le prénom du père du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getNomMere() == null) {
                    return new ResponseEntity<>("Le nom de la mère du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getPrenomMere() == null) {
                    return new ResponseEntity<>("Le prénom de la mère du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getPrefectureNaissanceId() == null) {
                    return new ResponseEntity<>("La préfecture de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getLieuNaissance() == null) {
                    return new ResponseEntity<>("La préfecture de naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getTypePieceId() == null) {
                    return new ResponseEntity<>("Le type de pièce utilisé pour la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                TypePiece typePiece = typePieceRepository.findById(dto.getTypePieceId()).orElse(null);
                if (typePiece == null) {
                    return new ResponseEntity<>(String.format("Aucun type de pièce trouvé avec l'identifiant %s ", dto.getTypePieceId()), HttpStatus.BAD_REQUEST);
                } else {
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

    private ResponseEntity<?> validerTypePiece(DemandeDto dto, TypePiece typePiece) {
        System.err.println(typePiece.getId());
        switch (typePiece.getId()) {
            case 1: {
                if (dto.getNumeroActe() == null) {
                    return new ResponseEntity<>("Le numéro de l'acte naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getNumeroFeuillet() == null) {
                    return new ResponseEntity<>("Le numéro du feuillet de l'acte naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getNumeroRegistre() == null) {
                    return new ResponseEntity<>("Le numéro du registre de l'acte naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getEtatCivil() == null) {
                    return new ResponseEntity<>("L'état civil  de l'acte naissance du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                break;
            }
            case 4:
            case 5: {
                if (dto.getNumeroPasseport() == null) {
                    return new ResponseEntity<>("Le numéro du passeport ou de la carte  du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getDateDelivranceCarte() == null) {
                    return new ResponseEntity<>("La date de délivrance du passeport  ou de la carte  du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                break;
            }
            case 6:
            case 7: {
                if (dto.getNumeroJugement() == null) {
                    return new ResponseEntity<>("Le numéro du jugement du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getTribunalJugement() == null) {
                    return new ResponseEntity<>("Le tribunal du jugement du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                break;
            }
            case 11: {
                if (dto.getNumeroCarte() == null) {
                    return new ResponseEntity<>("Le numéro du certificat de nationalité du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                if (dto.getDateDelivranceCarte() == null) {
                    return new ResponseEntity<>("La date de délivrance du certificat de nationalité du demandeur ou de la personne concernée par la demande est obligatoire", HttpStatus.BAD_REQUEST);
                }
                break;
            }
            default: {
                return null;
            }

        }
        return null;
    }

    @Override
    public DemandeDto enregistrerDemande(DemandeDto dto, MultipartFile multipartFile) {
        try {

            if (dto == null) {
                return null;
            }

            Demande demande = entityMapper.demandeDtoToDemande(dto);
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
            checkTypeDemande(dto, demande);

            if (dto.getPaysNaissanceCode() != null) {
                demande.setPaysNaissance(paysRepository.findById(dto.getPaysNaissanceCode()).orElse(null));

            }
            if (dto.getPaysNationaliteCode() != null) {
                demande.setPaysNationalite(paysRepository.findById(dto.getPaysNationaliteCode()).orElse(null));

            }
            if (dto.getPaysResidenceCode() != null) {
                demande.setPaysResidence(paysRepository.findById(dto.getPaysResidenceCode()).orElse(null));

            }

            if (dto.getPrefectureNaissanceId() != null) {
                demande.setPrefectureNaissance(prefectureRepository.findById(dto.getPrefectureNaissanceId()).orElse(null));
            }

            if (dto.getSituationMatrimonialeId() != null) {
                demande.setSituationMatrimoniale(situationMatrimonialeRepository.findById(dto.getSituationMatrimonialeId()).orElse(null));
            }
            if (dto.getSexeCode() != null) {
                demande.setSexe(sexeRepository.findById(dto.getSexeCode()).orElse(null));
            }
          /*  if (dto.getCategorieSocioProfessionnelleId()!=null){
                demande.setCategorieSocioProfessionnelle(categorieSocioProfessionnelleRepository.findById(dto.getCategorieSocioProfessionnelleId()).orElse(null));
            }*/
            if (dto.getProfessionId() != null) {
                demande.setProfession(professionRepository.findById(dto.getProfessionId()).orElse(null));
                demande.setEmploi(demande.getProfession().getLibelle());
                demande.setCategorieSocioProfessionnelle(demande.getProfession().getCategorieSocioProfessionnelle());
            }
            if (dto.getTypePieceId() != null) {
                demande.setTypePiece(typePieceRepository.findById(dto.getTypePieceId()).orElse(null));
            }
            double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);
            if (dto.getBureauPosteRetraitId() != null) {
                System.err.println(dto.getBureauPosteRetraitId());
                demande.setBureauPosteRetrait(pointRetraitRepository.findById(dto.getBureauPosteRetraitId()).orElse(null));
            } else {
                demande.setBureauPosteRetrait(null);
            }
            demande.setDateDemande(new Date());
            demande.setAnneeDemande(Year.now().getValue());
            demande.setProvenance(Provenance.ATD);
            demande.setDisponible(false);
            demande.setTraitee(false);
            demande.setNumeroDemande(compteurService.nextFormated(demande));
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
                demandeRepository.save(demande);
            } catch (IOException e) {
                return null;
            }
            UtilisateurCasier utilisateur = getCurrentUser();
            logService.save(String.format("Enregistrement de la demande %s ", demande.getNumeroDemande()),
                    new ObjectMapper().writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return entityMapper.demandeToDemandeDto(demande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ResponseObject update(DemandeSite dto) {
        DemandeDto demandeDto = new DemandeDto();
        ResponseObject responseObject = new ResponseObject();
        try {
            Demande demande = demandeRepository.findByNumeroDemande(dto.getNumeroDemande());
            if (demande == null) {
                responseObject.setResponseCode(NOT_FOUND);
                responseObject.setDescription("Aucune demande trouvée avec le numéro " + dto.getNumeroDemande());
                return responseObject;
            }
            if (dto.getPointRetrait() != null) {
                demande.setPointRetrait(pointRetraitRepository.findById(dto.getPointRetrait().getId())
                        .orElseThrow(() -> new Exception(String.format("Aucun point de retrait trouvé avec le id %s ", dto.getPointRetrait().getId()))));
                if (dto.getTracked() != null && dto.getTracked()) {
                    if (dto.getBureauPosteRetrait() == null) {
                        responseObject.setResponseCode(NOT_FOUND);
                        responseObject.setDescription("Aucun bureau de post n'est indiqué comme point de retrait");
                        return responseObject;

                    }
                    demande.setBureauPosteRetrait(pointRetraitRepository.findById(dto.getBureauPosteRetrait().getId())
                            .orElseThrow(() -> new Exception(String.format("Aucun bureau de poste trouvé avec le id %s ", dto.getBureauPosteRetrait().getId()))));
                }
                demande.setDisponible(false);
                demande.setValider(false);
                demande.setInvalidee(false);
                demande.setEtape3Valider(false);
                demande.setEtape2Valider(false);
                demande.setEtape1Valider(false);
                demande.setTraitee(false);
                Demande demandeSaved = demandeRepository.save(demande);
                DemandeSite demandeSite = demandeDto.convert(demandeSaved);
                responseObject.setResponseCode(SUCCESS);
                responseObject.setDemandeB3(demandeSite);
                return responseObject;
            } else {
                responseObject.setResponseCode(NOT_FOUND);
                responseObject.setDescription("Le point de retrait est obligatoire");
                return responseObject;

            }
        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseObject raccourciSearch(RaccourciRequest raccourciRequest) {
        try {
            ResponseObject responseObject = new ResponseObject();
            DemandeDto demandeDto = new DemandeDto();
            if (!raccourciRequest.getNames_provided()) {
                Demande demande = demandeRepository.findByNumeroDemandeAndTraiteeIsTrue(raccourciRequest.getNumero_demande());
                if (demande == null) {
                    responseObject.setResponseCode(NOT_FOUND);
                    responseObject.setDescription("Aucune demande aboutie trouvée avec le numéro " + raccourciRequest.getNumero_demande());
                    return responseObject;
                } else if (isMatchingPieceNumber(demande, raccourciRequest.getNumero_piece())) {
                    System.err.println(demande);
                    responseObject.setResponseCode(SUCCESS);
                    responseObject.setDemandeB3(demandeDto.convert(demande));
                    return responseObject;
                }
            }
            Demande demande = this.demandeRepository.findTopByNomAndPrenomAndDateNaissanceAndTraiteeIsTrue(raccourciRequest.getNom(), raccourciRequest.getPrenom(), raccourciRequest.getDate_naissance());
            if (demande == null) {
                responseObject.setResponseCode(NOT_FOUND);
                responseObject.setDescription("Aucune demande aboutie trouvée avec le nom " + raccourciRequest.getNom() + " prenom " + raccourciRequest.getPrenom() + " date naissance " + raccourciRequest.getDate_naissance());
                return responseObject;
            }
            responseObject.setResponseCode(SUCCESS);
            responseObject.setDemandeB3(demandeDto.convert(demande));
            return responseObject;
        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            return null;
        }
    }

    private boolean isMatchingPieceNumber(Demande demande, String numeroPiece) {
        return (demande.getNumeroActe() != null && demande.getNumeroActe().equalsIgnoreCase(numeroPiece))
                || (demande.getNumeroCarte() != null && demande.getNumeroCarte().equalsIgnoreCase(numeroPiece))
                || (demande.getNumeroPasseport() != null && demande.getNumeroPasseport().equalsIgnoreCase(numeroPiece))
                || (demande.getNumeroJugement() != null && demande.getNumeroJugement().equals(numeroPiece));
    }

    @Override
    public DemandeDto enregistrerDemandeSansPointreatrait(DemandeDto dto, MultipartFile multipartFile) {
        try {

            if (dto == null) {
                return null;
            }

            Demande demande = entityMapper.demandeDtoToDemande(dto);
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
            checkTypeDemande(dto, demande);

            if (dto.getPaysNaissanceCode() != null) {
                demande.setPaysNaissance(paysRepository.findById(dto.getPaysNaissanceCode()).orElse(null));

            }
            if (dto.getPaysNationaliteCode() != null) {
                demande.setPaysNationalite(paysRepository.findById(dto.getPaysNationaliteCode()).orElse(null));

            }
            if (dto.getPaysResidenceCode() != null) {
                demande.setPaysResidence(paysRepository.findById(dto.getPaysResidenceCode()).orElse(null));

            }

            if (dto.getPrefectureNaissanceId() != null) {
                demande.setPrefectureNaissance(prefectureRepository.findById(dto.getPrefectureNaissanceId()).orElse(null));
            }

            if (dto.getSituationMatrimonialeId() != null) {
                demande.setSituationMatrimoniale(situationMatrimonialeRepository.findById(dto.getSituationMatrimonialeId()).orElse(null));
            }
            if (dto.getSexeCode() != null) {
                demande.setSexe(sexeRepository.findById(dto.getSexeCode()).orElse(null));
            }
          /*  if (dto.getCategorieSocioProfessionnelleId()!=null){
                demande.setCategorieSocioProfessionnelle(categorieSocioProfessionnelleRepository.findById(dto.getCategorieSocioProfessionnelleId()).orElse(null));
            }*/
            if (dto.getProfessionId() != null) {
                demande.setProfession(professionRepository.findById(dto.getProfessionId()).orElse(null));
                demande.setEmploi(demande.getProfession().getLibelle());
                demande.setCategorieSocioProfessionnelle(demande.getProfession().getCategorieSocioProfessionnelle());
            }
            if (dto.getTypePieceId() != null) {
                demande.setTypePiece(typePieceRepository.findById(dto.getTypePieceId()).orElse(null));
            }
            double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);
            if (dto.getBureauPosteRetraitId() != null) {
                System.err.println(dto.getBureauPosteRetraitId());
                demande.setBureauPosteRetrait(pointRetraitRepository.findById(dto.getBureauPosteRetraitId()).orElse(null));
            } else {
                demande.setBureauPosteRetrait(null);
            }
            if (dto.getPointRetraitId() != null) {
                demande.setPointRetrait(pointRetraitRepository.findById(dto.getPointRetraitId()).orElse(null));

            }
            demande.setDateDemande(new Date());
            demande.setAnneeDemande(Year.now().getValue());
            demande.setProvenance(Provenance.ATD);
            demande.setDisponible(false);
            demande.setTraitee(false);
            demande.setNumeroDemande(compteurService.nextFormated(demande));
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
                //  CasierUtils.saveToFile(multipartFile.getInputStream(), uploadedFileLocation);
                demande.setNomFichier(nomFichier);
                demandeRepository.save(demande);
            } catch (Exception e) {
                return null;
            }
            return entityMapper.demandeToDemandeDto(demande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<?> validerUneDemande(DemandePMDto dto) {
        return null;
    }

    @Override
    public DemandeDto enregistrerDemande(DemandePMDto dto, MultipartFile multipartFile) {
        try {

            if (dto == null) {
                return null;
            }

            Demande demande = entityMapper.demandeDtoToDemande(dto);
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
            demande.setDateNaissance(new Date());
            demande.setNom(dto.getDenomination());
            demande.setPrenom(dto.getDenomination());
            demande.setNomPere("N/A");
            demande.setPrenomPere("N/A");
            demande.setNomMere("N/A");
            demande.setPrenomMere("N/A");
            checkTypeDemande(dto, demande);
            if (dto.getPaysNaissanceCode() != null) {
                demande.setPaysNaissance(paysRepository.findById(dto.getPaysNaissanceCode()).orElse(null));

            }
            if (dto.getPaysNationaliteCode() != null) {
                demande.setPaysNationalite(paysRepository.findById(dto.getPaysNationaliteCode()).orElse(null));

            }
            if (dto.getPaysResidenceCode() != null) {
                demande.setPaysResidence(paysRepository.findById(dto.getPaysResidenceCode()).orElse(null));

            }
            if (dto.getLocaliteNaissanceId() != null) {
                demande.setLocaliteNaissance(localiteRepository.findById(dto.getLocaliteNaissanceId()).orElse(null));
            }


            if (dto.getTypePieceId() != null) {
                demande.setTypePiece(typePieceRepository.findById(dto.getTypePieceId()).orElse(null));
            }
            double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);

            demande.setDateDemande(new Date());
            demande.setAnneeDemande(Year.now().getValue());
            demande.setProvenance(Provenance.ATD);
            demande.setDisponible(false);
            demande.setNumeroDemande(compteurService.nextFormated(demande));
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
                demandeRepository.save(demande);
            } catch (IOException e) {
                return null;
            }
            UtilisateurCasier utilisateur = getCurrentUser();
            logService.save(String.format("Enregistrement de la demande %s ", demande.getNumeroDemande()),
                    new ObjectMapper().writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return entityMapper.demandeToDemandeDto(demande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto enregistrerDemande(DemandeDto dto) {
        try {

            if (dto == null) {
                return null;
            }

            Demande demande = entityMapper.demandeDtoToDemande(dto);
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
            if (dto.getDemandeur() == null) {
                throw new Exception("Tout demandadeur du B1 ou du B2 doit être indiqué");
            }
            UtilisateurCasier utilisateurCasier = checkTypeDemande(dto, demande);

            if (dto.getPaysNaissanceCode() != null) {
                demande.setPaysNaissance(paysRepository.findById(dto.getPaysNaissanceCode()).orElse(null));

            }
            if (dto.getPaysNationaliteCode() != null) {
                demande.setPaysNationalite(paysRepository.findById(dto.getPaysNationaliteCode()).orElse(null));

            }
            if (dto.getPaysResidenceCode() != null) {
                demande.setPaysResidence(paysRepository.findById(dto.getPaysResidenceCode()).orElse(null));

            }
            if (dto.getPrefectureNaissanceId() != null) {
                demande.setPrefectureNaissance(prefectureRepository.findById(dto.getPrefectureNaissanceId()).orElse(null));
            }

            if (dto.getSituationMatrimonialeLibelle() != null) {
                demande.setSituationMatrimoniale(situationMatrimonialeRepository.findByLibelle(dto.getSituationMatrimonialeLibelle()).orElse(null));
            }
            if (dto.getSexeCode() != null) {
                demande.setSexe(sexeRepository.findById(dto.getSexeCode()).orElse(null));
            }
          /*  if (dto.getCategorieSocioProfessionnelleId()!=null){
                demande.setCategorieSocioProfessionnelle(categorieSocioProfessionnelleRepository.findById(dto.getCategorieSocioProfessionnelleId()).orElse(null));
            }*/
            if (dto.getProfessionId() != null) {
                demande.setProfession(professionRepository.findById(dto.getProfessionId()).orElse(null));
                demande.setEmploi(demande.getProfession().getLibelle());
                demande.setCategorieSocioProfessionnelle(demande.getProfession().getCategorieSocioProfessionnelle());
            }
            if (dto.getTypePieceId() != null) {
                demande.setTypePiece(typePieceRepository.findById(dto.getTypePieceId()).orElse(null));
            }
            double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);

            demande.setDateDemande(new Date());
            demande.setAnneeDemande(Year.now().getValue());
            demande.setProvenance(Provenance.ATD);
            demande.setDisponible(false);
            demande.setNumeroDemande(compteurService.nextFormated(demande));
            demande = demandeRepository.save(demande);
            DemandeDto demandeDto = entityMapper.demandeToDemandeDto(demande);
            demandeDto.setDemandeur(this.utilisateurToUserB2(utilisateurCasier));
            UtilisateurCasier utilisateur = getCurrentUser();
            logService.save(String.format("Enregistrement de la demande %s ", demande.getNumeroDemande()),
                    new ObjectMapper().writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return demandeDto;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto enregistrerDemande(DemandePMDto dto) {
        try {

            if (dto == null) {
                return null;
            }

            Demande demande = entityMapper.demandeDtoToDemande(dto);
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
            demande.setDateNaissance(new Date());
            demande.setNom(dto.getDenomination());
            demande.setPrenom(dto.getDenomination());
            demande.setNomPere("N/A");
            demande.setPrenomPere("N/A");
            demande.setNomMere("N/A");
            demande.setPrenomMere("N/A");
            checkTypeDemande(dto, demande);
            if (dto.getPaysNaissanceCode() != null) {
                demande.setPaysNaissance(paysRepository.findById(dto.getPaysNaissanceCode()).orElse(null));

            }
            if (dto.getDemandeur() == null) {
                throw new Exception("Tout demandadeur du B1 ou du B2 doit être indiqué");
            }
            UtilisateurCasier utilisateurCasier = checkTypeDemande(dto, demande);

            if (dto.getPaysNationaliteCode() != null) {
                demande.setPaysNationalite(paysRepository.findById(dto.getPaysNationaliteCode()).orElse(null));

            }
            if (dto.getPaysResidenceCode() != null) {
                demande.setPaysResidence(paysRepository.findById(dto.getPaysResidenceCode()).orElse(null));

            }
            if (dto.getLocaliteNaissanceId() != null) {
                demande.setLocaliteNaissance(localiteRepository.findById(dto.getLocaliteNaissanceId()).orElse(null));
            }


            if (dto.getTypePieceId() != null) {
                demande.setTypePiece(typePieceRepository.findById(dto.getTypePieceId()).orElse(null));
            }
            double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);

            demande.setDateDemande(new Date());
            demande.setAnneeDemande(Year.now().getValue());
            demande.setProvenance(Provenance.ATD);
            demande.setDisponible(false);
            demande.setNumeroDemande(compteurService.nextFormated(demande));
            demande = demandeRepository.save(demande);
            DemandeDto demandeDto = entityMapper.demandeToDemandeDto(demande);
            demandeDto.setDemandeur(this.utilisateurToUserB2(utilisateurCasier));
            return entityMapper.demandeToDemandeDto(demande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private UtilisateurCasier checkTypeDemande(DemandeDto dto, Demande demande) {
        try {
            if (dto.getTypeDemande().equalsIgnoreCase("B1") || dto.getTypeDemande().equalsIgnoreCase("B2")) {
                if (dto.getDemandeur() == null) {
                    throw new Exception("Tout demandadeur du B1 ou du B2 doit être indiqué");
                } else {
                    UtilisateurCasier utilisateur = null;
                    List<UserB2> occurences = userB2Repository.findByCni(dto.getDemandeur().getCni());
                    if (occurences.isEmpty() || occurences == null) {
                        utilisateur = createB2AndUtilisateurCasier(dto.getDemandeur());
                    } else {
                        utilisateur = utilisateurCasierRepository.getFromUsername(dto.getDemandeur().getCni()).orElse(null);
                    }
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

    private UtilisateurCasier checkTypeDemande(DemandePMDto dto, Demande demande) {
        try {
            if (dto.getTypeDemande().equalsIgnoreCase("B1") || dto.getTypeDemande().equalsIgnoreCase("B2")) {
                if (dto.getDemandeur() == null) {
                    throw new Exception("Tout demandadeur du B1 ou du B2 doit être indiqué");
                } else {
                    UtilisateurCasier utilisateur = null;
                    List<UserB2> occurences = userB2Repository.findByCni(dto.getDemandeur().getCni());
                    if (occurences.isEmpty() || occurences == null) {
                        utilisateur = createB2AndUtilisateurCasier(dto.getDemandeur());
                    } else {
                        utilisateur = utilisateurCasierRepository.getFromUsername(dto.getDemandeur().getCni()).orElse(null);
                    }
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

    private UtilisateurCasier createB2AndUtilisateurCasier(UserB2Dto demandeur) {
        try {
            UserB2 userB2 = entityMapper.userB2DtoToUserB2(demandeur);
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
            User coreUser = User.builder().username(userB2.getCni())
                    .active(false)
                    .changePassword(false)
                    .password(userB2.getCni())
                    .build();
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
                        categorieDemandeurB2 = CategorieDemandeurB2.builder()
                                .libelle(userB2.getAutreCategorieDemandeur()).build();
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
                        serviceDemandeurB2 = ServiceDemandeurB2.builder()
                                .entiteDemandeurB2(entiteDemandeurB2)
                                .libelle(userB2.getAutreServiceDemandeur())
                                .build();
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

    @Override
    public DemandeDto payerDemande(DemandeDto demandeRequest) {
        return null;
    }

    @Override
    public List<DemandeDto> getListeByTypeByPeriode() {
        return demandeRepository.getListe().stream().map(d -> entityMapper.demandeToDemandeDto(d)).collect(Collectors.toList());
    }

    @Override
    public DemandeDto recupererDemande(String numeroDemande, String numeroPiece) {
        return null;
    }

    @Override
    public DemandeDto findByNumero(String numero) {
        Demande demande = demandeRepository.findByNumeroDemande(numero);
        if (demande == null) {
            return null;
        }
        return entityMapper.demandeToDemandeDto(demande);
    }

    @Override
    public DemandeDto findByNumeroAndTraiteIsTrue(String numero) {
        Demande demande = demandeRepository.findByNumeroDemandeAndTraiteeIsTrue(numero);
        if (demande == null) {
            return null;
        }
        return entityMapper.demandeToDemandeDto(demande);
    }

    @Override
    public DemandeDto findById(Long id) {
        Demande demande = demandeRepository.findById(id).orElse(null);
        if (demande == null) {
            return null;
        }
        return entityMapper.demandeToDemandeDto(demande);
    }

    @Override
    public DemandeDto modifierDemande(DemandeDto dto, MultipartFile fichier) {
        try {

            if (dto == null) {
                return null;
            }

            Demande demande = entityMapper.demandeDtoToDemande(dto);
            Demande oldDemande = demandeRepository.findByNumeroDemande(dto.getNumeroDemande());
            Demande logDemande = oldDemande;
            UtilisateurCasier utilisateurCasier = getCurrentUser();
            if (oldDemande != null) {
                if (dto.getTypeDemande().equalsIgnoreCase("B1") || dto.getTypeDemande().equalsIgnoreCase("B2")) {
                    if (dto.getUsername() == null) {
                        throw new Exception("Tout demandadeur du B1 ou du B2 doit être indiqué");
                    } else {
                        UtilisateurCasier utilisateur = utilisateurCasierRepository.getFromUsername(dto.getUsername()).orElse(null);
                        if (utilisateur == null) {
                            throw new Exception("Aucun demandeur n'est enregistré avec le nom: " + dto.getUsername());
                        } else {
                            demande.setDemandeurB1(utilisateur);
                        }
                    }
                }
                if (dto.getPaysNaissanceCode() != null) {
                    demande.setPaysNaissance(paysRepository.findById(dto.getPaysNaissanceCode()).orElse(null));

                }
                if (dto.getPaysNationaliteCode() != null) {
                    demande.setPaysNationalite(paysRepository.findById(dto.getPaysNationaliteCode()).orElse(null));

                }
                if (dto.getPaysResidenceCode() != null) {
                    demande.setPaysResidence(paysRepository.findById(dto.getPaysResidenceCode()).orElse(null));

                }

                if (dto.getPrefectureNaissanceId() != null) {
                    demande.setPrefectureNaissance(prefectureRepository.findById(dto.getPrefectureNaissanceId()).orElse(null));
                }

                if (dto.getSituationMatrimonialeLibelle() != null) {
                    demande.setSituationMatrimoniale(situationMatrimonialeRepository.findByLibelle(dto.getSituationMatrimonialeLibelle()).orElse(null));
                }
                if (dto.getSexeCode() != null) {
                    demande.setSexe(sexeRepository.findById(dto.getSexeCode()).orElse(null));
                }
          /*  if (dto.getCategorieSocioProfessionnelleId()!=null){
                demande.setCategorieSocioProfessionnelle(categorieSocioProfessionnelleRepository.findById(dto.getCategorieSocioProfessionnelleId()).orElse(null));
            }*/
                if (dto.getProfessionId() != null) {
                    demande.setProfession(professionRepository.findById(dto.getProfessionId()).orElse(null));
                    demande.setEmploi(demande.getProfession().getLibelle());
                    demande.setCategorieSocioProfessionnelle(demande.getProfession().getCategorieSocioProfessionnelle());
                }
                if (dto.getTypePieceId() != null) {
                    demande.setTypePiece(typePieceRepository.findById(dto.getTypePieceId()).orElse(null));
                }
                double prixDemande = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX);

                BeanUtils.copyProperties(demande, oldDemande, new String[]{"id", "payement", "nombreCopie"});
                oldDemande.setDateDemande(new Date());
                oldDemande.setValider(null);
                oldDemande.setEtape1Valider(false);
                oldDemande.setEtape2Valider(false);
                oldDemande.setEtape3Valider(false);
                oldDemande = demandeRepository.save(oldDemande);
                String UPLOAD_FOLDER = variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER);
                try {
                    CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER);
                } catch (SecurityException se) {
                    return null;
                }
                String uploadedFileLocation = UPLOAD_FOLDER + oldDemande.getId() + "." + FilenameUtils.getExtension(fichier.getOriginalFilename());
                try {
                    CasierUtils.saveToFile(fichier.getInputStream(), uploadedFileLocation);
                } catch (IOException e) {
                    return null;
                }
                logService.save(String.format("Modification de la demande %s ", logDemande.getNumeroDemande()),
                        new ObjectMapper().writeValueAsString(logDemande), new ObjectMapper().writeValueAsString(demande),
                        utilisateurCasier.getPersonneInfo().getUser(),
                        demande
                );
                return entityMapper.demandeToDemandeDto(oldDemande);
            } else {
                throw new Exception("Aucune demande ne correspond");
            }
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }


    @Override
    public DemandeDto modifierFichierDemande(DemandeDto dto, MultipartFile multipartFile) {
        try {

            if (dto == null) {
                return null;
            }

            Demande demande = demandeRepository.findByNumeroDemande(dto.getNumeroDemande());
            if (demande != null) {

                String UPLOAD_FOLDER = variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER);
                try {
                    CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER);
                } catch (SecurityException se) {
                    se.printStackTrace();
                    return null;
                }
                String nomFichier = demande.getId() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                String uploadedFileLocation = UPLOAD_FOLDER + nomFichier;
                try {
                    MetaData metaData = MetaData.builder().folder(null).submittedFileName(String.valueOf(demande.getId())).racine(UPLOAD_FOLDER).build();
                    CasierUtils.transfert(multipartFile, metaData, this.ftpApiBaseUrl + "storages/update");
                    //  CasierUtils.saveToFile(multipartFile.getInputStream(), uploadedFileLocation);
                    demande.setNomFichier(nomFichier);
                    demande.setInvalidee(false);
                    demande.setEtape1Valider(false);
                    demande.setEtape2Valider(false);
                    demande.setEtape3Valider(false);
                    demande.setRetirer(false);
                    demande.setValider(false);
                    demande.setInvalidee(false);
                    demande.setSignee(false);
                    demande.setDisponible(false);
                    UtilisateurCasier utilisateurCasier = getCurrentUser();
                    logService.save(String.format("Modification de pièce de la demande %s ", demande.getNumeroDemande()),
                            new ObjectMapper().writeValueAsString(demande), null,
                            utilisateurCasier.getPersonneInfo().getUser(),
                            demande
                    );
                    demandeRepository.save(demande);
                } catch (IOException e) {
                    // e.printStackTrace();
                    return null;
                }
                return entityMapper.demandeToDemandeDto(demande);
            } else {
                throw new Exception("Aucune demande ne correspond");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeDto> findByTypeAndValideeAndDisponible(String type, Boolean validee, Boolean imprimee) {
        try {
            List<Demande> demandes = demandeRepository.findByValiderAndDisponibleAndTypeDemandeAndPointRetraitIn(validee, imprimee, type, getUserPointRetrait());
            if (demandes == null || demandes.isEmpty()) {
                return null;
            }
            return demandes.stream().map(demande -> entityMapper.demandeToDemandeDto(demande)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeDto> findByType(String type) {
        return null;
    }

    @Override
    public List<DemandeDto> findByTypeAndDisponible(String type, Boolean imprimee) {
        try {

            List<Demande> demandes = demandeRepository.findByValiderAndTypeDemandeAndPointRetraitInOrderByIdAsc(imprimee, type, getUserPointRetrait());
            if (demandes == null || demandes.isEmpty()) {
                return null;
            }
            return demandes.stream().map(demande -> entityMapper.demandeToDemandeDto(demande)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeList> findByTypeAndDisponibleAndInvalideeAndPeriode(String type, Boolean imprimee, Boolean invalider, Date debut, Date fin) {
        try {

            List<DemandeList> demandes = new ArrayList<>();
            switch (type) {
                case "CJE":
                case "ANC":
                case "M3":
                case "B3": {
                    demandes = demandeRepository.findAllByTypeDemande(type, imprimee, debut, fin, getUserPointRetraitV1(), invalider);

                    break;
                }
                case "B2":
                case "B1": {
                    demandes = demandeRepository.findAllByTypeDemandeB2orB1(type, imprimee, debut, fin, getUserPointRetraitV1(), invalider);
                    break;
                }
                default: {
                    break;
                }

            }
            return demandes;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeList> findByTypeAndDisponibleAndInvalideeAndPeriodePoste(String type, Boolean imprimee, Boolean invalider, Date debut, Date fin) {
        try {

            List<DemandeList> demandes = new ArrayList<>();
            switch (type) {
                case "CJE":
                case "ANC":
                case "M3":
                case "B3": {
                    demandes = demandeRepository.findAllByTypeDemandePoste(type, imprimee, debut, fin, getUserPointRetraitV1(), invalider);

                    break;
                }
                case "B2":
                case "B1": {
                    demandes = demandeRepository.findAllByTypeDemandeB2orB1(type, imprimee, debut, fin, getUserPointRetraitV1(), invalider);
                    break;
                }
                default: {
                    break;
                }

            }
            return demandes;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto invalider(DemandeDto dto) {
        try {
            Demande oldDemande = demandeRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Aucune demande trouvé avec l'identifiant indiqué"));
            oldDemande.setMotifInvalidation(dto.getMotifInvalidation());
            oldDemande.setInvalidee(true);
            oldDemande.setDisponible(false);
            oldDemande.setValider(false);
            oldDemande.setEtape3Valider(false);
            oldDemande.setEtape2Valider(false);
            oldDemande.setEtape1Valider(false);
            oldDemande.setRetirer(false);
            oldDemande.setTraitee(false);
            oldDemande.setCouleur(null);
            oldDemande.setSignee(false);
            UtilisateurCasier utilisateurCasier = getCurrentUser();
            oldDemande.setUtilisateurDerniereModification(utilisateurCasier);
            oldDemande.setUtilisateurValidateur(utilisateurCasier);
            oldDemande = demandeRepository.save(oldDemande);
            // TODO: sent notification to ATD
            ModelSMS sms = null;
            String message = null;

            sms = modelSMSRepository.findByNom("DMDREJ").orElse(null);
            if (sms != null) {
                message = oldDemande.getMotifInvalidation();
                message = message.replace("[ND]", oldDemande.getNumeroDemande());
                message = message.replace("[PR]", oldDemande.getPointRetrait().getLibelle());
                String corpsNumero = sms.getContenu().replace("[ND]", oldDemande.getNumeroDemande());
                String corpsMotif = corpsNumero.replace("[MOTIF]", message);
                FeedbackModel feedbackModel = FeedbackModel
                        .builder()
                        .message(corpsMotif)
                        .feedbackTaskId(oldDemande.getFeedbackTaskId())
                        .order(oldDemande.getOrder())
                        .record(oldDemande.getRecord())
                        .step(oldDemande.getStep())
                        .process(oldDemande.getProcess())
                        .feedbackTaskId(oldDemande.getFeedbackTaskId())
                        .title("CORRECTION")
                        .build();
                logger.error("feedbackModel ", feedbackModel);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

                logService.save(String.format("Rejet de la demande %s ", oldDemande.getNumeroDemande()),
                        objectMapper.writeValueAsString(oldDemande), null,
                        utilisateurCasier.getPersonneInfo().getUser(),
                        oldDemande
                );
                serviceEnvoyeur.envoyerFeadBack(feedbackModel);
            }
            return entityMapper.demandeToDemandeDto(oldDemande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto invalider(Long id) {
        try {
            Demande oldDemande = demandeRepository.findById(id).orElseThrow(() -> new Exception("Aucune demande trouvé avec l'identifiant indiqué"));
            //   oldDemande.setMotifInvalidation(dto.getMotifInvalidation());
            oldDemande.setInvalidee(true);
            oldDemande.setDisponible(false);
            oldDemande.setValider(false);
            oldDemande.setEtape3Valider(false);
            oldDemande.setEtape2Valider(false);
            oldDemande.setEtape1Valider(false);
            oldDemande.setRetirer(false);
            oldDemande.setTraitee(false);
            oldDemande.setCouleur(null);
            oldDemande.setSignee(false);
            UtilisateurCasier utilisateurCasier = getCurrentUser();
            oldDemande.setUtilisateurDerniereModification(utilisateurCasier);
            oldDemande.setUtilisateurValidateur(utilisateurCasier);
            oldDemande = demandeRepository.save(oldDemande);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            logService.save(String.format("Rejet de la demande %s ", oldDemande.getNumeroDemande()),
                    objectMapper.writeValueAsString(oldDemande), null,
                    utilisateurCasier.getPersonneInfo().getUser(),
                    oldDemande
            );
            return entityMapper.demandeToDemandeDto(oldDemande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto valider(Long id) {
        try {
            Demande oldDemande = demandeRepository.findById(id).orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec l'identifiant %s ", id)));
            oldDemande.setInvalidee(false);
            oldDemande.setValider(true);
            oldDemande.setEtape3Valider(true);
            oldDemande.setEtape2Valider(true);
            oldDemande.setEtape1Valider(true);
            oldDemande.setRetirer(false);
            oldDemande.setRetirer(false);
            oldDemande.setDateValidation(new Date());
            UtilisateurCasier utilisateurCasier = getCurrentUser();
            oldDemande.setNumeroTraitement(String.valueOf(compteurTraitementService.next(oldDemande)));
            oldDemande.setUtilisateurDerniereModification(utilisateurCasier);
            oldDemande.setUtilisateurValidateur(utilisateurCasier);
            oldDemande = demandeRepository.save(oldDemande);
            // TODO: sent notification to ATD
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Validation de la demande %s ", oldDemande.getNumeroDemande()),
                    mapper.writeValueAsString(oldDemande), null,
                    utilisateurCasier.getPersonneInfo().getUser(),
                    oldDemande
            );

            return entityMapper.demandeToDemandeDto(oldDemande);

        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }


    @Override
    public byte[] imprimer(Long id) {
        try {
            Bulletin bulletin = new Bulletin();
            Demande demande = demandeRepository.findById(id).orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec l'identifiant %s ", id)));
            // TODO: check for condamnations from FNC
            DemandeFnc demandeFnc = entityMapper.demandeToDemandeFNC(demande);
           ResponseEntity response = fncService.getCondamnations(demandeFnc);
            Collection<Condamnation> condamnations = null;
            if (response.getStatusCode() == HttpStatus.OK) {

                Casier casier = (Casier) response.getBody();
                if (casier.getCondamnations() != null) {
                    condamnations = casier.getCondamnations().stream().map(c -> {
                        Set<Infraction> infractions = getInfraction(casier, c);
                        return Condamnation.builder()
                                .dateCondamnation(c.getDatejugement())
                                .dateMandatDepot(c.getDatejugement())
                                .infractions(infractions)
                                .cours(c.getJuridiction().getLibellecourt())
                                .quantumPeine(Integer.valueOf(c.getPeine().getLibelle()))
                                .build();
                    }).collect(Collectors.toList());
                }
            }

            InputStream inputStream = null;
            String typeDemande = demande.getTypeDemande();
            switch (typeDemande) {
                case "B1": {
                    inputStream = bulletinGenerator.generateB1(demande, condamnations);
                    break;
                }
                case "B2": {
                    inputStream = bulletinGenerator.generateB2(demande, condamnations);
                    break;
                }
                case "B3": {
                    inputStream = bulletinGenerator.generateB3(demande, condamnations);
                    break;
                }
                case "CJE":
                case "ANC": {
                    inputStream = bulletinGenerator.generateCJE(demande, condamnations);
                    break;
                }
                case "M3": {
                    inputStream = bulletinGenerator.generateB3Morale(demande, condamnations);
                    break;
                }


            }

            InputStream inputStream1 = inputStream;
            //byte[] data =IOUtils.toByteArray(inputStream);
            String UPLOAD_FOLDER = variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER);
            try {
                CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER);
            } catch (SecurityException se) {
                return null;
            }
            String nomFichier = demande.getId() + ".pdf";
            String relativeBaseFolder = "bulletins/" + demande.getTypeDemande() + "/" + CasierUtils.getAnneeDemande(demande);
            String uploadedFileLocation = UPLOAD_FOLDER + relativeBaseFolder;
            String path = uploadedFileLocation + "/" + nomFichier;
            String relativePath = relativeBaseFolder + "/" + nomFichier;
            try {
                //  CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER + "bulletins/" + demande.getTypeDemande() + "/" + CasierUtils.getAnneeDemande(demande));
                CasierUtils.createFolders(uploadedFileLocation);
                CasierUtils.saveToFile(inputStream, path);
                //  check if file is transfered and is valide.
                File tempFile = new File(path);
                if (tempFile.exists()) {
                    if (tempFile.length() == 0) {
                        return null;
                    }
                } else {
                    return null;
                }
                demande.setNumeroTraitement(compteurTraitementService.next(demande) + "");
                //  update du chemin vers le buletin généré
                UtilisateurCasier utilisateurCasier = getCurrentUser();
                demande.setUtilisateurDerniereModification(utilisateurCasier);
                bulletin.setRelativePath(demande.getPathBulletin());
                demandeRepository.save(demande);
                File file = new File(path);
                InputStream stream = new FileInputStream(file);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
                mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                logService.save(String.format("Impression de la demande %s ", demande.getNumeroDemande()),
                        mapper.writeValueAsString(demande), null,
                        utilisateurCasier.getPersonneInfo().getUser(),
                        demande
                );
                return IOUtils.toByteArray(stream);
                //  demande.setNomFichier(nomFichier);
                //  demandeRepository.save(demande);
            } catch (IOException e) {
                logger.error("Erreur interne", e);
                return null;
            }
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public DemandeDto imprimer1(Long id) {
        try {
            Demande demande = demandeRepository.findById(id).orElseThrow(
                    () -> new Exception(String.format("Aucune demande trouvée avec l'identifiant %s ", id)));
            // TODO: check for condamnations from FNC
            DemandeFnc demandeFnc = entityMapper.demandeToDemandeFNC(demande);
            ResponseEntity response = fncService.getCondamnations(demandeFnc);
            Collection<Condamnation> condamnations = null;
            System.err.println("response.getStatusCode(= " + response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.OK) {

                Casier casier = (Casier) response.getBody();
                if (casier.getCondamnations() != null) {
                    condamnations = casier.getCondamnations().stream().map(c -> {
                        Set<Infraction> infractions = getInfraction(casier, c);
                        return Condamnation.builder()
                                .dateCondamnation(c.getDatejugement())
                                .dateMandatDepot(c.getDatejugement())
                                .infractions(infractions)
                                .cours(c.getJuridiction().getLibellecourt())
                                .build();
                    }).collect(Collectors.toList());
                }
            }
            InputStream inputStream = bulletinGenerator.generateB3(demande, condamnations);
            String UPLOAD_FOLDER = variableService.getValue(CasierConstants.VAR_PIECE_JOINTE_DOSSIER);
            try {
                CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER);
            } catch (SecurityException se) {
                return null;
            }
            String nomFichier = demande.getId() + ".pdf";
            String relativeBaseFolder = "bulletins/" + demande.getTypeDemande() + "/" + CasierUtils.getAnneeDemande(demande);
            String uploadedFileLocation = UPLOAD_FOLDER + relativeBaseFolder;
            String path = uploadedFileLocation + "/" + nomFichier;
            String relativePath = relativeBaseFolder + "/" + nomFichier;
            try {
                //  CasierUtils.createFolderIfNotExists(UPLOAD_FOLDER + "bulletins/" + demande.getTypeDemande() + "/" + CasierUtils.getAnneeDemande(demande));
                CasierUtils.createFolders(uploadedFileLocation);
                CasierUtils.saveToFile(inputStream, path);
                //  check if file is transfered and is valide.
                File tempFile = new File(path);
                if (tempFile.exists()) {
                    if (tempFile.length() == 0) {
                        return null;
                    }
                } else {
                    return null;
                }
                demande.setNumeroTraitement(compteurTraitementService.next(demande) + "");
                //  update du chemin vers le buletin généré
                demande.setPathBulletin(this.fileServerPath + relativePath);
                demandeRepository.save(demande);
            } catch (IOException e) {
                return null;
            }
            return entityMapper.demandeToDemandeDto(demande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }


    private Set<Infraction> getInfraction(Casier c, CondamnationCasier cc) {
        try {
            Set<Infraction> infractions = null;
            if (c != null && cc != null) {
                infractions = new HashSet<>();
                for (PeineInfractionDto p : c.getInfractions()) {
                    if (p.getPeine().equals(cc.getPeine())) {
                        Infraction infraction = Infraction.builder()
                                .dateInfraction(p.getDateinfraction())
                                .libelle(p.getInfraction().getLibelle())
                                .build();
                        infractions.add(infraction);
                    }
                }

            }
            return infractions;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeDto> findByTypeAndSigneeAndPeriode(String type, Boolean signee, Date debut, Date fin) {
        try {


            List<Demande> demandes = demandeRepository.findByTypeDemandeEqualsIgnoreCaseAndPayement_DatePayementGreaterThanEqualAndPayement_DatePayementLessThanEqualAndPayement_ReglerAndPointRetraitInAndSigneeAndInvalideeOrderByIdAsc(type, debut, fin, true, getUserPointRetrait(), signee, false);

            if (demandes == null || demandes.isEmpty()) {
                return null;
            }
            return demandes.stream().map(demande -> entityMapper.demandeToDemandeDto(demande)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ResumePaiement resumePaiement(Date debut, Date fin, Long centreId) {
        try {
            ResumePaiement resumePaiement = demandeRepository.resumePaiement(debut, fin, centreId);
            if (resumePaiement == null) {
                return null;
            }
            return resumePaiement;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }


    @Override
    public List<DetailExtraitDemande> detailExtraitDemande(Date debut, Date fin) {
        try {
            List<DetailExtraitDemande> detailExtraitDemandes = demandeRepository.detailExtraitDemande(debut, fin);
            if (detailExtraitDemandes == null || detailExtraitDemandes.isEmpty()) {
                return null;
            }
            return detailExtraitDemandes;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }


    @Override
    public List<DemandeDto> findByTypeAndSigneeAndPeriodeForAdmin(String type, Boolean signee, Date debut, Date fin, Long IdPointRetrait) {
        try {
            PointRetrait pointRetrait = this.pointRetraitRepository.findById(IdPointRetrait).orElse(null);
            System.out.println("demandes=" + pointRetrait);
            List<PointRetrait> pointRetraits = new ArrayList<>();
            pointRetraits.add(pointRetrait);
            List<Demande> demandes = demandeRepository.findByTypeDemandeEqualsIgnoreCaseAndPayement_DatePayementGreaterThanEqualAndPayement_DatePayementLessThanEqualAndPayement_ReglerAndPointRetraitInAndSigneeAndInvalideeOrderByIdAsc(type, debut, fin, true, getUserPointRetrait(), signee, false);


            if (demandes == null || demandes.isEmpty()) {
                return null;
            }
            return demandes.stream().map(demande -> entityMapper.demandeToDemandeDto(demande)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeList> findByTypeAndSigneeAndPeriodeV1(String type, Boolean signee, Date debut, Date fin) {
        try {
            List<DemandeList> demandes = new ArrayList<>();
            switch (type) {
                case "CJE":
                case "ANC":
                case "B3":
                case "M3": {
                    demandes = demandeRepository.findDemandes(type, debut, fin, getUserPointRetraitV1(), signee, false);
                    break;
                }
                case "B2":
                case "B1":
                case "M2":
                case "M1": {
                    demandes = demandeRepository.findDemandesB1OrB2(type, debut, fin, getUserPointRetraitV1(), signee, false);
                    break;
                }
                default: {
                    break;
                }

            }

            return demandes;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeList> findByTypeAndSigneeAndPeriodeV1Poste(String type, Boolean signee, Date debut, Date fin) {
        try {
            List<DemandeList> demandes = new ArrayList<>();
            switch (type) {
                case "CJE":
                case "ANC":
                case "B3":
                case "M3": {
                    demandes = demandeRepository.findDemandesPoste(type, debut, fin, getUserPointRetraitV1(), signee, false);
                    break;
                }
                case "B2":
                case "B1":
                case "M2":
                case "M1": {
                    demandes = demandeRepository.findDemandesB1OrB2(type, debut, fin, getUserPointRetraitV1(), signee, false);
                    break;
                }
                default: {
                    break;
                }

            }

            return demandes;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeList> findAllByTypeAndSigneeAndPeriodeV1(String type, Date debut, Date fin, Long pointRetrait) {
        try {
            List<DemandeList> demandes = demandeRepository.findDemandes(type, debut, fin, pointRetrait);
            return demandes;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeDto> rechercher(String nom, String prenom) {
        try {

            List<Demande> demandes = demandeRepository.findByNomEqualsIgnoreCaseAndPrenomEqualsIgnoreCaseOrderByIdAsc(nom, prenom);
            if (demandes == null || demandes.isEmpty()) {
                return null;
            }
            return demandes.stream().map(demande -> entityMapper.demandeToDemandeDto(demande)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeDto> rechercher(String nom, String prenom, Date debut, Date fin) {
        try {

            List<Demande> demandes = demandeRepository.loadSearcheDemandes(getLikeForme(nom), getLikeForme(prenom), debut, fin);
            if (demandes == null || demandes.isEmpty()) {
                return null;
            }
            return demandes.stream().map(demande -> entityMapper.demandeToDemandeDto(demande)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private String getLikeForme(String s) {
        if (s == null) {
            return "";
        }
        return "%" + s.toLowerCase() + "%";
    }

    @Override
    public DemandeDto rechercheParNumeroDemande(DemandeDto dto) {
        try {
            return entityMapper.demandeToDemandeDto(demandeRepository.findByNumeroDemandeOrRecord(dto.getNumeroDemande(), dto.getNumeroDemande()));
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public Casier findBCondanations(Long id) {
        try {
            Demande demande = demandeRepository.findById(id).orElseThrow(() -> new Exception(String.format("Aucune demande de casier trouvée avec l'identifiant %s ", id)));
            DemandeFnc demandeFnc = entityMapper.demandeToDemandeFNC(demande);
            ResponseEntity responseEntity = fncService.getCondamnations(demandeFnc);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Casier casier = (Casier) responseEntity.getBody();
                casier = getCondamnations(casier, demande);
                return casier;
            } else {
                logger.error("Erreur d'impression " + responseEntity);
                return null;
            }
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Casier getCondamnations(Casier casier) {
        if (casier.getCondamnations() != null) {
            Collection<Condamnation> condamnations = casier.getCondamnations().stream().map(c -> {
                Set<Infraction> infractions = getInfraction(casier, c);
                return Condamnation.builder()
                        .dateCondamnation(c.getDatejugement())
                        .dateMandatDepot(c.getDatejugement())
                        .infractions(infractions)
                        .cours(c.getJuridiction().getLibellecourt())
                        .quantumPeine(Integer.valueOf(c.getPeine().getLibelle()))
                        .build();
            }).collect(Collectors.toList());
            List<CondamnationModel> condamnationModels = condamnations.stream().map(entityMapper::condamnationToCondamnationModel).collect(Collectors.toList());
            casier.setModeles(condamnationModels);
        }
        return casier;
    }

    @Override
    public DemandeDto traiter(DemandeDto dto) {
        try {
            Demande demande = demandeRepository.findByNumeroDemande(dto.getNumeroDemande());
            if (demande == null) {
                logger.error("Erreur interne", String.format("Aucune demande trouvée avec le numéro %s ", dto.getNumeroDemande()));
                throw new Exception(String.format("Aucune demande trouvée avec le numéro %s ", dto.getNumeroDemande()));
            }
            demande.setTraitee(true);
            demande.setUtilisateurTraiteur(getCurrentUser());
            demande.setSignee(false);
            demande.setRetirer(false);
            demande.setCouleur(dto.getCouleur());
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Traitement de la demande %s ", demande.getNumeroDemande()),
                    mapper.writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return entityMapper.demandeToDemandeDto(demandeRepository.save(demande));
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }

    }

    @Override
    public DemandeDto signer(DemandeDto dto) {
        try {
            Demande demande = demandeRepository.findByNumeroDemande(dto.getNumeroDemande());
            if (demande == null) {
                logger.error("Erreur interne", String.format("Aucune demande trouvée avec le numéro %s ", dto.getNumeroDemande()));
                throw new Exception(String.format("Aucune demande trouvée avec le numéro %s ", dto.getNumeroDemande()));
            }
            demande.setTraitee(true);
            demande.setDateDisponibilite(new Date());
            demande.setUtilisateurTraiteur(getCurrentUser());
            demande.setSignee(true);
            demande.setRetirer(false);
            demande.setDisponible(true);
            demandeRepository.save(demande);
            // TODO send SMS to demandeur
            ModelSMS sms = null;
            String message = null;
            //&& demande.getDeliveryMode() != null
            if (demande.getBureauPosteRetrait() != null) {
                sms = modelSMSRepository.findByNom("DMDSIGNPOSTE").orElse(null);
                message = sms.getContenu();
            } else {
                sms = modelSMSRepository.findByNom("DMDSIGNSIMPLE").orElse(null);
                if (sms != null) {
                    message = sms.getContenu();
                    message = message.replace("[ND]", demande.getRecord());
                    message = message.replace("[PR]", demande.getPointRetrait().getLibelle());
                }
            }

            if (sms != null) {
                FeedbackModel feedbackModel = FeedbackModel
                        .builder()
                        .message(message)
                        .feedbackTaskId(demande.getFeedbackTaskId())
                        .order(demande.getOrder())
                        .record(demande.getRecord())
                        .step(demande.getStep())
                        .process(demande.getProcess())
                        .feedbackTaskId(demande.getFeedbackTaskId())
                        .title("DISPONIBLE")
                        .build();
                logger.error("feedbackModel ", feedbackModel);
                //  System.err.println(new ObjectMapper().writeValueAsString(feedbackModel));
                if (demande.getFeedbackTaskId() != null) {
                    serviceEnvoyeur.envoyerFeadBack(feedbackModel);
                }

            }
            // TODO notify La poste if point retrait is poste
            //  demande.getDeliveryMode() != null &&
            if (demande.getBureauPosteRetrait() != null) {
                laPosteApi.sendEtabliRequest(demande);
            }

            this.serviceEnvoyeur.notifier(demande, message, "DISPONIBLE");
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Modification de la demande %s ", demande.getNumeroDemande()),
                    mapper.writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return entityMapper.demandeToDemandeDto(demandeRepository.save(demande));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto retirer(DemandeDto dto) {
        try {
            Demande demande = demandeRepository.findByNumeroDemande(dto.getNumeroDemande());
            if (demande == null) {
                logger.error("Erreur interne", String.format("Aucune demande trouvée avec le numéro %s ", dto.getNumeroDemande()));
                throw new Exception(String.format("Aucune demande trouvée avec le numéro %s ", dto.getNumeroDemande()));
            }
            demande.setUtilisateurTraiteur(getCurrentUser());
            demande.setSignee(true);
            demande.setRetirer(true);
            demande.setDateRetrait(new Date());
            // TODO send SMS to demandeur
            ModelSMS sms = null;
            String message = null;
            if (demande.getBureauPosteRetrait() != null && demande.getDeliveryMode() != null) {
                sms = modelSMSRepository.findByNom("DMDRETRPOSTE").orElse(null);
                message = sms.getContenu();
            } else {
                sms = modelSMSRepository.findByNom("DMDRET").orElse(null);
                message = sms.getContenu();
            }
            if (sms != null) {
                FeedbackModel feedbackModel = FeedbackModel
                        .builder()
                        .message(message)
                        .feedbackTaskId(demande.getFeedbackTaskId())
                        .order(demande.getOrder())
                        .record(demande.getRecord())
                        .step(demande.getStep())
                        .process(demande.getProcess())
                        .feedbackTaskId(demande.getFeedbackTaskId())
                        .title("RETRAIT")
                        .build();
                serviceEnvoyeur.envoyerFeadBack(feedbackModel);
            }
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            logService.save(String.format("Retrait de la demande  %s ", demande.getNumeroDemande()),
                    mapper.writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return entityMapper.demandeToDemandeDto(demandeRepository.save(demande));
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DashboardModel> getDashbord(Date debut, Date fin) {
        try {
            return demandeRepository.getTableauBord(debut, fin);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<Integer> getAnnee() {
        try {
            return demandeRepository.getAnnee();
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<ExtraitB3> getStatistiqueB3Montant(Date debut, Date fin) {
        try {
            return demandeRepository.getStatistiqueB3Montant(debut, fin);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto update(Long id, DemandeDto demandeDto) {


        try {
            DemandeDto demandeInvalider = this.invalider(id);
            if (demandeInvalider == null) {
                return null;
            }

            Demande demande = this.demandeRepository.findById(id).orElse(null);
            demande.setNom(demandeDto.getNom());
            demande.setPrenom(demandeDto.getPrenom());
            demande.setDateNaissance(demandeDto.getDateNaissance());
            demande.setLieuNaissance(demandeDto.getLieuNaissance());
            demande.setLieuResidence(demandeDto.getLieuResidence());
            Profession profession = this.professionRepository.findById(demandeDto.getProfessionId()).orElse(null);
            if (profession.getId() == 1639) {
                demande.setProfession(profession);
                demande.setEmploi(demandeDto.getEmploi());
                demande.setAutreProfession(demandeDto.getEmploi());
            } else {
                demande.setProfession(profession);
                demande.setEmploi(demandeDto.getAutreProfession());
            }

            demande.setSexe(this.sexeRepository.findById(demandeDto.getSexeCode()).orElse(null));
            demande.setPaysNationalite(this.paysRepository.findById(demandeDto.getPaysNationaliteCode()).orElse(null));
            demande.setPaysNaissance(this.paysRepository.findById(demandeDto.getPaysNaissanceCode()).orElse(null));
            demande.setPaysResidence(this.paysRepository.findById(demandeDto.getPaysResidenceCode()).orElse(null));
            demande.setSituationMatrimoniale(situationMatrimonialeRepository.findById(demandeDto.getSituationMatrimonialeId()).orElse(null));
            demande.setEmail(demandeDto.getEmail());
            demande.setNomPere(demandeDto.getNomPere());
            demande.setPrenomPere(demandeDto.getPrenomPere());
            demande.setNomMere(demandeDto.getNomMere());
            demande.setPrenomMere(demandeDto.getPrenomMere());
            if (demandeDto.getNumeroActe() != null) {
                demandeDto.setNumeroActe(demandeDto.getNumeroActe());
            } else if (demandeDto.getNumeroCarte() != null) {
                demandeDto.setNumeroCarte(demandeDto.getNumeroCarte());
            } else if (demandeDto.getNumeroJugement() != null) {
                demandeDto.setNumeroJugement(demandeDto.getNumeroJugement());
            } else if (demandeDto.getNumeroPasseport() != null) {
                demandeDto.setNumeroPasseport(demandeDto.getNumeroPasseport());
            }
            demande.setEtape1Valider(false);
            demande.setEtape2Valider(false);
            demande.setEtape3Valider(false);
            demande.setRetirer(false);
            demande.setValider(false);
            demande.setInvalidee(false);
            demande.setSignee(false);

            return this.entityMapper.demandeToDemandeDto(demandeRepository.save(demande));

        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }


    }


    @Override
    public List<DemandeStatistiqueByCentreModel> getDemandeStatistiqueByCentreFilterByPeriode(Date debut, Date fin) {
        try {
            return demandeRepository.getStatistiqueByCentreFilterByPeriode(debut, fin);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    public Casier getCondamnations(Casier casier, Demande demande) {
        if (casier == null) {
            return null;
        }
        if (casier.getCondamnations() != null) {
            Collection<Condamnation> condamnations = casier.getCondamnations().stream().map(c -> {
                Set<Infraction> infractions = getInfraction(casier, c);
                return Condamnation.builder()
                        .dateCondamnation(c.getDatejugement())
                        .dateMandatDepot(c.getDatejugement())
                        .infractions(infractions)
                        .cours(c.getJuridiction().getLibellecourt())
                        .quantumPeine(Integer.valueOf(c.getPeine().getLibelle()))
                        .build();
            }).collect(Collectors.toList());
            List<CondamnationModel> condamnationModels = condamnations.stream().map(
                    condamnation -> {
                        CondamnationModel model = entityMapper.condamnationToCondamnationModel(condamnation);
                        model.setObservations(BulletinGenerator.observation(demande));
                        model.setNaturePeines(CasierUtils.esimerQuantumPeine(condamnation.getQuantumPeine()));
                        return model;
                    }
            ).collect(Collectors.toList());
            casier.setModeles(condamnationModels);
        }
        return casier;
    }

    private UtilisateurCasier getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UtilisateurCasier utilisateurCasier = utilisateurCasierRepository.getFromUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException(String.format("Aucun utilisateur trouvé avec le nom %s", auth.getName())));
        return utilisateurCasier;
    }

    private List<PointRetrait> getUserPointRetrait() {
        UtilisateurCasier utilisateurCasier = getCurrentUser();
        List<PointRetrait> pointRetraits = new ArrayList<>();
        pointRetraits.add(this.utilisateurCasierPointRetraitRepository.findActiveByIdUtilisateurCasier(utilisateurCasier.getCode()).getPointRetrait());
        return pointRetraits;
    }

    private List<Long> getUserPointRetraitV1() {
        try {
            UtilisateurCasier utilisateurCasier = getCurrentUser();
            List<Long> pointRetraits = new ArrayList<>();
            if (utilisateurCasier.getPointRetraits() != null && utilisateurCasier.getPointRetraits().size() > 0) {
                utilisateurCasier.getPointRetraits().forEach(pointRetrait -> {
                    if (pointRetrait.getActive().equals(Boolean.TRUE)) {
                        pointRetraits.add(pointRetrait.getId());
                    }
                });
            } else {
                Boolean isAdmin = accountService.checkIfSupAdmin(utilisateurCasier);
                if (isAdmin) {
                    pointRetraitRepository.getAllTribunaux().forEach(pointRetrait -> {
                        if (pointRetrait.getActive().equals(Boolean.TRUE)) {
                            pointRetraits.add(pointRetrait.getId());
                        }
                    });
                }

                System.err.println("pointRetraits== " + pointRetraits);
            }
            return pointRetraits;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    UserB2Dto utilisateurToUserB2(UtilisateurCasier u) {
        if (u != null) {
            return UserB2Dto.builder()
                    .cni(u.getPersonneInfo().getUser().getUsername())
                    .nom(u.getPersonneInfo().getNom())
                    .prenoms(u.getPersonneInfo().getPrenom())
                    .email(u.getPersonneInfo().getEmail())
                    .tel(u.getPersonneInfo().getTelephone())
                    .titre(u.getPersonneInfo().getTitre())
                    .sexe(u.getPersonneInfo().getSexe().getCode())
                    .build();
        }
        return null;
    }


    @Override
    public InputStream exporterDemandeStatistique(Date debut, Date fin, String format) throws IOException, JRException {
        List<DashboardModel> dashboardModelList = this.getDashbord(debut, fin);
        JRBeanCollectionDataSource demandeStatistiqueDataSource = new JRBeanCollectionDataSource(dashboardModelList);
        HashMap params = new HashMap();
        /*String armoirie = Paths.get(armoirieTogo).toString();
        params.put("ARMOIRIE_TOGO", armoirie);*/
        params.put("DATE_DEBUT", debut);
        params.put("STATISTIQUE_DATA_SOURCE", demandeStatistiqueDataSource);
        params.put("DATE_FIN", fin);
        ReportManager reportManager = new ReportManager(params, statBaseTemplate + "/demandeStatistique.jrxml");
        return exporter(reportManager, format);

    }

    @Override
    public InputStream exporterDemandeParCentre(Date debut, Date fin, String format) throws JRException, IOException {
        List<DemandeStatistiqueByCentreModel> demandeStatistiqueByCentreList = this.getDemandeStatistiqueByCentreFilterByPeriode(debut, fin);
        JRBeanCollectionDataSource demandeParcentreStatDataSource = new JRBeanCollectionDataSource(demandeStatistiqueByCentreList);
        HashMap params = new HashMap();
        /*String armoirie = Paths.get(armoirieTogo).toString();
        params.put("ARMOIRIE_TOGO", armoirie);*/
        params.put("DATE_DEBUT", debut);
        params.put("DATE_FIN", fin);
        params.put("STATISTIQUE_DATA_SOURCE", demandeParcentreStatDataSource);
        ReportManager reportManager = new ReportManager(params, statBaseTemplate + "/Demande_par_centre.jrxml");
        return exporter(reportManager, format);
    }

    @Override
    public InputStream exporterExtraitB3(Date debut, Date fin, String format) throws JRException, IOException {
        List<ExtraitB3> extraitB3 = this.getStatistiqueB3Montant(debut, fin);
        JRBeanCollectionDataSource extraitB3StatDataSource = new JRBeanCollectionDataSource(extraitB3);
        HashMap params = new HashMap();
        /*String armoirie = Paths.get(armoirieTogo).toString();
        params.put("ARMOIRIE_TOGO", armoirie);*/
        params.put("DATE_DEBUT", debut);
        params.put("DATE_FIN", fin);
        params.put("STATISTIQUE_DATA_SOURCE", extraitB3StatDataSource);
        ReportManager reportManager = new ReportManager(params, statBaseTemplate + "/Extrait_b3.jrxml");
        return exporter(reportManager, format);
    }

    @Override
    public InputStream recuDemende(String numeroDemande) throws JRException, IOException {
        Demande demande = demandeRepository.findByNumeroDemande(numeroDemande);
        HashMap params = new HashMap();
        params.put("armoirie", armoirieTogo);
        params.put("signature", signatureImage);
        params.put("nom", demande.getNom());
        params.put("prenom", demande.getPrenom());
        params.put("numeroDemande", demande.getNumeroDemande());
        params.put("copie", demande.getNombreCopie());
        params.put("quitus", demande.getPayement().getQuittanceTresor());
        params.put("datePaiement", demande.getPayement().getDatePayement());

        Integer cout = demande.getPrestation().getCout();
        Integer reviens = cout * demande.getNombreCopie();
        String prestation = demande.getPrestation().getLibelle();

        String prixReviens = "" + reviens;
        String prix = "" + cout;
        Recu recu = Recu.builder()
                .prestation(prestation)
                .prix(prix)
                .prixReviens(prixReviens).build();
        List<Recu> recuList = new ArrayList<>();
        recuList.add(recu);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(recuList);
        params.put("datasource", dataSource);

        ReportManager reportManager = new ReportManager(params, statBaseTemplate + "/recu_paiement.jrxml");
        return exporter(reportManager, "pdf");
    }

    @Override
    public DemandeDto annulerValidation(Long id) {
        try {
            Demande oldDemande = demandeRepository.findById(id).orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec l'identifiant %s ", id)));
            oldDemande.setInvalidee(false);
            oldDemande.setValider(false);
            oldDemande.setEtape3Valider(false);
            oldDemande.setEtape2Valider(false);
            oldDemande.setEtape1Valider(false);
            oldDemande.setSignee(false);
            oldDemande.setRetirer(false);
            oldDemande.setRetirer(false);
            oldDemande.setDisponible(false);
            oldDemande.setTraitee(false);
            oldDemande.setSignee(false);
            oldDemande.setRetirer(false);
            oldDemande.setDateValidation(null);
            UtilisateurCasier utilisateurCasier = getCurrentUser();
            // oldDemande.setUtilisateurDerniereModification(utilisateurCasier);
            oldDemande.setUtilisateurValidateur(utilisateurCasier);
            oldDemande = demandeRepository.save(oldDemande);
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Annulation de la validation de la demande %s ", oldDemande.getNumeroDemande()),
                    objectMapper.writeValueAsString(oldDemande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    oldDemande
            );
            // TODO: sent notification to ATD
            return entityMapper.demandeToDemandeDto(oldDemande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto annulerTraitement(Long id) {
        try {
            Demande oldDemande = demandeRepository.findById(id).orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec l'identifiant %s ", id)));

            oldDemande.setDisponible(false);
            oldDemande.setTraitee(false);
            oldDemande.setSignee(false);
            oldDemande.setRetirer(false);
            oldDemande.setInvalidee(false);
            UtilisateurCasier utilisateurCasier = getCurrentUser();
            // oldDemande.setUtilisateurDerniereModification(utilisateurCasier);
            oldDemande.setUtilisateurValidateur(utilisateurCasier);
            oldDemande = demandeRepository.save(oldDemande);
            // TODO: sent notification to ATD
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Annulation du traitement de la demande %s ", oldDemande.getNumeroDemande()),
                    objectMapper.writeValueAsString(oldDemande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    oldDemande
            );
            return entityMapper.demandeToDemandeDto(oldDemande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto annulerSignature(Long id) {
        try {
            Demande oldDemande = demandeRepository.findById(id).orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec l'identifiant %s ", id)));

            oldDemande.setDisponible(true);
            oldDemande.setTraitee(true);
            oldDemande.setSignee(false);
            oldDemande.setRetirer(false);
            UtilisateurCasier utilisateurCasier = getCurrentUser();
            // oldDemande.setUtilisateurDerniereModification(utilisateurCasier);
            oldDemande.setUtilisateurValidateur(utilisateurCasier);
            oldDemande = demandeRepository.save(oldDemande);
            // TODO: sent notification to ATD
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Annulation de la signature de la demande %s ", oldDemande.getNumeroDemande()),
                    objectMapper.writeValueAsString(oldDemande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    oldDemande
            );
            return entityMapper.demandeToDemandeDto(oldDemande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ResponseObject recupererInfoB3(String numeroDemande, String numeroPiece) {
        try {
            ResponseObject responseObject = new ResponseObject();
            Demande demandeB3 = demandeRepository.findByNumeroDemande(numeroDemande);

            if (demandeB3 != null && CasierUtils.getNumeroPiece(demandeB3).equals(numeroPiece)) {
                DemandeUtils demandeUtils = new DemandeUtils(demandeB3, numeroPiece);
                demandeUtils.setMontant(variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX) * demandeB3.getNombreCopie());
                // Delivery tracking data
                if (demandeB3.getTracked() != null && demandeB3.getTracked() == true) {
                    demandeUtils.setDeliveryData(laPosteApi.trackingDeliveryStatus(demandeB3));
                }
                responseObject.setDemandeUtils(demandeUtils);
                responseObject.setResponseCode(SUCCESS);
                responseObject.setDescription("Recherche éffectuée. Demande retrouvée");
            } else {
                responseObject.setResponseCode(NOT_FOUND);
                responseObject.setDescription("Demande inexistante.");
            }
            return responseObject;

        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ResponseObject recupererDemandeB3(String numeroDemande, String numeroPiece) {
        System.err.println("NUMERO PIECE" + numeroPiece);
        try {
            ResponseObject responseObject = new ResponseObject();
            DemandeDto demandeDto = new DemandeDto();
            Demande demandeB3 = demandeRepository.findByNumeroDemande(numeroDemande);
            if (demandeB3 != null && CasierUtils.getNumeroPiece(demandeB3).equals(numeroPiece)) {
                DemandeSite demandeSite = demandeDto.convert(demandeB3);
                responseObject.setResponseCode(SUCCESS);
                responseObject.setDemandeB3(demandeSite);
                return responseObject;
            } else {
                throw new Exception(String.format("Aucune demande trouvée avec le numéro %s ", numeroDemande));
            }
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeList> findByTypeAndPeriodeForAdmin(String type, Date debut, Date fin) {
        try {

            List<DemandeList> demandes = new ArrayList<>();
            switch (type) {
                case "CJE":
                case "ANC":
                case "B3":
                case "M3": {

                    demandes = demandeRepository.findAllByTypeDemandeB3AndANC(type, debut, fin);

                    break;
                }
                case "B2":
                case "B1": {
                    demandes = demandeRepository.findAllByTypeDemandeB1AndB2(type, debut, fin);
                    break;
                }
                default: {
                    break;
                }

            }
            return demandes;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public DemandeDto moveToNewPointTraitement(DemandeDto dto) {
        try {
            Demande oldDemande = demandeRepository.findById(dto.getId()).orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec l'identifiant %s ", dto.getId())));
            PointRetrait pointRetrait = pointRetraitRepository.findById(dto.getPointRetraitId()).orElseThrow(() -> new Exception(String.format("Aucun point de traitement trouvé avec l'identifiant %s ", dto.getPointRetraitId())));
            oldDemande.setPointRetrait(pointRetrait);
            oldDemande = demandeRepository.save(oldDemande);
            // TODO: sent notification to ATD
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Tranfert de la demande %s ", oldDemande.getNumeroDemande()),
                    objectMapper.writeValueAsString(oldDemande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    oldDemande
            );
            return entityMapper.demandeToDemandeDto(oldDemande);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DemandeList> getDemandeEncoursFilterByPeriodeAndTypeDemandeAndPointRetrait(Date debut, Date fin, String typeDemande, Long pointRetrait) {
        try {
            return demandeRepository.findDemandesEnCours(typeDemande, debut, fin, pointRetrait);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DashboardModel> getCountDemandeByPeriodeAndDelay(Date debut, Date fin, Integer delay) {
        try {
            return demandeRepository.getCountDemandeByPeriodeAndDelay(debut, fin, delay);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<DashboardModel> getCountDemandeByPeriodeAndDelayAndPointRetrait(Date debut, Date fin, Integer delay, Integer pointRetraitId) {
        try {

            return demandeRepository.getCountDemandeByPeriodeAndDelayAndPointRetrait(debut, fin, delay, pointRetraitId);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ResponseObject raccourciSave(FormulaireModification formulaireModification) {
        try {
            if (formulaireModification == null) {
                return null;
            }
            Demande demande = demandeRepository.findByNumeroDemande(formulaireModification.getNumeroDemande());
            Demande demandeNew = createNewFromDemande(demande);
            System.err.println("*************************************************************************");
            System.err.println(demandeNew);
            if (demandeNew == null) {
                return ResponseObject.builder().responseCode(NOT_FOUND).build();
            }
            demandeNew.setId(null);
            demandeNew.setInvalidee(false);
            demandeNew.setValider(false);
            demandeNew.setEtape3Valider(false);
            demandeNew.setEtape2Valider(false);
            demandeNew.setEtape1Valider(false);
            demandeNew.setSignee(false);
            demandeNew.setRetirer(false);
            demandeNew.setRetirer(false);
            demandeNew.setDisponible(false);
            demandeNew.setTraitee(false);
            demandeNew.setSignee(false);
            demandeNew.setRetirer(false);
            demandeNew.setDateValidation(null);
            demande.setTelephone(formulaireModification.getTelephone());
            if (formulaireModification.getProfession() != null) {
                demandeNew.setProfession(professionRepository.findById(formulaireModification.getProfession().getId()).orElse(null));
            }
            if (demandeNew.getSituationMatrimoniale() != null) {
                demandeNew.setSituationMatrimoniale(situationMatrimonialeRepository.findById(formulaireModification.getSituationMatrimoniale().getId()).orElse(null));
            }
            demandeNew.setDateDemande(new Date());
            demandeNew.setTypeDemande(demande.getTypeDemande());
            demandeNew.setNumeroDemande(compteurService.nextFormated(demandeNew));
            demandeNew.setNombreCopie(formulaireModification.getNombreCopie());
            demandeRepository.save(demandeNew);
            return ResponseObject.builder()
                    .numeroPiece(CasierUtils.getNumeroPiece(demande))
                    .numeroDemande(demandeNew.getNumeroDemande())
                    .demandeB3(DemandeDto.convert(demandeNew))
                    .responseCode(SUCCESS)
                    .build();
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<?> updateProfession(UpdateObject object) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Aucune demande à mettre à jour", HttpStatus.BAD_REQUEST);
            }
            System.err.println(object);
            Demande demande = demandeRepository.findById(object.getId_demande())
                    .orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec le id %s", object.getId_demande())));
            if (object.getId_profession() != null && object.getId_profession() == 1639) {
                if (object.getAutre_profession() == null) {
                    return new ResponseEntity<>("La profession est obligatoire", HttpStatus.BAD_REQUEST);

                }
                demande.setAutreProfession(object.getAutre_profession());
                demande.setEmploi(object.getAutre_profession());
                demande.setProfession(professionRepository.findById(1639).orElse(null));
            } else {
                Profession profession = professionRepository.findById(Math.toIntExact(object.getId_profession()))
                        .orElseThrow(() -> new Exception(String.format("Aucune profession trouvée avec le id %s", object.getId_profession())));
                demande.setAutreProfession(profession.getLibelle());
                demande.setEmploi(profession.getLibelle());
                demande.setProfession(profession);
            }
            demande = demandeRepository.save(demande);
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Modification la profession pour la demande %s ", demande.getNumeroDemande()),
                    mapper.writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return new ResponseEntity<>("Mise à jour de la profession effectuée avec succès", HttpStatus.OK);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Echèc de mise ) jour de la profession", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateSituationMatrimoniale(UpdateObject object) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Aucune demande à mettre à jour", HttpStatus.BAD_REQUEST);
            }

            Demande demande = demandeRepository.findById(object.getId_demande())
                    .orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec le id %s", object.getId_demande())));
            SituationMatrimoniale situationMatrimoniale = situationMatrimonialeRepository.findById(object.getId_situation_matrimoniale())
                    .orElseThrow(() -> new Exception(String.format("Aucun  status matrimonial trouvé avec le id %s", object.getId_situation_matrimoniale())));
            demande.setSituationMatrimoniale(situationMatrimoniale);
            demande = demandeRepository.save(demande);
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Modification la situation matrimoniale pour la demande %s ", demande.getNumeroDemande()),
                    mapper.writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return new ResponseEntity<>("Mise à jour de la profession effectuée avec succès", HttpStatus.OK);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Echèc de mise à jour de la profession", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateNationalite(UpdateObject object) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Aucune demande à mettre à jour", HttpStatus.BAD_REQUEST);
            }
            Demande demande = demandeRepository.findById(object.getId_demande())
                    .orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec le id %s", object.getId_demande())));
            Pays pays = paysRepository.findById(object.getCode_pays())
                    .orElseThrow(() -> new Exception(String.format("Aucune profession trouvée avec le id %s", object.getId_demande())));
            demande.setPaysNationalite(pays);
            demande = demandeRepository.save(demande);
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Modification la nationalité pour la demande %s ", demande.getNumeroDemande()),
                    mapper.writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return new ResponseEntity<>("Mise à jour de la profession effectuée avec succès", HttpStatus.OK);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Echèc de mise à jour de la nationalité", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updatePrefectureNaissance(UpdateObject object) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Aucune demande à mettre à jour", HttpStatus.BAD_REQUEST);
            }
            System.err.println(object);
            Demande demande = demandeRepository.findById(object.getId_demande())
                    .orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec le id %s", object.getId_demande())));
            Prefecture prefecture = prefectureRepository.findById(object.getId_prefecture())
                    .orElseThrow(() -> new Exception(String.format("Aucune préfecture trouvée avec le id %s", object.getId_demande())));
            demande.setPrefectureNaissance(prefecture);
            demande = demandeRepository.save(demande);
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Modification la préfecture de naissance pour la demande %s ", demande.getNumeroDemande()),
                    mapper.writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return new ResponseEntity<>("Mise à jour de la profession effectuée avec succès", HttpStatus.OK);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Echèc de mise à jour de la nationalité", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updatePaysNaissnace(UpdateObject object) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Aucune demande à mettre à jour", HttpStatus.BAD_REQUEST);
            }
            Demande demande = demandeRepository.findById(object.getId_demande())
                    .orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec le id %s", object.getId_demande())));
            Pays pays = paysRepository.findById(object.getCode_pays())
                    .orElseThrow(() -> new Exception(String.format("Aucune profession trouvée avec le id %s", object.getId_demande())));
            demande.setPaysNaissance(pays);
            demande = demandeRepository.save(demande);
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Modification du pays de naissance pour la demande %s ", demande.getNumeroDemande()),
                    mapper.writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return new ResponseEntity<>("Mise à jour de la profession effectuée avec succès", HttpStatus.OK);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Echèc de mise à jour de la nationalité", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updatePaysResidence(UpdateObject object) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Aucune demande à mettre à jour", HttpStatus.BAD_REQUEST);
            }
            Demande demande = demandeRepository.findById(object.getId_demande())
                    .orElseThrow(() -> new Exception(String.format("Aucune demande trouvée avec le id %s", object.getId_demande())));
            Pays pays = paysRepository.findById(object.getCode_pays())
                    .orElseThrow(() -> new Exception(String.format("Aucune profession trouvée avec le id %s", object.getId_demande())));
            demande.setPaysResidence(pays);
            demande = demandeRepository.save(demande);
            UtilisateurCasier utilisateur = getCurrentUser();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            logService.save(String.format("Modification du pays de résidence pour la demande %s ", demande.getNumeroDemande()),
                    mapper.writeValueAsString(demande),
                    null,
                    utilisateur.getPersonneInfo().getUser(),
                    demande
            );
            return new ResponseEntity<>("Mise à jour de la profession effectuée avec succès", HttpStatus.OK);
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Echèc de mise à jour de la nationalité", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Demande createNewFromDemande(Demande demande) {
        try {
            if (demande == null) {
                return null;
            }
            Demande demandeNew = new Demande();
            BeanUtils.copyProperties(demande, demandeNew, new String[]{"id", "payement", "nombreCopie", "numeroDemande"});
            return demandeNew;
        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return null;
        }
    }

    public InputStream exporter(ReportManager reportManager, String format) throws JRException, IOException {
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
    public void sendFeedBack(FeedbackModel notificationModel) {
        try {
            serviceEnvoyeur.envoyerFeadBack(notificationModel);

        } catch (Exception e) {
            this.logger.error("Erreur interne: " + e);
            e.printStackTrace();
        }
    }
}

