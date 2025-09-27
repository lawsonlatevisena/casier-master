package tg.ceel.cj.casierapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;
import tg.ceel.cj.casierapi.repositories.UtilisateurCasierRepository;
import tg.ceel.cj.casierapi.utils.UtilisateurModel;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UtilisateurCasierRepository utilisateurCasierRepository;
    private final AccountService accountService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final CompteActives compteActives;
    Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UtilisateurCasierRepository utilisateurCasierRepository, AccountService accountService, LoginSuccessHandler loginSuccessHandler, CompteActives compteActives) {
        this.authenticationManager = authenticationManager;
        this.utilisateurCasierRepository = utilisateurCasierRepository;
        this.accountService = accountService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.compteActives = compteActives;
        setFilterProcessesUrl("/utilisateurs/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
       // logger.info("Dans la methode username = " + username + " and password = " + password);
       // System.err.println("Dans la methode username = " + username + " and password = " + password);
        return accountService.loginFromRequest(username,password,authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        UtilisateurCasier utilisateurCasier = this.utilisateurCasierRepository.getFromUsername(user.getUsername()).orElseThrow(() -> new RuntimeException(String.format("Aucun utilisateur avec le login %s trouvé", user.getUsername())));

        Algorithm algorithm = Algorithm.HMAC256(Constants.SIGNING_KEY);
        String jwtAccessToken = accountService.genererToken(user, request.getRequestURL().toString(), algorithm);
        String jwtRefeshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (15 * 60 * 1000)))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        UtilisateurModel utilisateurModel = this.accountService.initCurrentCompteModel(utilisateurCasier);

        Map<String, Object> authToken = new HashMap<>();
        authToken.put("access-token", jwtAccessToken);
        authToken.put("refresh-token", jwtRefeshToken);
        authToken.put("user-info", utilisateurModel);
        response.addHeader(Constants.HEADER_STRING, jwtAccessToken);
        response.setContentType("application/json");
        String mac = request.getParameter("machine");
        loginSuccessHandler.onAuthenticationSuccess(utilisateurCasier, request.getRemoteAddr(), request.getRemoteHost(), mac);
        List<tg.ceel.cj.casierapi.entities.User> logins = compteActives.getLogins();
        tg.ceel.cj.casierapi.entities.User connecte = new tg.ceel.cj.casierapi.entities.User();
        connecte.setUsername(user.getUsername());
        connecte.setDateDerniereConnexion(new Date());
        Integer indexe = -1;
        for (tg.ceel.cj.casierapi.entities.User l : logins) {
            if (l.getUsername().equalsIgnoreCase(connecte.getUsername())) {
                indexe = logins.indexOf(l);
            }
        }
        System.err.println(indexe);
        if (indexe != -1) {
            Boolean b = logins.remove(logins.get(indexe));
            System.err.println(b);
        }
        logins.add(connecte);

        try {
            new ObjectMapper().writeValue(response.getOutputStream(), authToken);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


}
