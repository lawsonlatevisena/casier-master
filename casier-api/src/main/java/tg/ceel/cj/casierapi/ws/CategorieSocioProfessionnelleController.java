package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.CategorieSocioProfessionnelleDto;
import tg.ceel.cj.casierapi.services.CategorieSocioProfessionnelleService;

@RestController
@RequestMapping("categories-socio-professionnelles")
public class CategorieSocioProfessionnelleController {
    Logger logger = LoggerFactory.getLogger(CategorieSocioProfessionnelleController.class);
    private  final CategorieSocioProfessionnelleService categorieSocioProfessionnelleService;

    public CategorieSocioProfessionnelleController(CategorieSocioProfessionnelleService categorieSocioProfessionnelleService) {
        this.categorieSocioProfessionnelleService = categorieSocioProfessionnelleService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(categorieSocioProfessionnelleService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> saveCategorieSocioProfessionnelle(@RequestBody CategorieSocioProfessionnelleDto categorieSocioProfessionnelleDto) {
        if (categorieSocioProfessionnelleDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(categorieSocioProfessionnelleService.save(categorieSocioProfessionnelleDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur: ",e);
            return new ResponseEntity<>("Erreur intern : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("id/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id , @RequestBody CategorieSocioProfessionnelleDto categorieSocioProfessionnelleDto) {
        if (categorieSocioProfessionnelleDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.categorieSocioProfessionnelleService.update(id,categorieSocioProfessionnelleDto), HttpStatus.OK);
    }
}
