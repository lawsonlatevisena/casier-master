package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.CategorieDemandeurB2Dto;


import java.util.List;

public interface CategorieDemandeurB2Service {


    List<CategorieDemandeurB2Dto> getAll();

    CategorieDemandeurB2Dto save(CategorieDemandeurB2Dto dto);
    CategorieDemandeurB2Dto findById(Integer id);

    CategorieDemandeurB2Dto update(CategorieDemandeurB2Dto dto, Integer id);

}
