package tg.ceel.cj.casierapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.foreign.entities.Prefecture;
import tg.ceel.cj.casierapi.models.DemandeSite;

import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeDto {

    private String typeDemande;

    private Long id;

    private String numeroTraitement;

    private Date dateDemande;

    private String nom;

    private String prenom;

    private Date dateNaissance;

    private String telephone;

    private String email;

    private Integer nombreCopie;

    private String nomPere;

    private String prenomPere;

    private String nomMere;

    private String prenomMere;
    private Boolean valider;
    private Boolean etape1Valider;
    private Boolean etape2Valider;
    private Boolean etape3Valider;
    private String etape1Motif;
    private String etape2Motif;
    private String etape3Motif;

    private String numeroCarte;

    private String numeroPasseport;

    private Date dateDelivranceCarte;

    private String numeroActe;

    private String numeroFeuillet;

    private String numeroRegistre;

    private String annee; // Année de l'acte de naissance

    private Integer anneeDemande;  // Annee de la demande

    private String etatCivil;

    private String numeroJugement;

    private Date dateJugement;

    private String tribunalJugement;

    private Date dateTranscriptionJugement;

    private String numeroTranscriptionJugement;

    private String nomJugement;

    private String prenomJugement;

    private Date dateNaissanceJugement;

    private String nomPereJugement;

    private String prenomPereJugement;

    private String nomMereJugement;

    private String prenomMereJugement;

    private String numeroActeRectifie;

    private String etatCivilActeRectifie;

    private Date date_arivee_togo;
    private Date dateActeRectifie;
    private Boolean disponible;
    private Date dateValidation;
    private Date dateEdition;
    private Date dateRetrait;
    private Date dateDisponibilite;
    private Boolean retirer;


    private String paysNaissanceCode;

    private String paysNaissanceLibelle;

    private String paysNationaliteCode;

    private String paysNationaliteLibelle;

    private String paysResidenceCode;

    private String paysResidenceLibelle;

    private String lieuNaissance;

    private Integer prefectureNaissanceId;
    private String prefectureNaissanceLibelle;

    private String lieuResidence;
    private String extentionFichier;

    private Integer situationMatrimonialeId;
    private String situationMatrimonialeLibelle;
    private String couleur;

    private String sexeCode;
    private String libelle;
    private Integer categorieSocioProfessionnelleId;
    private String categorieSocioProfessionnelleLibelle;
    private String emploi;


    private Integer professionId;
    private String professionLibelle;
    private String autreProfession;

    private Integer typePieceId;
    private String typePieceLibelle;
    private String provenance;

    private Long pointRetraitId;

    private String pointRetraitLibelle;
    private Boolean tracked;
    private String trackingCode;
    private Boolean trackingNotificationSuccess;
    private Boolean trackingDeliverySuccess;
    private String deliveryMode;
    private Long bureauPosteRetraitId;
    private String bureauPosteRetraitLibelle;
    private String numeroDemande;
    private String mimeType;
    private String nomFichier;
    private Integer nombreEnfant;
    private String tribunal_destination;
    private String personne_destinataire;
    /**
     * gestion des B1
     */
    private String username;
    /** fin gestion des B1 */

    /**
     * Gestion information ATD
     */

    private String record;
    private String process;
    private String step;
    private String feedbackTaskId;
    private Integer order;
    private String qrCode;
    private String motifInvalidation;
    private Boolean invalidee;
    private Long payementId;
    private Boolean payementRegler;
    private Date payementDatePayement;
    private String payementModePayement;
    private Boolean signee;
    private Boolean traitee;

    private UtilisateurCasierDto utilisateurValidateur;

    // TODO: Champ à valider
    private String pathBulletin;

    private UserB2Dto demandeur;

    private String denomination;
    private String numeroRccm;
    private String nif;
    private String refExistenceLegale;
    private String siege;
    private String numero_rccm;
    private String ref_existence_legale;
    private String numero_piece_personne_morale;
    private String mobile_demandeur_personne_morale;
    private String type_personne_morale_code;
    private String nom_complet_dirigeant;
    private String telephone_dirigeant;
    private String loaclite_residence_dirigeant;
    private String titre_dirigeant;
    private String adresse_dirigeant;
    private String pays_siege;

    private Long categoriePersonneMoraleId;
    private String name;

    public static DemandeSite convert(Demande demande) {
        try {
            if (demande == null) {
                return null;
            }
            DemandeSite demandeSite = DemandeSite.builder()
                    .nom(demande.getNom())
                    .prenom(demande.getPrenom())
                    .dateNaissance(demande.getDateNaissance())
                    .lieuNaissance(demande.getLieuNaissance())
                    .nombreEnfant(demande.getNombreEnfant())
                    .nomPere(demande.getNomPere())
                    .prenomPere(demande.getPrenomPere())
                    .nomMere(demande.getNomMere())
                    .prenomMere(demande.getPrenomMere())
                    .telephone(demande.getTelephone())
                    .lieuResidence(demande.getLieuResidence())
                    .numeroDemande(demande.getNumeroDemande())
                    .nombreCopie(demande.getNombreCopie())
                    .numeroCarte(demande.getNumeroCarte())
                    .numeroJugement(demande.getNumeroJugement())
                    .dateJugement(demande.getDateJugement())
                    .tribunalJugement(demande.getTribunalJugement())
                    .dateTranscriptionJugement(demande.getDateTranscriptionJugement())
                    .numeroTranscriptionJugement(demande.getNumeroTranscriptionJugement())
                    .numeroActeRectifie(demande.getNumeroActeRectifie())
                    .etatCivilActeRectifie(demande.getEtatCivilActeRectifie())
                    .dateActeRectifie(demande.getDateActeRectifie())
                    .nomJugement(demande.getNomJugement())
                    .prenomJugement(demande.getPrenomJugement())
                    .dateNaissanceJugement(demande.getDateNaissanceJugement())
                    .nomPereJugement(demande.getNomPereJugement())
                    .prenomPereJugement(demande.getPrenomPereJugement())
                    .nomMereJugement(demande.getNomMereJugement())
                    .prenomMereJugement(demande.getPrenomMereJugement())
                    .etatCivil(demande.getEtatCivil())
                    .numeroFeuillet(demande.getNumeroFeuillet())
                    .numeroRegistre(demande.getNumeroRegistre())
                    .numeroActe(demande.getNumeroActe())
                    .annee(demande.getAnnee())
                    .tracked(demande.getTracked())
                    .date_arivee_togo(demande.getDate_arivee_togo())
                    .dateDelivranceCarte(demande.getDateDelivranceCarte())
                    .build();
            if (demande.getPaysNaissance()!=null){
                demandeSite.setPaysNaissance(PaysDto.builder()
                        .code(demande.getPaysNaissance().getCode())
                        .libelle(demande.getPaysNaissance().getLibelle())
                        .build());
            }
            if (demande.getPaysResidence()!=null){
                demandeSite.setPaysResidence(PaysDto.builder()
                        .code(demande.getPaysResidence().getCode())
                        .libelle(demande.getPaysResidence().getLibelle())
                        .build());
            }
            if (demande.getPaysNationalite()!=null){
                demandeSite.setPaysNationalite(PaysDto.builder()
                        .code(demande.getPaysNationalite().getCode())
                        .libelle(demande.getPaysNationalite().getLibelle())
                        .build());
            }
            if (demande.getSexe()!=null){
                demandeSite.setSexe(SexeDto.builder()
                        .code(demande.getSexe().getCode())
                        .libelle(demande.getSexe().getLibelle())
                        .build());
            }
            if (demande.getSituationMatrimoniale()!=null){
                demandeSite.setSituationMatrimoniale(SituationMatrimonialeDto.builder()
                                .id(demande.getSituationMatrimoniale().getId())
                                .libelle(demande.getSituationMatrimoniale().getLibelle())
                        .build());
            }
            if (demande.getPrefectureNaissance()!=null){
                demandeSite.setPrefectureNaissance(PrefectureDto.builder()
                                .id(demande.getPrefectureNaissance().getId())
                                .chefLieu(demande.getPrefectureNaissance().getChefLieu())
                                .libelle(demande.getPrefectureNaissance().getLibelle())
                        .build());
            }
            if(demande.getTypePiece() != null){
                demandeSite.setTypePiece(TypePieceDto.builder()
                                .code(demande.getTypePiece().getCode())
                                .id(demande.getTypePiece().getId())
                                .libelle(demande.getTypePiece().getLibelle())
                        .build());
            }
            if(demande.getProfession() != null){
                demandeSite.setProfession(ProfessionDto.builder()
                                .id(demande.getProfession().getId())
                                .libelle(demande.getProfession().getLibelle())
                        .build());
            }
            if(demande.getPointRetrait() != null){
                demandeSite.setPointRetrait(PointRetraitDto.builder()
                                .code(demande.getPointRetrait().getCode())
                                .id(demande.getPointRetrait().getId())
                                .libelle(demande.getPointRetrait().getLibelle())
                                .libelleLong(demande.getPointRetrait().getLibelleLong())
                                .localite(demande.getPointRetrait().getLocalite())
                        .build());
            }
            return demandeSite;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
