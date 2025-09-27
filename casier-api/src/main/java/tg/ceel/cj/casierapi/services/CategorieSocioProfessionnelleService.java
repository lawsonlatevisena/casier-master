package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.CategorieSocioProfessionnelleDto;


import java.util.List;

public interface CategorieSocioProfessionnelleService {
    List<CategorieSocioProfessionnelleDto> getAll();
    CategorieSocioProfessionnelleDto save(CategorieSocioProfessionnelleDto categorieSocioProfessionnelleDto);
    CategorieSocioProfessionnelleDto update(Integer id,CategorieSocioProfessionnelleDto categorieSocioProfessionnelleDto);

}
