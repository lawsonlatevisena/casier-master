package tg.ceel.cj.casierapi.dto;

import lombok.*;
import tg.ceel.cj.casierapi.entities.CanalPayement;
import tg.ceel.cj.casierapi.entities.ModePayement;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayementDto implements Serializable {
    private  Long id;
    private  String numero;
    private  String numeroTransaction;
    private  Date datePayement;
    private  Double montant;
    private  CanalPayement canalPayement;
    private  ModePayement modePayement;
    private  String quittanceTresor;
    private  Boolean regler;
    private  String transactionUUID;
    private  Double montantPaye;
    private  String devisePaiement;
    private  String dateTransaction;
    private  String reponseJson;
    private  String signedAttributeNames;
    private  String moyenPaiement;
    private  Date dateCreation;
}
