package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.CoursAppelDto;
import tg.ceel.cj.casierapi.dto.VariableDto;
import tg.ceel.cj.casierapi.entities.Variable;

import java.util.Date;
import java.util.List;

public interface VariableService {

    String getId(Variable e);

    void setValue(String name, Object value);

    void setValue(String name, String value);

    String getValue(String name);

    int getIntValue(String name);

    long getLongValue(String name);

    float getFloatValue(String name);

    double getDoubleValue(String name);

    Date getDateValue(String name);

    boolean getBooleanValue(String name);
    List<VariableDto> getAll();
    VariableDto save(VariableDto variableDto);
    VariableDto update(String name,VariableDto variableDto);
}
