package tg.ceel.cj.casierapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.Historique;
import tg.ceel.cj.casierapi.entities.User;

public interface HistoriqueService {
    void save(Historique historique);

    void save(String action, Demande demande);
    ResponseEntity<?> getHistoriques(Long idDemande);
    ResponseEntity<?> getHistoriques(String utilisateur);

    void save(String action, Demande logDemande, User user);
}
