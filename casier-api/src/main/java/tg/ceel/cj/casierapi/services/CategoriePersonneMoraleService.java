package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.CategoriePersonneMoraleDto;

import java.util.List;

public interface CategoriePersonneMoraleService {
    List<CategoriePersonneMoraleDto> save(List<CategoriePersonneMoraleDto> categoriePersonneMoraleDtos);

    CategoriePersonneMoraleDto save(CategoriePersonneMoraleDto dto);

    List<CategoriePersonneMoraleDto> findAll();

    void init();
}
