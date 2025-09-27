package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.CoursAppel;
import tg.ceel.cj.casierapi.entities.Variable;

import java.util.Optional;

public interface VariableRepository extends JpaRepository<Variable, String> {
    Optional<Variable> findByName(String name);

}