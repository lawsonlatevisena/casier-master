package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.PermissionDto;
import tg.ceel.cj.casierapi.entities.Permission;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.PermissionRepository;
import tg.ceel.cj.casierapi.services.PermissionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final EntityMapper entityMapper;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public PermissionServiceImpl(PermissionRepository permissionRepository, EntityMapper entityMapper) {
        this.permissionRepository = permissionRepository;
        this.entityMapper = entityMapper;
    }


    @Override
    public List<PermissionDto> findAll() {
        List<Permission> list = permissionRepository.findAll();
        return list.stream().map(p -> entityMapper.permissionToPermissionDto(p)).collect(Collectors.toList());
    }

    @Override
    public void delete(String code) {
        try{
            Permission permission = this.permissionRepository.findById(code).orElseThrow(() -> new Exception(String.format("Permission ", " id ", code)));
            this.permissionRepository.delete(permission);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
        }
    }





}
