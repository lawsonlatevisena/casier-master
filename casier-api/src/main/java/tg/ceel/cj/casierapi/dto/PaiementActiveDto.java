package tg.ceel.cj.casierapi.dto;

import lombok.Data;
import tg.ceel.cj.casierapi.entities.PaiementActive;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link PaiementActive}
 */
@Data
public class PaiementActiveDto implements Serializable {
    private String numeroDemande;
    private String numeroPaiement;
    private String numeroTransaction;
    private String modePaiement;
    private Date datePaiement;
    private Long pointRetraitId;
    private String pointRetraitLibelle;
    private String userName;
    private String demandeur;


}