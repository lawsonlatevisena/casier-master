package tg.ceel.cj.casierapi.servicesimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.LogDto;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.Log;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;
import tg.ceel.cj.casierapi.ints.LogInt;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.DemandeRepository;
import tg.ceel.cj.casierapi.repositories.LogRepository;
import tg.ceel.cj.casierapi.repositories.UserRepository;
import tg.ceel.cj.casierapi.services.HistoriqueService;
import tg.ceel.cj.casierapi.services.LogService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    private final DemandeRepository demandeRepository;
    private final HistoriqueService historiqueService;

    public LogServiceImpl(LogRepository logRepository, UserRepository userRepository, EntityMapper entityMapper, DemandeRepository demandeRepository, HistoriqueService historiqueService) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
        this.demandeRepository = demandeRepository;
        this.historiqueService = historiqueService;
    }

    @Override
    public List<LogDto> findAllByPeriode(Date debut, Date fin) {
        List<Log> logs = this.logRepository.findAllByPeriode(debut, fin);
        return logs.stream().map(l -> this.entityMapper.logToLogDto(l)).collect(Collectors.toList());
    }

    @Override
    @Async
    public void save(String action) {
        Log log = new Log();
        log.setAction(action);
        log.setUser(this.getCurrentUser());
        this.logRepository.save(log);
    }

    @Override
    @Async
    public void save(String action, User user) {
        Log log = new Log();
        log.setAction(action);
        log.setUser(user);
        this.logRepository.save(log);
    }

    @Override
    @Async
    public void save(String action, User user, String remoteIp, String hostAdress, String mac) {
        try {
            Log log = new Log();
            log.setAction(action);
            log.setUserIpAddress(remoteIp);
            log.setUserMacAddress(mac);
            log.setUser(this.getCurrentUser());
            this.logRepository.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String action, UtilisateurCasier user, String remoteIp, String hostAdress, String mac) {
        try {
            Log log = new Log();
            log.setAction(action);
            log.setUserIpAddress(remoteIp);
            log.setUserMacAddress(mac);
            log.setUser(user.getPersonneInfo().getUser());
            this.logRepository.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void save(String action, String cible, String destination) {
        Log log = new Log();
        log.setAction(action);
        log.setCible(cible);
        log.setDestination(destination);
        log.setUser(this.getCurrentUser());
        this.logRepository.save(log);
    }

    @Override
    @Async
    public void save(String action, String cible, String destination, User user, Demande... demande) {
        Log log = new Log();
        log.setAction(action);
        log.setCible(cible);
        log.setDestination(destination);
        log.setUser(user);
        if (demande != null && demande.length > 0) {
            Demande logDemande = demandeRepository.findByNumeroDemande(demande[0].getNumeroDemande());
            log.setDemande(logDemande);
            if (logDemande != null) {
                historiqueService.save(action, logDemande, user);
            }
        }
        this.logRepository.save(log);
    }

    @Override
    @Async
    public void save(String action, String cible, String destination, User user, HttpServletRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Log log = new Log();
            log.setAction(action);
            log.setCible(cible);
            log.setDestination(destination);
            log.setUser(user);
            log.setUserIpAddress(log.getUserIpAddress());
            log.setAuteur(mapper.writeValueAsString(this.getCurrentUser()));
            this.logRepository.save(log);
        } catch (Exception e) {

        }
    }

    @Override
    public List<LogInt> findAllByDemandeId(Long demandeId) {
        return logRepository.loadByDemandeId(demandeId);
    }

    public User getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User authUser = userRepository.findByUsername(auth.getName());
            return authUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
