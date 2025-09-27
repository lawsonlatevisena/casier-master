package tg.ceel.cj.casierapi.servicesimpl;

import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.PrefectureDto;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.PrefectureRepository;
import tg.ceel.cj.casierapi.services.PrefectureService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrefectureServiceImpl implements PrefectureService {
    private final PrefectureRepository prefectureRepository;
    private  final EntityMapper entityMapper;

    public PrefectureServiceImpl(PrefectureRepository prefectureRepository, EntityMapper entityMapper) {
        this.prefectureRepository = prefectureRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<PrefectureDto> getAll() {
        return prefectureRepository.findAll().stream().map( p -> {
            return entityMapper.prefectureToPrefectureDto(p);
        }).collect(Collectors.toList());
    }
}
