package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tg.ceel.cj.casierapi.fnc.models.Casier;
import tg.ceel.cj.casierapi.fnc.models.DemandeFnc;
import tg.ceel.cj.casierapi.services.FNCService;

import java.nio.charset.StandardCharsets;

@Service
public class FNCServiceImpl implements FNCService {
    @Value("${fnc.base.url}")
    private String fncBaseUrl;
    Logger logger = LoggerFactory.getLogger(FNCServiceImpl.class);

    @Override
    public ResponseEntity<?> getCondamnations(DemandeFnc demande) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            HttpHeaders headers = new HttpHeaders();
            System.err.println("Demane envoyée : " +demande);
            String autURL = fncBaseUrl + "/condamnations/";
            headers.add("Content-Type", "application/json");
            HttpEntity<DemandeFnc> requestEntity = new HttpEntity<>(demande, headers);
            ResponseEntity<Casier> responseEntity = restTemplate.exchange(autURL, HttpMethod.POST,
                    requestEntity, Casier.class);
            if(responseEntity.getStatusCode()==HttpStatus.OK){
                Casier casier = responseEntity.getBody();
                return new ResponseEntity<>(casier, responseEntity.getStatusCode());
            }else {
                return new ResponseEntity<>(responseEntity.getBody() , responseEntity.getStatusCode());

            }

        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();
            return new ResponseEntity<>("Erreur lors de la demande d'immatriculation: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
