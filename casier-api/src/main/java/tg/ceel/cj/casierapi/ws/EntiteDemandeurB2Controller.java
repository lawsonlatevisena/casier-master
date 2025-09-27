package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.CategorieDemandeurB2Dto;
import tg.ceel.cj.casierapi.dto.EntiteDemandeurB2Dto;
import tg.ceel.cj.casierapi.services.EntiteDemandeurB2Service;

@RestController
@RequestMapping("entite-demandeur-b2s")
public class EntiteDemandeurB2Controller {
    Logger logger = LoggerFactory.getLogger(PrefectureController.class);
    private final EntiteDemandeurB2Service entiteDemandeurB2Service;

    public EntiteDemandeurB2Controller(EntiteDemandeurB2Service entiteDemandeurB2Service) {
        this.entiteDemandeurB2Service = entiteDemandeurB2Service;
    }


    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(entiteDemandeurB2Service.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id , @RequestBody EntiteDemandeurB2Dto entiteDemandeurB2Dto) {
        if (entiteDemandeurB2Dto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        EntiteDemandeurB2Dto entiteDemandeurB2Dto1 = this.entiteDemandeurB2Service.update(entiteDemandeurB2Dto, id);
        return new ResponseEntity<>(entiteDemandeurB2Dto1, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody EntiteDemandeurB2Dto entiteDemandeurB2Dto) {
        if (entiteDemandeurB2Dto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        EntiteDemandeurB2Dto entiteDemandeurB2DtoSaved = this.entiteDemandeurB2Service.save(entiteDemandeurB2Dto);
        return new ResponseEntity<>(entiteDemandeurB2DtoSaved, HttpStatus.OK);
    }



}
