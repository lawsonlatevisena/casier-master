package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetraitDto implements Serializable {
    private  Integer version;
    private  Integer id;
    private  String nom;
    private  String prenom;
    private  String numeroPiece;
    private  String telephone;
    private  String adresse;
    private  Date dateRetrait;
    private  String fileName;
    private  Integer typePieceId;
    private  String typePieceLibelle;
    private  String typePieceBulletin;
    private  Boolean typePieceActive;
    private  String demandeNom;
    private  String demandePrenom;
    private  String demandeNumeroDemande;
    private  Integer utilisateurCasierVersion;
    private  Long utilisateurCasierCode;
    private  String utilisateurCasierPersonneInfoNom;
    private  String utilisateurCasierPersonneInfoPrenom;
    private  String utilisateurCasierPersonneInfoEmail;
    private  Boolean utilisateurCasierIsPosteUser;
}
