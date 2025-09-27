package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.VariableDto;
import tg.ceel.cj.casierapi.services.VariableService;

@RestController
@RequestMapping("variables")
public class VariableController {
    private final VariableService variableService;
    Logger logger = LoggerFactory.getLogger(VariableController.class);

    public VariableController(VariableService variableService) {
        this.variableService = variableService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(variableService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody VariableDto variableDto) {
        if (variableDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        try {
            VariableDto savedVariable = variableService.save(variableDto);
            if (savedVariable != null) {
                return new ResponseEntity<>(savedVariable, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Le Nom de variable "+variableDto.getName()+" existe déjà", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur: ",e);
            return new ResponseEntity<>("Erreur intern : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("name/{name}")
    public ResponseEntity<?> update(@PathVariable("name") String name , @RequestBody VariableDto variableDto) {
        if (variableDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        VariableDto updatedVariable = this.variableService.update(name,variableDto);
        if (updatedVariable != null) {
            return new ResponseEntity<>(updatedVariable, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Erreur lors de la modification de la variable", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
