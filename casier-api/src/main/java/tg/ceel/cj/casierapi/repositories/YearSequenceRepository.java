package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.YearSequence;

public interface YearSequenceRepository extends JpaRepository<YearSequence, String> {
}