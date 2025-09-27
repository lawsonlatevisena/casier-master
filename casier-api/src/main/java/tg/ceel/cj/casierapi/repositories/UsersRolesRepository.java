package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.UserRolePK;
import tg.ceel.cj.casierapi.entities.UsersRoles;

import java.util.List;

public interface UsersRolesRepository extends JpaRepository<UsersRoles, UserRolePK> {

    @Query(value = "select r from UsersRoles r where r.user.id =?1")
    List<UsersRoles> findAllByUserId(Long userId);

    @Query(value = "select r from UsersRoles r where r.role.id=?1 and r.user.id =?2")
   UsersRoles findByRoleIdAndUserId(Long roleId,  Long userId);
}