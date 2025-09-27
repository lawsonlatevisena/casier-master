package tg.ceel.fnc.fnc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.fnc.fnc.entities.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
}