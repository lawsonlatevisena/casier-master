package tg.ceel.cj.casierapi.foreign.entities.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tg.ceel.cj.casierapi.dto.LogDto;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.entities.User;


import java.util.List;

@Service
public class SecurityFeigManagementServiceImpl implements SecurityFeigManagementService{

    @Value("${user.service.url}")
    private String userServiceUrl;
    Logger logger = LoggerFactory.getLogger(SecurityFeigManagementServiceImpl.class);
    @Override
    public PointRetrait getPointRetraitById(Long id, String auth) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String autURL = userServiceUrl + "centres/{id}";
            HttpEntity request = new HttpEntity(headers);
            headers.add("Content-Type", "application/json");
            headers.add("Authorization", auth);
            ResponseEntity<PointRetrait> responseEntity = restTemplate.exchange(autURL, HttpMethod.GET,
                    request, PointRetrait.class,id);
            System.err.println(responseEntity.getBody());
            System.err.println(responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.err.println("Dans la condition if");
                return  responseEntity.getBody();

            }else {
                this.logger.error("Statut code: "+responseEntity.getStatusCode()+ "");
                this.logger.error(responseEntity.getBody()+ "");
                return  null;
            }


        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error(e.getMessage()+ "");
            return null;
        }
    }

    @Override
    public LogDto saveLog(LogDto log, String auth) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String autURL = userServiceUrl + "logs/logger";
            HttpEntity<LogDto> requestEntity = new HttpEntity<>(log, headers);
            headers.add("Content-Type", "application/json");
            headers.add("Authorization", auth);
            ResponseEntity<LogDto> responseEntity = restTemplate.exchange(autURL, HttpMethod.POST,
                    requestEntity, LogDto.class);
            System.err.println(responseEntity.getBody());
            System.err.println(responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.err.println("Dans la condition if");
                return  responseEntity.getBody();

            }else {
                this.logger.error("Statut code: "+responseEntity.getStatusCode()+ "");
                this.logger.error(responseEntity.getBody()+ "");
                return  null;
            }


        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error(e.getMessage()+ "");
            return null;
        }
    }

    @Override
    public List<PointRetrait> getCentreAll(String type, String auth) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String autURL = userServiceUrl + "centres/type/{type}";
            HttpEntity request = new HttpEntity(headers);
            headers.add("Content-Type", "application/json");
            headers.add("Authorization", auth);
            ResponseEntity<List<PointRetrait>> responseEntity = restTemplate.exchange(autURL, HttpMethod.GET,
                    request,  new ParameterizedTypeReference<List<PointRetrait>>() {},type);
            System.err.println(responseEntity.getBody());
            System.err.println(responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.err.println("Dans la condition if");
                return  responseEntity.getBody();

            }else {
                this.logger.error("Statut code: "+responseEntity.getStatusCode()+ "");
                this.logger.error(responseEntity.getBody()+ "");
                return  null;
            }


        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error(e.getMessage()+ "");
            return null;
        }
    }

    @Override
    public User getByLogin(String login, String auth) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String autURL = userServiceUrl + "login/{login}";
            HttpEntity request = new HttpEntity(headers);
            headers.add("Content-Type", "application/json");
            headers.add("Authorization", auth);
            ResponseEntity<User> responseEntity = restTemplate.exchange(autURL, HttpMethod.GET,
                    request, User.class,login);
            System.err.println(responseEntity.getBody());
            System.err.println(responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.err.println("Dans la condition if");
                return  responseEntity.getBody();

            }else {
                this.logger.error("Statut code: "+responseEntity.getStatusCode()+ "");
                this.logger.error(responseEntity.getBody()+ "");
                return  null;
            }


        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error(e.getMessage()+ "");
            return null;
        }
    }

    @Override
    public String getToken() {
     try   {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
            map.add("username", "systeme");
            map.add("password","7PjygW3s7pT7$C#7ewxaGyQ7jVS%");
            String autURL = userServiceUrl + "utilisateurs/auth/login";

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            headers.add("Content-Type", "MediaType.APPLICATION_FORM_URLENCODED");
            ResponseEntity<String> responseEntity = restTemplate.exchange(autURL, HttpMethod.POST,
                    request, String.class);
            System.err.println(responseEntity.getBody());
            System.err.println(responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.err.println("Dans la condition if");
                HttpHeaders httpHeaders = responseEntity.getHeaders();
                String token = httpHeaders.getFirst("Authorization");
                return token;

            }else {
                this.logger.error("Statut code: "+responseEntity.getStatusCode()+ "");
                this.logger.error(responseEntity.getBody()+ "");
                return  null;
            }


        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error(e.getMessage()+ "");
            return null;
        }

    }
}
