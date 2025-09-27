package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.Localite;
import tg.ceel.cj.casierapi.ints.LocaliteInt;

import java.util.List;

public interface LocaliteRepository extends JpaRepository<Localite, Integer> {
    @Query(value = "select l.id ,l.libelle  from localites l",nativeQuery = true)
    List<LocaliteInt> findLocalites();
}