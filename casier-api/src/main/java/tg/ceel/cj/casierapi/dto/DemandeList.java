package tg.ceel.cj.casierapi.dto;

import java.util.Date;

public interface DemandeList {
    String getTypeDemande();

    Long getId();

    Date getDateDemande();

    String getNom();

    String getPrenom();

    Date getDateNaissance();

    String getTelephone();

    String getEmail();

    Boolean getValider();

    Boolean getEtape1Valider();

    Boolean getEtape2Valider();

    Boolean getEtape3Valider();

    Boolean getDisponible();

    Date getDateValidation();

    Boolean getRetirer();

    String getCouleur();

    Boolean getTracked();

    Boolean getTrackingNotificationSuccess();

    Boolean getTrackingDeliverySuccess();

    String getDeliveryMode();

    String getNumeroDemande();

    String getUsername();

    Boolean getInvalidee();

    Boolean getSignee();

    Boolean getTraitee();

    String getDenomination();

    String getNumeroRccm();

    String getNif();

    String getName();

    Integer getNombreCopie();

    String getMotifInvalidation();

    String getRecord();

    String getRef_existence_legale();

    String getSiege();

    String getNumero_piece_personne_morale();

    String getMobile_demandeur_personne_morale();

    String getType_personne_morale_code();

    String getNom_complet_dirigeant();

    String getTelephone_dirigeant();

    String getLoaclite_residence_dirigeant();

    String geTitre_dirigeant();

    String getAdresse_dirigeant();

    String getPays_siege();

    String getBureauPosteRetraitLibelle();

    String getEmploi();
     String getTribunal_destination();
     String getPersonne_destinataire();
}
