package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.ModelSMSDto;
import tg.ceel.cj.casierapi.entities.ModelSMS;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.ModelSMSRepository;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.ModelSMSService;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoedelSMSServiceImpl implements ModelSMSService {
    private final ModelSMSRepository modelSMSRepository;
    Logger logger = LoggerFactory.getLogger(MoedelSMSServiceImpl.class);
    private final EntityMapper entityMapper;
    private final LogService logService;
    private final UserService userService;
    public MoedelSMSServiceImpl(ModelSMSRepository modelSMSRepository, EntityMapper entityMapper, LogService logService, UserService userService) {
        this.modelSMSRepository = modelSMSRepository;
        this.entityMapper = entityMapper;
        this.logService = logService;
        this.userService = userService;
    }

    @Override
    public List<ModelSMSDto> getAll() {
        List<ModelSMS> list = modelSMSRepository.findAll();
        return list.stream().map(c -> entityMapper.modelSMSToModelSMSDto(c)).collect(Collectors.toList());
    }

    @Override
    public ModelSMSDto save(ModelSMSDto modelSMSDto) {
        try{
            User user = this.userService.getCurrentUser();
            ModelSMS modelSMS = this.entityMapper.modelSMSDtoToModelSMS(modelSMSDto);
            modelSMS.setCode(generateCode());
            modelSMS.setCreatedBy(user.getId());
            ModelSMS modelSMSSaved = this.modelSMSRepository.save(modelSMS);
            String logAction = "Ajout d'une nouvelle modelSMS : " + modelSMSSaved.getCode() ;
            this.logService.save(logAction, null, modelSMSSaved.toString(), user);
            return this.entityMapper.modelSMSToModelSMSDto(modelSMSSaved);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ModelSMSDto update(String nom, ModelSMSDto modelSMSDto) {
        try{
            ModelSMS modelSMS = this.modelSMSRepository.findByCode(modelSMSDto.getCode())
                    .orElseThrow(() -> new Exception(String.format("modelSMS %s n'est pas trouvé", modelSMSDto.getCode())));
            User user = this.userService.getCurrentUser();
            modelSMS.setNom(modelSMSDto.getNom());
            modelSMS.setContenu(modelSMSDto.getContenu());
            modelSMS.setVersion(modelSMSDto.getVersion());
            ModelSMS modelSMSUpdated = this.modelSMSRepository.save(modelSMS);
            String logAction = "Modification du modelSMS : " + modelSMS.getCode();
            this.logService.save(logAction, null, modelSMSUpdated.toString(), user);
            return this.entityMapper.modelSMSToModelSMSDto(modelSMSUpdated);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }
    private String generateCode() {
        String lastCode = modelSMSRepository.findTopByOrderByCodeDesc()
                .map(ModelSMS::getCode).orElse(ModelSMS.SEQUENCE_CODE + "0");
        int lastSequence = Integer.parseInt(lastCode.replace(ModelSMS.SEQUENCE_CODE, ""));
        int nextSequence = lastSequence + 1;
        return ModelSMS.SEQUENCE_CODE + nextSequence;
    }
}
