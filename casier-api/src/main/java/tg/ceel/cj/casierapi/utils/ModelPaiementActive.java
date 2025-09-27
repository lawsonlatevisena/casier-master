package tg.ceel.cj.casierapi.utils;

import lombok.Data;

import java.util.Date;

@Data
public class ModelPaiementActive {

    private  String numeroDemande;
    private  String numePaiement;
    private  String numeroTransaction;
    private  String modePaiement;
    private Date datePaiement;
    private  Long pointRetrait;
    private Boolean regler;
    private String demandeur;
}
