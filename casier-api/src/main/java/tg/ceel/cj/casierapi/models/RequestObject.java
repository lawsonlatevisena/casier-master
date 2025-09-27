package tg.ceel.cj.casierapi.models;

import lombok.*;
import tg.ceel.cj.casierapi.dto.*;
import tg.ceel.cj.casierapi.utils.RaccourciDemandeRequest;
import tg.ceel.cj.casierapi.utils.zpay.ZpayResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "request")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RequestObject implements Serializable {

    private DemandeSite demandeB1;
    private DemandeSite demandeB2;
    private DemandeSite demandeB3;
    private DemandeSite demandeANC;
    private UtilisateurCasierDto utilisateur;
    private String username;
    private String password;
    private String numeroDemande;
    private Integer nombreCopie;
    private Integer debut;
    private Integer max;
    private DemandeSite demande;
    private PayementDto payement;
    private TmoneyRequestDto tmoneyRequest;
    private FloozTransactionDto floozTransaction;
    private FloozTransactionDto floozTransactionUpdate;
    private UserB2Dto userB2;
    private ZpayResponse zpayResponse;
    private RaccourciRequest raccourciRequest;
}
