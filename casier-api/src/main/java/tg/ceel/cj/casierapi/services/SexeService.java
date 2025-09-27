package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.SexeDto;

import java.util.List;

public interface SexeService {
    List<SexeDto> getAll();
    SexeDto save(SexeDto dto);
    SexeDto findById(String code);
    SexeDto update(SexeDto dto, String code);
}
