package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;

import java.util.List;
import java.util.Optional;

public interface UtilisateurCasierRepository extends JpaRepository<UtilisateurCasier, Long> {
    @Query(value = "SELECT u FROM UtilisateurCasier u WHERE u.personneInfo.user.username = ?1")
    Optional<UtilisateurCasier> getFromUsername(String username);
    @Query(value = "SELECT u.personneInfo.user FROM UtilisateurCasier u WHERE u.personneInfo.user.username = ?1")
    Optional<User> getUserFromUsername(String username);
    @Query(value = "SELECT u FROM UtilisateurCasier u WHERE u.code_certification= ?1")
    UtilisateurCasier  findByCode_certification(String code);
    Boolean existsByPersonneInfoNom(String nom);
    Boolean existsByPersonneInfoPrenom(String prenom);

    @Query(value = "SELECT u FROM UtilisateurCasier u WHERE u.personneInfo.user.changePassword is false and u.personneInfo.user.active is true and u.personneInfo.user.username not in ('ATD')")
    List<UtilisateurCasier> loadAllUserFromResetPassword();
}