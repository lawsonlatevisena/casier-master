package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.PaysDto;
import tg.ceel.cj.casierapi.entities.Fonction;
import tg.ceel.cj.casierapi.entities.Pays;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.PaysRepository;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.PaysService;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaysServiceImpl implements PaysService {
    private final PaysRepository paysRepository;
    private final EntityMapper entityMapper;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserService userService;
    private final LogService logService;

    public PaysServiceImpl(PaysRepository paysRepository, EntityMapper entityMapper, UserService userService, LogService logService) {
        this.paysRepository = paysRepository;
        this.entityMapper = entityMapper;
        this.userService = userService;
        this.logService = logService;
    }

    @Override
    public List<PaysDto> findAll() {
        List<Pays> list = paysRepository.findAll();
        return list.stream().map(p -> entityMapper.paysToPaysDto(p)).collect(Collectors.toList());
    }

    @Override
    public PaysDto save(PaysDto paysDto) {
        try{
            User user = this.userService.getCurrentUser();
            Pays pays = this.entityMapper.paysDtoToPays(paysDto);
            pays.setCreatedBy(user.getId());
            Pays paysSaved = this.paysRepository.save(pays);
            String logAction = "Ajout d'un nouveau pays : " + paysSaved.getLibelle() ;
            this.logService.save(logAction, null, paysSaved.toString(), user);
            return this.entityMapper.paysToPaysDto(paysSaved);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public PaysDto update(String code, PaysDto paysDto) {
        try{
            Pays pays = this.paysRepository.findById(code).orElseThrow(() -> new Exception(String.format("Objet %s n'est pas trouvé", code)));
            User user = this.userService.getCurrentUser();
            pays.setLibelle(paysDto.getLibelle());
            pays.setLibelleNationalite(paysDto.getLibelleNationalite());
            Pays paysUpdate = this.paysRepository.save(pays);
            String logAction = "Modification de pays : " + paysDto.getLibelle() + " à "  + paysUpdate.getLibelle() ;
            this.logService.save(logAction, null, paysUpdate.toString(), user);
            return this.entityMapper.paysToPaysDto(paysUpdate);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
