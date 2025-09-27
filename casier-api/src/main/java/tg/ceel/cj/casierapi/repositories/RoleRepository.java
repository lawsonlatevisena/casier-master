package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.Role;
import tg.ceel.cj.casierapi.entities.User;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select r.role from UsersRoles r where r.user.id=?1")
    List<Role>  findByUser(Long userId);

    @Query(value = "select r from Role r where r.active = true")
    List<Role>  findAllRoles();
}