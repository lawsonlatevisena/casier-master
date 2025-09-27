package tg.ceel.cj.casierapi.servicesimpl;

import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.PaysDto;
import tg.ceel.cj.casierapi.dto.PermissionCategoryDto;
import tg.ceel.cj.casierapi.entities.Pays;
import tg.ceel.cj.casierapi.entities.PermissionCategory;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.PermissionCategoryRepository;
import tg.ceel.cj.casierapi.services.PaysService;
import tg.ceel.cj.casierapi.services.PermissionCategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionCategoryServiceImpl implements PermissionCategoryService {
    private final PermissionCategoryRepository permissionCategoryRepository;
    private final EntityMapper entityMapper;

    public PermissionCategoryServiceImpl(PermissionCategoryRepository permissionCategoryRepository, EntityMapper entityMapper) {
        this.permissionCategoryRepository = permissionCategoryRepository;
        this.entityMapper = entityMapper;
    }


    @Override
    public List<PermissionCategoryDto> findAll() {
        List<PermissionCategory> list = permissionCategoryRepository.findAll();
        return list.stream().map(p -> entityMapper.permissionCategoryToPermissionCategoryDto(p)).collect(Collectors.toList());
    }


}
