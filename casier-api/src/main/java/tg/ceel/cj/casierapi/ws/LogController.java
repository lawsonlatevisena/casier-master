package tg.ceel.cj.casierapi.ws;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tg.ceel.cj.casierapi.services.LogService;

import java.util.Date;

@RestController
@RequestMapping("/logs")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }
    @GetMapping(value = "/demande/{id}")

    public ResponseEntity<?> loadLogsByDemandeId(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(logService.findAllByDemandeId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors du chargement des logs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/debut/{debut}/fin/{fin}")
    public ResponseEntity<?> findAllByPeriode(@PathVariable("debut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date debut,
                                              @PathVariable("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        return new ResponseEntity<>(logService.findAllByPeriode(debut, fin),  HttpStatus.OK);
    }

}
