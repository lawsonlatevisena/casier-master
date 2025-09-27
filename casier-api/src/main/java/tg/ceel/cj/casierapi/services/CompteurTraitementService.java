package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.entities.Demande;

public interface CompteurTraitementService {
    Long next(Demande demande);
}
