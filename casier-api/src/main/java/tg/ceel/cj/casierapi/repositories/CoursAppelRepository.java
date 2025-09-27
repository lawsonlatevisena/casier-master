package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.CoursAppel;

import java.util.Optional;

public interface CoursAppelRepository extends JpaRepository<CoursAppel, Integer> {
    Optional<CoursAppel> findById(Integer id);
}