package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.VariableDto;
import tg.ceel.cj.casierapi.entities.Variable;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.entities.Variable;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.VariableRepository;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.UserService;
import tg.ceel.cj.casierapi.services.VariableService;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VariableServiceImpl implements VariableService {
    Logger logger = LoggerFactory.getLogger(VariableServiceImpl.class);
    private final EntityMapper entityMapper;
    private final LogService logService;
    private final UserService userService;
    private final VariableRepository variableRepository;

    public VariableServiceImpl(EntityMapper entityMapper, LogService logService, UserService userService, VariableRepository variableRepository) {
        this.entityMapper = entityMapper;
        this.logService = logService;
        this.userService = userService;
        this.variableRepository = variableRepository;
    }
    @Override
    public String getId(Variable e) {
        return e.getName();
    }

    @Override
    public void setValue(String name, Object value) {
        this.setValue(name, value.toString());
    }

    @Override
    public void setValue(String name, String value) {
        Variable variable = this.variableRepository.findById(name).orElse(null);
        if (variable == null) {
            variable = new Variable(name);
            this.logger.warn("La variable nommée '%s' n'existe pas; Création...", name);
        }
        variable.setValue(value);
        this.variableRepository.save(variable);
    }

    @Override
    public String getValue(String name) {
        Variable variable = this.variableRepository.findById(name).orElse(null);
        if (variable == null) {
            this.logger.warn( "La variable nommée '%s' n'existe pas; 'null' renvoyée.", name);
            return null;
        }
        return variable.getValue();
    }

    @Override
    public int getIntValue(String name) {
        String value = this.getValue(name);
        return value == null ? 0 : Integer.valueOf(value);
    }

    @Override
    public long getLongValue(String name) {
        String value = this.getValue(name);
        return value == null ? 0l : Long.valueOf(value);
    }

    @Override
    public float getFloatValue(String name) {
        String value = this.getValue(name);
        return value == null ? 0.f : Float.valueOf(value);
    }

    @Override
    public double getDoubleValue(String name) {
        String value = this.getValue(name);
        return value == null ? 0. : Double.valueOf(value);
    }

    @Override
    public Date getDateValue(String name) {
        try {
            String value = this.getValue(name);
            return value == null ? null : new SimpleDateFormat("dd/MM/yyyy").parse(value);
        } catch (ParseException ex) {
            this.logger.warn( "Erreur de conversion de la date: %s.", ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean getBooleanValue(String name) {
        String value = this.getValue(name);
        return value == null ? false : Boolean.valueOf(value);
    }

    @Override
    public List<VariableDto> getAll() {
        List<Variable> list = variableRepository.findAll();
        return list.stream().map(c -> entityMapper.variableToVariableDto(c)).collect(Collectors.toList());
    }

    @Override
    public VariableDto save(VariableDto variableDto) {
        try{
            Variable var = this.variableRepository.findByName(variableDto.getName())
                    .orElse(null);
            if(var != null) {
                throw new IllegalArgumentException(String.format("La variable %s existe déjà", var.getName()));
            }
            User user = this.userService.getCurrentUser();
            Variable variable = this.entityMapper.variableDtoToVariable(variableDto);
            variable.setCreatedBy(user.getId());
            Variable variableSaved = this.variableRepository.save(variable);
            String logAction = "Ajout d'une nouvelle variable : " + variableSaved.getName() ;
            this.logService.save(logAction, null, variableSaved.toString(), user);
            return this.entityMapper.variableToVariableDto(variableSaved);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public VariableDto update(String name, VariableDto variableDto) {
        try{
            Variable variable = this.variableRepository.findByName(name)
                    .orElseThrow(() -> new Exception(String.format("variable %s n'est pas trouvé", name)));
            User user = this.userService.getCurrentUser();
            variable.setName(variableDto.getName());
            variable.setValue(variableDto.getValue());
            variable.setVersion(variableDto.getVersion());
            Variable variableUpdated = this.variableRepository.save(variable);
            String logAction = "Modification du variable : " + variable.getName();
            this.logService.save(logAction, null, variableUpdated.toString(), user);
            return this.entityMapper.variableToVariableDto(variableUpdated);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }
}
