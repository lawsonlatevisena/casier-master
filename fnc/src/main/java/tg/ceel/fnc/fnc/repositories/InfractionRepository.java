package tg.ceel.fnc.fnc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.fnc.fnc.entities.Infraction;

public interface InfractionRepository extends JpaRepository<Infraction, Long> {
}