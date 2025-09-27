package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.CategorieSocioProfessionnelleDto;
import tg.ceel.cj.casierapi.entities.CategorieSocioProfessionnelle;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.CategorieSocioProfessionnelleRepository;
import tg.ceel.cj.casierapi.services.CategorieSocioProfessionnelleService;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.UserService;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorieSocioProfessionnelleServiceImpl implements CategorieSocioProfessionnelleService {

    private final CategorieSocioProfessionnelleRepository categorieSocioProfessionnelleRepository;
    Logger logger = LoggerFactory.getLogger(CategorieSocioProfessionnelleServiceImpl.class);
    private final EntityMapper entityMapper;
    private final LogService logService;
    private final UserService userService;

    public CategorieSocioProfessionnelleServiceImpl(CategorieSocioProfessionnelleRepository categorieSocioProfessionnelleRepository, EntityMapper entityMapper, LogService logService, UserService userService) {
        this.categorieSocioProfessionnelleRepository = categorieSocioProfessionnelleRepository;
        this.entityMapper = entityMapper;
        this.logService = logService;
        this.userService = userService;
    }

    @Override
    public List<CategorieSocioProfessionnelleDto> getAll() {
        List<CategorieSocioProfessionnelle> list = categorieSocioProfessionnelleRepository.findAll();
        return list.stream().map(c -> entityMapper.categorieSocioProfessionnelleToCategorieSocioProfessionnelleDto(c)).collect(Collectors.toList());
    }

    @Override
    public CategorieSocioProfessionnelleDto save(CategorieSocioProfessionnelleDto categorieSocioProfessionnelleDto) {
        try{
            User user = this.userService.getCurrentUser();
            CategorieSocioProfessionnelle categorieSocioProfessionnelle = this.entityMapper.categorieSocioProfessionnelleDtoToCategorieSocioProfessionnelle(categorieSocioProfessionnelleDto);
            CategorieSocioProfessionnelle categorieSocioProfessionnelleSaved = this.categorieSocioProfessionnelleRepository.save(categorieSocioProfessionnelle);
            String logAction = "Ajout d'une nouvelle categorie socio professionnelle : " + categorieSocioProfessionnelleSaved.getLibelle() ;
            this.logService.save(logAction, null, categorieSocioProfessionnelleSaved.toString(), user);
            return this.entityMapper.categorieSocioProfessionnelleToCategorieSocioProfessionnelleDto(categorieSocioProfessionnelleSaved);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public CategorieSocioProfessionnelleDto update(Integer id, CategorieSocioProfessionnelleDto categorieSocioProfessionnelleDto) {
        try{
            CategorieSocioProfessionnelle categorieSocioProfessionnelle = this.categorieSocioProfessionnelleRepository.findById(id).orElseThrow(() -> new Exception(String.format("Categorie socio professionnelle %s n'est pas trouvé", id)));
            User user = this.userService.getCurrentUser();
            categorieSocioProfessionnelle.setLibelle(categorieSocioProfessionnelleDto.getLibelle());
            CategorieSocioProfessionnelle categorieSocioProfessionnelleUpdated = this.categorieSocioProfessionnelleRepository.save(categorieSocioProfessionnelle);
            String logAction = "Modification de la categorie socio professionnelle : " + categorieSocioProfessionnelleDto.getLibelle() + " à "  + categorieSocioProfessionnelleUpdated.getLibelle() ;
            this.logService.save(logAction, null, categorieSocioProfessionnelleUpdated.toString(), user);
            return this.entityMapper.categorieSocioProfessionnelleToCategorieSocioProfessionnelleDto(categorieSocioProfessionnelleUpdated);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
