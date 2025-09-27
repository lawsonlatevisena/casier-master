package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tg.ceel.cj.casierapi.dto.TypeDemandeDto;
import tg.ceel.cj.casierapi.services.*;
import tg.ceel.cj.casierapi.utils.CasierConstants;
import tg.ceel.cj.casierapi.utils.ServiceResponse;

@RestController
@RequestMapping("references")
public class ReferenceController {
    Logger logger = LoggerFactory.getLogger(ReferenceController.class);
    private final LocaliteService localiteService;
    private final TypePieceService typePieceService;
    private final TypeDemandeService typeDemandeService;
    private final ProfessionService professionService;
    private final PaysService paysService;
    private final PrefectureService prefectureService;
    private final PointRetraitService pointRetraitService;
    private final VariableService variableService;
    private final CategorieSocioProfessionnelleService categorieSocioProfessionnelleService;
    private final SituationMatrimonialeService situationMatrimonialeService;
    private final EntiteDemandeurB2Service entiteDemandeurB2Service;
    private final CategorieDemandeurB2Service categorieDemandeurB2Service;
    private final ServiceDemandeurB2Service serviceDemandeurB2Service;
    private final CategoriePersonneMoraleService categoriePersonneMoraleService;

    public ReferenceController(LocaliteService localiteService, TypePieceService typePieceService, TypeDemandeService typeDemandeService, ProfessionService professionService, PaysService paysService, PrefectureService prefectureService, PointRetraitService pointRetraitService, VariableService variableService, CategorieSocioProfessionnelleService categorieSocioProfessionnelleService, SituationMatrimonialeService situationMatrimonialeService, EntiteDemandeurB2Service entiteDemandeurB2Service, CategorieDemandeurB2Service categorieDemandeurB2Service, ServiceDemandeurB2Service serviceDemandeurB2Service, CategoriePersonneMoraleService categoriePersonneMoraleService) {
        this.localiteService = localiteService;
        this.typePieceService = typePieceService;
        this.typeDemandeService = typeDemandeService;
        this.professionService = professionService;
        this.paysService = paysService;
        this.prefectureService = prefectureService;
        this.pointRetraitService = pointRetraitService;
        this.variableService = variableService;
        this.categorieSocioProfessionnelleService = categorieSocioProfessionnelleService;
        this.situationMatrimonialeService = situationMatrimonialeService;
        this.entiteDemandeurB2Service = entiteDemandeurB2Service;
        this.categorieDemandeurB2Service = categorieDemandeurB2Service;
        this.serviceDemandeurB2Service = serviceDemandeurB2Service;
        this.categoriePersonneMoraleService = categoriePersonneMoraleService;
    }

    @GetMapping("/pp/{type}")
    public ResponseEntity<?> getReferenceData(@PathVariable("type") String type) {
        try {
            return new ResponseEntity<>(this.getServiceResponseByTypeDemande(type), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/pm/{type}")
    public ResponseEntity<?> getReferenceDataPm(@PathVariable("type") String type) {
        try {
            return new ResponseEntity<>(this.getServiceResponseByTypeDemandePm(type), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @GetMapping("/postes/point-retrait")
    public ResponseEntity<?> getPoste() {
        try {
            return new ResponseEntity<>(pointRetraitService.getAllBureauxPoste(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @GetMapping("/juridiction/point-retrait/{type}")
    public ResponseEntity<?> getJuridiction(@PathVariable("type") String type) {
        try {
            return new ResponseEntity<>(pointRetraitService.getAllJuridictionsV1(type), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }
    private ServiceResponse getServiceResponseByTypeDemande(String type) {
        try {
            ServiceResponse response = new ServiceResponse();
            TypeDemandeDto typeDemande = typeDemandeService.findByCode(type);
            if (typeDemande == null) {
                return  response;
            }
            response.setPays(this.paysService.findAll());
           // response.setLocalites(localiteService.getAll());
            response.setPrefectures(prefectureService.getAll());
            System.err.println("sssssssssssssssssssssssssssssssss");
            if (typeDemande != null) {

                response.setPointRetraits(pointRetraitService.getAllJuridictionsV1(typeDemande.getCode()));
                response.setAutrePointRetraits(pointRetraitService.getAllBureauxPoste(typeDemande.getCode()));
            }
            if (type.equalsIgnoreCase("B3") || type.equalsIgnoreCase("ANC") || type.equalsIgnoreCase("CJE")) {
                if (type.equalsIgnoreCase("CJE")) {
                    type = "ANC";
                }
                response.setPrixUnitaireDemande(variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX));
                response.setTypePieces(typePieceService.getAll(type, Boolean.TRUE));
            }
            if (type.equalsIgnoreCase("B2") || type.equalsIgnoreCase("B1")) {
                response.setEntititesDemandeurs(entiteDemandeurB2Service.getAll());
                response.setCategoriesDemandeurs(categorieDemandeurB2Service.getAll());
                response.setServiceDemandeurB2s(serviceDemandeurB2Service.getAll());
                response.setPrixUnitaireDemande(null);

            }

            response.setCategorieSocioProfessionnelles(categorieSocioProfessionnelleService.getAll());
            response.setSituationMatrimoniale(situationMatrimonialeService.getAll());
            response.setProfessions(professionService.getAll());
            // response.setPointRetraits(pointRetraitService.getAllANCPointRetraits());
            return response;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }


    }

    private ServiceResponse getServiceResponseByTypeDemandePm(String type) {
        try {
            ServiceResponse response = new ServiceResponse();
            TypeDemandeDto typeDemande = typeDemandeService.findByCode(type);
            if (typeDemande == null) {
                return  response;
            }
            response.setPays(this.paysService.findAll());
            // response.setLocalites(localiteService.getAll());
           // response.setPrefectures(prefectureService.getAll());
            System.err.println("sssssssssssssssssssssssssssssssss");
            if (typeDemande != null) {

                response.setPointRetraits(pointRetraitService.getAllJuridictionsV1(typeDemande.getCode()));
                //  response.setAutrePointRetraits(pointRetraitService.getAllBureauxPoste(typeDemande.getCode()));
            }
            if (type.equalsIgnoreCase("B3") || type.equalsIgnoreCase("ANC") || type.equalsIgnoreCase("CJE")) {
                if (type.equalsIgnoreCase("CJE")) {
                    type = "ANC";
                }
                response.setPrixUnitaireDemande(variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX));
                response.setTypePieces(typePieceService.getAll(type));
            }
            if (type.equalsIgnoreCase("B2") || type.equalsIgnoreCase("B1")) {
                response.setEntititesDemandeurs(entiteDemandeurB2Service.getAll());
                response.setCategoriesDemandeurs(categorieDemandeurB2Service.getAll());
                response.setServiceDemandeurB2s(serviceDemandeurB2Service.getAll());
                response.setPrixUnitaireDemande(null);
            }
//agbenotowossi amevi
      //  dettikou
            //      T5942825
           // response.setCategorieSocioProfessionnelles(categorieSocioProfessionnelleService.getAll());
          //  response.setSituationMatrimoniale(situationMatrimonialeService.getAll());
           // response.setProfessions(professionService.getAll());
            // response.setPointRetraits(pointRetraitService.getAllANCPointRetraits());
            response.setCategoriePersonneMorale(categoriePersonneMoraleService.findAll());
            return response;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }


    }

    @GetMapping("postes/point-retrait/{id}")
    public ResponseEntity<?> getBureauDePosteFromJuridiction(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(pointRetraitService.getPosteByIdJuricdiction(id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
