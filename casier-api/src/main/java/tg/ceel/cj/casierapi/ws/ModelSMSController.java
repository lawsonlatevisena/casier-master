package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.ModelSMSDto;
import tg.ceel.cj.casierapi.services.ModelSMSService;

@RestController
@RequestMapping("model-sms")
public class ModelSMSController {
    private final ModelSMSService modelSMSService;
    Logger logger = LoggerFactory.getLogger(ModelSMSController.class);

    public ModelSMSController(ModelSMSService modelSMSService) {
        this.modelSMSService = modelSMSService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(modelSMSService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody ModelSMSDto modelSMSDto) {
        if (modelSMSDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(modelSMSService.save(modelSMSDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur: ",e);
            return new ResponseEntity<>("Erreur intern : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("nom/{nom}")
    public ResponseEntity<?> update(@PathVariable("nom") String nom , @RequestBody ModelSMSDto modelSMSDto) {
        if (modelSMSDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.modelSMSService.update(nom,modelSMSDto), HttpStatus.OK);
    }
}
