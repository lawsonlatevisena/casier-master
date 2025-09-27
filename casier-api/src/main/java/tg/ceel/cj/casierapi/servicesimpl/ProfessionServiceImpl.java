package tg.ceel.cj.casierapi.servicesimpl;

import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.ProfessionDto;
import tg.ceel.cj.casierapi.entities.CategorieSocioProfessionnelle;
import tg.ceel.cj.casierapi.entities.Profession;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.ProfessionRepository;
import tg.ceel.cj.casierapi.services.ProfessionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionServiceImpl implements ProfessionService {
    private final ProfessionRepository professionRepository;
    private final  EntityMapper entityMapper;

    public ProfessionServiceImpl(ProfessionRepository professionRepository, EntityMapper entityMapper) {
        this.professionRepository = professionRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<ProfessionDto> getAll() {
        return professionRepository.findAll().stream().map(p ->{return entityMapper.professionToProfessionDto(p);}).collect(Collectors.toList());
    }

    @Override
    public List<ProfessionDto> getAllByCategorie(Integer idCategorie) {
        return professionRepository.findByCatedorieId(idCategorie).stream().map(p ->{return entityMapper.professionToProfessionDto(p);}).collect(Collectors.toList());

    }

    @Override
    public ProfessionDto save(ProfessionDto dto) {
        Profession profession = entityMapper.professionDtoToProfession(dto);
        return entityMapper.professionToProfessionDto(professionRepository.save(profession));
    }
    @Override
    public ProfessionDto update(Integer id, ProfessionDto professionDto) {
        try{
            Profession profession = this.professionRepository.findById(id).orElseThrow(() -> new Exception(String.format("profession %s n'est pas trouvé", id)));
            profession.setLibelle(professionDto.getLibelle());
            profession.setCategorieSocioProfessionnelle(
                    CategorieSocioProfessionnelle
                            .builder()
                            .id(professionDto.getCategorieSocioProfessionnelleId())
                            .build()
            );
            Profession professionUpdated = this.professionRepository.save(profession);
            return this.entityMapper.professionToProfessionDto(professionUpdated);
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
