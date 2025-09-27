package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.AutrePointRetrait;

import java.util.Optional;

public interface AutrePointRetraitRepository extends JpaRepository<AutrePointRetrait, Long> {
    Optional<AutrePointRetrait> findById(String code);
}