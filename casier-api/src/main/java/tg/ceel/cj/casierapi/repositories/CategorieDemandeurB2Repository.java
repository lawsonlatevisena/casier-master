package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.CategorieDemandeurB2;

public interface CategorieDemandeurB2Repository extends JpaRepository<CategorieDemandeurB2, Integer> {
    CategorieDemandeurB2 findByLibelleEqualsIgnoreCase(String libelle);

}