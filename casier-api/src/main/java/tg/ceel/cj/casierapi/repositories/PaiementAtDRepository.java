package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.Paiement;

public interface PaiementAtDRepository extends JpaRepository<Paiement, Long> {
}