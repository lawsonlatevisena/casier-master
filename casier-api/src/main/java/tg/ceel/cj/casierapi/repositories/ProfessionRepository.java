package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.Profession;

import java.util.List;

public interface ProfessionRepository extends JpaRepository<Profession, Integer> {
    @Query("select p from Profession  p where p.categorieSocioProfessionnelle.id=?1")
    List<Profession> findByCatedorieId(Integer categorieId);
}