package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.ProfessionDto;

import java.util.List;

public interface ProfessionService {
    List<ProfessionDto> getAll();
    List<ProfessionDto> getAllByCategorie(Integer idCategorie);
    ProfessionDto save(ProfessionDto dto);
    ProfessionDto update(Integer id,ProfessionDto dto);



}
