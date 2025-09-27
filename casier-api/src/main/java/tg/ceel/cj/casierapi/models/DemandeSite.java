package tg.ceel.cj.casierapi.models;

import lombok.*;
import org.springframework.lang.Nullable;
import tg.ceel.cj.casierapi.dto.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeSite {
    private String sessionId;
    private String provenance;
    private TypePieceDto typePiece;
    private String numeroCarte;
    private Date dateDelivranceCarte;
    private String numeroDemande;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String lieuNaissance;
    private Integer nombreEnfant;
    private String nomPere;
    private String prenomPere;
    private String nomMere;
    private String prenomMere;
    private SexeDto sexe;
    private SituationMatrimonialeDto situationMatrimoniale;
    private PrefectureDto prefectureNaissance;
    private ProfessionDto profession;
    private String telephone;
    private String lieuResidence;
    private PaysDto paysResidence;
    private Integer nombreCopie;
    private PaysDto paysNationalite;
    private PaysDto paysNaissance;
    private PayementDto payementDto;
    private AutrePointRetraitDto bureauPosteRetrait;
    private PointRetraitDto pointRetrait;
    private Boolean tracked;
    //JUGEMENT RECTIFICATIF
    private String numeroJugement;
    private Date dateJugement;
    private String tribunalJugement;
    private Date dateTranscriptionJugement;
    private String numeroTranscriptionJugement;
    private String numeroActeRectifie;
    private String etatCivilActeRectifie;
    private Date dateActeRectifie;
    private String nomJugement;
    private String prenomJugement;
    private Date dateNaissanceJugement;
    private String nomPereJugement;
    private String prenomPereJugement;
    private String nomMereJugement;
    private String prenomMereJugement;
    //ACTE NAISSANCE
    private String etatCivil;
    private String numeroFeuillet;
    private String numeroRegistre;
    private String numeroActe;
    private String annee;
    @Nullable
    private Date date_arivee_togo;

    @Override
    public String toString() {
        return "DemandeSite{" +
                "provenance='" + provenance + '\'' +
                ", typePiece=" + typePiece +
                ", numeroCarte='" + numeroCarte + '\'' +
                ", dateDelivranceCarte=" + dateDelivranceCarte +
                ", numeroDemande='" + numeroDemande + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", lieuNaissance='" + lieuNaissance + '\'' +
                ", nombreEnfant=" + nombreEnfant +
                ", nomPere='" + nomPere + '\'' +
                ", prenomPere='" + prenomPere + '\'' +
                ", nomMere='" + nomMere + '\'' +
                ", prenomMere='" + prenomMere + '\'' +
                ", sexe=" + sexe +
                ", situationMatrimoniale=" + situationMatrimoniale +
                ", prefectureNaissance=" + prefectureNaissance +
                ", profession=" + profession +
                ", telephone='" + telephone + '\'' +
                ", lieuResidence='" + lieuResidence + '\'' +
                ", paysResidence=" + paysResidence +
                ", nombreCopie=" + nombreCopie +
                ", paysNationalite=" + paysNationalite +
                ", paysNaissance=" + paysNaissance +
                ", payementDto=" + payementDto +
                ", bureauPosteRetrait=" + bureauPosteRetrait +
                ", pointRetrait=" + pointRetrait +
                ", tracked=" + tracked +
                ", numeroJugement='" + numeroJugement + '\'' +
                ", dateJugement=" + dateJugement +
                ", tribunalJugement='" + tribunalJugement + '\'' +
                ", dateTranscriptionJugement=" + dateTranscriptionJugement +
                ", numeroTranscriptionJugement='" + numeroTranscriptionJugement + '\'' +
                ", numeroActeRectifie='" + numeroActeRectifie + '\'' +
                ", etatCivilActeRectifie='" + etatCivilActeRectifie + '\'' +
                ", dateActeRectifie=" + dateActeRectifie +
                ", nomJugement='" + nomJugement + '\'' +
                ", prenomJugement='" + prenomJugement + '\'' +
                ", dateNaissanceJugement=" + dateNaissanceJugement +
                ", nomPereJugement='" + nomPereJugement + '\'' +
                ", prenomPereJugement='" + prenomPereJugement + '\'' +
                ", nomMereJugement='" + nomMereJugement + '\'' +
                ", prenomMereJugement='" + prenomMereJugement + '\'' +
                ", etatCivil='" + etatCivil + '\'' +
                ", numeroFeuillet='" + numeroFeuillet + '\'' +
                ", numeroRegistre='" + numeroRegistre + '\'' +
                ", numeroActe='" + numeroActe + '\'' +
                ", annee='" + annee + '\'' +
                '}';
    }
}
