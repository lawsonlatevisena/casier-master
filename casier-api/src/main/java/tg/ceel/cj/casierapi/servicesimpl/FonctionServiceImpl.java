package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.FonctionDto;
import tg.ceel.cj.casierapi.entities.Fonction;
import tg.ceel.cj.casierapi.entities.Role;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.FonctionRepository;
import tg.ceel.cj.casierapi.services.FonctionService;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FonctionServiceImpl implements FonctionService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final FonctionRepository fonctionRepository;
    private final EntityMapper entityMapper;

    private final UserService userService;

    private final LogService logService;

    public FonctionServiceImpl(FonctionRepository fonctionRepository, EntityMapper entityMapper, UserService userService, LogService logService) {
        this.fonctionRepository = fonctionRepository;
        this.entityMapper = entityMapper;
        this.userService = userService;
        this.logService = logService;
    }


    @Override
    public List<FonctionDto> findAll() {
        List<Fonction> list = fonctionRepository.findAll();
        return list.stream().map(p -> entityMapper.fontionToFonctionDto(p)).collect(Collectors.toList());
    }




    @Override
    public FonctionDto save(FonctionDto fonctionDto) {
        try{
            User user = this.userService.getCurrentUser();
            Fonction fonction = this.entityMapper.fonctionDtoToFonction(fonctionDto);
            fonction.setCreatedBy(user.getId());
            Fonction fonctionSaved = this.fonctionRepository.save(fonction);
            String logAction = "Ajout d'une nouvelle fonction : " + fonctionSaved.getLibelle() ;
            this.logService.save(logAction, null, fonctionSaved.toString(), user);
            return this.entityMapper.fontionToFonctionDto(fonctionSaved);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }

    }


    @Override
    public FonctionDto update(Integer id, FonctionDto fonctionDto) {

        try{
            Fonction fonction = this.fonctionRepository.findById(id).orElseThrow(() -> new Exception(String.format("Rolde %s n'est pas trouvé", id)));
            User user = this.userService.getCurrentUser();
            fonction.setLibelle(fonctionDto.getLibelle());
            Fonction fonctionUpdate = this.fonctionRepository.save(fonction);
            String logAction = "Modification de fonction : " + fonctionDto.getLibelle() + " à "  + fonctionUpdate.getLibelle() ;
            this.logService.save(logAction, null, fonctionUpdate.toString(), user);
            return this.entityMapper.fontionToFonctionDto(fonctionUpdate);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }

    }
}
