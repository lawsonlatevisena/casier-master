package tg.ceel.cj.casierapi.dto;


import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaiementDto {
    private String transactionUUID;
    private String numeroTransaction;
    private String message;
    private Long montant;
    private String devise;
    private Date date;
    private String signedAttributeNames;
    private String statut;
    private String moyenPaiement;
    private String telephone;
    private String signature;
    private String numeroDemande;
    private Integer nombreCopie;
}
