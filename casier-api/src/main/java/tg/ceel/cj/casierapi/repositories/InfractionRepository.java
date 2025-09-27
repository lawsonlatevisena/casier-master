package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.Infraction;

public interface InfractionRepository extends JpaRepository<Infraction, Long> {
}