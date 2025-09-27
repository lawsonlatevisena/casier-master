package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.FonctionDto;
import tg.ceel.cj.casierapi.dto.PaysDto;
import tg.ceel.cj.casierapi.entities.Pays;
import tg.ceel.cj.casierapi.services.PaysService;

@RestController
@RequestMapping("pays")
public class PaysController {
    Logger logger = LoggerFactory.getLogger(PaysController.class);
    private final PaysService paysService;

    public PaysController(PaysService paysService) {
        this.paysService = paysService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(paysService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> update(@PathVariable("code") String code , @RequestBody PaysDto paysDto) {
        if (paysDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        PaysDto paysUpdate = this.paysService.update(code, paysDto);
        return new ResponseEntity<>(paysUpdate, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody PaysDto paysDto) {
        if (paysDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        PaysDto paysSaved = this.paysService.save(paysDto);
        return new ResponseEntity<>(paysSaved, HttpStatus.OK);
    }
}
