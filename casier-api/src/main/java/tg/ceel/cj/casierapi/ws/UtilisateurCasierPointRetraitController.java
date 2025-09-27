package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.RoleDto;
import tg.ceel.cj.casierapi.dto.UtilisateurCasierPointRetraitDto;
import tg.ceel.cj.casierapi.services.UtilisateurCasierPointRetraitService;

@RestController
@RequestMapping("userCasier-pointRetraits")
public class UtilisateurCasierPointRetraitController {
    Logger logger = LoggerFactory.getLogger(UtilisateurCasierPointRetraitController.class);
    private final UtilisateurCasierPointRetraitService utilisateurCasierPointRetraitService;

    public UtilisateurCasierPointRetraitController(UtilisateurCasierPointRetraitService utilisateurCasierPointRetraitService) {
        this.utilisateurCasierPointRetraitService = utilisateurCasierPointRetraitService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(utilisateurCasierPointRetraitService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody UtilisateurCasierPointRetraitDto utilisateurCasierPointRetraitDto) {
        if (utilisateurCasierPointRetraitDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        UtilisateurCasierPointRetraitDto utilisateurCasierPointRetraitDtoSaved = this.utilisateurCasierPointRetraitService.save(utilisateurCasierPointRetraitDto);
        return new ResponseEntity<UtilisateurCasierPointRetraitDto>(utilisateurCasierPointRetraitDtoSaved, HttpStatus.OK);
    }


    @GetMapping("active/{id}")
    public ResponseEntity<?> findActiveByCaisierUserId(@PathVariable("id") Long id ) {
        try {
            return new ResponseEntity<>(utilisateurCasierPointRetraitService.findActive(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("allByUserId/{id}")
    public ResponseEntity<?> findAllByCaisierUserId(@PathVariable("id") Long id ) {
        try {
            return new ResponseEntity<>(utilisateurCasierPointRetraitService.findAllByUserId(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("desactive")
    public ResponseEntity<?> findAllNonActive() {
        try {
            return new ResponseEntity<>(utilisateurCasierPointRetraitService.findAllNonActive(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
