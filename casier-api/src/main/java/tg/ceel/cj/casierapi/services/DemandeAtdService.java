package tg.ceel.cj.casierapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tg.ceel.cj.casierapi.dto.Traitement;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.Paiement;
import tg.ceel.cj.casierapi.entities.Payement;
import tg.ceel.cj.casierapi.models.DemandeAtd;
import tg.ceel.cj.casierapi.models.DemandeModele;
import tg.ceel.cj.casierapi.models.RaccourciRequest;
import tg.ceel.cj.casierapi.models.ResponseObject;

public interface DemandeAtdService {
    ResponseEntity<?> saveDemandeNew(DemandeModele modele, MultipartFile multipartFile);

    DemandeAtd saveDemande(DemandeAtd demandeAtd, MultipartFile multipartFile);

    ResponseEntity<?> saveDemande(DemandeModele demandeAtd, MultipartFile multipartFile);

    ResponseEntity<?> saveDemande(DemandeModele demandeAtd);

    ResponseEntity<?> saveDemandePM(DemandeModele demandeAtd, MultipartFile multipartFile);

    DemandeAtd updateDemande(DemandeAtd demandeAtd, MultipartFile multipartFile);

    DemandeAtd updateDemande(DemandeAtd demandeAtd);

    DemandeAtd updateDemandeB1etB2(DemandeAtd demandeAtd);

    DemandeAtd demandeToDemandeAtd(Demande demande);

    ResponseEntity<?> validerUneDemande(DemandeAtd dto);

    ResponseEntity<?> validerUneDemandeB1etB2(DemandeAtd dto);

    DemandeAtd saveDemande(DemandeAtd demande);

    DemandeAtd rechercheParNumeroDemande(DemandeAtd dto);

    DemandeAtd rechercheParNumeroDemande(String dto);

    Payement atdPaiementToPayement(Paiement paiement);

    Paiement payementToAtdPaiement(Payement payement);

    Boolean checkMontantPaiement(DemandeModele modele);

    ResponseEntity<?> validerUneDemandePersonneMorale(DemandeAtd dto);

    ResponseEntity<?> validerUneDemande(DemandeModele dto);

    Traitement updateOldDemande();

    ResponseEntity<?> raccourciSearch(RaccourciRequest raccourciRequest);

    ResponseEntity<?> raccourciSave(DemandeModele demandeAtd);
}
