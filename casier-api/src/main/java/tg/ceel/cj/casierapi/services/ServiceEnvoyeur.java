package tg.ceel.cj.casierapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.NotificationModel;
import tg.ceel.cj.casierapi.entities.Token;
import tg.ceel.cj.casierapi.models.APIResponse;
import tg.ceel.cj.casierapi.models.FeedbackModel;
import tg.ceel.cj.casierapi.models.UserAdt;
import tg.ceel.cj.casierapi.repositories.NotificationModelRepository;
import tg.ceel.cj.casierapi.repositories.TokenRepository;

import java.util.Date;

@Service
public class ServiceEnvoyeur {
    Logger logger = LoggerFactory.getLogger(ServiceEnvoyeur.class);
    @Value("${base_url}")
    private String baseUrl;
    @Value("${x-flow.api.email}")
    private String xflowApiEmail;
    @Value("${x-flow.api.password}")
    private String getXflowApiPassword;
    private final NotificationModelRepository notificationModelRepository;
    private final TokenRepository tokenRepository;

    public ServiceEnvoyeur(NotificationModelRepository notificationModelRepository, TokenRepository tokenRepository) {
        this.notificationModelRepository = notificationModelRepository;
        this.tokenRepository = tokenRepository;
    }

    // @Async("asyncExecutor")
    public ResponseEntity<?> notifier(NotificationModel notification) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String autURL = baseUrl + "api/v1/xflow/asyncNotif";
            headers.add("Content-Type", "application/json");
            String token = genererToken();
            NotificationModel entity = null;
            if (notification.getId() == null) {
                notification.setEnCopie(notification.ccToString());
                notification.setEnvoyeAvecSucces(Boolean.FALSE);
                notification.setNombreTentantive(1);
                notification.setDateCreation(new Date());
                entity = notificationModelRepository.save(notification);
            } else {
                entity = notificationModelRepository.findById(notification.getId()).orElse(null);
                entity.setNombreTentantive(entity.getNombreTentantive() + 1);
                notification = entity;
            }
            if (entity == null) {
                return null;
            }
            System.err.println(new ObjectMapper().writeValueAsString(notification));
            ResponseEntity<APIResponse> responseEntity = null;
            if (token != null) {
                headers.add("x-token", token);
                HttpEntity<NotificationModel> requestEntity = new HttpEntity<>(notification, headers);
                System.err.println(notification);

                responseEntity = restTemplate.exchange(autURL, HttpMethod.POST,
                        requestEntity, APIResponse.class);
                System.err.println(responseEntity.getBody());
                System.err.println(responseEntity.getStatusCode());
                if (responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getStatusCode() == HttpStatus.CREATED) {
                    System.err.println("Dans la condition if");
                    logger.info("Notification envoyé avec succès: " + notification);
                    entity.setDateEnvoie(new Date());
                    entity.setEnvoyeAvecSucces(Boolean.TRUE);
                    notificationModelRepository.save(entity);
                    return new ResponseEntity<>(new APIResponse(true, "Notification envoyé avec succès"), HttpStatus.OK);

                } else {
                    notificationModelRepository.save(entity);
                }
            } else {
                logger.error("Echèc de notification: " + responseEntity.getBody());
                return new ResponseEntity<>(new APIResponse(false, "Echèc de notification"), responseEntity.getStatusCode());

            }

        } catch (Exception e) {
            logger.error("Echèc de notification: " + e);
            return new ResponseEntity<>(new APIResponse(false, "Echèc de notification"), HttpStatus.PRECONDITION_FAILED);

        }
        return new ResponseEntity<>(new APIResponse(false, "Echèc de notification "), HttpStatus.PRECONDITION_FAILED);

    }

  private String  genererToken(){
        Token token = tokenRepository.findByApi("ATD");
        return token.getToken_value();
     /*   if (token==null){
            token = new Token();
            token.setApi("ATD");
            return initToken(token);
        }else {

            if (token.getExpire_date().before(new Date())){
                return initToken(token);
            }else {
                token.setLast_recuperation_date(new Date());
                tokenRepository.save(token);
                return token.getToken_value();
            }
        }*/
    }

   @Async
    public String initToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String autURL = baseUrl + "api/v1/xflow/auth";
        headers.add("Content-Type", "application/json");
        UserAdt user = new UserAdt(xflowApiEmail, getXflowApiPassword);
        HttpEntity<UserAdt> requestEntity = new HttpEntity<>(user, headers);
       System.err.println();
        ResponseEntity<APIResponse> responseEntity = restTemplate.exchange(autURL, HttpMethod.POST,
                requestEntity, APIResponse.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getStatusCode() == HttpStatus.CREATED) {
            System.err.println(responseEntity);
            Token token = tokenRepository.findByApi("ATD");
            token.setToken_value( responseEntity.getBody().getData().getToken());
            token.setExpire_date(new Date( responseEntity.getBody().getData().getExpiry()*1000));
            token.setExpiry( responseEntity.getBody().getData().getExpiry()*1000);
            token.setLast_recuperation_date(new Date());
            tokenRepository.save(token);
            return token.getToken_value();
        } else {
            System.err.println(responseEntity.getBody());
            logger.error("Echèc de notification: " + responseEntity.getBody());
            return null;
        }
    }

    public String getTonken() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String autURL = baseUrl + "api/v1/xflow/auth";
            headers.add("Content-Type", "application/json");
            UserAdt user = new UserAdt(xflowApiEmail, getXflowApiPassword);
            HttpEntity<UserAdt> requestEntity = new HttpEntity<>(user, headers);
            ResponseEntity<APIResponse> responseEntity = restTemplate.exchange(autURL, HttpMethod.POST,
                    requestEntity, APIResponse.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getStatusCode() == HttpStatus.CREATED) {
                System.err.println("Dans la condition if" + requestEntity.getBody());
                System.err.println("Code HTTP: " + responseEntity.getStatusCode());
                System.err.println("corps de la réponse: " + responseEntity.getBody());
                logger.error("notification: " + responseEntity);
                return responseEntity.getBody().getData().getToken();
            } else {
                System.err.println(responseEntity.getBody());
                logger.error("Echèc de notification: " + responseEntity.getBody());
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Echèc de notification: " + e);
            return null;
        }
    }

    @Async("asyncExecutor")
    public ResponseEntity<?> envoyerFeadBack(FeedbackModel notification) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String autURL = baseUrl + "api/v1/xflow/returnFeedback";
            headers.add("Content-Type", "application/json");
            String token = genererToken();
            System.err.println("notification: "+notification);
            logger.error("feedbackModel: ",notification);
            if (token != null) {
                headers.add("x-token", token);
                HttpEntity<FeedbackModel> requestEntity = new HttpEntity<>(notification, headers);
                System.err.println("Objet à envoyer est : ");
                System.err.println(new ObjectMapper().writeValueAsString(notification));
                ResponseEntity<APIResponse> responseEntity = restTemplate.exchange(autURL, HttpMethod.POST,
                        requestEntity, APIResponse.class);
                if (responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getStatusCode() == HttpStatus.CREATED) {
                    System.err.println("Dans la condition if");
                    logger.info("Notification envoyé avec succès: " + notification);
                    return new ResponseEntity<>(new APIResponse(true, "Notification envoyé avec succès"), HttpStatus.OK);
                } else {


                    logger.error("Echèc de notification: " + responseEntity.getBody());
                    return new ResponseEntity<>(new APIResponse(false, "Echèc de notification: " + responseEntity.getBody()), responseEntity.getStatusCode());
                }
            } else {
                logger.error("Le token n'a pas pu être récupéré");
                return new ResponseEntity<>(new APIResponse(false, "Le token n'a pas pu être récupéré "), HttpStatus.PRECONDITION_FAILED);

            }

        } catch (Exception e) {
            logger.error("Echèc de notification: " + e);
            e.printStackTrace();
            return new ResponseEntity<>(new APIResponse(false, "Echèc de notification "), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Async("asyncExecutor")
    public void logNotification(NotificationModel notification) {
        NotificationModel entity = null;
        if (notification.getId() == null) {
            notification.setEnCopie(notification.ccToString());
            notification.setEnvoyeAvecSucces(Boolean.FALSE);
            notification.setNombreTentantive(1);
            notification.setDateCreation(new Date());
            entity = notificationModelRepository.save(notification);
        } else {
            entity = notificationModelRepository.findById(notification.getId()).orElse(null);
            entity.setNombreTentantive(entity.getNombreTentantive() + 1);
            notification = entity;
        }

        notificationModelRepository.save(entity);
    }

    @Async("asyncExecutor")
    public void notifier(Demande demande, String message, String operation) {
        try {
            String subject = "";
            String title = "";
            switch (operation) {
                case "DISPONIBLE": {
                    subject = "Bulletin imprimé et signé";
                    title = "Bulletin imprimé et signé";
                    break;
                }
                case "RETRAIT": {
                    subject = "Retrait du belletion";
                    title = "Retrait du belletion";
                    break;
                }
                default: {
                    break;
                }
            }
            if (demande.getTelephone() != null) {
                NotificationModel smsNotif = new NotificationModel();
                if (demande.getTelephone().length() <= 8) {
                    smsNotif.setNumber("228" + demande.getTelephone());
                } else {
                    smsNotif.setNumber(demande.getTelephone());
                }
                smsNotif.setRecord(demande.getRecord());
                smsNotif.setMessage(message);
                smsNotif.setContent(message);
                smsNotif.setType("SMS");
                smsNotif.setSubject(subject);
                smsNotif.setTitle(title);
                notifier(smsNotif);
            }
            if (demande.getEmail() != null) {
                NotificationModel email = new NotificationModel();
                email.setEmail(demande.getEmail());
                email.setRecord(demande.getRecord());
                email.setMessage(message);
                email.setContent(message);
                email.setType("MAIL");
                email.setSubject(subject);
                email.setTitle(title);
                notifier(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Echèc de notification: " + e);
        }
    }
}
