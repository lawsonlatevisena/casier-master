package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.PermissionDto;
import tg.ceel.cj.casierapi.dto.RolePermissionDto;
import tg.ceel.cj.casierapi.entities.RolesPermissions;
import tg.ceel.cj.casierapi.entities.RolesPermissionsPK;

import java.util.List;

public interface RolePermissionService {
    List<RolePermissionDto> findAll();

    List<RolePermissionDto> findAllByRole(Long role);

    RolePermissionDto save (RolePermissionDto rolePermissionDto);


    void delete(RolesPermissionsPK id);
}
