package tg.ceel.cj.casierapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import tg.ceel.cj.casierapi.security.AccountService;
import tg.ceel.cj.casierapi.services.*;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class CasierApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(CasierApiApplication.class, args);
    }

/* @Bean
    CommandLineRunner start(AccountService accountService,
                            CategoriePersonneMoraleService categoriePersonneMoraleService,
                            TypePieceService typePieceService,
                            UtilisateurCasierService utilisateurCasierService,
                            TypePsersonneMoraleService typePsersonneMoraleService,
                            ServiceEnvoyeur serviceEnvoyeur,
                            DemandeAtdService demandeAtdService


    ) {
        return args -> {
            //accountService.initPassword();
            // accountService.initUserATD();
            //accountService.initDefaultUser();
            // categoriePersonneMoraleService.init();
            //typePieceService.initForPm();
            // utilisateurCasierService.initPointRetrait();
            //  typePsersonneMoraleService.initTypePersonne();
          //  demandeAtdService.updateOldDemande();
        };
    }*/




}
