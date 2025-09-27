package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.AutrePointRetraitDto;
import tg.ceel.cj.casierapi.entities.AutrePointRetrait;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.AutrePointRetraitRepository;
import tg.ceel.cj.casierapi.services.AutrePointRetraitService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutrePointRetraitServiceImpl implements AutrePointRetraitService {

    Logger logger = LoggerFactory.getLogger(SexeServiceImpl.class);
    private final AutrePointRetraitRepository autrePointRetraitRepository;
    private final EntityMapper entityMapper;

    public AutrePointRetraitServiceImpl(AutrePointRetraitRepository autrePointRetraitRepository, EntityMapper entityMapper) {
        this.autrePointRetraitRepository = autrePointRetraitRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<AutrePointRetraitDto> getAll() {
        List<AutrePointRetrait> list = autrePointRetraitRepository.findAll();
        return list.stream().map(s -> entityMapper.autrePointRetraitToAutrePointRetraitDto(s)).collect(Collectors.toList());
    }

    @Override
    public AutrePointRetraitDto save(AutrePointRetraitDto dto) {
        try {
            AutrePointRetrait autrePointRetrait = entityMapper.autrePointRetraitDtoToAutrePointRetrait(dto);
            return entityMapper.autrePointRetraitToAutrePointRetraitDto(autrePointRetraitRepository.save(autrePointRetrait));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }

    }

    @Override
    public AutrePointRetraitDto findById(String code) {
        try {
            AutrePointRetrait autrePointRetrait = autrePointRetraitRepository.findById(code).
                    orElseThrow(() -> new Exception(String.format("Aucun point de retrait correspondant au code %s ", code)));
            return entityMapper.autrePointRetraitToAutrePointRetraitDto(autrePointRetrait);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error interne", e);
            return null;
        }
    }

    @Override
    public AutrePointRetraitDto update(AutrePointRetraitDto dto, String code) {
        try {
            AutrePointRetrait autrePointRetrait = autrePointRetraitRepository.findById(code).
                    orElseThrow(() -> new Exception(String.format("Aucun point de retrait correspondant au code %s ", code)));
            //autrePointRetrait.setTypeAutrePointRetrait(dto.getTypeAutrePointRetraitCode());
            //Les mises a jour
            return entityMapper.autrePointRetraitToAutrePointRetraitDto(autrePointRetrait);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error interne", e);
            return null;
        }
    }
}
