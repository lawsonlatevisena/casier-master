package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.SituationMatrimoniale;

import java.util.Optional;

public interface SituationMatrimonialeRepository extends JpaRepository<SituationMatrimoniale, Integer> {
    Optional<SituationMatrimoniale> findByLibelle(String libelle);
}