package tg.ceel.cj.casierapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.annotation.ApplicationScope;
import tg.ceel.cj.casierapi.repositories.UtilisateurCasierRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    private final LoginSuccessHandler loginSuccessHandler;
    private final AccountService accountService;
    private final UtilisateurCasierRepository utilisateurCasierRepository;

    public SecurityConfiguration(LoginSuccessHandler loginSuccessHandler, AccountService accountService, UtilisateurCasierRepository utilisateurCasierRepository) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.accountService = accountService;
        this.utilisateurCasierRepository = utilisateurCasierRepository;
    }

    @Bean
    @ApplicationScope
    public CompteActives utilisateurActiveBean() {
        return new CompteActives();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(encoder());
        return authenticationProvider;
    }
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
          auth.authenticationProvider(authenticationProvider());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/utilisateurs/**").permitAll();
        http.authorizeRequests().antMatchers("/api/**").permitAll();
        http.authorizeRequests().antMatchers("/references/**").permitAll();
        http.authorizeRequests().antMatchers("/demandes/init-fichiers/**").permitAll();
        http.authorizeRequests().antMatchers("/logs/**").permitAll();
        http.authorizeRequests().antMatchers("/users-api/docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(), utilisateurCasierRepository, accountService, loginSuccessHandler, utilisateurActiveBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
