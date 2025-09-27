package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.FloozTransactionDto;
import tg.ceel.cj.casierapi.dto.PaiementActiveDto;
import tg.ceel.cj.casierapi.dto.TmoneyNotificationDto;
import tg.ceel.cj.casierapi.services.PaiementActiveService;
import tg.ceel.cj.casierapi.utils.ModelPaiementActive;

@RestController
@RequestMapping("paiement-actives")
public class PaiementActiveController {
    Logger logger = LoggerFactory.getLogger(PaiementActiveController.class);
    private final PaiementActiveService paiementActiveService;

    public PaiementActiveController(PaiementActiveService paiementActiveService) {
        this.paiementActiveService = paiementActiveService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(paiementActiveService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("numeroDemande/")
    public ResponseEntity<?> activerPaiement(@RequestBody String numeroDemande) {
        try {
            ModelPaiementActive modelPaiementActive  =  paiementActiveService.activerPaiement(numeroDemande);
            if (modelPaiementActive ==  null){
                return  new ResponseEntity<>("Aucun paiement ne correspond à cette demande ou plusieurs paiements ont  été trouvés pour cette demande ", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return  new ResponseEntity<>(modelPaiementActive, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> saveActiverPaiement(@RequestBody PaiementActiveDto paiementActiveDto) {
        try {
            PaiementActiveDto paiementActiveSaved  =  paiementActiveService.save(paiementActiveDto);
            return  new ResponseEntity<>(paiementActiveSaved, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
