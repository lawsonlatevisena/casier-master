package tg.ceel.cj.casierapi.dto;

import lombok.Data;

import java.util.Date;
@Data
public class UtilisateurCasierPointRetraitDto {

    private Long id;
    private Long pointRetraitId;
    private String pointRetraitLibelle;
    private Long utilisateurCasierCode;

    private Boolean active;

    private Date dateAjout;

    private Date dateFin;

    private  String username;
    private  String userNomPrenoms;
}
