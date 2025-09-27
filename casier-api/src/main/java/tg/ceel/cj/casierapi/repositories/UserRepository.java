package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    Boolean existsByUsername(String name);


    @Query("select u from  User  u where u.dateDerniereConnexion != null ")
    List<User> findAllUtilisateursConnectes();
}