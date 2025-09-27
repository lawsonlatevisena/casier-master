package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.EntiteDemandeurB2Dto;
import tg.ceel.cj.casierapi.dto.EntiteDemandeurB2Dto;

import java.util.List;

public interface EntiteDemandeurB2Service {
    List<EntiteDemandeurB2Dto> getAll();

    EntiteDemandeurB2Dto save(EntiteDemandeurB2Dto dto);
    EntiteDemandeurB2Dto findById(Integer id);

    EntiteDemandeurB2Dto update(EntiteDemandeurB2Dto dto, Integer id);
}
