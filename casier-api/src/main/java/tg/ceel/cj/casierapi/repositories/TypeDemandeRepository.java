package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.TypeDemande;

import java.util.Optional;

public interface TypeDemandeRepository extends JpaRepository<TypeDemande, Integer> {
    TypeDemande findByCode(String code);
    Optional<TypeDemande> findByLibelle(String libelle);
}