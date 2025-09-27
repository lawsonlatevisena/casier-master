package tg.ceel.cj.casierapi.fnc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandeFnc {
    private String nom;
    private String prenom;
    private String sexe;
    private Date dateNaissance;
    private String nomPere;
    private String prenomPere;
    private String nomMere;
    private String prenomMere;
    private String typeBulletin;
    public String denomination;
    private String nif;
    private String numeroIdentification;
}
