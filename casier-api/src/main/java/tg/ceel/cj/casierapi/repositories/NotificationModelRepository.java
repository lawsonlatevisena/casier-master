package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.NotificationModel;


import java.util.List;

public interface NotificationModelRepository extends JpaRepository<NotificationModel, Long> {

    List<NotificationModel> findByEnvoyeAvecSuccesAndNombreTentantiveLessThanEqual(Boolean statut, Integer tentative);
}