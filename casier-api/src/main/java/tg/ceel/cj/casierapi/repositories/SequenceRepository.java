package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.Sequence;

public interface SequenceRepository extends JpaRepository<Sequence, String> {
}