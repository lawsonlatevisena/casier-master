package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.LogEvent;

public interface LogEventRepository extends JpaRepository<LogEvent, Long> {
}