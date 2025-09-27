package tg.ceel.cj.casierapi.servicesimpl;

import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.LocaliteDto;
import tg.ceel.cj.casierapi.entities.CategorieSocioProfessionnelle;
import tg.ceel.cj.casierapi.entities.Juridiction;
import tg.ceel.cj.casierapi.entities.Localite;
import tg.ceel.cj.casierapi.entities.Profession;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.LocaliteRepository;
import tg.ceel.cj.casierapi.services.LocaliteService;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocaliteServiceImpl implements LocaliteService {
    private final LocaliteRepository localiteRepository;
    private final EntityMapper entityMapper;

    public LocaliteServiceImpl(LocaliteRepository localiteRepository, EntityMapper entityMapper) {
        this.localiteRepository = localiteRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<LocaliteDto> getAll() {
        List<Localite> list = localiteRepository.findAll();
        if (list == null) {
            return null;
        }
        return list.stream().map(l -> entityMapper.localiteToLocaliteDto(l)).collect(Collectors.toList());
    }

    @Override
    public LocaliteDto findById(Integer id) {
        Localite localite = localiteRepository.findById(id).orElse(null);
        if (localite == null) {
            return null;
        }
        return entityMapper.localiteToLocaliteDto(localite);
    }

    @Override
    public LocaliteDto save(LocaliteDto localiteDto) {
        Localite localite = entityMapper.localiteDtoToLocalite(localiteDto);
        return entityMapper.localiteToLocaliteDto(localiteRepository.save(localite));
    }

    @Override
    public LocaliteDto update(Integer id, LocaliteDto localiteDto) {
        try{
            Localite localite = this.localiteRepository.findById(id).orElseThrow(() -> new Exception(String.format("localite %s n'est pas trouvé", id)));
            Juridiction juridiction = new Juridiction();
            juridiction.setId(localiteDto.getJuridictionId());
            localite.setLibelle(localiteDto.getLibelle());
            localite.setJuridiction(juridiction);
            Localite localiteUpdated = this.localiteRepository.save(localite);
            return this.entityMapper.localiteToLocaliteDto(localiteUpdated);
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
