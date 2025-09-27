package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.PermissionCategoryDto;
import tg.ceel.cj.casierapi.dto.PermissionDto;

import java.util.List;

public interface PermissionService {
    List<PermissionDto> findAll();


    void delete(String code);
}
