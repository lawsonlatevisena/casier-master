package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.MenuItem;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, String> {
    List<MenuItem> findByPermissionCodeAndParentItemCode(String code,String parentCode);
    List<MenuItem> findByParentItemCode(String parentCode);
    MenuItem findByCode(String code);
}