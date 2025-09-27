package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;
import tg.ceel.cj.casierapi.entities.Permission;
import tg.ceel.cj.casierapi.entities.Role;
import tg.ceel.cj.casierapi.entities.RolesPermissions;
import tg.ceel.cj.casierapi.entities.RolesPermissionsPK;

import java.util.List;

public interface RolesPermissionsRepository extends JpaRepository<RolesPermissions, RolesPermissionsPK> {


    @Query(value = "select count (r) From RolesPermissions  r where r.role in ?1")
    Long countByPermission(List<Role> roles);

    @Query("select p from RolesPermissions p where p.role.id = ?1 and p.actif = true")
    List<RolesPermissions> findAllByRole(Long profil);

    @Query("select p from RolesPermissions p where p.role.id = ?1 and p.actif = true")
    List<RolesPermissions> findAllPermissionByRole(Long profil);
}