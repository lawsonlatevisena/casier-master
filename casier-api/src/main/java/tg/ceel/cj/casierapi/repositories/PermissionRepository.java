package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.Permission;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, String> {

    @Query(value = " select p from Permission p group by p.category.label, p.code" )
    List<Permission> findAllGroupByCategorie();






}