package tg.ceel.cj.casierapi.services;



import tg.ceel.cj.casierapi.dto.LocaliteDto;

import java.util.List;

public interface LocaliteService {
    List<LocaliteDto> getAll();
    LocaliteDto findById(Integer id);
    LocaliteDto save(LocaliteDto localiteDto);
    LocaliteDto update(Integer id,LocaliteDto localiteDto);

}
