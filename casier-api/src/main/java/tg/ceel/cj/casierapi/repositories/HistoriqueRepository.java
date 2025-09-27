package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.Historique;

import java.util.List;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

    List<Historique> findByIdDemande(long idDemande);
    List<Historique> findByUsername(String username);
}