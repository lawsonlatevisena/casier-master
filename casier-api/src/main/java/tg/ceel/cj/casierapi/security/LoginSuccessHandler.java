package tg.ceel.cj.casierapi.security;

import org.springframework.stereotype.Service;

import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;
import tg.ceel.cj.casierapi.repositories.UserRepository;
import tg.ceel.cj.casierapi.services.LogService;

import java.util.Date;

@Service
public class LoginSuccessHandler {

    private final UserRepository userRepository;
    private final LogService logService;

    public LoginSuccessHandler(UserRepository userRepository, LogService logService) {
        this.userRepository = userRepository;
        this.logService = logService;
    }

    public void onAuthenticationSuccess(User user, String remoteAddr, String remoteHost,String mac) {

        user.setDateDerniereConnexion(new Date());
        userRepository.save(user);

        this.logService.save("Connexion", user,remoteAddr,remoteHost,mac);

    }

    public void onAuthenticationSuccess(UtilisateurCasier user, String remoteAddr, String remoteHost, String mac) {

        User user1 =user.getPersonneInfo().getUser();
        user1.setDateDerniereConnexion(new Date());
        userRepository.save(user1);

        this.logService.save("Connexion", user,remoteAddr,remoteHost,mac);

    }
}
