package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.ModelSMS;
import tg.ceel.cj.casierapi.entities.Variable;

import java.util.Optional;

public interface ModelSMSRepository extends JpaRepository<ModelSMS, String> {
    Optional<ModelSMS> findByCode(String code);
    Optional<ModelSMS> findByNom(String nom);
    Optional<ModelSMS> findTopByOrderByCodeDesc();
}