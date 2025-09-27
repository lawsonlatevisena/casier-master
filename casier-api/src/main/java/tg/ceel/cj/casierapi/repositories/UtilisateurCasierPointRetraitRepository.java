package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.entities.RolesPermissions;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;
import tg.ceel.cj.casierapi.entities.UtilisateurCasierPointRetrait;

import java.util.List;

public interface UtilisateurCasierPointRetraitRepository extends JpaRepository<UtilisateurCasierPointRetrait,Long> {

    @Query("select u from UtilisateurCasierPointRetrait u where u.utilisateurCasier.code = ?1 and u.active = true")
    UtilisateurCasierPointRetrait findActiveByIdUtilisateurCasier(Long id);

    @Query("select u from UtilisateurCasierPointRetrait u where u.utilisateurCasier.code = ?1 order by  u.dateAjout desc ")
    List<UtilisateurCasierPointRetrait> findAllByIdUtilisateurCasier(Long id);
    @Query("select u from UtilisateurCasierPointRetrait u where   u.active = false ")
    List<UtilisateurCasierPointRetrait> findAllNonActive();

    List<UtilisateurCasierPointRetrait> findByPointRetraitEqualsAndUtilisateurCasierEquals(PointRetrait pointRetrait, UtilisateurCasier utilisateurCasier);

    UtilisateurCasierPointRetrait findByDefautAndUtilisateurCasier(Boolean defaut,UtilisateurCasier utilisateurCasier);
}
