package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
}