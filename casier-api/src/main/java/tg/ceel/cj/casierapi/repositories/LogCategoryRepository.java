package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.LogCategory;

public interface LogCategoryRepository extends JpaRepository<LogCategory, String> {
}