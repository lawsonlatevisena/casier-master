package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.CompteurTraitement;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.entities.TypeDemande;

public interface CompteurTraitementRepository extends JpaRepository<CompteurTraitement, Long> {
    @Query(value = "SELECT c FROM CompteurTraitement c WHERE c.annee = ?1 AND c.typeDemande = ?2 AND c.pointRetrait = ?3")
    CompteurTraitement getLastValue(Integer annee, TypeDemande typeDemande, PointRetrait pointRetrait);
}