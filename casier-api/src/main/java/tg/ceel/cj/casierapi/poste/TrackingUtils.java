/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.poste;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.foreign.entities.access.SecurityFeigManagementService;
import tg.ceel.cj.casierapi.entities.Demande;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * La Poste tracking utils
 *
 * @author lkpeto
 */
@Service
public class TrackingUtils {
    @Value("${poste.api.key}")
    private String posteKey;
    private SecurityFeigManagementService securityFeigManagementService;

    public TrackingUtils(SecurityFeigManagementService securityFeigManagementService) {
        this.securityFeigManagementService = securityFeigManagementService;
    }

    /**
     * Build tracking data
     *
     * @param demande
     * @return
     */
    public Map<String, Object> assembleTrackingData(Demande demande) {

        UUID uuid = UUID.fromString(demande.getTrackingCode());
        if (uuid.version() != 4) {
            String message = String.format("UUID version %s is not equal to 4", uuid.version());
            throw new IllegalArgumentException(message);
        }
        // Data map
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", demande.getTrackingCode());

        // Data
        Map<String, Object> data = new HashMap<>();
        data.put("v", 1L);
        data.put("d", dataMap);

        return data;
    }

    /**
     * Convert tracking data to JSON
     *
     * @param trackindData
     * @return
     * @throws JsonProcessingException
     */
    public String trackingJsonData(Map<String, Object> trackindData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(trackindData);
    }


    /**
     * Encode tracking JSON data to URL safe string
     *
     * @param json
     * @return
     * @throws UnsupportedEncodingException
     */
    public String encodeTrackingInfos(String json) throws UnsupportedEncodingException {
        String base64Encoded = Base64.getEncoder().encodeToString(json.getBytes());
        String encoded = URLEncoder.encode(base64Encoded, StandardCharsets.UTF_8.toString());
        return encoded;
    }


    public Colis deliveryData(Demande demande) {
        // Todo: Think of updating this section with the builder pattern
        Colis colis = new Colis();
        colis.setExtappkey(posteKey);
        colis.setCextid(demande.getTrackingCode());
       // colis.setNaturalkey(demande.getCodeABarres());
      //  colis.getAttachments().add(new ColisAttachment("Identifiant", colis.getCextid(), false));

        colis.setNotes(String.format("Colis de la Justice pour %s %s", demande.getNom(), demande.getPrenom()));

        if (demande.getNom() != null) {
            colis.getAttachments().add(new ColisAttachment("Nom", demande.getNom(), false));
        }
        if (demande.getPrenom() != null) {
            colis.getAttachments().add(new ColisAttachment("Prénoms", demande.getPrenom(), false));
        }
        if (demande.getTelephone() != null) {
            colis.getAttachments().add(new ColisAttachment("Téléphone", "+228" + demande.getTelephone(), false));
        }
        if (demande.getEmail() != null) {
            colis.getAttachments().add(new ColisAttachment("Email", demande.getEmail(), false));
        }
        if (demande.getLieuResidence() != null) {
            colis.getAttachments().add(new ColisAttachment("Adresse", demande.getLieuResidence(), false));
        }
      //  colis.getAttachments().add(new ColisAttachment("Copies", demande.getNombreCopie() + "", false));

        if (demande.getPointRetrait() != null) {
            colis.getAttachments().add(new ColisAttachment("Tribunal", demande.getPointRetrait().getLibelle(), false));
        }

        colis.getAttachments().add(new ColisAttachment("Mode de livraison", "Bureau de poste", false));

        if (demande.getBureauPosteRetrait() != null) {
            PointRetrait centre = demande.getBureauPosteRetrait();
            colis.getAttachments().add(new ColisAttachment("Bureau de poste", centre.getLibelle(), false));
            if (centre.getCode() == null || centre.getCode().replaceAll(" ", "").isEmpty()) {
                return null;
            }
            colis.getAttachments().add(new ColisAttachment("postOfficeId", centre.getCode(), true));

        }
        if (demande.getPointRetrait() != null) {
            PointRetrait centre = demande.getPointRetrait();
            colis.getAttachments().add(new ColisAttachment("Centre de traitement", centre.getLocalite(), false));
        }

        // Correlation ID
//        colis.getAttachments().add(new ColisAttachment("correlationId", demande.getId().toString(), true));
        colis.getAttachments().add(new ColisAttachment("correlationId", demande.getNumeroDemande(), true));

        return colis;
    }


    public Plis etabliData(Demande demande) {
        Plis colis = new Plis();
        colis.setExtappkey(posteKey);
        colis.getCextids().add(demande.getTrackingCode());
        colis.setNotes(String.format("Plis du Service National de Casier Judiciaire pour %s %s", demande.getNom(), demande.getPrenom()));
        colis.setNewstep("Établi");
        //  colis.setNaturalkey(demande.getCodeABarres());
        colis.setActiondate(convertToISO8001(new Date()));

        PointRetrait centre = demande.getPointRetrait();

        colis.getAttachments().add(new ColisAttachment("Centre de traitement", centre.getLocalite(), false));
        //        colis.getAttachments().add(new ColisAttachment("correlationId", demande.getId().toString()));
        colis.getAttachments().add(new ColisAttachment("correlationId", demande.getNumeroDemande(), true));
        return colis;
    }

    public String convertToISO8001(Date date) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        return sdf.format(date);
    }
}
