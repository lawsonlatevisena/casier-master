package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.CoursAppelDto;
import tg.ceel.cj.casierapi.services.CoursAppelService;

@RestController
@RequestMapping(value = "cours-d-appels")
public class CoursAppelController {
    Logger logger = LoggerFactory.getLogger(CoursAppelController.class);
    private  final CoursAppelService coursAppelService;
    public CoursAppelController(CoursAppelService coursAppelService) {
        this.coursAppelService = coursAppelService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(coursAppelService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CoursAppelDto coursAppelDto) {
        if (coursAppelDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(coursAppelService.save(coursAppelDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur: ",e);
            return new ResponseEntity<>("Erreur intern : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("id/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id , @RequestBody CoursAppelDto coursAppelDto) {
        if (coursAppelDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.coursAppelService.update(id,coursAppelDto), HttpStatus.OK);
    }
}
