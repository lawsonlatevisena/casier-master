package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.FonctionDto;
import tg.ceel.cj.casierapi.dto.PaysDto;
import tg.ceel.cj.casierapi.dto.RoleDto;

import java.util.List;

public interface FonctionService {
    List<FonctionDto> findAll();

    FonctionDto save(FonctionDto fonctionDto);

    FonctionDto update(Integer id, FonctionDto fonctionDto);
}
