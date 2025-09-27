package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.SexeDto;
import tg.ceel.cj.casierapi.entities.Sexe;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.SexeRepository;
import tg.ceel.cj.casierapi.services.SexeService;
import tg.ceel.cj.casierapi.ws.DemandeController;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SexeServiceImpl implements SexeService {
    Logger logger = LoggerFactory.getLogger(SexeServiceImpl.class);
    private final SexeRepository sexeRepository;
    private final EntityMapper entityMapper;
    public SexeServiceImpl(SexeRepository sexeRepository, EntityMapper entityMapper) {
        this.sexeRepository = sexeRepository;
        this.entityMapper = entityMapper;
    }
    @Override
    public List<SexeDto> getAll() {
        List<Sexe> list = sexeRepository.findAll();
        return list.stream().map(s -> entityMapper.sexeToSexeDto(s)).collect(Collectors.toList());
    }

    @Override
    public SexeDto save(SexeDto dto) {
       try {
           Sexe sexe = entityMapper.sexeDtoToSexe(dto);
           return entityMapper.sexeToSexeDto(sexeRepository.save(sexe));
       }catch (Exception e) {
           e.printStackTrace();
           logger.error("Erreur interne", e);
           return null;
       }
    }

    @Override
    public SexeDto findById(String code) {
       try {
           Sexe sexe = sexeRepository.findById(code).orElseThrow(() ->
                   new Exception(String.format("Aucun sexe n'est trouvé avec le %s",code)));
           return entityMapper.sexeToSexeDto(sexe);
       }catch (Exception e) {
           e.printStackTrace();
           logger.error("Erreur interne", e);
           return null;
       }
    }

    @Override
    public SexeDto update(SexeDto dto, String code) {
        try {
            Sexe sexe = sexeRepository.findById(code).orElseThrow(() ->
                    new Exception(String.format("Aucun sexe n'est troivé avec le %s",code)));
            sexe.setLibelle(dto.getLibelle());
            return entityMapper.sexeToSexeDto(sexeRepository.save(sexe));
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
