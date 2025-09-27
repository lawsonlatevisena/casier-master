package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.PersonneInfoDto;
import tg.ceel.cj.casierapi.entities.PersonneInfo;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.models.DashboardModel;

import java.util.List;

public interface PersonneInfoService {
    List<PersonneInfoDto> findAll();
    PersonneInfoDto save(PersonneInfoDto dto);
    PersonneInfoDto findById(Long id);
    PersonneInfoDto findByUsername(String username);

    PersonneInfoDto update(PersonneInfoDto dto, String code);



    PersonneInfoDto update(Long id, PersonneInfoDto PersonneInfoDto);




}
