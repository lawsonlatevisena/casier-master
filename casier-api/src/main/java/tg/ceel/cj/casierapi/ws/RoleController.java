package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.RoleDto;
import tg.ceel.cj.casierapi.dto.UsersRolesDto;
import tg.ceel.cj.casierapi.dto.UtilisateurCasierDto;
import tg.ceel.cj.casierapi.services.RoleService;

@RestController
@RequestMapping("roles")
public class RoleController {
    Logger logger = LoggerFactory.getLogger(PrefectureController.class);
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("actifs")
    public ResponseEntity<?> findAllActifs() {
        try {
            return new ResponseEntity<>(roleService.findAllActifs(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("userId/{userId}")
    public ResponseEntity<?> findAllByUser(@PathVariable("userId") Long userId ) {
        try {
            return new ResponseEntity<>(roleService.findAllByUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id , @RequestBody RoleDto roleDto) {
        if (roleDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        RoleDto roleUpdate = this.roleService.update(id, roleDto);
        return new ResponseEntity<RoleDto>(roleUpdate, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody RoleDto roleDto) {
        if (roleDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        RoleDto roleSaved = this.roleService.save(roleDto);
        return new ResponseEntity<RoleDto>(roleSaved, HttpStatus.OK);
    }

    @DeleteMapping("/roleId/{roleId}/userId/{userId}")
    public ResponseEntity<?> delete(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId){
        this.roleService.delete(roleId,userId );
        return new ResponseEntity<>("Suppression effectué avec succès!", HttpStatus.OK);
    }


/*    @PostMapping ("userRole")
    public ResponseEntity<?> addUserRole(@RequestBody UsersRolesDto usersRolesDto) {
        try {
            return new ResponseEntity<>(roleService.saveUserRole(usersRolesDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
