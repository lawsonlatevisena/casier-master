package tg.ceel.fnc.fnc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.fnc.fnc.entities.Prison;

public interface PrisonRepository extends JpaRepository<Prison, Long> {
}