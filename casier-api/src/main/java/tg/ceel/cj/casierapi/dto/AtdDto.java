package tg.ceel.cj.casierapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AtdDto {

    private Long id_demande;
    private String type_demande;
    private Integer id_type_piece_demande;
    private Long id_point_retrait_demande;
    private Integer nombre_copie_demande;
    private String nom_demandeur;
    private String prenom_demandeur;
    private Date date_naissance_demandeur;
    private String lieu_naissance_demandeur;
    private Integer id_prefecture_naissance_demandeur;
    private String  sexe_demandeur;
    private Integer  nombre_enfant_demandeur;
    private String telephone_demandeur;
    private String indicatif_demandeur;
    private String email_demandeur;
    private Integer profession_demandeur;
    private String lieu_residence_demandeur;
    private Integer  id_situation_matrimoniale_demandeur;
    private  String nom_pere_demandeur;
    private  String prenom_pere_demandeur;
    private  String nom_mere_demandeur;
    private  String prenom_mere_demandeur;
    private  String numero_acte_naissance;
    private  String numero_feuillet_naissance;
    private  String numero_registre_naissance;
    private  String annee_acte_naissance;
    private  String etat_civil_naissance;

    private  String numero_certificat_nationalite;
    private  Date date_delivrance_nationalite;

    private  String numero_jugement_rectificatif;
    private  String date_jugement_rectificatif;
    private  String tribunal_jugement_rectificatif;
    private  Date date_mention_jugement_rectificatif;
    private  String numero_mention_jugement_rectificatif;
    private  String numero_acte_rectifie;
    private  String etat_civil_acte_rectifie;
    private  Date date_etablissement_acte_rectifie;
    private  String ancien_nom_demandeur;
    private  String ancien_prenom_demandeur;
    private  Date ancien_date_naissance_demandeur;
    private  String ancien_nom_pere_demandeur;

    private  String numero_jugement_sup_recons;
    private  Date date_jugement_sup_recons;
    private  String tribunal_jugement_sup_recons;
    private  Date date_transcription_jugement_sup_recons;
    private  String numero_transcription_jugement_sup_recons;
    private  String pays_residence_demandeur;







}
