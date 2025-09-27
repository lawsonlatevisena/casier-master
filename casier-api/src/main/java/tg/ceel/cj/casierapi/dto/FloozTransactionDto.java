package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.cj.casierapi.entities.CanalPayement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FloozTransactionDto implements Serializable {
    private String numeroDemande;
    private String recode;
    private  Long id;
    private  String ipMerchantName;
    private  String ipTransactionCode;
    private  String ipDestMobileNumber;
    private  String iopAmount;
    private  String opReferenceId;
    private  Integer opStatus;
    private  String opStatusMessage;
    private  String opSubscriberMsisdn;
    private  String opFloozRefid;
    private  Boolean traiter;
    private  LocalDateTime datecreation;
    private  Long demandeId;
    private  String demandeTypeDemande;
    private  String demandeNom;
    private  String demandePrenom;
    private  Date demandeDateNaissance;
    private  String demandeTelephone;
    private  Long demandePayementId;
    private  String demandePayementNumero;
    private  String demandePayementNumeroTransaction;
    private  Double demandePayementMontant;
    private  CanalPayement demandePayementCanalPayement;
    private  Boolean demandePayementRegler;
    private  String demandePayementDateTransaction;
    private  String demandePayementMoyenPaiement;
    private  String demandeNumeroDemande;
}
