/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;



/*@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING, length = 3)
@DiscriminatorValue(value = "D")
@XmlRootElement
@XmlSeeAlso({
        DemandeANC.class,
        DemandeB2.class,
        DemandeB3.class
})*/

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "demandes")
@Builder
public class Demande extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    @Column(name = "dtype")
    private String typeDemande;
    @Column(name = "numero_traitement", nullable = true)
    private String numeroTraitement;

    @Column(name = "date_demande")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateDemande;

    @Column(name = "nom", length = 31)
    protected String nom;

    @Column(name = "prenom", length = 51)
    protected String prenom;

    @Column(name = "date_naissance")
    @Temporal(TemporalType.DATE)
    protected Date dateNaissance;

    @Column(name = "telephone", length = 21)
    protected String telephone;

    @Column(name = "email", length = 31)
    protected String email;

    @Column(name = "nombre_copie")
    protected Integer nombreCopie;

    @Column(name = "nom_pere", length = 31)
    protected String nomPere;

    @Column(name = "prenom_pere", length = 51)
    protected String prenomPere;

    @Column(name = "nom_mere", length = 31)
    protected String nomMere;

    @Column(name = "prenom_mere", length = 51)
    protected String prenomMere;

    @Column(name = "valider", nullable = true)
    protected Boolean valider;

    @Column(name = "etape_1_valider")
    protected Boolean etape1Valider;

    @Column(name = "etape_2_valider")
    protected Boolean etape2Valider;

    @Column(name = "etape_3_valider")
    protected Boolean etape3Valider;

    @Column(name = "etape_1_motif", nullable = true, length = 221)
    protected String etape1Motif;

    @Column(name = "etape_2_motif", nullable = true, length = 221)
    protected String etape2Motif;

    @Column(name = "etape_3_motif", nullable = true, length = 221)
    protected String etape3Motif;

    @Column(name = "numero_carte", nullable = true, length = 21)
    protected String numeroCarte;

    @Column(name = "numero_passeport", nullable = true, length = 21)
    protected String numeroPasseport;

    @Column(name = "date_delivrance_carte", nullable = true)
    @Temporal(TemporalType.DATE)
    protected Date dateDelivranceCarte;

    @Column(name = "numero_acte", nullable = true, length = 21)
    protected String numeroActe;

    @Column(name = "numero_feuillet", nullable = true, length = 21)
    protected String numeroFeuillet;

    @Column(name = "numero_registre", nullable = true, length = 21)
    protected String numeroRegistre;

    @Column(name = "annee", nullable = true, length = 21)
    protected String annee; // Année de l'acte de naissance

    @Column(name = "annee_demande", nullable = true)
    private Integer anneeDemande;  // Annee de la demande 

    @Column(name = "etat_civil", nullable = true, length = 100)
    protected String etatCivil;

    @Column(name = "numero_jugement", nullable = true, length = 21)
    protected String numeroJugement;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_jugement", nullable = true)
    protected Date dateJugement;

    @Column(name = "tribunal_jugement", nullable = true, length = 100)
    protected String tribunalJugement;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_transcription_jugement", nullable = true)
    protected Date dateTranscriptionJugement;

    @Column(name = "numero_transcription_jugement", nullable = true, length = 21)
    protected String numeroTranscriptionJugement;

    @Column(name = "nom_jugement", nullable = true, length = 100)
    protected String nomJugement;

    @Column(name = "prenom_jugement", nullable = true, length = 100)
    protected String prenomJugement;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_arivee_togo", nullable = true)
    private Date date_arivee_togo;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_naissance_jugement", nullable = true)
    protected Date dateNaissanceJugement;

    @Column(name = "nom_pere_jugement", nullable = true, length = 100)
    protected String nomPereJugement;

    @Column(name = "prenom_pere_jugement", nullable = true, length = 100)
    protected String prenomPereJugement;

    @Column(name = "nom_mere_jugement", nullable = true, length = 100)
    protected String nomMereJugement;

    @Column(name = "prenom_mere_jugement", nullable = true, length = 100)
    protected String prenomMereJugement;

    @Column(name = "numero_acte_rectifie", nullable = true)
    protected String numeroActeRectifie;

    @Column(name = "etat_civil_acte_rectifie", nullable = true)
    protected String etatCivilActeRectifie;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_acte_rectifie", nullable = true)
    protected Date dateActeRectifie;

    @Column(name = "disponible", nullable = true)
    protected Boolean disponible;

    @Column(name = "date_validation", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateValidation;
    private Integer nombreEnfant;

    @Column(name = "date_edition", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateEdition;

    @Column(name = "date_retrait", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateRetrait;
    private Date dateMention;
    private String numeroMention;

    @Column(name = "date_disponibilite", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateDisponibilite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_pays_naissances")
    protected Pays paysNaissance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_pays_nationalites")
    protected Pays paysNationalite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_pays_residences")
    protected Pays paysResidence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_localite_naissance", nullable = true)
    protected Localite localiteNaissance;

    @Column(name = "lieu_naissance", length = 51)
    protected String lieuNaissance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_prefecture_naissance", nullable = true)
    protected Prefecture prefectureNaissance;

    @Column(name = "lieu_residence", length = 51)
    protected String lieuResidence;

    @Column(name = "extension_fichier", nullable = true, length = 121)
    protected String extentionFichier;

    @Column(name = "couleur", nullable = true, length = 17)
    protected String couleur;

    @ManyToOne
    @JoinColumn(name = "code_sexes")
    protected Sexe sexe;

    @ManyToOne
    @JoinColumn(name = "id_categorieSocioProfessionnelle", nullable = true)
    @JsonIgnore
    protected CategorieSocioProfessionnelle categorieSocioProfessionnelle;

    @Column(name = "profession", length = 521)
    protected String emploi;

    @ManyToOne
    @JoinColumn(name = "id_profession")
    protected Profession profession;

    @Column(name = "autre_profession", nullable = true)
    protected String autreProfession;

    @ManyToOne
    @JoinColumn(name = "id_type_piece", nullable = true)
    protected TypePiece typePiece;

    @ManyToOne
    @JoinColumn(name = "id_situation_matrimoniale")
    protected SituationMatrimoniale situationMatrimoniale;

    @Column(name = "provenance", length = 17)
    protected Provenance provenance;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_payement", nullable = true)
    protected Payement payement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_point_retrait", nullable = true)
    private PointRetrait pointRetrait;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_prestation", nullable = true)
    protected Prestation prestation;

    /*  @OneToMany(mappedBy = "demande", cascade = {}, fetch = FetchType.EAGER)
      @XmlTransient
      @JoinColumn(name = "id_condamnation_demande")
      protected List<CondamnationDemande> condamnationDemandes = new ArrayList<>();
  */
    @Column(name = "tracked", nullable = true)
    protected Boolean tracked;

    @Column(name = "tracking_code", nullable = true)
    protected String trackingCode;

    @Column(name = "tracking_notification_success", nullable = true)
    protected Boolean trackingNotificationSuccess;

    @Column(name = "tracking_delivery_success", nullable = true)
    protected Boolean trackingDeliverySuccess;

    @Column(name = "delivery_mode", nullable = true)
    private String deliveryMode;

    @ManyToOne
    @JoinColumn(name = "id_bureau_poste_retrait", nullable = true)
    protected PointRetrait bureauPosteRetrait;

    @Column(name = "numero_demande", nullable = true)
    protected String numeroDemande;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur_validateur", nullable = true)
    @JsonIgnore
    protected UtilisateurCasier utilisateurValidateur;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur_traiteur", nullable = true)
    @JsonIgnore
    protected UtilisateurCasier utilisateurTraiteur;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur_retrait", nullable = true)
    @JsonIgnore
    protected UtilisateurCasier utilisateurRetrait;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur_derniere_modification", nullable = true)
    @JsonIgnore
    protected UtilisateurCasier utilisateurDerniereModification;
    private String mimeType;
    private String nomFichier;


    /**
     * gestion des B1
     */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_demandeur", nullable = true)
    @JsonIgnore
    private UtilisateurCasier demandeurB1;
    @Transient
    private String username;
    private String tribunal_destination;
    private String personne_destinataire;

    /**
     * fin gestion des B1
     */

    // ATD

    private String record;
    private String process;
    private String step;
    private String feedbackTaskId;
    @Column(name = "ordre")
    private Integer order;
    @Column(columnDefinition = "text default null")
    private String qrCode;
    @Column(columnDefinition = "text default null")
    private String motifInvalidation;
    @Column(columnDefinition = "boolean default false")
    private Boolean invalidee;
    @Column(columnDefinition = "boolean default false")
    private Boolean signee;
    @Column(columnDefinition = "boolean default false")
    private Boolean traitee;
    private String pathBulletin;

    @Column(name = "retirer", columnDefinition = "boolean default false")
    protected Boolean retirer;
    // Gestion des personnes morales
    private String denomination;
    private String numeroRccm;
    private String nif;
    private String refExistenceLegale;
    private String siege;
    private String numero_piece_personne_morale;
    private String nom_complet_dirigeant;
    private String titre_dirigeant;
    private String telephone_dirigeant;
    private String adresse_dirigeant;
    private String localite_residence_dirigeant;

    @ManyToOne
    @JoinColumn(name = "type_personne_morale_id")
    private TypePersonneMorale typePersonneMorale;

    private Integer nombreTentative = 0;
    private String indicatif_demandeur;
    private String indicatif_telephone_dirigeant;
    @ManyToOne
    @JoinColumn(name = "categorie_personne_morale_id", nullable = true)
    private CategoriePersonneMorale categoriePersonneMorale;


    @Column(columnDefinition = "text")
    private String objet;
    @Column(name = "parent_demande_id", nullable = true)
    private Long parentDemandeId;
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Demande other = (Demande) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


    public void cleanAndRenew() {
        this.setId(null);
        this.setPayement(null);
        this.setDateDemande(new Date());
        this.setDateValidation(null);
        this.setValider(null);
        this.setDisponible(null);
        this.setDateDisponibilite(null);
        this.setDateRetrait(null);
        this.setCouleur(null);
        this.setEtape1Valider(false);
        this.setEtape2Valider(false);
        this.setEtape3Valider(false);
        this.setEtape1Motif(null);
        this.setEtape2Motif(null);
        this.setEtape3Motif(null);
        //  this.setCondamnationDemandes(null);
        this.setDeliveryMode(null);
        this.setBureauPosteRetrait(null);
        this.setPointRetrait(null);
    }

    @Override
    public String toString() {
        return "Demande{" + "id=" + id + ", numeroTraitement=" + numeroTraitement + ", dateDemande=" + dateDemande + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", telephone=" + telephone + ", email=" + email + ", nombreCopie=" + nombreCopie + ", nomPere=" + nomPere + ", prenomPere=" + prenomPere + ", nomMere=" + nomMere + ", prenomMere=" + prenomMere + ", valider=" + valider + ", etape1Valider=" + etape1Valider + ", etape2Valider=" + etape2Valider + ", etape3Valider=" + etape3Valider + ", etape1Motif=" + etape1Motif + ", etape2Motif=" + etape2Motif + ", etape3Motif=" + etape3Motif + ", numeroCarte=" + numeroCarte + ", numeroPasseport=" + numeroPasseport + ", dateDelivranceCarte=" + dateDelivranceCarte + ", numeroActe=" + numeroActe + ", numeroFeuillet=" + numeroFeuillet + ", numeroRegistre=" + numeroRegistre + ", annee=" + annee + ", anneeDemande=" + anneeDemande + ", etatCivil=" + etatCivil + ", numeroJugement=" + numeroJugement + ", dateJugement=" + dateJugement + ", tribunalJugement=" + tribunalJugement + ", dateTranscriptionJugement=" + dateTranscriptionJugement + ", numeroTranscriptionJugement=" + numeroTranscriptionJugement + ", nomJugement=" + nomJugement + ", prenomJugement=" + prenomJugement + ", dateNaissanceJugement=" + dateNaissanceJugement + ", nomPereJugement=" + nomPereJugement + ", prenomPereJugement=" + prenomPereJugement + ", nomMereJugement=" + nomMereJugement + ", prenomMereJugement=" + prenomMereJugement + ", numeroActeRectifie=" + numeroActeRectifie + ", etatCivilActeRectifie=" + etatCivilActeRectifie + ", dateActeRectifie=" + dateActeRectifie + ", disponible=" + disponible + ", dateValidation=" + dateValidation + ", dateEdition=" + dateEdition + ", dateRetrait=" + dateRetrait + ", dateDisponibilite=" + dateDisponibilite + ", retirer=" + retirer + ", paysNaissance=" + paysNaissance + ", paysNationalite=" + paysNationalite + ", paysResidence=" + paysResidence + ", localiteNaissance=" + localiteNaissance + ", lieuNaissance=" + lieuNaissance + ", prefectureNaissance=" + prefectureNaissance + ", lieuResidence=" + lieuResidence + ", extentionFichier=" + extentionFichier + ", couleur=" + couleur + ", sexe=" + sexe + ", categorieSocioProfessionnelle=" + categorieSocioProfessionnelle + ", emploi=" + emploi + ", profession=" + profession + ", autreProfession=" + autreProfession + ", typePiece=" + typePiece + ", situationMatrimoniale=" + situationMatrimoniale + ", provenance=" + provenance + ", payement=" + payement + ", pointRetrait=" + pointRetrait + ", prestation=" + prestation + ", tracked=" + tracked + ", trackingCode=" + trackingCode + ", trackingNotificationSuccess=" + trackingNotificationSuccess + ", trackingDeliverySuccess=" + trackingDeliverySuccess + ", deliveryMode=" + deliveryMode + ", bureauPosteRetrait=" + bureauPosteRetrait + ", numeroDemande=" + numeroDemande + ", utilisateurValidateur=" + utilisateurValidateur + ", utilisateurTraiteur=" + utilisateurTraiteur + ", utilisateurRetrait=" + utilisateurRetrait + ", utilisateurDerniereModification=" + utilisateurDerniereModification + '}';
    }

}
