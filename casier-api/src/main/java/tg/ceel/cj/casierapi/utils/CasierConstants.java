/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the modifieror.
 */
package tg.ceel.cj.casierapi.utils;


public class CasierConstants {
    
//    public static final String CASIER_CLIENT_USERNAME = "casierclient";
//    public static final String CASIER_CLIENT_PASSWORD = "casierclient@mjrir";

    public static final String CASIER_WS_USERNAME = "ws-user";
    public static final String CASIER_WS_PASSWORD = "ws-user@casiermjrir";
    
    /* Types demande*/
    public static final String TD_DEMANDE_B3 = "Demande B3";
    public static final String TD_DEMANDE_B2 = "Demande B2";
    public static final String TD_DEMANDE_CJE = "Demande CJE";
    public static final String TD_DEMANDE_B1 = "Demande B1";
    public static final String TD_DEMANDE_M3 = "Demande M3";
    public static final String TD_DEMANDE_M2 = "Demande M2";
    public static final String TD_DEMANDE_MCJE = "Demande MCJE";
    public static final String TD_DEMANDE_M1 = "Demande M1";
    /*Log*/
    public static final String VAR_LOG_ADMINISTRATION = "administration";
    public static final String VAR_LOG_DEMANDE_B1 = "demande-b1";
    public static final String VAR_LOG_DEMANDE_B2 = "demande-b2";
    public static final String VAR_LOG_DEMANDE_B3= "demande-b3";
    public static final String VAR_LOG_DEMANDE_CJE= "demande-cje";
    public static final String VAR_LOG_DONNEES_REFERENCES= "reference";
    public static final String VAR_LOG_PAYEMENT= "paiement";
    public static final String VAR_LOG_RETRAIT= "retrait";
    
    // variables 
    public static final String VAR_SMS_MODEM_USERNAME = "casier.sms.modem.username";
    public static final String VAR_SMS_MODEM_PASSWORD = "casier.sms.modem.password";
    public static final String VAR_SMS_MODEM_SENDER = "casier.sms.modem.sender";
    public static final String VAR_SMS_MODEM_ROUTE_FORTE = "casier.sms.modem.route_forte";
    public static final String VAR_PIECE_JOINTE_DOSSIER = "casier.piece_jointe.dossier";
    public static final String VAR_PIECE_JOINTE_DOSSIER_RETRAIT = "casier.piece_jointe.dossier";
    public static final String VAR_DEMANDE_PRIX = "casier.demande.prix_unitaire";
    public static final String VAR_FLOOZ_MERCHANT_NAME = "casier.flooz.merchant_name";
    public static final String VAR_FLOOZ_MERCHANT_ID = "casier.flooz.merchant_id";
    public static final String VAR_FLOOZ_AUTHORIZATION = "casier.flooz.authorization";
    
    //web  service permissions
    
    public static final String PERM_WS_UPLOAD = "casier:upload";
    public static final String PERM_WS_DEMANDE_ENREGISTRER = "demande:enregistrer";
    public static final String PERM_WS_DEMANDE_MODIFIER = "demande:modifier";
    public static final String PERM_WS_DEMANDE_SUPPRIMER = "demande:supprimer";
    public static final String PERM_WS_DEMANDE_INFO = "demande:info";
    public static final String PERM_WS_REFERENCE = "demande:reference";
//    public static final String PERM_WS_REFERENCE_1 = "demande:reference1";
    
    // Permissions
    public static final String PERM_ADMINISTRATION_TOUT = "administration:*";
    public static final String PERM_ADMINISTRATION_UTILISATEUR_CASIER_TOUT = "administration:utilisateur-casier:*";
    public static final String PERM_ADMINISTRATION_UTILISATEUR_CASIER_AJOUTER = "administration:utilisateur-casier:ajouter";
    public static final String PERM_ADMINISTRATION_UTILISATEUR_CASIER_MODIFIER = "administration:utilisateur-casier:modifier";
    public static final String PERM_ADMINISTRATION_UTILISATEUR_CASIER_DETAILLER = "administration:utilisateur-casier:detailler";
    public static final String PERM_ADMINISTRATION_UTILISATEUR_CASIER_LISTER = "administration:utilisateur-casier:lister";
    public static final String PERM_ADMINISTRATION_UTILISATEUR_CASIER_SUPPRIMER = "administration:utilisateur-casier:supprimer";

    public static final String PERM_REFERENCE_TOUT = "reference:*";
    
    public static final String PERM_REFERENCE_LOCALITE_TOUT = "reference:localite:*";
    public static final String PERM_REFERENCE_LOCALITE_AJOUTER = "reference:localite:ajouter";
    public static final String PERM_REFERENCE_LOCALITE_MODIFIER = "reference:localite:modifier";
    public static final String PERM_REFERENCE_LOCALITE_DETAILLER = "reference:localite:detailler";
    public static final String PERM_REFERENCE_LOCALITE_LISTER = "reference:localite:lister";
    public static final String PERM_REFERENCE_LOCALITE_SUPPRIMER = "reference:localite:supprimer";

    public static final String PERM_REFERENCE_PREFECTURE_TOUT = "reference:prefecture:*";
    public static final String PERM_REFERENCE_PREFECTURE_AJOUTER = "reference:prefecture:ajouter";
    public static final String PERM_REFERENCE_PREFECTURE_MODIFIER = "reference:prefecture:modifier";
    public static final String PERM_REFERENCE_PREFECTURE_DETAILLER = "reference:prefecture:detailler";
    public static final String PERM_REFERENCE_PREFECTURE_LISTER = "reference:prefecture:lister";
    public static final String PERM_REFERENCE_PREFECTURE_SUPPRIMER = "reference:prefecture:supprimer";

    public static final String PERM_REFERENCE_REGION_TOUT = "reference:region:*";
    public static final String PERM_REFERENCE_REGION_AJOUTER = "reference:region:ajouter";
    public static final String PERM_REFERENCE_REGION_MODIFIER = "reference:region:modifier";
    public static final String PERM_REFERENCE_REGION_DETAILLER = "reference:region:detailler";
    public static final String PERM_REFERENCE_REGION_LISTER = "reference:region:lister";
    public static final String PERM_REFERENCE_REGION_SUPPRIMER = "reference:region:supprimer";

    public static final String PERM_REFERENCE_PAYS_TOUT = "reference:pays:*";
    public static final String PERM_REFERENCE_PAYS_AJOUTER = "reference:pays:ajouter";
    public static final String PERM_REFERENCE_PAYS_MODIFIER = "reference:pays:modifier";
    public static final String PERM_REFERENCE_PAYS_DETAILLER = "reference:pays:detailler";
    public static final String PERM_REFERENCE_PAYS_LISTER = "reference:pays:lister";
    public static final String PERM_REFERENCE_PAYS_SUPPRIMER = "reference:pays:supprimer";

    public static final String PERM_REFERENCE_JURIDICTION_TOUT = "reference:juridiction:*";
    public static final String PERM_REFERENCE_JURIDICTION_AJOUTER = "reference:juridiction:ajouter";
    public static final String PERM_REFERENCE_JURIDICTION_MODIFIER = "reference:juridiction:modifier";
    public static final String PERM_REFERENCE_JURIDICTION_DETAILLER = "reference:juridiction:detailler";
    public static final String PERM_REFERENCE_JURIDICTION_LISTER = "reference:juridiction:lister";
    public static final String PERM_REFERENCE_JURIDICTION_SUPPRIMER = "reference:juridiction:supprimer";

    public static final String PERM_REFERENCE_POINT_RETRAIT_TOUT = "reference:point-retrait:*";
    public static final String PERM_REFERENCE_POINT_RETRAIT_AJOUTER = "reference:point-retrait:ajouter";
    public static final String PERM_REFERENCE_POINT_RETRAIT_MODIFIER = "reference:point-retrait:modifier";
    public static final String PERM_REFERENCE_POINT_RETRAIT_DETAILLER = "reference:point-retrait:detailler";
    public static final String PERM_REFERENCE_POINT_RETRAIT_LISTER = "reference:point-retrait:lister";
    public static final String PERM_REFERENCE_POINT_RETRAIT_SUPPRIMER = "reference:point-retrait:supprimer";

    public static final String PERM_REFERENCE_CATEGORIE_DEMANDEUR_B2_TOUT = "reference:categorie-demandeur-B2:*";
    public static final String PERM_REFERENCE_CATEGORIE_DEMANDEUR_B2_AJOUTER = "reference:categorie-demandeur-B2:ajouter";
    public static final String PERM_REFERENCE_CATEGORIE_DEMANDEUR_B2_MODIFIER = "reference:categorie-demandeur-B2:modifier";
    public static final String PERM_REFERENCE_CATEGORIE_DEMANDEUR_B2_DETAILLER = "reference:categorie-demandeur-B2:detailler";
    public static final String PERM_REFERENCE_CATEGORIE_DEMANDEUR_B2_LISTER = "reference:categorie-demandeur-B2:lister";
    public static final String PERM_REFERENCE_CATEGORIE_DEMANDEUR_B2_SUPPRIMER = "reference:categorie-demandeur-B2:supprimer";

    public static final String PERM_REFERENCE_ENTITE_DEMANDEUR_B2_TOUT = "reference:entite-demandeur-B2:*";
    public static final String PERM_REFERENCE_ENTITE_DEMANDEUR_B2_AJOUTER = "reference:entite-demandeur-B2:ajouter";
    public static final String PERM_REFERENCE_ENTITE_DEMANDEUR_B2_MODIFIER = "reference:entite-demandeur-B2:modifier";
    public static final String PERM_REFERENCE_ENTITE_DEMANDEUR_B2_DETAILLER = "reference:entite-demandeur-B2:detailler";
    public static final String PERM_REFERENCE_ENTITE_DEMANDEUR_B2_LISTER = "reference:entite-demandeur-B2:lister";
    public static final String PERM_REFERENCE_ENTITE_DEMANDEUR_B2_SUPPRIMER = "reference:entite-demandeur-B2:supprimer";

    public static final String PERM_REFERENCE_SERVICE_DEMANDEUR_B2_TOUT = "reference:service-demandeur-B2:*";
    public static final String PERM_REFERENCE_SERVICE_DEMANDEUR_B2_AJOUTER = "reference:service-demandeur-B2:ajouter";
    public static final String PERM_REFERENCE_SERVICE_DEMANDEUR_B2_MODIFIER = "reference:service-demandeur-B2:modifier";
    public static final String PERM_REFERENCE_SERVICE_DEMANDEUR_B2_DETAILLER = "reference:service-demandeur-B2:detailler";
    public static final String PERM_REFERENCE_SERVICE_DEMANDEUR_B2_LISTER = "reference:service-demandeur-B2:lister";
    public static final String PERM_REFERENCE_SERVICE_DEMANDEUR_B2_SUPPRIMER = "reference:service-demandeur-B2:supprimer";
    
    public static final String PERM_REFERENCE_VARIABLE_TOUT = "reference:variable:*";
    public static final String PERM_REFERENCE_VARIABLE_AJOUTER = "reference:variable:ajouter";
    public static final String PERM_REFERENCE_VARIABLE_MODIFIER = "reference:variable:modifier";
    public static final String PERM_REFERENCE_VARIABLE_DETAILLER = "reference:variable:detailler";
    public static final String PERM_REFERENCE_VARIABLE_LISTER = "reference:variable:lister";
    public static final String PERM_REFERENCE_VARIABLE_SUPPRIMER = "reference:variable:supprimer";

    public static final String PERM_REFERENCE_MODELE_SMS = "reference:modele-sms:*";
    public static final String PERM_REFERENCE_MODELE_SMS_AJOUTER = "reference:modele-sms:ajouter";
    public static final String PERM_REFERENCE_MODELE_SMS_MODIFIER = "reference:modele-sms:modifier";
    public static final String PERM_REFERENCE_MODELE_SMS_DETAILLER = "reference:modele-sms:detailler";
    public static final String PERM_REFERENCE_MODELE_SMS_LISTER = "reference:modele-sms:lister";
    public static final String PERM_REFERENCE_MODELE_SMS_SUPPRIMER = "reference:modele-sms:supprimer";

    public static final String PERM_REFERENCE_FONCTION = "reference:fonction:*";
    public static final String PERM_REFERENCE_FONCTION_AJOUTER = "reference:fonction:ajouter";
    public static final String PERM_REFERENCE_FONCTION_MODIFIER = "reference:fonction:modifier";
    public static final String PERM_REFERENCE_FONCTION_DETAILLER = "reference:fonction:detailler";
    public static final String PERM_REFERENCE_FONCTION_LISTER = "reference:fonction:lister";
    public static final String PERM_REFERENCE_FONCTION_SUPPRIMER = "reference:fonction:supprimer";

    public static final String PERM_REFERENCE_COURS_APPEL = "reference:cours-appel:*";
    public static final String PERM_REFERENCE_COURS_APPEL_AJOUTER = "reference:cours-appel:ajouter";
    public static final String PERM_REFERENCE_COURS_APPEL_MODIFIER = "reference:cours-appel:modifier";
    public static final String PERM_REFERENCE_COURS_APPEL_DETAILLER = "reference:cours-appel:detailler";
    public static final String PERM_REFERENCE_COURS_APPEL_LISTER = "reference:cours-appel:lister";
    public static final String PERM_REFERENCE_COURS_APPEL_SUPPRIMER = "reference:cours-appel:supprimer";
    
    public static final String PERM_REFERENCE_PROFESSION = "reference:profession:*";
    public static final String PERM_REFERENCE_PROFESSION_AJOUTER = "reference:profession:ajouter";
    public static final String PERM_REFERENCE_PROFESSION_MODIFIER = "reference:profession:modifier";
    public static final String PERM_REFERENCE_PROFESSION_DETAILLER = "reference:profession:detailler";
    public static final String PERM_REFERENCE_PROFESSION_LISTER = "reference:profession:lister";
    public static final String PERM_REFERENCE_PROFESSION_SUPPRIMER = "reference:profession:supprimer";

    public static final String PERM_DEMANDE_ANC_TOUT = "demande-anc:*";
    public static final String PERM_DEMANDE_ANC_VALIDATION_TOUT = "demande-anc:validation:*";
    public static final String PERM_DEMANDE_ANC_VALIDATION_LISTER = "demande-anc:validation:lister";
    public static final String PERM_DEMANDE_ANC_VALIDATION_VALIDER = "demande-anc:validation:valider";
    public static final String PERM_DEMANDE_ANC_VALIDATION_CONSULTER = "demande-anc:validation:consulter";
    public static final String PERM_DEMANDE_ANC_VALIDATION_TRAITER = "demande-anc:validation:traiter";
    public static final String PERM_DEMANDE_ANC_VALIDATION_TERMINER = "demande-anc:validation:terminer";
    public static final String PERM_DEMANDE_ANC_VALIDATION_SIGNER = "demande-anc:validation:signer";
    public static final String PERM_DEMANDE_ANC_TRAITEES_TOUT = "demande-anc:traitees:*";
    public static final String PERM_DEMANDE_ANC_TRAITEES_LISTER = "demande-anc:traitees:lister";
    public static final String PERM_DEMANDE_ANC_TRAITEES_CONSULTER = "demande-anc:traitees:consulter";
    public static final String PERM_DEMANDE_ANC_TRAITEES_VALIDER_RETRAIT = "demande-anc:traitees:valider-retrait";
    public static final String PERM_DEMANDE_ANC_REJETEES_TOUT = "demande-anc:rejetees:*";
    public static final String PERM_DEMANDE_ANC_REJETEES_LISTER = "demande-anc:rejetees:lister";
    public static final String PERM_DEMANDE_ANC_REJETEES_CONSULTER = "demande-anc:rejetees:consulter";
    
    public static final String PERM_DEMANDE_B1_TOUT = "demande-b1:*";
    public static final String PERM_DEMANDE_B1_VALIDATION_TOUT = "demande-b1:validation:*";
    public static final String PERM_DEMANDE_B1_VALIDATION_LISTER = "demande-b1:validation:lister";
    public static final String PERM_DEMANDE_B1_VALIDATION_VALIDER = "demande-b1:validation:valider";
    public static final String PERM_DEMANDE_B1_VALIDATION_CONSULTER = "demande-b1:validation:consulter";
    public static final String PERM_DEMANDE_B1_VALIDATION_TRAITER = "demande-b1:validation:traiter";
    public static final String PERM_DEMANDE_B1_VALIDATION_TERMINER = "demande-b1:validation:terminer";
    public static final String PERM_DEMANDE_B1_VALIDATION_SIGNER = "demande-b1:validation:signer";
    public static final String PERM_DEMANDE_B1_TRAITEES_TOUT = "demande-b1:traitees:*";
    public static final String PERM_DEMANDE_B1_TRAITEES_LISTER = "demande-b1:traitees:lister";
    public static final String PERM_DEMANDE_B1_TRAITEES_CONSULTER = "demande-b1:traitees:consulter";
    public static final String PERM_DEMANDE_B1_TRAITEES_VALIDER_RETRAIT = "demande-b1:traitees:valider-retrait";
    public static final String PERM_DEMANDE_B1_REJETEES_TOUT = "demande-b1:rejetees:*";
    public static final String PERM_DEMANDE_B1_REJETEES_LISTER = "demande-b1:rejetees:lister";
    public static final String PERM_DEMANDE_B1_REJETEES_CONSULTER = "demande-b1:rejetees:consulter";

    public static final String PERM_DEMANDE_B2_TOUT = "demande-b2:*";
    public static final String PERM_DEMANDE_B2_VALIDATION_TOUT = "demande-b2:validation:*";
    public static final String PERM_DEMANDE_B2_VALIDATION_LISTER = "demande-b2:validation:lister";
    public static final String PERM_DEMANDE_B2_VALIDATION_VALIDER = "demande-b2:validation:valider";
    public static final String PERM_DEMANDE_B2_VALIDATION_CONSULTER = "demande-b2:validation:consulter";
    public static final String PERM_DEMANDE_B2_VALIDATION_TRAITER = "demande-b2:validation:traiter";
    public static final String PERM_DEMANDE_B2_VALIDATION_TERMINER = "demande-b2:validation:terminer";
    public static final String PERM_DEMANDE_B2_VALIDATION_SIGNER = "demande-b2:validation:signer";
    public static final String PERM_DEMANDE_B2_TRAITEES_TOUT = "demande-b2:traitees:*";
    public static final String PERM_DEMANDE_B2_TRAITEES_LISTER = "demande-b2:traitees:lister";
    public static final String PERM_DEMANDE_B2_TRAITEES_CONSULTER = "demande-b2:traitees:consulter";
    public static final String PERM_DEMANDE_B2_TRAITEES_VALIDER_RETRAIT = "demande-b2:traitees:valider-retrait";
    public static final String PERM_DEMANDE_B2_REJETEES_TOUT = "demande-b2:rejetees:*";
    public static final String PERM_DEMANDE_B2_REJETEES_LISTER = "demande-b2:rejetees:lister";
    public static final String PERM_DEMANDE_B2_REJETEES_CONSULTER = "demande-b2:rejetees:consulter";

    public static final String PERM_DEMANDE_B3_TOUT = "demande-b3:*";
    public static final String PERM_DEMANDE_B3_VALIDATION_TOUT = "demande-b3:validation:*";
    public static final String PERM_DEMANDE_B3_VALIDATION_LISTER = "demande-b3:validation:lister";
    public static final String PERM_DEMANDE_B3_VALIDATION_VALIDER = "demande-b3:validation:valider";
    public static final String PERM_DEMANDE_B3_VALIDATION_CONSULTER = "demande-b3:validation:consulter";
    public static final String PERM_DEMANDE_B3_VALIDATION_TRAITER = "demande-b3:validation:traiter";
    public static final String PERM_DEMANDE_B3_VALIDATION_TERMINER = "demande-b3:validation:terminer";
    public static final String PERM_DEMANDE_B3_VALIDATION_SIGNER = "demande-b3:validation:signer";
    public static final String PERM_DEMANDE_B3_TRAITEES_TOUT = "demande-b3:traitees:*";
    public static final String PERM_DEMANDE_B3_TRAITEES_LISTER = "demande-b3:traitees:lister";
    public static final String PERM_DEMANDE_B3_TRAITEES_CONSULTER = "demande-b3:traitees:consulter";
    public static final String PERM_DEMANDE_B3_TRAITEES_VALIDER_RETRAIT = "demande-b3:traitees:valider-retrait";
    public static final String PERM_DEMANDE_B3_REJETEES_TOUT = "demande-b3:rejetees:*";
    public static final String PERM_DEMANDE_B3_REJETEES_LISTER = "demande-b3:rejetees:lister";
    public static final String PERM_DEMANDE_B3_REJETEES_CONSULTER = "demande-b3:rejetees:consulter";
    
    public static final String PERM_PAIEMENT_PAIEMENT_PAYER = "paiement:paiement:payer";
    
    public static final String PERM_RETRAIT = "retrait:*";
    public static final String PERM_RETRAIT_RETRAIT_LISTER = "retrait:retrait:lister";
    public static final String PERM_RETRAIT_RETRAIT_AJOUTER = "retrait:retrait:ajouter";
    public static final String PERM_RETRAIT_RETRAIT_MODIFIER = "retrait:retrait:modifier";
    public static final String PERM_RETRAIT_RETRAIT_DETAILLER = "retrait:retrait:detailler";
    
//    public static final String PERM_CASIER_LOGIN = "casier:login";

}
