package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.Permission;
import tg.ceel.cj.casierapi.entities.PermissionCategory;

import java.util.Date;
import java.util.List;

public interface PermissionCategoryRepository extends JpaRepository<PermissionCategory, String> {

}