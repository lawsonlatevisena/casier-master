package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.TmoneyRequest;

public interface TmoneyRequestRepository extends JpaRepository<TmoneyRequest, Long> {

    TmoneyRequest findByDemandeId(Long id);
}