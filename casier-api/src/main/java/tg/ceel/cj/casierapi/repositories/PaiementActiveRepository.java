package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.PaiementActive;

public interface PaiementActiveRepository extends JpaRepository<PaiementActive, Long> {
}