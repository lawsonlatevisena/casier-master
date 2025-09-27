package tg.ceel.fnc.fnc.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tg.ceel.fnc.fnc.model.Demande;
import tg.ceel.fnc.fnc.service.CondamnationService;

@RestController
@RequestMapping("condamnations")
public class CondamnationController {
    Logger logger = LoggerFactory.getLogger(CondamnationController.class);
    private final CondamnationService condamnationService;

    public CondamnationController(CondamnationService condamnationService) {
        this.condamnationService = condamnationService;
    }

    @PostMapping()
    public ResponseEntity<?> getCondamnation(@RequestBody Demande demande) {
        try {
            return condamnationService.findCondamnation(demande);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
