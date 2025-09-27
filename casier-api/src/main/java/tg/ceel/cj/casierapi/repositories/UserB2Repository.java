package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.UserB2;

import java.util.List;

public interface UserB2Repository extends JpaRepository<UserB2, Long> {
    List<UserB2> findByCni(String cni);
    List<UserB2> findAllByValideIsFalse();
}