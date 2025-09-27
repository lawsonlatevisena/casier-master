package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.TmoneyNotification;

public interface TmoneyNotificationRepository extends JpaRepository<TmoneyNotification, Long> {
}