package tg.ceel.cj.casierapi.fnc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonneDto implements Serializable {
    private String nom;
    private String prenom;
    private String nomPere;
    private String prenomPere;
    private String nomMere;
    private String prenomMere;
    private Date datenaissance;
    private String numpi;
    private String numactenaiss;
    private String lieunaissance;
    private String sexe;
    private String profession;
    private String nomprenom;
    private String telephone;
    private String email;
    private String adresse;
    private Date datecreation;
    public String denomination;
    private String nif;
    private String numeroIdentification;
}
