package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.Log;
import tg.ceel.cj.casierapi.ints.LogInt;

import java.util.Date;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    @Query(value = "select l from Log l where l.dateAction >= ?1 and l.dateAction <= ?2 order by l.dateAction")
    List<Log> findAllByPeriode(Date debut, Date fin);
    @Query(value = "select l.*,  CONCAT (pi2.nom,' ', pi2.prenom) as utilisateur,l.comptes_id  as utilisateur_id from logs l, core_users cu ,personne_infos pi2  where demande_id =?1 and cu.id =l.comptes_id and cu.id =pi2.id_user  order by id", nativeQuery = true)
    List<LogInt> loadByDemandeId(Long id);
}