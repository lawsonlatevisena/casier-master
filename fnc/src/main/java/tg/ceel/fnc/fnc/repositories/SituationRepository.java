package tg.ceel.fnc.fnc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.fnc.fnc.entities.Situation;

public interface SituationRepository extends JpaRepository<Situation, Long> {
}