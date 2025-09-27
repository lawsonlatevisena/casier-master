package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.PermissionDto;
import tg.ceel.cj.casierapi.dto.RoleDto;
import tg.ceel.cj.casierapi.dto.UsersRolesDto;
import tg.ceel.cj.casierapi.entities.UsersRoles;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAll();

    void delete(Long roleId, Long userId);

    List<RoleDto> findAllActifs();
    List<UsersRolesDto> findAllByUser(Long userId);

    RoleDto save(RoleDto roleDto);

    RoleDto update(Long id, RoleDto roleDto);

    UsersRolesDto saveUserRole(UsersRoles usersRole);
}
