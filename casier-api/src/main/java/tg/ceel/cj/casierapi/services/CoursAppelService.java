package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.CoursAppelDto;

import java.util.List;

public interface CoursAppelService {
    List<CoursAppelDto> getAll();
    CoursAppelDto save(CoursAppelDto coursAppelDto);
    CoursAppelDto update(Integer id,CoursAppelDto coursAppelDto);
}
