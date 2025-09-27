package tg.ceel.cj.casierapi.servicesimpl;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.entities.CompteurDemande;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.TypeDemande;
import tg.ceel.cj.casierapi.repositories.CompteurDemandeRepository;
import tg.ceel.cj.casierapi.repositories.TypeDemandeRepository;
import tg.ceel.cj.casierapi.services.CompteurService;
import tg.ceel.cj.casierapi.utils.CasierConstants;
import tg.ceel.cj.casierapi.utils.CasierUtils;

import javax.persistence.LockModeType;

@Service
public class CompteurServiceImpl implements CompteurService {

    private final TypeDemandeRepository typeDemandeRepository;
    private final CompteurDemandeRepository compteurDemandeRepository;

    public CompteurServiceImpl(TypeDemandeRepository typeDemandeRepository,
                               CompteurDemandeRepository compteurDemandeRepository) {
        this.typeDemandeRepository = typeDemandeRepository;
        this.compteurDemandeRepository = compteurDemandeRepository;
    }


    @Override
    @Lock(LockModeType.WRITE)
    public String nextFormated(Demande demande) {
        if (demande.getAnneeDemande() == null) {
            demande.setAnneeDemande(CasierUtils.getAnneeDemande(demande));
        }
        Long generatedNumber = next(demande);
        return String.format("%d/%s/%d", generatedNumber, demande.getTypeDemande(), CasierUtils.getAnneeDemande(demande));
    }

    @Override
    @Lock(LockModeType.WRITE)
    public Long next(Demande demande) {
        Integer annee = CasierUtils.getAnneeDemande(demande);
        String type;
        switch (demande.getTypeDemande()) {
            case "B3": {
                type = CasierConstants.TD_DEMANDE_B3;
                break;
            }
            case "M3": {
                type = CasierConstants.TD_DEMANDE_M3;
                break;
            }
            case "B2": {
                type = CasierConstants.TD_DEMANDE_B2;
                break;
            }
            case "M2": {
                type = CasierConstants.TD_DEMANDE_M2;
                break;
            }

            case "B1": {
                type = CasierConstants.TD_DEMANDE_B1;
                break;
            }
            case "M1": {
                type = CasierConstants.TD_DEMANDE_M1;
                break;
            }
            case "ANC":
            case "CJE": {
                type = CasierConstants.TD_DEMANDE_CJE;
                break;
            }
            default:
                return null;
        }

        TypeDemande td = typeDemandeRepository.findByLibelle(type).orElse(null);
        System.err.println(td);
        if (td == null) {
            return null;

        }
        if (demande.getAnneeDemande() == null) {
            demande.setAnneeDemande(CasierUtils.getAnneeDemande(demande));
        }
        CompteurDemande compteurDemande = compteurDemandeRepository.findByAnneeAndTypeDemande(demande.getAnneeDemande(), td).orElse(null);
        if (compteurDemande == null) {
            CompteurDemande cd = new CompteurDemande(Long.valueOf("1"), annee, td);
            compteurDemandeRepository.save(cd);
            return cd.getNumero();
        }
        compteurDemande.setNumero(compteurDemande.getNumero() + 1);
        compteurDemandeRepository.save(compteurDemande);
        return compteurDemande.getNumero();

    }
}
