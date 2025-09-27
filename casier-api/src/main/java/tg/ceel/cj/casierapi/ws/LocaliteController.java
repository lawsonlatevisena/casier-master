package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.CategorieSocioProfessionnelleDto;
import tg.ceel.cj.casierapi.dto.LocaliteDto;
import tg.ceel.cj.casierapi.services.LocaliteService;

@RestController
@RequestMapping("localites")
public class LocaliteController {
    Logger logger = LoggerFactory.getLogger(LocaliteController.class);
    private  final LocaliteService localiteService;

    public LocaliteController(LocaliteService localiteService) {
        this.localiteService = localiteService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(localiteService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody LocaliteDto localiteDto) {
        if (localiteDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(localiteService.save(localiteDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur: ",e);
            return new ResponseEntity<>("Erreur intern : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("id/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id , @RequestBody LocaliteDto localiteDto) {
        if (localiteDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.localiteService.update(id,localiteDto), HttpStatus.OK);
    }
}
