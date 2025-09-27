package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.FonctionDto;
import tg.ceel.cj.casierapi.dto.PointRetraitDto;
import tg.ceel.cj.casierapi.dto.UsersRolesDto;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.services.PointRetraitService;

@RestController
@RequestMapping("point-retraits")
public class PointRetraitController {
    Logger logger = LoggerFactory.getLogger(PrefectureController.class);
    private final PointRetraitService pointRetraitService;

    public PointRetraitController(PointRetraitService pointRetraitService) {
        this.pointRetraitService = pointRetraitService;
    }


    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(pointRetraitService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findAllJuridiction() {
        try {
            return new ResponseEntity<>(pointRetraitService.getAllJuridictions(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("getAllByTypeJuridiction/")
    public ResponseEntity<?> getAllJuridictionByTypeAutrePointRetrait() {
        try {
            return new ResponseEntity<>(pointRetraitService.getAllJuridictionByTypeAutrePointRetrait(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id , @RequestBody PointRetraitDto pointRetraitDto) {
        if (pointRetraitDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        PointRetraitDto pointRetraitUpdate = this.pointRetraitService.update(id, pointRetraitDto);
        return new ResponseEntity<>(pointRetraitUpdate, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody PointRetraitDto pointRetraitDto) {
        if (pointRetraitDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        PointRetraitDto pointRetraitSaved = this.pointRetraitService.save(pointRetraitDto);
        return new ResponseEntity<>(pointRetraitSaved, HttpStatus.OK);
    }


}
