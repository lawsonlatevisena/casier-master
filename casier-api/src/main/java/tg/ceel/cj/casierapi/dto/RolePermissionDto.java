package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tg.ceel.cj.casierapi.entities.Permission;
import tg.ceel.cj.casierapi.entities.Role;
import tg.ceel.cj.casierapi.entities.RolesPermissionsPK;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDto {

    private String permissionCode;
    private Long roleId;
    private  Boolean actif;
}
