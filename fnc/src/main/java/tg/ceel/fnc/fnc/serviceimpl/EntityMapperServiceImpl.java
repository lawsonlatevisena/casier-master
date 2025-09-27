package tg.ceel.fnc.fnc.serviceimpl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.fnc.fnc.dto.CondamnationCasier;
import tg.ceel.fnc.fnc.dto.CondamnationDto;
import tg.ceel.fnc.fnc.dto.PeineInfractionDto;
import tg.ceel.fnc.fnc.dto.PersonneDto;
import tg.ceel.fnc.fnc.entities.Condamnation;
import tg.ceel.fnc.fnc.entities.PeineInfraction;
import tg.ceel.fnc.fnc.entities.Personne;
import tg.ceel.fnc.fnc.service.EntityMapperService;


@Service
public class EntityMapperServiceImpl implements EntityMapperService {
    private final ModelMapper entityManager;
    Logger logger = LoggerFactory.getLogger(EntityMapperServiceImpl.class);

    public EntityMapperServiceImpl(ModelMapper entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public CondamnationDto condamnationToCondamnationDto(Condamnation condamnation) {
        try {
            if (condamnation == null) {
                return null;
            }
            return entityManager.map(condamnation, CondamnationDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public CondamnationCasier condamnationToCondamnationCasier(Condamnation condamnation) {
        try {
            if (condamnation == null) {
                return null;
            }
            return entityManager.map(condamnation, CondamnationCasier.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public PersonneDto personneToPersonneDto(Personne personne) {
        try {
            if (personne == null) {
                return null;
            }
            return entityManager.map(personne, PersonneDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public PeineInfractionDto peinInfractionToPeinInfractionDto(PeineInfraction peineInfraction) {
        try {
            if (peineInfraction == null) {
                return null;
            }
            return entityManager.map(peineInfraction, PeineInfractionDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
