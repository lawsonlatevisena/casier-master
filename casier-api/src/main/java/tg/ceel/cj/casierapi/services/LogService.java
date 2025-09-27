package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.LogDto;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;
import tg.ceel.cj.casierapi.ints.LogInt;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface LogService {

    List<LogDto> findAllByPeriode(Date debut, Date fin);

    void save(String action);

    void save(String action, User user);

    void save(String action, User user, String remoteIp, String hostAdress, String mac);

    void save(String action, UtilisateurCasier user, String remoteIp, String hostAdress, String mac);

    void save(String action, String cible, String destination);

    void save(String action, String cible, String destination, User user, Demande... demande);

    void save(String s, String cible, String destination, User user, HttpServletRequest request);

    List<LogInt> findAllByDemandeId(Long demandeId);
}
