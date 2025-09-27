package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.RoleDto;
import tg.ceel.cj.casierapi.services.TypeDemandeService;

@RestController
@RequestMapping("type-demandes")
public class TypeDemandeController {
    Logger logger = LoggerFactory.getLogger(PrefectureController.class);
    private final TypeDemandeService  typeDemandeService;

    public TypeDemandeController(TypeDemandeService typeDemandeService) {
        this.typeDemandeService = typeDemandeService;
    }


    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(typeDemandeService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
