package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.ServiceDemandeurB2Dto;
import tg.ceel.cj.casierapi.entities.ServiceDemandeurB2;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.EntiteDemandeurB2Repository;
import tg.ceel.cj.casierapi.repositories.ServiceDemandeurB2Repository;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.ServiceDemandeurB2Service;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceDemandeurB2ServiceImpl implements ServiceDemandeurB2Service {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final EntityMapper entityMapper;
    private final ServiceDemandeurB2Repository serviceDemandeurB2Repository;
    private final EntiteDemandeurB2Repository entiteDemandeurB2Repository;
    private final UserService userService;
    private final LogService logService;

    public ServiceDemandeurB2ServiceImpl(EntityMapper entityMapper, ServiceDemandeurB2Repository serviceDemandeurB2Repository, EntiteDemandeurB2Repository entiteDemandeurB2Repository, UserService userService, LogService logService) {
        this.entityMapper = entityMapper;
        this.serviceDemandeurB2Repository = serviceDemandeurB2Repository;
        this.entiteDemandeurB2Repository = entiteDemandeurB2Repository;
        this.userService = userService;
        this.logService = logService;
    }

    @Override
    public List<ServiceDemandeurB2Dto> getAll() {
        List<ServiceDemandeurB2> serviceDemandeurB2s = serviceDemandeurB2Repository.findAll();
        return serviceDemandeurB2s.stream().map(serviceDemandeurB2 -> entityMapper.serviceDemandeurB2ToServiceDemandeurB2Dto(serviceDemandeurB2)).collect(Collectors.toList());
    }

    @Override
    public ServiceDemandeurB2Dto save(ServiceDemandeurB2Dto serviceDemandeurB2Dto) {
        try{
            User user = this.userService.getCurrentUser();
            ServiceDemandeurB2 serviceDemandeurB2 = this.entityMapper.serviceDemandeurB2DtoToServiceDemandeurB2(serviceDemandeurB2Dto);
            serviceDemandeurB2.setCreatedBy(user.getId());
            ServiceDemandeurB2 serviceDemandeurB2Saved = this.serviceDemandeurB2Repository.save(serviceDemandeurB2);
            String logAction = "Ajout d'un nouveau serviceDemandeur : " + serviceDemandeurB2Saved.getLibelle() ;
            this.logService.save(logAction, null, serviceDemandeurB2Saved.toString(), user);
            return this.entityMapper.serviceDemandeurB2ToServiceDemandeurB2Dto(serviceDemandeurB2Saved);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }

    }

    @Override
    public ServiceDemandeurB2Dto update(Integer id, ServiceDemandeurB2Dto serviceDemandeurB2Dto) {
        try{
            ServiceDemandeurB2 serviceDemandeurB2 = this.serviceDemandeurB2Repository.findById(id).orElseThrow(() -> new Exception(String.format("Objet %s n'est pas trouvé", id)));
            User user = this.userService.getCurrentUser();
            serviceDemandeurB2.setLibelle(serviceDemandeurB2Dto.getLibelle());
            serviceDemandeurB2.setEntiteDemandeurB2(this.entiteDemandeurB2Repository.findById(serviceDemandeurB2Dto.getEntiteDemandeurB2Id()).orElse(null));
            ServiceDemandeurB2 serviceDemandeurB2Update = this.serviceDemandeurB2Repository.save(serviceDemandeurB2);
            String logAction = "Modification de serviceDemandeur : " + serviceDemandeurB2Dto.getLibelle() + " à "  + serviceDemandeurB2Update.getLibelle() ;
            this.logService.save(logAction, null, serviceDemandeurB2Update.toString(), user);
            return this.entityMapper.serviceDemandeurB2ToServiceDemandeurB2Dto(serviceDemandeurB2Update);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
