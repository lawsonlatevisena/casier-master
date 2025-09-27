package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandePMDto {
    private String typeDemande;
    private Long id;
    private String numeroTraitement;
    private Date dateDemande;
    private String telephone;
    private String email;
    private Integer nombreCopie;
    private String paysNaissanceCode;
    private String paysNaissanceLibelle;
    private String paysNationaliteCode;
    private String paysNationaliteLibelle;
    private String paysResidenceCode;
    private String paysResidenceCodeLibelle;
    private Integer localiteNaissanceId;
    private String localiteNaissanceIdLibelle;
    private String lieuNaissance;
    private String lieuResidence;
    private Integer typePieceId;
    private String typePieceLibelle;
    private Integer pointRetraitId;
    private Boolean tracked;
    private String trackingCode;
    private Boolean trackingNotificationSuccess;
    private Boolean trackingDeliverySuccess;
    private String deliveryMode;
    private Integer bureauPosteRetraitId;
    private String numeroDemande;
    private String record;
    private String process;
    private String step;
    private String feedbackTaskId;
    private Integer order;
    private String qrCode;
    private UserB2Dto demandeur;
    private  String denomination;
    private String numeroRccm;
    private String nif;
    private String refExistenceLegale;
    private String siege;
    private Long categoriePersonneMoraleId;
    private String extentionFichier;
    private String mimeType;
    private String nomFichier;

}
