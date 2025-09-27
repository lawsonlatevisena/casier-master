package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import tg.ceel.cj.casierapi.entities.EntiteDemandeurB2;

public interface EntiteDemandeurB2Repository extends JpaRepository<EntiteDemandeurB2, Integer> {
    EntiteDemandeurB2 findByLibelleEqualsIgnoreCase(@NonNull String libelle);

}