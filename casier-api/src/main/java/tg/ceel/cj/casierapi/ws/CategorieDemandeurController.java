package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.CategorieDemandeurB2Dto;
import tg.ceel.cj.casierapi.dto.PaysDto;
import tg.ceel.cj.casierapi.services.CategorieDemandeurB2Service;

@RestController
@RequestMapping("categorie-demandeurs")
public class CategorieDemandeurController {
    Logger logger = LoggerFactory.getLogger(CategorieDemandeurController.class);
    private final CategorieDemandeurB2Service categorieDemandeurB2Service;

    public CategorieDemandeurController(CategorieDemandeurB2Service categorieDemandeurB2Service) {
        this.categorieDemandeurB2Service = categorieDemandeurB2Service;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(categorieDemandeurB2Service.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id , @RequestBody CategorieDemandeurB2Dto categorieDemandeurB2Dto) {
        if (categorieDemandeurB2Dto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        CategorieDemandeurB2Dto categorieDemandeurB2DtoUpdate = this.categorieDemandeurB2Service.update(categorieDemandeurB2Dto, id);
        return new ResponseEntity<>(categorieDemandeurB2DtoUpdate, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CategorieDemandeurB2Dto categorieDemandeurB2Dto) {
        if (categorieDemandeurB2Dto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        CategorieDemandeurB2Dto categorieDemandeurB2DtoSaved = this.categorieDemandeurB2Service.save(categorieDemandeurB2Dto);
        return new ResponseEntity<>(categorieDemandeurB2DtoSaved, HttpStatus.OK);
    }
}
