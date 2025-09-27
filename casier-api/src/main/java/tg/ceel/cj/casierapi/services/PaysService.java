package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.FonctionDto;
import tg.ceel.cj.casierapi.dto.PaysDto;
import tg.ceel.cj.casierapi.entities.Pays;

import java.util.List;

public interface PaysService {
    List<PaysDto> findAll();

    PaysDto save(PaysDto paysDto);

    PaysDto update(String code, PaysDto paysDto);
}
