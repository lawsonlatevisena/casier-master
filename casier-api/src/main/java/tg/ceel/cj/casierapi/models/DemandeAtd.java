package tg.ceel.cj.casierapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.TypePiece;
import tg.ceel.cj.casierapi.utils.CasierUtils;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandeAtd {
    private String type_demande;
    private String id_demande;
    private Integer nombre_copie_demande;
    private String id_type_piece_demande;
    private Integer id_point_retrait_demande;
    private Boolean retrait_par_poste_demande;
    private String nom_demandeur;
    private String prenom_demandeur;
    private Date date_naissance_demandeur;
    private String telephone_demandeur;
    private String indicatif_demandeur;
    private String email_demandeur;
    private String nom_pere_demandeur;
    private String prenom_pere_demandeur;
    private String nom_mere_demandeur;
    private String prenom_mere_demandeur;
    private String sexe_demandeur;
    private Integer nombre_enfant_demandeur;
    private String lieu_naissance_demandeur;
    private Integer id_situation_matrimoniale_demandeur;
    private Integer id_prefecture_naissance_demandeur;
    private String profession_demandeur;
    private String autre_profession_demandeur;
    private String autre_profession;
    private String lieu_residence_demandeur;
    private String pays_residence_demandeur;
    private String pays_naissance_demandeur;
    private String pays_nationalite_demandeur;
    private String numero_certificat_nationalite;
    private Date date_delivrance_nationalite;

    private String numero_feuillet_naissance;
    private String numero_registre_naissance;
    private String annee_acte_naissance;
    private String numero_acte_naissance;
    private String etat_civil_naissance;

    private String numero_jugement_sup_recons;
    private Date date_jugement_sup_recons;
    private String tribunal_jugement_sup_recons;
    private Date date_transcription_jugement_sup_recons;
    private String numero_transcription_jugement_sup_recons;

    private Integer id_bureau_poste_retrait_demande;
    private String record;
    private String process;
    private String step;
    private String feedbackTaskId;
    private Integer order;
    private String numero_passeport_demandeur;
    private Demandeur demandeur;
    private String numero_demande;
    private String numero_carte;
    private String numero_jugement_rectificatif;
    private Date date_jugement_rectificatif;
    private String tribunal_jugement_rectificatif;
    private Date date_mention_jugement_rectificatif;
    private String numero_mention_jugement_rectificatif;
    private String numero_acte_rectifie;
    private String etat_civil_acte_rectifie;
    private Date date_etablissement_acte_rectifie;
    private String ancien_nom_demandeur;
    // A prendre en compte dans l'entité demande
    private String ancien_prenom_demandeur;
    private Date ancien_date_naissance_demandeur;
    private String ancien_nom_pere_demandeur;
    private String ancien_prenom_pere_demandeur;
    private String ancien_nom_mere_demandeur;
    private String ancien_prenom_mere_demandeur;

    private Date date_arrivee_demandeur;
    private Date date_delivrance_naissance;
    private Date date_delivrance_passeport;
    private Date date_delivrance_carte_sejour;

    private String numero_passeport;
    private String numero_carte_sejour;
    @JsonIgnore
    private String extention_fichier;
    @JsonIgnore
    private String mimeType;

    private String denomination;
    private String numero_rccm;
    private String nif;
    private String ref_existence_legale;
    private String siege;
    private String numero_piece_personne_morale;
    private String mobile_demandeur_personne_morale;

    private String type_personne_morale_code;

    private String nom_complet_dirigeant;
    private String telephone_dirigeant;

    private String localite_residence_dirigeant;
    private String titre_dirigeant;
    private String adresse_dirigeant;
    private String pays_siege;
    private String indicatif_telephone_dirigeant;
    private String indicatif_mobile_demandeur_personne_morale;

    private String code_certification;
    @Nullable
    @JsonIgnore
    private Date date_arivee_togo;
    private String tribunal_destination;
    private String personne_destinataire;

    public static DemandeAtd demandeToDemandeAtd(Demande demande) {
        DemandeAtd atd = DemandeAtd.builder()
                .type_demande(demande.getTypeDemande())
                .email_demandeur(demande.getEmail())
                .lieu_naissance_demandeur(demande.getLieuNaissance())
                .lieu_residence_demandeur(demande.getLieuResidence())
                .nom_demandeur(demande.getNom())
                .prenom_demandeur(demande.getPrenom())
                .date_naissance_demandeur(demande.getDateNaissance())
                .nom_pere_demandeur(demande.getNomPere())
                .prenom_pere_demandeur(demande.getPrenomPere())
                .nom_mere_demandeur(demande.getNomMere())
                .prenom_mere_demandeur(demande.getPrenomMere())
                .numero_feuillet_naissance(demande.getNumeroFeuillet())
                .numero_registre_naissance(demande.getNumeroRegistre())
                .numero_acte_naissance(demande.getNumeroActe())
                .nombre_enfant_demandeur(demande.getNombreEnfant())
                .telephone_demandeur(demande.getTelephone())
                .numero_passeport(demande.getNumeroPasseport())
                .numero_passeport_demandeur(demande.getNumeroPasseport())
                .nombre_copie_demande(demande.getNombreCopie())
                .id_point_retrait_demande(Math.toIntExact(demande.getPointRetrait().getId()))
                .id_demande(demande.getRecord())
                .record(demande.getRecord())
                .order(demande.getOrder())
                .numero_carte_sejour(demande.getNumeroCarte())
                .id_situation_matrimoniale_demandeur(demande.getSituationMatrimoniale().getId())
                .process(demande.getProcess())
                .step(demande.getStep())
                .feedbackTaskId(demande.getStep())
                .build();
        atd = construireDemande(atd, demande);
        atd.setPays_naissance_demandeur(demande.getPaysNaissance().getCode());
        atd.setPays_nationalite_demandeur(demande.getPaysNationalite().getCode());
        atd.setPays_residence_demandeur(demande.getPaysResidence().getCode());
        atd.setId_point_retrait_demande(Math.toIntExact(demande.getPointRetrait().getId()));
        atd.setId_prefecture_naissance_demandeur(demande.getPrefectureNaissance().getId());
        atd.setId_situation_matrimoniale_demandeur(demande.getSituationMatrimoniale().getId());
        atd.setSexe_demandeur(demande.getSexe().getCode());
        atd = setDateArriveeAutogo(demande, atd);
        atd = setProfession(atd, demande);
        return atd;
    }

    public static DemandeAtd setDateArriveeAutogo(Demande demande, DemandeAtd atd) {
        if (demande.getDate_arivee_togo() == null) {
            atd.setDate_arivee_togo(demande.getDateNaissance());
        } else {
            atd.setDate_arivee_togo(demande.getDate_arivee_togo());
        }
        return atd;
    }

    public static DemandeAtd construireDemande(DemandeAtd atd, Demande demande) {
        try {
            TypePiece typePiece = demande.getTypePiece();
            switch (typePiece.getId()) {
                case 1: {
                    atd.setNumero_acte_naissance(demande.getNumeroActe());
                    atd.setNumero_feuillet_naissance(demande.getNumeroFeuillet());
                    atd.setNumero_registre_naissance(demande.getNumeroRegistre());
                    atd.setEtat_civil_naissance(demande.getEtatCivil());
                    atd.setAnnee_acte_naissance(CasierUtils.getYearOfDate(demande.getDateNaissance()) + "");
                    break;
                }
                case 4: {
                    if (demande.getNumeroPasseport() != null) {
                        atd.setNumero_passeport(demande.getNumeroPasseport());
                        atd.setNumero_passeport_demandeur(demande.getNumeroPasseport());
                    }
                    break;
                }
                case 5: {
                    atd.setNumero_carte_sejour(demande.getNumeroActe());
                    atd.setDate_delivrance_carte_sejour(demande.getDateDelivranceCarte());
                    break;
                }
                case 6: {

                    atd.setDate_naissance_demandeur(demande.getDateNaissance());
                    atd.setNumero_jugement_sup_recons(demande.getNumeroJugement());
                    atd.setDate_jugement_sup_recons(demande.getDateJugement());
                    atd.setDate_transcription_jugement_sup_recons(demande.getDateTranscriptionJugement());
                    atd.setNumero_transcription_jugement_sup_recons(demande.getNumeroTranscriptionJugement());
                    break;
                }
                case 7: {
                    atd.setDate_jugement_rectificatif(demande.getDateJugement());
                    atd.setTribunal_jugement_rectificatif(demande.getTribunalJugement());
                    atd.setNumero_jugement_rectificatif(demande.getNumeroJugement());
                    atd.setDate_transcription_jugement_sup_recons(demande.getDateTranscriptionJugement());
                    atd.setNumero_mention_jugement_rectificatif(demande.getNumeroTranscriptionJugement());
                    atd.setAncien_nom_demandeur(demande.getNomJugement());
                    atd.setAncien_prenom_demandeur(demande.getPrenomJugement());
                    atd.setAncien_nom_pere_demandeur(demande.getNomPereJugement());
                    atd.setAncien_prenom_pere_demandeur(demande.getPrenomPereJugement());
                    atd.setAncien_nom_mere_demandeur(demande.getNomMereJugement());
                    atd.setAncien_prenom_mere_demandeur(demande.getPrenomMereJugement());
                    atd.setAncien_date_naissance_demandeur(demande.getDateNaissanceJugement());
                    atd.setDate_jugement_rectificatif(demande.getDateNaissance());
                    atd.setNumero_acte_rectifie(demande.getNumeroActeRectifie());
                    atd.setEtat_civil_acte_rectifie(demande.getEtatCivilActeRectifie());
                    break;
                }
                case 11: {
                    atd.setNumero_certificat_nationalite(demande.getNumeroCarte());
                    atd.setDate_delivrance_nationalite(demande.getDateDelivranceCarte());
                    break;
                }
                case 12:
                case 13:
                case 14: {
                    atd.setDate_naissance_demandeur(CasierUtils.parseDate("dd/MM/yyyy", "01/01/2024"));
                    atd.setDenomination(demande.getDenomination());
                    atd.setNumero_piece_personne_morale(demande.getNumero_piece_personne_morale());
                    break;
                }
                default: {
                    return atd;
                }
            }
            return atd;
        } catch (Exception e) {
            return null;
        }

    }

    public static DemandeAtd setProfession(DemandeAtd demandeAtd, Demande demande) {
        demandeAtd.setProfession_demandeur(demande.getProfession().getId() + "");
        demandeAtd.setAutre_profession_demandeur(demande.getAutreProfession());
        demandeAtd.setAutre_profession_demandeur(demande.getAutreProfession());
        return demandeAtd;
    }
}
