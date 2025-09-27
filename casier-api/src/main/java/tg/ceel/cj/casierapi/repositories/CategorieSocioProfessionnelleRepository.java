package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.CategorieSocioProfessionnelle;

import java.util.Optional;

public interface CategorieSocioProfessionnelleRepository extends JpaRepository<CategorieSocioProfessionnelle, Integer> {
    Optional<CategorieSocioProfessionnelle> findById(Integer id);
    boolean existsById(Integer id);
}