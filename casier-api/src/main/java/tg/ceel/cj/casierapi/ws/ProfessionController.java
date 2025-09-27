package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.CategorieSocioProfessionnelleDto;
import tg.ceel.cj.casierapi.dto.ProfessionDto;
import tg.ceel.cj.casierapi.services.ProfessionService;

@RestController
@RequestMapping("professions")
public class ProfessionController {
    Logger logger = LoggerFactory.getLogger(ProfessionController.class);
    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping("/{idCategorie}")

    public ResponseEntity<?> getProfessions(@PathVariable("idCategorie") Integer idCategorie) {
        try {
            return new ResponseEntity<>(professionService.getAllByCategorie(idCategorie), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur: ",e);
            return new ResponseEntity<>("Erreur intern : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("")
    public ResponseEntity<?> getAllProfessions() {
        try {
            return new ResponseEntity<>(professionService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur: ",e);
            return new ResponseEntity<>("Erreur intern : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody ProfessionDto professionDto) {
        if (professionDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(professionService.save(professionDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur: ",e);
            return new ResponseEntity<>("Erreur intern : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ProfessionDto professionDto) {
        if (professionDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        try {

            return new ResponseEntity<>(professionService.update(id,professionDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur: ",e);
            return new ResponseEntity<>("Erreur intern : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
