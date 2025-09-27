package tg.ceel.fnc.fnc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.fnc.fnc.entities.Peine;
import tg.ceel.fnc.fnc.entities.PeineInfraction;
import tg.ceel.fnc.fnc.entities.PeineInfractionId;

import java.util.Collection;
import java.util.List;

public interface PeineInfractionRepository extends JpaRepository<PeineInfraction, PeineInfractionId> {
    List<PeineInfraction> findByPeineIn(List<Peine> peines);

}