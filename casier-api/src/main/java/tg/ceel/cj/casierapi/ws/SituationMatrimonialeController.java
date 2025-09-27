package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.PaysDto;
import tg.ceel.cj.casierapi.services.SituationMatrimonialeService;

@RestController
@RequestMapping("situation-matrimoniales")
public class SituationMatrimonialeController {
    Logger logger = LoggerFactory.getLogger(SituationMatrimonialeController.class);
    private final SituationMatrimonialeService situationMatrimonialeService;

    public SituationMatrimonialeController(SituationMatrimonialeService situationMatrimonialeService) {
        this.situationMatrimonialeService = situationMatrimonialeService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(situationMatrimonialeService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
