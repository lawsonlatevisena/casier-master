package tg.ceel.cj.casierapi.ws;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tg.ceel.cj.casierapi.dto.DemandeDto;
import tg.ceel.cj.casierapi.dto.DemandePMDto;
import tg.ceel.cj.casierapi.dto.PaiementDto;
import tg.ceel.cj.casierapi.dto.TmoneyRequestDto;
import tg.ceel.cj.casierapi.entities.Paiement;
import tg.ceel.cj.casierapi.models.*;
import tg.ceel.cj.casierapi.services.*;
import tg.ceel.cj.casierapi.utils.CasierConstants;
import tg.ceel.cj.casierapi.utils.CasierUtils;
import tg.ceel.cj.casierapi.utils.Utilitaires;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@RestController
@RequestMapping("demandes")
public class DemandeController {
    Logger logger = LoggerFactory.getLogger(DemandeController.class);
    private final DemandeService demandeService;
    private final DemandeAtdService demandeAtdService;
    private final PaiementService paiementService;
    private final VariableService variableService;
    private final HistoriqueService historiqueService;

    public DemandeController(DemandeService demandeService, DemandeAtdService demandeAtdService, PaiementService paiementService, VariableService variableService, HistoriqueService historiqueService) {
        this.demandeService = demandeService;
        this.demandeAtdService = demandeAtdService;
        this.paiementService = paiementService;
        this.variableService = variableService;
        this.historiqueService = historiqueService;
    }

// chargement des demandes selon le type
    @GetMapping("/type/{type}/validee/{validee}/imprimee/{imprimee}")
    public ResponseEntity<?> getAllDemandeNonValideByCentreRetrait(@PathVariable("type") String type, @PathVariable("validee") Boolean validee, @PathVariable("imprimee") Boolean imprimee) {
        try {
            return new ResponseEntity<>(demandeService.findByTypeAndValideeAndDisponible(type, imprimee, validee), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/type/{type}/imprimee/{imprimee}")
    public ResponseEntity<?> getAllDemandeNonDisponibleByCentreRetrait(@PathVariable("type") String type, @PathVariable("imprimee") Boolean imprimee) {
        try {
            return new ResponseEntity<>(demandeService.findByTypeAndDisponible(type, imprimee), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/type/{type}/imprimee/{imprimee}/invalidee/{invalidee}/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> getAllDemandeNonDisponibleByCentreRetrait(@PathVariable("type") String type, @PathVariable("imprimee") Boolean imprimee, @PathVariable("invalidee") Boolean invalidee, @PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            return new ResponseEntity<>(demandeService.findByTypeAndDisponibleAndInvalideeAndPeriode(type, imprimee, invalidee, debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/poste/type/{type}/imprimee/{imprimee}/invalidee/{invalidee}/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> getAllDemandeNonDisponibleByCentreRetraitPoste(@PathVariable("type") String type, @PathVariable("imprimee") Boolean imprimee, @PathVariable("invalidee") Boolean invalidee, @PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            return new ResponseEntity<>(demandeService.findByTypeAndDisponibleAndInvalideeAndPeriodePoste(type, imprimee, invalidee, debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/all/type/{type}/point-retrait/{point}/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> getAllDemandeNonDisponibleByCentreRetraitAll(@PathVariable("type") String type, @PathVariable("point") Long pointRetraitId, @PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            return new ResponseEntity<>(demandeService.findAllByTypeAndSigneeAndPeriodeV1(type, debut, fin, pointRetraitId), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/type/{type}/signee/{signee}/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> getAllDemandeNonDisponibleByCentreRetrait(@PathVariable("type") String type, @PathVariable("signee") Boolean signee, @PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            return new ResponseEntity<>(demandeService.findByTypeAndSigneeAndPeriodeV1(type, signee, debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/poste/type/{type}/signee/{signee}/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> getAllDemandePosteNonDisponibleByCentreRetrait(@PathVariable("type") String type, @PathVariable("signee") Boolean signee, @PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            return new ResponseEntity<>(demandeService.findByTypeAndSigneeAndPeriodeV1Poste(type, signee, debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/resumePaiement/debut/{debut}/fin/{fin}/centre/{centre}")
    public ResponseEntity<?> resumePaiement(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin, @PathVariable("centre") Long centre) {
        try {
            return new ResponseEntity<>(demandeService.resumePaiement(debut, fin, centre), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/detailExtraitDemande/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> detailExtraitDemande(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            return new ResponseEntity<>(demandeService.detailExtraitDemande(debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/admin/type/{type}/signee/{signee}/debut/{debut}/fin/{fin}/idPointRetrait/{idPointRetrait}")
    public ResponseEntity<?> getAllDemandeNonDisponibleByCentreRetraitForAdmin(@PathVariable("type") String type, @PathVariable("signee") Boolean signee, @PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin, @PathVariable("idPointRetrait") Long idPointRetrait) {
        try {
            return new ResponseEntity<>(demandeService.findByTypeAndSigneeAndPeriodeForAdmin(type, signee, debut, fin, idPointRetrait), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/admin/type/{type}/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> getAllDemandForAdmin(@PathVariable("type") String type, @PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            return new ResponseEntity<>(demandeService.findByTypeAndPeriodeForAdmin(type, debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/dashbord/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> getdashbord(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            return new ResponseEntity<>(demandeService.getDashbord(debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("test")
    public ResponseEntity<?> hello() {
        return new ResponseEntity<>(demandeService.getListeByTypeByPeriode(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> saveNewDemande(@RequestParam(name = "demande") String demande, @RequestPart("fichier") MultipartFile fichier, HttpServletRequest httpServletRequest) {
        try {
            logger.info(httpServletRequest.getRemoteHost());
            logger.info("Data sented: " + demande);
            logger.info("ContentType: " + fichier.getOriginalFilename());
            if (fichier == null || fichier.getContentType() == null) {
                return new ResponseEntity<>("Aucun fichier n'est attaché", HttpStatus.BAD_REQUEST);
            }
            if (FilenameUtils.getExtension(fichier.getOriginalFilename()) == null || FilenameUtils.getExtension(fichier.getOriginalFilename()).isEmpty()) {
                return new ResponseEntity<>("L'extension du fichier n'est pas précisée", HttpStatus.BAD_REQUEST);
            }

            logger.error("Extension " + FilenameUtils.getExtension(fichier.getOriginalFilename()));
            System.err.println(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            if (!Utilitaires.isAccepted(FilenameUtils.getExtension(fichier.getOriginalFilename()))) {
                return new ResponseEntity<>("Seuls les fichiers avec les extensions suivantes sont autorisés: PDF,PNG,JPEG, JPG", HttpStatus.BAD_REQUEST);
            }
            if (demande == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            ObjectMapper mapper = new ObjectMapper();
            DemandeSite demandeSite = mapper.readValue(demande, DemandeSite.class);
            DemandeDto dto = convert(demandeSite);
            dto.setTypeDemande("B3");
            dto.setExtentionFichier(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            dto.setMimeType(fichier.getContentType());
            DemandeDto response = demandeService.enregistrerDemandeSansPointreatrait(dto, fichier);
            if (response == null) {
                return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("update/{id}")
    public ResponseEntity<?> updateDemande(@PathVariable("id") Long id, @RequestBody DemandeDto demandeDto) {
        try {
            return new ResponseEntity<>(demandeService.update(id, demandeDto), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("create/B3")
    public ResponseEntity<?> saveNewDemandeB3(@RequestParam(name = "demande") String demande, @RequestParam(name = "paiement") String paiement, @RequestParam("fichier") MultipartFile fichier, HttpServletRequest httpServletRequest) {
        try {
            logger.info(httpServletRequest.getRemoteHost());
            logger.info("Demabde Data sented : " + demande);
            logger.info("Paiement Data sented: " + demande);
            logger.info("ContentType: " + fichier.getContentType());
            if (fichier == null || fichier.getContentType() == null) {
                return new ResponseEntity<>("Aucun fichier n'est attaché", HttpStatus.BAD_REQUEST);
            }
            if (FilenameUtils.getExtension(fichier.getOriginalFilename()) == null || FilenameUtils.getExtension(fichier.getOriginalFilename()).isEmpty()) {
                return new ResponseEntity<>("L'extension du fichier n'est pas précisée", HttpStatus.BAD_REQUEST);
            }

            logger.error("Extension " + FilenameUtils.getExtension(fichier.getOriginalFilename()));
           if (!Utilitaires.isAccepted(FilenameUtils.getExtension(fichier.getOriginalFilename()))) {
                return new ResponseEntity<>("Seuls les fichiers avec les extensions suivantes sont autorisés: PDF,PNG,JPEG, JPG", HttpStatus.BAD_REQUEST);
            }
            if (demande == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            ObjectMapper mapper = new ObjectMapper();
            DemandeAtd demandeAtd = mapper.readValue(demande, DemandeAtd.class);
            Paiement p = mapper.readValue(paiement, Paiement.class);
            DemandeModele dto = DemandeModele.builder()
                    .demande(demandeAtd)
                    .paiement(p)
                    .build();
            dto.getDemande().setExtention_fichier(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            dto.getDemande().setMimeType(fichier.getContentType());
            demandeAtd.setExtention_fichier(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            demandeAtd.setMimeType(fichier.getContentType());
            ResponseEntity responseEntity = demandeAtdService.validerUneDemande(dto);
            if (responseEntity == null) {
                return demandeAtdService.saveDemandeNew(dto, fichier);

            } else {
                return responseEntity;
            }

        } catch (Exception e) {

            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite"+e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("casier-b3/update")
    public ResponseEntity<?> updateDemandeB3(@RequestParam(name = "demande") String demande, @RequestParam(name = "fichier", required = false) MultipartFile fichier, HttpServletRequest httpServletRequest) {
        try {
            if (demande == null || demande.length() == 0) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            ObjectMapper mapper = new ObjectMapper();

            DemandeAtd dto = mapper.readValue(demande, DemandeAtd.class);
            DemandeAtd demandeAtd = demandeAtdService.rechercheParNumeroDemande(dto.getRecord());
            ResponseEntity responseEntity = null;
            if (demandeAtd.getType_demande().equalsIgnoreCase("M3")) {
                responseEntity = demandeAtdService.validerUneDemandePersonneMorale(dto);
            } else {
                responseEntity = demandeAtdService.validerUneDemande(dto);
            }

            if (fichier == null) {
                if (responseEntity == null) {
                    DemandeAtd response = demandeAtdService.updateDemande(dto);
                    if (response == null) {
                        return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

                    }
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                } else {
                    return responseEntity;
                }
            } else {
                if (FilenameUtils.getExtension(fichier.getOriginalFilename()) == null || FilenameUtils.getExtension(fichier.getOriginalFilename()).isEmpty()) {
                    return new ResponseEntity<>("L'extension du fichier n'est pas précisée", HttpStatus.BAD_REQUEST);
                }
                logger.error("Extension " + FilenameUtils.getExtension(fichier.getOriginalFilename()));
                System.err.println(FilenameUtils.getExtension(fichier.getOriginalFilename()));
                if (!Utilitaires.isAccepted(FilenameUtils.getExtension(fichier.getOriginalFilename()))) {
                    return new ResponseEntity<>("Seuls les fichiers avec les extensions suivantes sont autorisés: PDF,PNG,JPEG, JPG", HttpStatus.BAD_REQUEST);
                }

                dto.setExtention_fichier(FilenameUtils.getExtension(fichier.getOriginalFilename()));
                dto.setMimeType(fichier.getContentType());
                if (responseEntity == null) {
                    DemandeAtd response = demandeAtdService.updateDemande(dto, fichier);
                    if (response == null) {
                        return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

                    }
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                } else {
                    return responseEntity;
                }
            }


        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("casier-b1/update")
    public ResponseEntity<?> updateDemandeB1(@RequestBody DemandeAtd dto, HttpServletRequest httpServletRequest) {
        try {
            if (dto == null || dto.getType_demande() == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            ObjectMapper mapper = new ObjectMapper();
            DemandeAtd demandeAtd = demandeAtdService.rechercheParNumeroDemande(dto.getRecord());
            ResponseEntity responseEntity = null;
            if (demandeAtd.getType_demande().equalsIgnoreCase("M1")) {
                responseEntity = demandeAtdService.validerUneDemandePersonneMorale(dto);
            } else {
                responseEntity = demandeAtdService.validerUneDemandeB1etB2(dto);
            }
            if (responseEntity == null) {
                DemandeAtd response = demandeAtdService.updateDemandeB1etB2(dto);
                if (response == null) {
                    return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return responseEntity;
            }

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("casier-b2/update")
    public ResponseEntity<?> updateDemandeB2(@RequestBody DemandeAtd dto, HttpServletRequest httpServletRequest) {
        try {
            System.err.println(dto);
            if (dto == null || dto.getType_demande() == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            ObjectMapper mapper = new ObjectMapper();
            DemandeAtd demandeAtd = demandeAtdService.rechercheParNumeroDemande(dto.getRecord());
            ResponseEntity responseEntity = null;
            System.err.println(demandeAtd.getType_demande());
            if (demandeAtd.getType_demande().equalsIgnoreCase("M2")) {
                responseEntity = demandeAtdService.validerUneDemandePersonneMorale(dto);
            } else {
                responseEntity = demandeAtdService.validerUneDemandeB1etB2(dto);
            }
            if (responseEntity == null) {
                DemandeAtd response = demandeAtdService.updateDemandeB1etB2(dto);
                if (response == null) {
                    return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return responseEntity;
            }

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("create/M3")
    public ResponseEntity<?> saveNewDemandeM3(@RequestParam(name = "demande") String demande, @RequestParam(name = "paiement") String paiement, @RequestParam("fichier") MultipartFile fichier, HttpServletRequest httpServletRequest) {
        try {
            logger.info(httpServletRequest.getRemoteHost());
            logger.info("Data sented: " + demande);
            logger.info("ContentType: " + fichier.getContentType());
            if (fichier == null || fichier.getContentType() == null) {
                return new ResponseEntity<>("Aucun fichier n'est attaché", HttpStatus.BAD_REQUEST);
            }
            if (FilenameUtils.getExtension(fichier.getOriginalFilename()) == null || FilenameUtils.getExtension(fichier.getOriginalFilename()).isEmpty()) {
                return new ResponseEntity<>("L'extension du fichier n'est pas précisée", HttpStatus.BAD_REQUEST);
            }

            logger.error("Extension " + FilenameUtils.getExtension(fichier.getOriginalFilename()));
            System.err.println(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            if (!Utilitaires.isAccepted(FilenameUtils.getExtension(fichier.getOriginalFilename()))) {
                return new ResponseEntity<>("Seuls les fichiers avec les extensions suivantes sont autorisés: PDF,PNG,JPEG, JPG", HttpStatus.BAD_REQUEST);
            }
            if (demande == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }

            ObjectMapper mapper = new ObjectMapper();
            DemandeAtd demandeAtd = mapper.readValue(demande, DemandeAtd.class);
            Paiement p = mapper.readValue(paiement, Paiement.class);
            DemandeModele dto = DemandeModele.builder()
                    .demande(demandeAtd)
                    .paiement(p)
                    .build();
            dto.getDemande().setExtention_fichier(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            dto.getDemande().setMimeType(fichier.getContentType());
            demandeAtd.setExtention_fichier(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            demandeAtd.setMimeType(fichier.getContentType());
            ResponseEntity<?> responseEntity = demandeAtdService.validerUneDemandePersonneMorale(demandeAtd);
            if (responseEntity == null) {
                return demandeAtdService.saveDemandePM(dto, fichier);

            } else {
                return responseEntity;
            }

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/M2")
    public ResponseEntity<?> saveNewDemandeM2(@RequestBody DemandePMDto demande, HttpServletRequest httpServletRequest) {
        try {
            logger.info(httpServletRequest.getRemoteHost());
            logger.info("Data sented: " + demande);

            if (demande == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }

            DemandeDto response = demandeService.enregistrerDemande(demande);
            if (response == null) {
                return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

            }
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/CJE")
    public ResponseEntity<?> saveNewDemandeCJE(@RequestParam(name = "demande") String demande, @RequestParam(name = "paiement") String paiement, @RequestParam("fichier") MultipartFile fichier, HttpServletRequest httpServletRequest) {
        try {
            logger.info(httpServletRequest.getRemoteHost());
            logger.info("Data sented: " + demande);
            logger.info("ContentType: " + fichier.getContentType());
            if (fichier == null || fichier.getContentType() == null) {
                return new ResponseEntity<>("Aucun fichier n'est attaché", HttpStatus.BAD_REQUEST);
            }
            if (FilenameUtils.getExtension(fichier.getOriginalFilename()) == null || FilenameUtils.getExtension(fichier.getOriginalFilename()).isEmpty()) {
                return new ResponseEntity<>("L'extension du fichier n'est pas précisée", HttpStatus.BAD_REQUEST);
            }

            logger.error("Extension " + FilenameUtils.getExtension(fichier.getOriginalFilename()));
            System.err.println(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            if (!Utilitaires.isAccepted(FilenameUtils.getExtension(fichier.getOriginalFilename()))) {
                return new ResponseEntity<>("Seuls les fichiers avec les extensions suivantes sont autorisés: PDF,PNG,JPEG, JPG", HttpStatus.BAD_REQUEST);
            }
            if (demande == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            ObjectMapper mapper = new ObjectMapper();
            DemandeAtd demandeAtd = mapper.readValue(demande, DemandeAtd.class);
            Paiement p = mapper.readValue(paiement, Paiement.class);
            DemandeModele dto = DemandeModele.builder()
                    .demande(demandeAtd)
                    .paiement(p)
                    .build();
            dto.getDemande().setExtention_fichier(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            dto.getDemande().setMimeType(fichier.getContentType());
            demandeAtd.setExtention_fichier(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            demandeAtd.setMimeType(fichier.getContentType());
            ResponseEntity responseEntity = demandeAtdService.validerUneDemande(dto);
            if (responseEntity == null) {
                return demandeAtdService.saveDemandeNew(dto, fichier);

            } else {
                return responseEntity;
            }
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("casier-cje/update")
    public ResponseEntity<?> updateDemandeCJE(@RequestParam(name = "demande") String demande, @RequestParam(name = "fichier", required = false) MultipartFile fichier, HttpServletRequest httpServletRequest) {
        try {
            if (demande == null || demande.length() == 0) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            ObjectMapper mapper = new ObjectMapper();
            DemandeAtd dto = mapper.readValue(demande, DemandeAtd.class);
            ResponseEntity responseEntity = demandeAtdService.validerUneDemande(dto);
            if (fichier == null) {
                if (responseEntity == null) {
                    DemandeAtd response = demandeAtdService.updateDemande(dto);
                    if (response == null) {
                        return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

                    }
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                } else {
                    return responseEntity;
                }
            } else {
                if (FilenameUtils.getExtension(fichier.getOriginalFilename()) == null || FilenameUtils.getExtension(fichier.getOriginalFilename()).isEmpty()) {
                    return new ResponseEntity<>("L'extension du fichier n'est pas précisée", HttpStatus.BAD_REQUEST);
                }
                logger.error("Extension " + FilenameUtils.getExtension(fichier.getOriginalFilename()));
                System.err.println(FilenameUtils.getExtension(fichier.getOriginalFilename()));
                if (!Utilitaires.isAccepted(FilenameUtils.getExtension(fichier.getOriginalFilename()))) {
                    return new ResponseEntity<>("Seuls les fichiers avec les extensions suivantes sont autorisés: PDF,PNG,JPEG, JPG", HttpStatus.BAD_REQUEST);
                }

                dto.setExtention_fichier(FilenameUtils.getExtension(fichier.getOriginalFilename()));
                dto.setMimeType(fichier.getContentType());
                if (responseEntity == null) {
                    DemandeAtd response = demandeAtdService.updateDemande(dto, fichier);
                    if (response == null) {
                        return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

                    }
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                } else {
                    return responseEntity;
                }
            }


        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/b2")
    public ResponseEntity<?> saveNewDemandeB2(@RequestBody DemandeAtd demande, HttpServletRequest httpServletRequest) {
        try {

            if (demande == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
          /*  if (demande.getCode_certification() == null) {
                return new ResponseEntity<>("Tout demandadeur du B1 ou du B2 doit être indiqué", HttpStatus.INTERNAL_SERVER_ERROR);

            }*/
            demande.setType_demande("B2");
            ResponseEntity responseEntity = demandeAtdService.validerUneDemandeB1etB2(demande);
            if (responseEntity == null) {
                //  DemandeDto response = demandeService.enregistrerDemande(demande);
                DemandeAtd response = demandeAtdService.saveDemande(demande);
                if (response == null) {
                    return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

                }
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return responseEntity;
            }
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/b1")
    public ResponseEntity<?> saveNewDemandeB1(@RequestBody DemandeAtd demande, HttpServletRequest httpServletRequest) {
        try {

            if (demande == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            /*if (demande.getCode_certification() == null) {
                return new ResponseEntity<>("Tout demandadeur du B1 ou du B2 doit être indiqué", HttpStatus.INTERNAL_SERVER_ERROR);

            }*/
            demande.setType_demande("B1");
            ResponseEntity responseEntity = demandeAtdService.validerUneDemandeB1etB2(demande);
            if (responseEntity == null) {
                DemandeAtd response = demandeAtdService.saveDemande(demande);
                if (response == null) {
                    return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

                }
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return responseEntity;
            }
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("update")
    public ResponseEntity<?> updateDemande(@RequestParam(name = "demande") String demande, @RequestParam("fichier") MultipartFile fichier, HttpServletRequest httpServletRequest) {
        try {

            if (fichier == null || fichier.getContentType() == null) {
                return new ResponseEntity<>("Aucun fichier n'est attaché", HttpStatus.BAD_REQUEST);
            }
            if (FilenameUtils.getExtension(fichier.getOriginalFilename()) == null || FilenameUtils.getExtension(fichier.getOriginalFilename()).isEmpty()) {
                return new ResponseEntity<>("L'extension du fichier n'est pas précisée", HttpStatus.BAD_REQUEST);
            }

            logger.error("Extension " + FilenameUtils.getExtension(fichier.getOriginalFilename()));
            System.err.println(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            if (!Utilitaires.isAccepted(FilenameUtils.getExtension(fichier.getOriginalFilename()))) {
                return new ResponseEntity<>("Seuls les fichiers avec les extensions suivantes sont autorisés: PDF,PNG,JPEG, JPG", HttpStatus.BAD_REQUEST);
            }
            if (demande == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            ObjectMapper mapper = new ObjectMapper();
            DemandeDto dto = mapper.readValue(demande, DemandeDto.class);
            DemandeDto demandeChecked = demandeService.findByNumero(dto.getNumeroDemande());
            if (demandeChecked == null) {
                this.logger.error("Le numéro de demande indiqué ne correspond à aucune demande");
                return new ResponseEntity<>("Le numéro de demande indiqué ne correspond à aucune demande", HttpStatus.NOT_FOUND);
            }

            DemandeDto response = demandeService.modifierDemande(dto, fichier);
            if (response == null) {
                return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

            }
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("payer")
    public ResponseEntity<?> savePaiement(@RequestBody PaiementDto dto) {
        try {
            if (dto == null) {
                this.logger.error("Le corps de l'objet paiement n'est pas indiqué");
                return new ResponseEntity<>("Le corps de l'objet paiement n'est pas indiqué", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumeroDemande() == null) {
                this.logger.error("Le numéro de la demande n'est pas indiqué");
                return new ResponseEntity<>("Le numéro de la demande n'est pas indiqué", HttpStatus.BAD_REQUEST);
            }
            DemandeDto demande = demandeService.findByNumero(dto.getNumeroDemande());
            if (demande == null) {
                this.logger.error("Le numéro de demande indiqué ne correspond à aucune demande");
                return new ResponseEntity<>("Le numéro de demande indiqué ne correspond à aucune demande", HttpStatus.NOT_FOUND);
            }
            int nbrCopie = demande.getNombreCopie();
            double montant = variableService.getDoubleValue(CasierConstants.VAR_DEMANDE_PRIX) * nbrCopie;
            if (dto.getMontant().doubleValue() == montant) {
                PaiementDto paiementDto = paiementService.savePaiement(dto);
                return new ResponseEntity<>(paiementDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Le montant indiqué ne correspond pas aux montant attendu", HttpStatus.BAD_REQUEST);

            }

        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            
            return new ResponseEntity<>("Erreur interne :" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("invalider")
    public ResponseEntity<?> rejeter(@RequestBody DemandeDto dto) {
        try {
            if (dto == null) {
                this.logger.error("Le corps de l'objet à invalider n'est pas indiqué");
                return new ResponseEntity<>("Le corps de l'objet à invalider n'est pas indiqué", HttpStatus.BAD_REQUEST);
            }
            if (dto.getId() == null) {
                this.logger.error("L'identifiant de la demande n'est pas indiqué");
                return new ResponseEntity<>("Le numéro de la demande n'est pas indiqué", HttpStatus.BAD_REQUEST);
            }
            if (dto.getMotifInvalidation() == null) {
                this.logger.error("Le motif d'invalidation de la demande n'est pas indiqué");
                return new ResponseEntity<>("Le motif d'invalidation de la demande n'est pas indiqué", HttpStatus.BAD_REQUEST);
            }

            DemandeDto paiementDto = demandeService.invalider(dto);
            return new ResponseEntity<>(paiementDto, HttpStatus.OK);


        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            
            return new ResponseEntity<>("Erreur interne :" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rejeter/{id}")
    public ResponseEntity<?> rejeter(@PathVariable("id") Long id) {
        return new ResponseEntity<>(demandeService.invalider(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        System.out.println("id=" + id);
        return new ResponseEntity<>(demandeService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/valider/{id}")
    public ResponseEntity<?> valider(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(demandeService.valider(id), HttpStatus.OK);
        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            
            return new ResponseEntity<>("Erreur interne :" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/imprimer/{id}")
    public ResponseEntity<?> imprimer(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        try {
            byte[] data = demandeService.imprimer(id);
            System.err.println(data.length);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @GetMapping("/couleur/{id}")
    public ResponseEntity<?> imprimerCouleur(@PathVariable("id") Long id) {

        try {
            ResponseEntity<?> responseEntity = new ResponseEntity<>(demandeService.findBCondanations(id), HttpStatus.OK);
            System.err.println(responseEntity);
            return responseEntity;
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return null;
        }
    }

    //@GetMapping("/imprimer/{id}")
    public ResponseEntity<?> imprimer1(@PathVariable("id") Long id) {

        try {

            return new ResponseEntity<>(demandeService.imprimer1(id), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @GetMapping("/nom/{nom}/prenom/{prenom}/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> searchByNomPrenom(@PathVariable("nom") String nom, @PathVariable("prenom") String prenom, @PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            return new ResponseEntity<>(demandeService.rechercher(nom, prenom, debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("recherche/numero")
    public ResponseEntity<?> rechercheParNumeroDemande(@RequestBody DemandeDto dto, HttpServletRequest httpServletRequest) {
        try {
            if (dto == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumeroDemande() == null) {
                return new ResponseEntity<>("Numero de demande obligatoire pour cette recherche", HttpStatus.BAD_REQUEST);
            }
            DemandeDto response = demandeService.rechercheParNumeroDemande(dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/condamnations/id/{id}")
    public ResponseEntity<?> getCondamnations(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(demandeService.findBCondanations(id), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("traiter")
    public ResponseEntity<?> traiter(@RequestBody DemandeDto dto, HttpServletRequest httpServletRequest) {
        try {
            if (dto == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumeroDemande() == null) {
                return new ResponseEntity<>("Numero de demande obligatoire pour cette recherche", HttpStatus.BAD_REQUEST);
            }
            DemandeDto response = demandeService.traiter(dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("signer")
    public ResponseEntity<?> signer(@RequestBody DemandeDto dto, HttpServletRequest httpServletRequest) {
        try {
            if (dto == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumeroDemande() == null) {
                return new ResponseEntity<>("Numero de demande obligatoire pour cette recherche", HttpStatus.BAD_REQUEST);
            }
            DemandeDto response = demandeService.signer(dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("retirer")
    public ResponseEntity<?> retiere(@RequestBody DemandeDto dto, HttpServletRequest httpServletRequest) {
        try {
            if (dto == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumeroDemande() == null) {
                return new ResponseEntity<>("Numero de demande obligatoire pour cette recherche", HttpStatus.BAD_REQUEST);
            }
            DemandeDto response = demandeService.retirer(dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("update-file")
    public ResponseEntity<?> updateDemandeFile(@RequestParam(name = "demande") String demande, @RequestParam("fichier") MultipartFile fichier, HttpServletRequest httpServletRequest) {
        try {
            logger.info(httpServletRequest.getRemoteHost());
            logger.info("Data sented: " + demande);
            logger.info("ContentType: " + fichier.getContentType());
            if (fichier == null || fichier.getContentType() == null) {
                return new ResponseEntity<>("Aucun fichier n'est attaché", HttpStatus.BAD_REQUEST);
            }
            if (FilenameUtils.getExtension(fichier.getOriginalFilename()) == null || FilenameUtils.getExtension(fichier.getOriginalFilename()).isEmpty()) {
                return new ResponseEntity<>("L'extension du fichier n'est pas précisée", HttpStatus.BAD_REQUEST);
            }

            logger.error("Extension " + FilenameUtils.getExtension(fichier.getOriginalFilename()));
            System.err.println(FilenameUtils.getExtension(fichier.getOriginalFilename()));
            if (!Utilitaires.isAccepted(FilenameUtils.getExtension(fichier.getOriginalFilename()))) {
                return new ResponseEntity<>("Seuls les fichiers avec les extensions suivantes sont autorisés: PDF,PNG,JPEG, JPG", HttpStatus.BAD_REQUEST);
            }
            if (demande == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);

            }
            ObjectMapper mapper = new ObjectMapper();
            DemandeDto dto = mapper.readValue(demande, DemandeDto.class);

            DemandeDto demandeChecked = demandeService.findByNumero(dto.getNumeroDemande());
            if (demandeChecked == null) {
                this.logger.error("Le numéro de demande indiqué ne correspond à aucune demande");
                return new ResponseEntity<>("Le numéro de demande indiqué ne correspond à aucune demande", HttpStatus.NOT_FOUND);
            }

            DemandeDto response = demandeService.modifierFichierDemande(dto, fichier);
            if (response == null) {
                return new ResponseEntity<>("Une erreur s'est produite lors de l'enregistrement", HttpStatus.INTERNAL_SERVER_ERROR);

            }
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/export/statistique/debut/{debut}/fin/{fin}/format/{format}")
    public ResponseEntity<?> exportDemandeStatistique(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut,
                                                      @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin,
                                                      @PathVariable("format") String format) {
        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream;
        try {
            inputStream = this.demandeService.exporterDemandeStatistique(debut, fin, format);
            byte[] media = IOUtils.toByteArray(inputStream);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity<>(media, headers, HttpStatus.OK);
        } catch (IOException | JRException e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/export/statistique/demande-par-centre/debut/{debut}/fin/{fin}/format/{format}")
    public ResponseEntity<?> exporterDemandeParCentre(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut,
                                                      @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin,
                                                      @PathVariable("format") String format) {
        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream;
        try {
            inputStream = this.demandeService.exporterDemandeParCentre(debut, fin, format);
            byte[] media = IOUtils.toByteArray(inputStream);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity<>(media, headers, HttpStatus.OK);
        } catch (IOException | JRException e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/export/statistique/extrait-b3/debut/{debut}/fin/{fin}/format/{format}")
    public ResponseEntity<?> exporterExtraitB3(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut,
                                                      @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin,
                                                      @PathVariable("format") String format) {
        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream;
        try {
            inputStream = this.demandeService.exporterExtraitB3(debut, fin, format);
            byte[] media = IOUtils.toByteArray(inputStream);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity<>(media, headers, HttpStatus.OK);
        } catch (IOException | JRException e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getAnnee")
    public ResponseEntity<?> getAnnee() {
        try {
            return new ResponseEntity<>(demandeService.getAnnee(), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getExtraitB3/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> getStatistiqueB3Montant(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut,
                                                     @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            System.err.println("DATE" + debut + "FIN" + fin);
            return new ResponseEntity<>(demandeService.getStatistiqueB3Montant(debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/statistiqueByCentre/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> getDemandeStatistiqueByCentreFilterByPeriode(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut,
                                                                          @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        try {
            System.err.println("DATE" + debut + "FIN" + fin);
            return new ResponseEntity<>(demandeService.getDemandeStatistiqueByCentreFilterByPeriode(debut, fin), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/annuler/validation/{id}")
    public ResponseEntity<?> annulerValidation(@PathVariable("id") Long id) {
        return new ResponseEntity<>(demandeService.annulerValidation(id), HttpStatus.OK);
    }

    @GetMapping("/annuler/traitement/{id}")
    public ResponseEntity<?> annulerTraitement(@PathVariable("id") Long id) {
        return new ResponseEntity<>(demandeService.annulerTraitement(id), HttpStatus.OK);
    }

    @GetMapping("/annuler/signature/{id}")
    public ResponseEntity<?> annulerSignature(@PathVariable("id") Long id) {
        return new ResponseEntity<>(demandeService.annulerSignature(id), HttpStatus.OK);
    }

    @PostMapping("centre/new")
    public ResponseEntity<?> moveToNewCentre(@RequestBody DemandeDto dto) {
        try {
            if (dto == null) {
                this.logger.error("Le corps de l'objet paiement n'est pas indiqué");
                return new ResponseEntity<>("Le corps de l'objet paiement n'est pas indiqué", HttpStatus.BAD_REQUEST);
            }
            if (dto.getId() == null) {
                this.logger.error("L'identifiant de la demande dont le centre de traitement est à modifier n'est pas indiqué");
                return new ResponseEntity<>("L'identifiant de la demande dont le centre de traitement est à modifier n'est pas indiqué", HttpStatus.BAD_REQUEST);
            }
            DemandeDto demande = demandeService.findById(dto.getId());
            if (demande == null) {
                this.logger.error("L'identifiant de demande indiqué ne correspond à aucune demande");
                return new ResponseEntity<>("L'identifiant de demande indiqué ne correspond à aucune demande", HttpStatus.NOT_FOUND);
            }
            if (dto.getPointRetraitId() == null) {
                this.logger.error("L'identifiant du nouveau centre n'est pas renseigné");
                return new ResponseEntity<>("L'identifiant du nouveau centre n'est pas renseigné", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(demandeService.moveToNewPointTraitement(dto), HttpStatus.OK);


        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            
            return new ResponseEntity<>("Erreur interne :" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/encours/debut/{debut}/fin/{fin}/tribunal/{tribunal}/type-demande/{typeDemande}")
    public ResponseEntity<?> getDemandeEncoursFilterByPeriodeAndTypeDemandeAndPointRetrait(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin, @PathVariable("typeDemande") String typeDemande, @PathVariable("tribunal") Long tribunal) {
        try {
            return new ResponseEntity<>(demandeService.getDemandeEncoursFilterByPeriodeAndTypeDemandeAndPointRetrait(debut, fin, typeDemande, tribunal), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/suivi-traitement/debut/{debut}/fin/{fin}/delay/{delay}")
    public ResponseEntity<?> getCountDemandeByPeriodeAndDelay(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin, @PathVariable("delay") Integer delay) {
        try {
            return new ResponseEntity<>(demandeService.getCountDemandeByPeriodeAndDelay(debut, fin, delay), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/suivi-traitement/debut/{debut}/fin/{fin}/delay/{delay}/point-retrait-id/{pointRetraitId}")
    public ResponseEntity<?> getCountDemandeByPeriodeAndDelayAndPointRetrait(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut, @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin, @PathVariable("delay") Integer delay, @PathVariable("pointRetraitId") Integer pointRetraitId) {
        try {
            return new ResponseEntity<>(demandeService.getCountDemandeByPeriodeAndDelayAndPointRetrait(debut, fin, delay, pointRetraitId), HttpStatus.OK);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("suivi-demande-casier-b3")
    public ResponseEntity<?> suiviDemande(@RequestBody DemandeAtd dto, HttpServletRequest httpServletRequest) {
        try {
            if (dto == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumero_demande() == null) {
                return new ResponseEntity<>("Numero de demande obligatoire pour cette recherche", HttpStatus.BAD_REQUEST);
            }
            DemandeAtd response = demandeAtdService.rechercheParNumeroDemande(dto);
            if (response == null) {
                return new ResponseEntity<>(String.format("Aucune de demandetrouvée avec le numéro %s", dto.getNumero_demande()), HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("suivi-demande-casier-b1")
    public ResponseEntity<?> suiviDemandeB1(@RequestBody DemandeAtd dto, HttpServletRequest httpServletRequest) {
        try {
            if (dto == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumero_demande() == null) {
                return new ResponseEntity<>("Numero de demande obligatoire pour cette recherche", HttpStatus.BAD_REQUEST);
            }
            DemandeAtd response = demandeAtdService.rechercheParNumeroDemande(dto);
            if (response == null) {
                return new ResponseEntity<>(String.format("Aucune de demandetrouvée avec le numéro %s", dto.getNumero_demande()), HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("suivi-demande-casier-b2")
    public ResponseEntity<?> suiviDemandeB2(@RequestBody DemandeAtd dto, HttpServletRequest httpServletRequest) {
        try {
            if (dto == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            if (dto.getNumero_demande() == null) {
                return new ResponseEntity<>("Numero de demande obligatoire pour cette recherche", HttpStatus.BAD_REQUEST);
            }
            DemandeAtd response = demandeAtdService.rechercheParNumeroDemande(dto);
            if (response == null) {
                return new ResponseEntity<>(String.format("Aucune de demandetrouvée avec le numéro %s", dto.getNumero_demande()), HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("update-b3")
    private ResponseEntity<?> update(@RequestBody DemandeSite demandeSite) {
        System.err.println(demandeSite);
        try {
            return new ResponseEntity<>(demandeService.update(demandeSite), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/init-tmoney")
    private ResponseEntity<?> initPaiement(@RequestBody TmoneyRequestDto dto) {
        System.err.println(dto);
        try {
            return paiementService.payerTmoney(dto);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private DemandeDto convert(DemandeSite demande) {
        try {
            if (demande == null) {
                return null;
            }
            DemandeDto dto = DemandeDto.builder()
                    .nom(demande.getNom())
                    .prenom(demande.getPrenom())
                    .dateNaissance(demande.getDateNaissance())
                    .lieuNaissance(demande.getLieuNaissance())
                    .nombreEnfant(demande.getNombreEnfant())
                    .nomPere(demande.getNomPere())
                    .prenomPere(demande.getPrenomPere())
                    .nomMere(demande.getNomMere())
                    .prenomMere(demande.getPrenomMere())
                    .telephone(demande.getTelephone())
                    .lieuResidence(demande.getLieuResidence())
                    .nombreCopie(demande.getNombreCopie())
                    .numeroCarte(demande.getNumeroCarte())
                    .typePieceId(demande.getTypePiece().getId())
                    .numeroJugement(demande.getNumeroJugement())
                    .dateJugement(demande.getDateJugement())
                    .tribunalJugement(demande.getTribunalJugement())
                    .dateTranscriptionJugement(demande.getDateTranscriptionJugement())
                    .numeroTranscriptionJugement(demande.getNumeroTranscriptionJugement())
                    .numeroActeRectifie(demande.getNumeroActeRectifie())
                    .etatCivilActeRectifie(demande.getEtatCivilActeRectifie())
                    .dateActeRectifie(demande.getDateActeRectifie())
                    .nomJugement(demande.getNomJugement())
                    .prenomJugement(demande.getPrenomJugement())
                    .dateNaissanceJugement(demande.getDateNaissanceJugement())
                    .nomPereJugement(demande.getNomPereJugement())
                    .prenomPereJugement(demande.getPrenomPereJugement())
                    .nomMereJugement(demande.getNomMereJugement())
                    .prenomMereJugement(demande.getPrenomMereJugement())
                    .etatCivil(demande.getEtatCivil())
                    .numeroFeuillet(demande.getNumeroFeuillet())
                    .numeroRegistre(demande.getNumeroRegistre())
                    .numeroActe(demande.getNumeroActe())
                    .annee(demande.getAnnee())
                    .dateDelivranceCarte(demande.getDateDelivranceCarte())
                    .build();
            if (demande.getPaysNaissance() != null) {
                dto.setPaysNaissanceCode(demande.getPaysNaissance().getCode());
            }
            if (demande.getPaysResidence() != null) {
                dto.setPaysResidenceCode(demande.getPaysResidence().getCode());
            }
            if (demande.getPaysNationalite() != null) {
                dto.setPaysNationaliteCode(demande.getPaysNationalite().getCode());
            }
            if (demande.getSexe() != null) {
                dto.setSexeCode(demande.getSexe().getCode());
            }
            if (demande.getSituationMatrimoniale() != null) {
                dto.setSituationMatrimonialeId(demande.getSituationMatrimoniale().getId());
            }
            if (demande.getPrefectureNaissance() != null) {
                dto.setPrefectureNaissanceId(demande.getPrefectureNaissance().getId());
            }
            if (demande.getProfession() != null) {
                dto.setProfessionId(demande.getProfession().getId());
            }
            return dto;
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @GetMapping("/numero-demande/{numeroDemande}/numero-piece/{numeroPiece}")
    private ResponseEntity<?> recupererInfoB3(@PathVariable("numeroDemande") String encryptedNumeroDemande,
                                              @PathVariable("numeroPiece") String encryptedNumeroPiece) {
        String numeroPiece = CasierUtils.base64Decode(encryptedNumeroPiece);
        String numeroDemande = CasierUtils.base64Decode(encryptedNumeroDemande);
        try {
            return new ResponseEntity<>(demandeService.recupererInfoB3(numeroDemande, numeroPiece), HttpStatus.ACCEPTED);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return null;
        }

    }

    @GetMapping("/recupererDemandeB3/numero-demande/{numeroDemande}/numero-piece/{numeroPiece}")
    private ResponseEntity<?> recupererDemandeB3(@PathVariable("numeroDemande") String encryptedNumeroDemande,
                                                 @PathVariable("numeroPiece") String encryptedNumeroPiece) {
        String numeroPiece = CasierUtils.base64Decode(encryptedNumeroPiece);
        String numeroDemande = CasierUtils.base64Decode(encryptedNumeroDemande);
        try {
            return new ResponseEntity<>(demandeService.recupererDemandeB3(numeroDemande, numeroPiece), HttpStatus.CREATED);

        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return null;
        }

    }

    @PostMapping("/raccourci-search")
    private ResponseEntity<?> raccourciSearch(@RequestBody RaccourciRequest raccourciRequest) {
        System.err.println(raccourciRequest);
        try {
            if (raccourciRequest == null) {
                return new ResponseEntity<>("Le formulaire de la recherche est vide", HttpStatus.BAD_REQUEST);
            }
            return demandeAtdService.raccourciSearch(raccourciRequest);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @PostMapping("/raccourci-save")
    private ResponseEntity<?> raccourciSave(@RequestBody DemandeModele demande) {
        try {

            return demandeAtdService.raccourciSave(demande);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @PostMapping("/annuler/impression")
    public ResponseEntity<?> annulerTraitement(@RequestBody DemandeDto dto) {
        return new ResponseEntity<>(demandeService.annulerTraitement(dto.getId()), HttpStatus.OK);
    }

    @PostMapping("profession/update")
    public ResponseEntity<?> updateDemande(@RequestBody UpdateObject object, HttpServletRequest httpServletRequest) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(demandeService.updateProfession(object), HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("situation-matrimoniale/update")
    public ResponseEntity<?> updateSituationMatrimoniale(@RequestBody UpdateObject object, HttpServletRequest httpServletRequest) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(demandeService.updateSituationMatrimoniale(object), HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("init-fichiers")
    public ResponseEntity<?> initFichier() {
        try {

            return new ResponseEntity<>(demandeAtdService.updateOldDemande(), HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("nationalite/update")
    public ResponseEntity<?> updateNationalite(@RequestBody UpdateObject object, HttpServletRequest httpServletRequest) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(demandeService.updateNationalite(object), HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("prefecture/update")
    public ResponseEntity<?> updatePrefecture(@RequestBody UpdateObject object, HttpServletRequest httpServletRequest) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(demandeService.updatePrefectureNaissance(object), HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("pays-naissance/update")
    public ResponseEntity<?> updatePaysNaissance(@RequestBody UpdateObject object, HttpServletRequest httpServletRequest) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(demandeService.updatePaysNaissnace(object), HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("pays-residence/update")
    public ResponseEntity<?> updatePaysResidence(@RequestBody UpdateObject object, HttpServletRequest httpServletRequest) {
        try {
            if (object == null) {
                return new ResponseEntity<>("Le corps de la requête est vide", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(demandeService.updatePaysResidence(object), HttpStatus.CREATED);
        } catch (Exception e) {
            
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("historique/get/{id}")
    public ResponseEntity<?> getHistorique(@PathVariable("id") Long id) {
        try {
            return historiqueService.getHistoriques(id);
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}