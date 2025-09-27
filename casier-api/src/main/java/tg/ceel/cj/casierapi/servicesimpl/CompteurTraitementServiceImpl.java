package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.entities.CompteurTraitement;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.entities.TypeDemande;
import tg.ceel.cj.casierapi.repositories.CompteurTraitementRepository;
import tg.ceel.cj.casierapi.repositories.PointRetraitRepository;
import tg.ceel.cj.casierapi.repositories.TypeDemandeRepository;
import tg.ceel.cj.casierapi.services.CompteurTraitementService;
import tg.ceel.cj.casierapi.utils.CasierUtils;

@Service
public class CompteurTraitementServiceImpl implements CompteurTraitementService {
    Logger logger = LoggerFactory.getLogger(CompteurTraitementServiceImpl.class);
    private final CompteurTraitementRepository compteurTraitementRepository;
    private final TypeDemandeRepository typeDemandeRepository;
    private final PointRetraitRepository pointRetraitRepository;

    public CompteurTraitementServiceImpl(CompteurTraitementRepository compteurTraitementRepository, TypeDemandeRepository typeDemandeRepository, PointRetraitRepository pointRetraitRepository) {
        this.compteurTraitementRepository = compteurTraitementRepository;
        this.typeDemandeRepository = typeDemandeRepository;
        this.pointRetraitRepository = pointRetraitRepository;
    }

    @Override
    public Long next(Demande demande) {
        try {
            Integer annee = CasierUtils.getAnneeDemande(demande);
            TypeDemande td = typeDemandeRepository.findByCode(demande.getTypeDemande());
            if (td == null) {
                throw new Exception(String.format("Aucun type de bulletin trouvé avec le code %s", demande.getTypeDemande()));
            }
            PointRetrait pr = pointRetraitRepository.findById(demande.getPointRetrait().getId())
                    .orElseThrow(() -> new Exception(String.format("Aucun point de retrait trouvé avec l'id %s", demande.getPointRetrait().getId())));
            CompteurTraitement cpt = compteurTraitementRepository.getLastValue(annee, td, pr);
            if (cpt == null) {
                cpt = new CompteurTraitement(pr, td, 1L, annee);
                cpt.setVersion(1);
                compteurTraitementRepository.save(cpt);
            } else {
                cpt.setNumero(cpt.getNumero() + 1);
                cpt.setVersion(cpt.getVersion()+1);
                compteurTraitementRepository.save(cpt);
            }
            return cpt.getNumero();
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
