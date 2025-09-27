package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.PersonneInfo;

public interface PersonneInfoRepository extends JpaRepository<PersonneInfo, Long> {
}