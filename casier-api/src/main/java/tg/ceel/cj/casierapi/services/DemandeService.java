package tg.ceel.cj.casierapi.services;

import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tg.ceel.cj.casierapi.dto.DemandeDto;
import tg.ceel.cj.casierapi.dto.DemandeList;
import tg.ceel.cj.casierapi.dto.DemandePMDto;
import tg.ceel.cj.casierapi.fnc.models.Casier;
import tg.ceel.cj.casierapi.models.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


public interface DemandeService {
    ResponseEntity<?> validerUneDemande(DemandeDto dto);
    DemandeDto enregistrerDemande(DemandeDto demande, MultipartFile multipartFile);

    ResponseObject update(DemandeSite dto);
    ResponseObject raccourciSearch(RaccourciRequest raccourciRequest) throws Exception;

    DemandeDto enregistrerDemandeSansPointreatrait(DemandeDto demande, MultipartFile multipartFile);
    ResponseEntity<?> validerUneDemande(DemandePMDto dto);
    DemandeDto enregistrerDemande(DemandePMDto demande, MultipartFile multipartFile);
    DemandeDto enregistrerDemande(DemandeDto demande);
    DemandeDto enregistrerDemande(DemandePMDto demande);
    DemandeDto payerDemande(DemandeDto demandeRequest);
    List<DemandeDto> getListeByTypeByPeriode();
    DemandeDto recupererDemande(String numeroDemande, String numeroPiece);
    DemandeDto findByNumero(String numero);
    DemandeDto findByNumeroAndTraiteIsTrue(String numero);
    DemandeDto findById(Long id);
    DemandeDto modifierDemande(DemandeDto dto, MultipartFile fichier);

    DemandeDto modifierFichierDemande(DemandeDto dto, MultipartFile fichier);

    List<DemandeDto> findByTypeAndValideeAndDisponible(String type, Boolean validee, Boolean imprimee);
    List<DemandeDto> findByType(String type);
    List<DemandeDto> findByTypeAndDisponible(String type, Boolean imprimee);
    List<DemandeList> findByTypeAndDisponibleAndInvalideeAndPeriode(String type, Boolean imprimee, Boolean invalider, Date debut, Date fin);
    List<DemandeList> findByTypeAndDisponibleAndInvalideeAndPeriodePoste(String type, Boolean imprimee, Boolean invalider, Date debut, Date fin);
    DemandeDto invalider(DemandeDto dto);
    DemandeDto invalider(Long id);
    DemandeDto valider(Long id);
    byte[] imprimer(Long id);
    DemandeDto imprimer1(Long id);

    List<DemandeList> findByTypeAndSigneeAndPeriodeV1(String type, Boolean signee, Date debut, Date fin);
    List<DemandeList> findByTypeAndSigneeAndPeriodeV1Poste(String type, Boolean signee, Date debut, Date fin);

    List<DemandeDto> findByTypeAndSigneeAndPeriode(String type, Boolean signee, Date debut, Date fin);

    ResumePaiement resumePaiement(Date debut, Date fin, Long centreId);

    List<DetailExtraitDemande> detailExtraitDemande(Date debut, Date fin);

    List<DemandeDto> findByTypeAndSigneeAndPeriodeForAdmin(String type, Boolean signee, Date debut, Date fin, Long IdPointRetrait);

    List<DemandeList> findAllByTypeAndSigneeAndPeriodeV1(String type, Date debut, Date fin, Long pointRetaitId);

    List<DemandeDto> rechercher(String nom, String prenom);
    List<DemandeDto> rechercher(String nom, String prenom, Date debut, Date fin);
    DemandeDto rechercheParNumeroDemande(DemandeDto dto);

    Casier findBCondanations(Long id);
    Casier getCondamnations(Casier casier);

    DemandeDto traiter(DemandeDto dto);
    DemandeDto signer(DemandeDto dto);

    DemandeDto retirer(DemandeDto dto);

    List<DashboardModel> getDashbord(Date debut, Date fin);
    List<Integer> getAnnee();



    List<ExtraitB3> getStatistiqueB3Montant(Date debut, Date fin);

    DemandeDto update(Long id, DemandeDto demandeDto);

    List<DemandeStatistiqueByCentreModel> getDemandeStatistiqueByCentreFilterByPeriode(Date debut, Date fin);
    InputStream exporterDemandeStatistique (Date debut, Date fin, String format) throws JRException, IOException;
    InputStream exporterDemandeParCentre (Date debut,Date fin,String format) throws JRException, IOException;
    InputStream exporterExtraitB3 (Date debut,Date fin,String format) throws JRException, IOException;
    InputStream recuDemende(String numeroDemande) throws JRException, IOException;

    DemandeDto annulerValidation(Long id);

    DemandeDto annulerTraitement(Long id);
    DemandeDto annulerSignature(Long id);

    ResponseObject recupererInfoB3(String numeroDemande, String numeroPiece) throws IOException;

    ResponseObject recupererDemandeB3(String numeroDemande, String numeroPiece);

    List<DemandeList>  findByTypeAndPeriodeForAdmin(String type, Date debut, Date fin);

    DemandeDto moveToNewPointTraitement(DemandeDto dto);
    List<DemandeList> getDemandeEncoursFilterByPeriodeAndTypeDemandeAndPointRetrait(Date debut, Date fin,String typeDemande,Long pointRetrait);
    List<DashboardModel> getCountDemandeByPeriodeAndDelay(Date debut, Date fin,Integer delay);
    List<DashboardModel> getCountDemandeByPeriodeAndDelayAndPointRetrait(Date debut, Date fin,Integer delay,Integer pointRetraitId);

    ResponseObject raccourciSave(FormulaireModification formulaireModification);
    ResponseEntity<?> updateProfession(UpdateObject object);

    ResponseEntity<?> updateSituationMatrimoniale(UpdateObject object);

    ResponseEntity<?> updateNationalite(UpdateObject object);

    ResponseEntity<?> updatePrefectureNaissance(UpdateObject object);

    ResponseEntity<?> updatePaysNaissnace(UpdateObject object);

    ResponseEntity<?> updatePaysResidence(UpdateObject object);


}
