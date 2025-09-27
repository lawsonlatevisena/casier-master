package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.ServiceDemandeurB2;

public interface ServiceDemandeurB2Repository extends JpaRepository<ServiceDemandeurB2, Integer> {
    ServiceDemandeurB2 findByLibelleEqualsIgnoreCase(String libelle);

}