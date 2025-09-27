package tg.ceel.cj.casierapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tg.ceel.cj.casierapi.services.ServiceEnvoyeur;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final ServiceEnvoyeur lanceur;

   // @Scheduled(cron = "1 * * * * *")
    @Scheduled(cron = "0 0 1 * * ?")
    public void getToken() {
        Date now = new Date();
        System.out.println(
                "Recupération du token " + now);
        try {
            this.lanceur.initToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
