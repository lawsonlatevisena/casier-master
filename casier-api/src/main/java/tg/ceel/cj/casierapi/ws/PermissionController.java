package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.services.PermissionService;

@RestController
@RequestMapping("permissions")
public class PermissionController {
    Logger logger = LoggerFactory.getLogger(PrefectureController.class);
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }



    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(permissionService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> delete(@PathVariable("code") String code){
        this.permissionService.delete(code);
        return new ResponseEntity<>("Suppression effectué avec succès!", HttpStatus.OK);
    }
}
