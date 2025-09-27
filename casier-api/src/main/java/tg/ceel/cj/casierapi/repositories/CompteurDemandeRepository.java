package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.CompteurDemande;
import tg.ceel.cj.casierapi.entities.TypeDemande;

import java.util.Optional;

public interface CompteurDemandeRepository extends JpaRepository<CompteurDemande, Long> {
    Optional<CompteurDemande> findByAnneeAndTypeDemande(Integer annee, TypeDemande typeDemande);
}