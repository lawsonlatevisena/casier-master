package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.PermissionCategoryDto;
import tg.ceel.cj.casierapi.dto.ProfessionDto;
import tg.ceel.cj.casierapi.entities.PermissionCategory;

import java.util.List;

public interface PermissionCategoryService {
    List<PermissionCategoryDto> findAll();

}
