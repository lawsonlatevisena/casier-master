package tg.ceel.fnc.fnc.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tg.ceel.fnc.fnc.dto.PeineInfractionDto;
import tg.ceel.fnc.fnc.entities.*;
import tg.ceel.fnc.fnc.model.Casier;
import tg.ceel.fnc.fnc.model.Demande;
import tg.ceel.fnc.fnc.repositories.CondamnationRepository;
import tg.ceel.fnc.fnc.repositories.PeineInfractionRepository;
import tg.ceel.fnc.fnc.service.CondamnationService;
import tg.ceel.fnc.fnc.service.EntityMapperService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CondamnationServiceImpl implements CondamnationService {
    Logger logger = LoggerFactory.getLogger(CondamnationServiceImpl.class);
    private final EntityMapperService entityMapperService;
    private final CondamnationRepository condamnationRepository;
    private final PeineInfractionRepository peineInfractionRepository;

    public CondamnationServiceImpl(EntityMapperService entityMapperService, CondamnationRepository condamnationRepository, PeineInfractionRepository peineInfractionRepository) {
        this.entityMapperService = entityMapperService;
        this.condamnationRepository = condamnationRepository;
        this.peineInfractionRepository = peineInfractionRepository;
    }

    @Override
    public ResponseEntity<?> findCondamnation(Demande demande) {
        System.err.println(demande);
        try {
            if (demande == null) {
                return null;
            }
            System.err.println("================================================="+demande);
            Casier casier = new Casier();
            switch (demande.getTypeBulletin()) {
                case "ANC":
                case "B3": {
                    System.err.println(demande.getPrenom().toLowerCase().replaceAll(" ", ""));
                    casier = this.getCondamnationsB3(demande);
                    break;
                }
                case "B2": {
                    casier = this.getCondamnationsB2(demande);
                    break;
                }
                case "B1": {
                    casier = this.getCondamnationsB1(demande);
                    break;
                } case "M3": {
                    casier = this.getCondamnationsM3(demande);
                    break;
                }
                default: {
                    return null;
                }
            }
            System.err.println(casier);
            return new ResponseEntity<>(casier, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private List<Peine> getPeineFromCondamnation(List<Condamnation> condamnations) {
        try {
            if (condamnations == null) {
                return null;
            }
            return condamnations.stream().map(condamnation -> condamnation.getPeine()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private List<PeineInfractionDto> getPeineInfraction(List<Condamnation> condamnations) {
        try {
            if (condamnations == null) {
                return null;
            }
            List<Peine> peines = getPeineFromCondamnation(condamnations);
            if (peines == null) {
                return null;
            }
            System.err.println("peine: "+peines);
            List<PeineInfraction> peineInfractions = peineInfractionRepository.findByPeineIn(peines);
            if (peineInfractions == null) {
                return null;
            }
            System.err.println(peineInfractions);
            return peineInfractions.stream().map(entityMapperService::peinInfractionToPeinInfractionDto).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private List<Infraction> getInfractions(List<Peine> peines) {
        try {
            if (peines == null) {
                return null;
            }
            List<PeineInfraction> peineInfractions = peineInfractionRepository.findByPeineIn(peines);
            if (peineInfractions == null) {
                return null;
            }
            return peineInfractions.stream().map(i -> i.getInfraction()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private Casier getCondamnationsB3(Demande demande) {
        try {
            List<Condamnation> condamnations = new ArrayList<>();
            Casier casier = new Casier();
            condamnations = condamnationRepository.findCondamnationsb3E1("casier",
                    demande.getNom().toLowerCase().replaceAll(" ", ""),
                    demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                    demande.getSexe().toLowerCase().replaceAll(" ", ""));

            if (condamnations != null && !condamnations.isEmpty()) {
                casier.setCouleur("Jaune");
                condamnations = condamnationRepository.findCondamnationsb3E2("casier",
                        demande.getNom().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                        demande.getSexe().toLowerCase().replaceAll(" ", ""),
                        demande.getDateNaissance());
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Orange");
                }
                condamnations = condamnationRepository.findCondamnationsb3E3("casier",
                        demande.getNom().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                        demande.getSexe().toLowerCase().replaceAll(" ", ""),
                        demande.getNomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getNomMere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomMere().toLowerCase().replaceAll(" ", ""));
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Orange");
                }
                condamnations = condamnationRepository.findCondamnationsb3("casier",
                        demande.getNom().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                        demande.getSexe().toLowerCase().replaceAll(" ", ""),
                        demande.getDateNaissance(),
                        demande.getNomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getNomMere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomMere().toLowerCase().replaceAll(" ", ""));
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Rouge");
                }
            } else {
                casier.setCouleur("Vert");
            }
            return getCasier(condamnations, casier);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private Casier getCondamnationsM3(Demande demande) {
        try {
            List<Condamnation> condamnations = new ArrayList<>();
            Casier casier = new Casier();
            condamnations = condamnationRepository.findCondamnationsm3E1("casier",
                    demande.getDenomination().toLowerCase().replaceAll(" ", ""));

            if (condamnations != null && !condamnations.isEmpty()) {
                casier.setCouleur("Jaune");
                condamnations = condamnationRepository.findCondamnationsm3E2("casier",
                        demande.getNumeroIdentification().toLowerCase().replaceAll(" ", ""),
                        demande.getNif().toLowerCase().replaceAll(" ", ""));
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Orange");
                }
                condamnations = condamnationRepository.findCondamnationsm3E3("casier",
                        demande.getDenomination().toLowerCase().replaceAll(" ", ""),
                        demande.getNumeroIdentification().toLowerCase().replaceAll(" ", ""),
                        demande.getNif().toLowerCase().replaceAll(" ", ""));
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Rouge");
                }
            } else {
                casier.setCouleur("Vert");
            }
            return getCasier(condamnations, casier);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private Casier getCondamnationsB2(Demande demande) {
        try {
            List<Condamnation> condamnations = new ArrayList<>();
            Casier casier = new Casier();
            condamnations = condamnationRepository.findCondamnationsB2E1("casier",
                    demande.getNom().toLowerCase().replaceAll(" ", ""),
                    demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                    demande.getSexe().toLowerCase().replaceAll(" ", ""));
            if (condamnations != null && !condamnations.isEmpty()) {
                casier.setCouleur("Jaune");
                condamnations = condamnationRepository.findCondamnationsB2E2("casier",
                        demande.getNom().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                        demande.getSexe().toLowerCase().replaceAll(" ", ""),
                        demande.getDateNaissance());
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Orange");
                }
                condamnations = condamnationRepository.findCondamnationsB2E3("casier",
                        demande.getNom().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                        demande.getSexe().toLowerCase().replaceAll(" ", ""),
                        demande.getNomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getNomMere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomMere().toLowerCase().replaceAll(" ", ""));
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Orange");
                }
                condamnations = condamnationRepository.findCondamnationsB2("casier",
                        demande.getNom().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                        demande.getSexe().toLowerCase().replaceAll(" ", ""),
                        demande.getDateNaissance(),
                        demande.getNomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getNomMere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomMere().toLowerCase().replaceAll(" ", ""));
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Rouge");
                }
            } else {
                casier.setCouleur("Vert");
            }
            return getCasier(condamnations, casier);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private Casier getCondamnationsB1(Demande demande) {
        try {
            List<Condamnation> condamnations = new ArrayList<>();
            Casier casier = new Casier();
            condamnations = condamnationRepository.findCondamnationsB1E1(
                    demande.getNom().toLowerCase().replaceAll(" ", ""),
                    demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                    demande.getSexe().toLowerCase().replaceAll(" ", ""));
            if (condamnations != null && !condamnations.isEmpty()) {
                casier.setCouleur("Jaune");
                condamnations = condamnationRepository.findCondamnationsB1E2(
                        demande.getNom().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                        demande.getSexe().toLowerCase().replaceAll(" ", ""),
                        demande.getDateNaissance());
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Orange");
                }
                condamnations = condamnationRepository.findCondamnationsB1E3(
                        demande.getNom().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                        demande.getSexe().toLowerCase().replaceAll(" ", ""),
                        demande.getNomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getNomMere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomMere().toLowerCase().replaceAll(" ", ""));
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Orange");
                }
                condamnations = condamnationRepository.findCondamnationsB1(
                        demande.getNom().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenom().toLowerCase().replaceAll(" ", ""),
                        demande.getSexe().toLowerCase().replaceAll(" ", ""),
                        demande.getDateNaissance(),
                        demande.getNomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomPere().toLowerCase().replaceAll(" ", ""),
                        demande.getNomMere().toLowerCase().replaceAll(" ", ""),
                        demande.getPrenomMere().toLowerCase().replaceAll(" ", ""));
                if (condamnations != null && !condamnations.isEmpty()) {
                    casier.setCouleur("Rouge");
                }
            } else {
                casier.setCouleur("Vert");
            }

            return getCasier(condamnations, casier);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private Casier getCasier(List<Condamnation> condamnations, Casier casier) {
        if (condamnations != null && !condamnations.isEmpty()) {
            Personne personne = condamnations.get(0).getPersonne();
            casier.setPersonne(entityMapperService.personneToPersonneDto(personne));
            casier.setCondamnations(condamnations.stream().map(entityMapperService::condamnationToCondamnationCasier).collect(Collectors.toList()));
            casier.setInfractions(getPeineInfraction(condamnations));
        }
        return casier;
    }
}
