package tg.ceel.cj.casierapi.servicesimpl;


import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tg.ceel.cj.casierapi.dto.CategorieDemandeurB2Dto;
import tg.ceel.cj.casierapi.entities.CategorieDemandeurB2;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.CategorieDemandeurB2Repository;
import tg.ceel.cj.casierapi.services.CategorieDemandeurB2Service;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategorieDemandeurB2ServiceImpl  implements CategorieDemandeurB2Service {
    Logger logger = LoggerFactory.getLogger(CategorieDemandeurB2ServiceImpl.class);
    private final EntityMapper entityMapper;
    private final UserService userService;

    private final LogService logService;
    private final CategorieDemandeurB2Repository categorieDemandeurB2Repository;



    public CategorieDemandeurB2ServiceImpl(CategorieDemandeurB2Repository categorieDemandeurB2Repository, EntityMapper entityMapper, UserService userService, LogService logService) {
        this.categorieDemandeurB2Repository = categorieDemandeurB2Repository;
        this.entityMapper = entityMapper;
        this.userService = userService;
        this.logService = logService;
    }

    @Override
    public List<CategorieDemandeurB2Dto> getAll() {
        List<CategorieDemandeurB2> list = categorieDemandeurB2Repository.findAll();
        return list.stream().map(s ->
                entityMapper.categorieDemandeurB2ToCategorieDemandeurB2Dto(s)).collect(Collectors.toList());
    }

    @Override
    public CategorieDemandeurB2Dto save(CategorieDemandeurB2Dto dto) {
        try {
            CategorieDemandeurB2 categorieDemandeurB2 = entityMapper.categorieDemandeurB2DtoToCategorieDemandeurB2(dto);
            return entityMapper.categorieDemandeurB2ToCategorieDemandeurB2Dto(categorieDemandeurB2Repository.save(categorieDemandeurB2));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public CategorieDemandeurB2Dto findById(Integer id) {
        try {
            String code = String.valueOf(id);
            CategorieDemandeurB2 categorieDemandeurB2 = categorieDemandeurB2Repository.findById(id).orElseThrow(() ->
                    //new Exception(String.format("Aucune categorie demandeur B2 correpondant à l'id %s", id)));
                    new Exception(String.format("Aucune categorie demandeur B2 correpondant à l'id %d", id)));
            return entityMapper.categorieDemandeurB2ToCategorieDemandeurB2Dto(categorieDemandeurB2);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public CategorieDemandeurB2Dto update(CategorieDemandeurB2Dto dto, Integer id) {
        try{

            User user = this.userService.getCurrentUser();

            CategorieDemandeurB2 categorieDemandeurB2 = this.categorieDemandeurB2Repository.findById(id).orElse(null);
            String ancienLibelle = categorieDemandeurB2.getLibelle();
            categorieDemandeurB2.setLibelle(dto.getLibelle());
            CategorieDemandeurB2 categorieDemandeurB2Update = this.categorieDemandeurB2Repository.save(categorieDemandeurB2);
            String logAction = "Modification de la catégorie demandeur : " + ancienLibelle + " à  " + categorieDemandeurB2Update.getLibelle();
            this.logService.save(logAction, null, categorieDemandeurB2Update.toString(), user);

            return this.entityMapper.categorieDemandeurB2ToCategorieDemandeurB2Dto(categorieDemandeurB2Update);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
