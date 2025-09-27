package tg.ceel.cj.casierapi.models;

import lombok.*;
import tg.ceel.cj.casierapi.dto.UtilisateurCasierDto;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;
import tg.ceel.cj.casierapi.utils.ResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "response")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseObject implements Serializable {

    private ResponseCode responseCode;
    private String description;
    private UtilisateurCasierDto utilisateurCasier;
    private String numeroDemande;
    private String numeroPiece;
    private double montant;
    private DemandeSite demandeB2;
    private DemandeSite demandeB3;
    private DemandeSite demandeANC;
    private List<DemandeSite> demandeB2s;
    private List<DemandeSite> demandeB1s;
    private DemandeUtils demandeUtils;
    private String tmoneyPurchaseref;
    private String floozStatut;
    private String signature;
    private String signedAttributes;


    public ResponseObject(ResponseCode responseCode, String description) {
        this.responseCode = responseCode;
        this.description = description;
    }


    @Override
    public String toString() {
        return "ResponseObject{" + "responseCode=" + responseCode + ", description=" + description + ", utilisateurCasier=" + utilisateurCasier + ", numeroDemande=" + numeroDemande + ", numeroPiece=" + numeroPiece + ", montant=" + montant + ", demandeB2=" + demandeB2 + ", demandeB3=" + demandeB3 + ", demandeANC=" + demandeANC + ", demandeB2s=" + demandeB2s + ", demandeB1s=" + demandeB1s + ", demandeUtils=" + demandeUtils + ", tmoneyPurchaseref=" + tmoneyPurchaseref + ", floozStatut=" + floozStatut + ", signature=" + signature + ", signedAttributes=" + signedAttributes + '}';
    }
}
