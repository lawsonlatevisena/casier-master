package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.FonctionDto;
import tg.ceel.cj.casierapi.dto.ServiceDemandeurB2Dto;
import tg.ceel.cj.casierapi.services.ServiceDemandeurB2Service;

@RestController
@RequestMapping("service-demandeur-b2s")
public class ServiceDemandeurB2Controller {
    Logger logger = LoggerFactory.getLogger(PrefectureController.class);
    private final ServiceDemandeurB2Service serviceDemandeurB2Service;

    public ServiceDemandeurB2Controller(ServiceDemandeurB2Service serviceDemandeurB2Service) {
        this.serviceDemandeurB2Service = serviceDemandeurB2Service;
    }


    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(serviceDemandeurB2Service.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id , @RequestBody ServiceDemandeurB2Dto serviceDemandeurB2Dto) {
        if (serviceDemandeurB2Dto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        ServiceDemandeurB2Dto serviceDemandeurUpdate = this.serviceDemandeurB2Service.update(id, serviceDemandeurB2Dto);
        return new ResponseEntity<>(serviceDemandeurUpdate, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody ServiceDemandeurB2Dto serviceDemandeurB2Dto) {
        if (serviceDemandeurB2Dto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        ServiceDemandeurB2Dto serviceDemandeurB2Saved = this.serviceDemandeurB2Service.save(serviceDemandeurB2Dto);
        return new ResponseEntity<>(serviceDemandeurB2Saved, HttpStatus.OK);
    }



}
