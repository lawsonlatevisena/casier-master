package tg.ceel.cj.casierapi.servicesimpl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.Historique;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;
import tg.ceel.cj.casierapi.repositories.HistoriqueRepository;
import tg.ceel.cj.casierapi.repositories.UtilisateurCasierRepository;
import tg.ceel.cj.casierapi.services.HistoriqueService;
import tg.ceel.cj.casierapi.ws.DemandeController;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class HistoriqueServiceImpl implements HistoriqueService {
    private final HistoriqueRepository historiqueRepository;
    private final UtilisateurCasierRepository utilisateurCasierRepository;
    Logger logger = LoggerFactory.getLogger(DemandeController.class);
    @Override
    public void save(Historique historique) {

    }

    @Override
    public void save(String action, Demande demande) {
      try {
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          UtilisateurCasier utilisateurCasier = utilisateurCasierRepository.getFromUsername(auth.getName())
                  .orElseThrow(() -> new RuntimeException(String.format("Aucun utilisateur trouvé avec le nom %s", auth.getName())));
          String utilisateur = getUsername(utilisateurCasier);
          String login = getLogin(utilisateurCasier);
          Historique historique = Historique.builder()
                  .action_realisee(action)
                  .datecreation(new Date())
                  .numeroDemande(demande.getNumeroDemande())
                  .utilisateur(utilisateur)
                  .username(login)
                  .idDemande(demande.getId())
                  .build();
          historiqueRepository.save(historique);
      } catch (Exception e) {
          logger.error("Erreur interne", e);
      }
    }

    private String getLogin(UtilisateurCasier utilisateurCasier) {
        try {
            if (utilisateurCasier == null) {
                return null;
            }
            return String.format("%s", utilisateurCasier.getPersonneInfo().getUser().getUsername());

        } catch (Exception e) {
            return null;
        }
    }

    private String getUsername(UtilisateurCasier utilisateurCasier) {
        try {
            if (utilisateurCasier == null) {
                return null;
            }
            return String.format("%s - %s %s ", utilisateurCasier.getPersonneInfo().getUser().getUsername(),utilisateurCasier.getPersonneInfo().getNom(), utilisateurCasier.getPersonneInfo().getPrenom());

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseEntity<?> getHistoriques(Long idDemande) {
        return new ResponseEntity<>(historiqueRepository.findByIdDemande(idDemande), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getHistoriques(String utilisateur) {
        return new ResponseEntity<>(historiqueRepository.findByUsername(utilisateur), HttpStatus.OK);
    }

    @Override
    public void save(String action, Demande demande, User user) {
        try {
           if(user!=null) {
               UtilisateurCasier utilisateurCasier = utilisateurCasierRepository.getFromUsername(user.getUsername())
                       .orElseThrow(() -> new RuntimeException(String.format("Aucun utilisateur trouvé avec le nom %s", user.getUsername())));
               String utilisateur = getUsername(utilisateurCasier);
               String login = getLogin(utilisateurCasier);
               Historique historique = Historique.builder()
                       .action_realisee(action)
                       .datecreation(new Date())
                       .numeroDemande(demande.getNumeroDemande())
                       .utilisateur(utilisateur)
                       .username(login)
                       .idDemande(demande.getId())
                       .build();
               historiqueRepository.save(historique);
           }
        } catch (Exception e) {
            logger.error("Erreur interne", e);
        }
    }
}
