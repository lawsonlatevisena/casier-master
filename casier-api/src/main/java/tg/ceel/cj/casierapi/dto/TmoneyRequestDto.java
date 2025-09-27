package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.cj.casierapi.models.DemandeSite;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.TmoneyRequest}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TmoneyRequestDto implements Serializable {
    private int version = 1;
    private Long id;
    private String sessionid;
    private String merchantid;
    private Integer amount;
    private Integer currency;
    private String purchaseref;
    private String phonenumber;
    private String brand;
    private String description;
    private String accepturl;
    private String declineurl;
    private String cancelurl;
    private String text;
    private String tmrLanguage;
    private Date dateDemande;
    private Long demandeId;
    private String demandeTypeDemande;
    private String demandeNumeroTraitement;
    private String demandeNom;
    private String demandePrenom;
    private String demandeTelephone;
    private int demandeNombreCopie;
    private Boolean traiter = Boolean.FALSE;
    private DemandeSite demande;
}