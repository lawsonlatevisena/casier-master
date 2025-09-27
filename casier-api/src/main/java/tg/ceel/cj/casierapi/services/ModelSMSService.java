package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.ModelSMSDto;

import java.util.List;

public interface ModelSMSService {
    List<ModelSMSDto> getAll();
    ModelSMSDto save(ModelSMSDto modelSMSDto);
    ModelSMSDto update(String code,ModelSMSDto modelSMSDto);
}
