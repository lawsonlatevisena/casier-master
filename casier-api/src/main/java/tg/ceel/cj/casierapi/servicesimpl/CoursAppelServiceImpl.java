package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.CoursAppelDto;
import tg.ceel.cj.casierapi.entities.CategorieSocioProfessionnelle;
import tg.ceel.cj.casierapi.entities.CoursAppel;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.CoursAppelRepository;
import tg.ceel.cj.casierapi.services.CoursAppelService;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CoursAppelServiceImpl implements CoursAppelService {
    private final CoursAppelRepository coursAppelRepository;
    Logger logger = LoggerFactory.getLogger(CoursAppelServiceImpl.class);
    private final EntityMapper entityMapper;
    private final LogService logService;
    private final UserService userService;
    public CoursAppelServiceImpl(CoursAppelRepository coursAppelRepository, EntityMapper entityMapper, LogService logService, UserService userService) {
        this.coursAppelRepository = coursAppelRepository;
        this.entityMapper = entityMapper;
        this.logService = logService;
        this.userService = userService;
    }

    @Override
    public List<CoursAppelDto> getAll() {
        List<CoursAppel> list = coursAppelRepository.findAll();
        return list.stream().map(c -> entityMapper.coursAppelToCoursAppelDto(c)).collect(Collectors.toList());
    }

    @Override
    public CoursAppelDto save(CoursAppelDto coursAppelDto) {
        try{
            User user = this.userService.getCurrentUser();
            CoursAppel coursAppel = this.entityMapper.coursAppelDtoToCoursAppel(coursAppelDto);
            coursAppel.setCreatedBy(user.getId());
            CoursAppel coursAppelSaved = this.coursAppelRepository.save(coursAppel);
            String logAction = "Ajout d'une nouvelle cours d'appel : " + coursAppelSaved.getLibelle() ;
            this.logService.save(logAction, null, coursAppelSaved.toString(), user);
            return this.entityMapper.coursAppelToCoursAppelDto(coursAppelSaved);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public CoursAppelDto update(Integer id, CoursAppelDto coursAppelDto) {
        try{
            CoursAppel coursAppel = this.coursAppelRepository.findById(id)
                    .orElseThrow(() -> new Exception(String.format("Cours d'appel %s n'est pas trouvé", id)));
            User user = this.userService.getCurrentUser();
            coursAppel.setLibelle(coursAppelDto.getLibelle());
            coursAppel.setVersion(coursAppelDto.getVersion());
            CoursAppel coursAppelupdated = this.coursAppelRepository.save(coursAppel);
            String logAction = "Modification de la cours d'appel : " + coursAppel.getLibelle() + " à "  +coursAppelupdated.getLibelle();
            this.logService.save(logAction, null, coursAppelupdated.toString(), user);
            return this.entityMapper.coursAppelToCoursAppelDto(coursAppelupdated);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
