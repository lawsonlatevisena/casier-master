package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.RolePermissionDto;
import tg.ceel.cj.casierapi.entities.RolesPermissionsPK;
import tg.ceel.cj.casierapi.services.RolePermissionService;

@RestController
@RequestMapping("role-permissions")
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<?> findAllByRole(@PathVariable Long id){
        return new ResponseEntity<>(this.rolePermissionService.findAllByRole(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody RolePermissionDto rolePermissionDto){
        if(rolePermissionDto == null){
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        RolePermissionDto profilRoleDtoSaved = this.rolePermissionService.save(rolePermissionDto);
        return new ResponseEntity<>(profilRoleDtoSaved,HttpStatus.CREATED);
    }


    @PostMapping("/delete/")
    public ResponseEntity<?> delete(@RequestBody RolesPermissionsPK id){
        this.rolePermissionService.delete(id);
        return new ResponseEntity<>("Suppression effectué avec succès!", HttpStatus.OK);
    }
}
