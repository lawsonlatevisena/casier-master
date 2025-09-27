/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.poste;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.repositories.DemandeRepository;
import tg.ceel.cj.casierapi.servicesimpl.DemandeServiceImpl;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author lkpeto
 */
@Service
public class LaPosteApi {

    @Value("${poste.endPoint}")
    private String endPoint;
    @Value("${poste.apiUrl}")
    private String laposteApiUr;
    private final DemandeRepository demandeRepository;
    private static final Logger LOG = Logger.getLogger(LaPosteApi.class.getName());
    private final TrackingUtils trackingUtils;
    org.slf4j.Logger logger = LoggerFactory.getLogger(DemandeServiceImpl.class);

    public LaPosteApi(DemandeRepository demandeRepository, TrackingUtils trackingUtils) {
        this.demandeRepository = demandeRepository;
        this.trackingUtils = trackingUtils;
    }

    /**
     * Notify La Poste of a Demande to track
     *
     * @param demande
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public void sendTrackingRequest(Demande demande) throws IOException, NoSuchAlgorithmException {
       // String baseUrl = laposteApiUr;
        OkHttpClient client = TrustAllHostsSSLClient.trustAllSslClient(new OkHttpClient());
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Colis colis =trackingUtils.deliveryData(demande);
        if (colis==null){
            return;
        }
        HttpEntity<Colis> requestEntity = new HttpEntity<>(colis, headers);
        try {
            System.err.println(mapper.writeValueAsString(colis));
            ResponseEntity<Object> responseEntity = restTemplate.exchange(laposteApiUr+ "/v0/rpc/externalcreatecase", HttpMethod.POST, requestEntity, Object.class);
            UpdateDemandeStatus(demande, responseEntity);
        } catch (Exception e) {
            this.logger.error("Erreur interne suite à une tentative de notification à la poste : ", e);
            e.printStackTrace();
        }


    }



    public void sendEtabliRequest(Demande demande) throws JsonProcessingException {
        String baseUrl = endPoint;
       // OkHttpClient client = TrustAllHostsSSLClient.trustAllSslClient(new OkHttpClient());
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(trackingUtils.etabliData(demande));
        RequestBody body = RequestBody.create( json,okhttp3.MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(baseUrl + "/v0/rpc/externalupdatecases")
                .post(body)
                .build();
        System.out.println(json);

        // Send asynchronous request and handle response 

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Plis plis =trackingUtils.etabliData(demande);
        HttpEntity<Plis> requestEntity = new HttpEntity<>(plis, headers);
        try {
            System.err.println(mapper.writeValueAsString(plis));
            ResponseEntity<Object> responseEntity = restTemplate.exchange("https://api.trak.codes/v0/rpc/externalupdatecases", HttpMethod.POST, requestEntity, Object.class);
            UpdateDemandeStatus(demande, responseEntity);
        } catch (Exception e) {
            logger.error("erreur lors de l'envoie à la poste:",e);
            e.printStackTrace();

        }
    }

    private void UpdateDemandeStatus(Demande demande, ResponseEntity<Object> responseEntity) {
        System.err.println(responseEntity.getStatusCode());
        Demande d = demandeRepository.findById(demande.getId()).get();
        if (responseEntity.getStatusCode() == HttpStatus.OK
                || responseEntity.getStatusCode() == HttpStatus.CREATED) {
            d.setTracked(true);
            d.setTrackingNotificationSuccess(Boolean.TRUE);
            LOG.log(Level.INFO, trackLink(demande));
        }else {
            System.err.println("Echec: "+responseEntity.getBody() + " statut=="+responseEntity.getStatusCode());
            d.setTrackingNotificationSuccess(Boolean.FALSE);
        }
        demandeRepository.save(d);
    }


    public  String trackingDeliveryStatus(Demande demande) throws IOException {
        String baseUrl = endPoint;
        OkHttpClient client = TrustAllHostsSSLClient.trustAllSslClient(new OkHttpClient());

        Request request = new Request.Builder()
                .url(baseUrl + "/v0/rpc/externalgetcasestatus?cextid=" + demande.getTrackingCode())
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            LOG.log(Level.INFO, trackLink(demande));
            return response.body().string();
        }
    }


    /**
     * Build track link of a Demande
     *
     * @param demande
     * @return link|null
     */
    public String trackLink(Demande demande) {
        Map<String, Object> trackingData = trackingUtils.assembleTrackingData(demande);
        if (!trackingData.isEmpty()) {
            try {
                String json = trackingUtils.trackingJsonData(trackingData);
                String trackingInfo = trackingUtils.encodeTrackingInfos(json);
                String link = String.format("%s?import&cd=%s", laposteApiUr, trackingInfo);
                Logger.getLogger(LaPosteApi.class.getName()).log(Level.INFO, "Tracklink: {0}", link);
                return link;
            } catch (JsonProcessingException | UnsupportedEncodingException ex) {
                Logger.getLogger(LaPosteApi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
