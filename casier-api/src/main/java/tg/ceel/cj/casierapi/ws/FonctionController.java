package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.FonctionDto;
import tg.ceel.cj.casierapi.dto.RoleDto;
import tg.ceel.cj.casierapi.entities.Fonction;
import tg.ceel.cj.casierapi.services.FonctionService;

@RestController
@RequestMapping("fonctions")
public class FonctionController {
    Logger logger = LoggerFactory.getLogger(PrefectureController.class);
    private final FonctionService fonctionService;

    public FonctionController(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(fonctionService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id , @RequestBody FonctionDto fonctionDto) {
        if (fonctionDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        FonctionDto fonctionUpdate = this.fonctionService.update(id, fonctionDto);
        return new ResponseEntity<>(fonctionUpdate, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody FonctionDto fonctionDto) {
        if (fonctionDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        FonctionDto fonctionSaved = this.fonctionService.save(fonctionDto);
        return new ResponseEntity<>(fonctionSaved, HttpStatus.OK);
    }



}
