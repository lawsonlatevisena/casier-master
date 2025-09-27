package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.AutrePointRetraitDto;

import java.util.List;

public interface AutrePointRetraitService {
    List<AutrePointRetraitDto> getAll();
    AutrePointRetraitDto save(AutrePointRetraitDto dto);
    AutrePointRetraitDto findById(String code);
    AutrePointRetraitDto update(AutrePointRetraitDto dto, String code);

}
