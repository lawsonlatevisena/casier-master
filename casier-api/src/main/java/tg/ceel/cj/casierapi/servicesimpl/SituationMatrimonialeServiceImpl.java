package tg.ceel.cj.casierapi.servicesimpl;

import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.SituationMatrimonialeDto;
import tg.ceel.cj.casierapi.entities.SituationMatrimoniale;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.SituationMatrimonialeRepository;
import tg.ceel.cj.casierapi.services.SituationMatrimonialeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SituationMatrimonialeServiceImpl  implements SituationMatrimonialeService {
    private final SituationMatrimonialeRepository situationMatrimonialeRepository;
    private final EntityMapper entityMapper;

    public SituationMatrimonialeServiceImpl(SituationMatrimonialeRepository situationMatrimonialeRepository, EntityMapper entityMapper) {
        this.situationMatrimonialeRepository = situationMatrimonialeRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<SituationMatrimonialeDto> getAll() {
        List<SituationMatrimoniale> list =situationMatrimonialeRepository.findAll();
        return list.stream().map(s -> entityMapper.situationMatrimonialeToSituationMatrimonialeDto(s)).collect(Collectors.toList());
    }
}
