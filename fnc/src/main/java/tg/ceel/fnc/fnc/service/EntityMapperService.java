package tg.ceel.fnc.fnc.service;

import tg.ceel.fnc.fnc.dto.CondamnationCasier;
import tg.ceel.fnc.fnc.dto.CondamnationDto;
import tg.ceel.fnc.fnc.dto.PeineInfractionDto;
import tg.ceel.fnc.fnc.dto.PersonneDto;
import tg.ceel.fnc.fnc.entities.Condamnation;
import tg.ceel.fnc.fnc.entities.PeineInfraction;
import tg.ceel.fnc.fnc.entities.Personne;

public interface EntityMapperService {
    CondamnationDto condamnationToCondamnationDto(Condamnation condamnation);
    CondamnationCasier condamnationToCondamnationCasier(Condamnation condamnation);
    PersonneDto personneToPersonneDto(Personne personne);
    PeineInfractionDto peinInfractionToPeinInfractionDto(PeineInfraction peineInfraction);
}
