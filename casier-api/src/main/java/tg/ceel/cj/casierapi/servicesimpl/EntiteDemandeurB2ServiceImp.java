package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.EntiteDemandeurB2Dto;
import tg.ceel.cj.casierapi.entities.EntiteDemandeurB2;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.CategorieDemandeurB2Repository;
import tg.ceel.cj.casierapi.repositories.EntiteDemandeurB2Repository;
import tg.ceel.cj.casierapi.services.EntiteDemandeurB2Service;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntiteDemandeurB2ServiceImp implements EntiteDemandeurB2Service {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final EntityMapper entityMapper;
    private final UserService userService;

    private final LogService logService;
    private final EntiteDemandeurB2Repository entiteDemandeurB2Repository;

    private final CategorieDemandeurB2Repository categorieDemandeurB2Repository;

    public EntiteDemandeurB2ServiceImp(EntityMapper entityMapper, UserService userService, LogService logService, EntiteDemandeurB2Repository entiteDemandeurB2Repository, CategorieDemandeurB2Repository categorieDemandeurB2Repository) {
        this.entityMapper = entityMapper;
        this.userService = userService;
        this.logService = logService;
        this.entiteDemandeurB2Repository = entiteDemandeurB2Repository;
        this.categorieDemandeurB2Repository = categorieDemandeurB2Repository;
    }

    @Override
    public List<EntiteDemandeurB2Dto> getAll() {
        List<EntiteDemandeurB2> entiteDemandeurB2s =entiteDemandeurB2Repository.findAll();
        return entiteDemandeurB2s.stream().map(entiteDemandeurB2 -> entityMapper.entiteDemandeurB2ToEntiteDemandeurB2Dto(entiteDemandeurB2)).collect(Collectors.toList());
    }

    @Override
    public EntiteDemandeurB2Dto save(EntiteDemandeurB2Dto dto) {
        try{

            User user = this.userService.getCurrentUser();

            EntiteDemandeurB2 entiteDemandeurB2 = this.entityMapper.entiteDemandeurB2DtoToEntiteDemandeurB2(dto);
            entiteDemandeurB2.setCreatedBy(user.getId());
            EntiteDemandeurB2 entiteDemandeurB2Saved = this.entiteDemandeurB2Repository.save(entiteDemandeurB2);
            String logAction = "Ajout d'une nouvelle entité demandeur  : " +entiteDemandeurB2Saved.getLibelle();
            this.logService.save(logAction, null, entiteDemandeurB2Saved.toString(), user);
            return this.entityMapper.entiteDemandeurB2ToEntiteDemandeurB2Dto(entiteDemandeurB2Saved);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }

    }

    @Override
    public EntiteDemandeurB2Dto findById(Integer id) {
        return null;
    }

    @Override
    public EntiteDemandeurB2Dto update(EntiteDemandeurB2Dto dto, Integer id) {
        try{

            User user = this.userService.getCurrentUser();

            EntiteDemandeurB2 entiteDemandeurB2 = this.entiteDemandeurB2Repository.findById(id).orElse(null);
            String ancienLibelle = entiteDemandeurB2.getLibelle();
            entiteDemandeurB2.setLibelle(dto.getLibelle());
            entiteDemandeurB2.setCategorieDemandeurB2(this.categorieDemandeurB2Repository.findById(dto.getCategorieDemandeurB2Id()).orElse(null));
            EntiteDemandeurB2 entiteDemandeurB2Update = this.entiteDemandeurB2Repository.save(entiteDemandeurB2);
            String logAction = "Modification de l'entité demandeur : " + ancienLibelle + " à  " + entiteDemandeurB2Update.getLibelle();
            this.logService.save(logAction, null, entiteDemandeurB2Update.toString(), user);

            return this.entityMapper.entiteDemandeurB2ToEntiteDemandeurB2Dto(entiteDemandeurB2);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }

    }
}
