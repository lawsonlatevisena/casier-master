package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.RolePermissionDto;
import tg.ceel.cj.casierapi.entities.RolesPermissions;
import tg.ceel.cj.casierapi.entities.RolesPermissionsPK;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.RolesPermissionsRepository;
import tg.ceel.cj.casierapi.repositories.UserRepository;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.RolePermissionService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    private final RolesPermissionsRepository rolesPermissionsRepository;
    private final EntityMapper entityMapper;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final LogService logService;
    private final UserRepository userRepository;


    public RolePermissionServiceImpl(RolesPermissionsRepository rolesPermissionsRepository, EntityMapper entityMapper, LogService logService, UserRepository userRepository) {
        this.rolesPermissionsRepository = rolesPermissionsRepository;
        this.entityMapper = entityMapper;
        this.logService = logService;
        this.userRepository = userRepository;
    }


    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userRepository.findByUsername(auth.getName());
        return authUser;
    }

    @Override
    public List<RolePermissionDto> findAll() {
        List<RolesPermissions> list = rolesPermissionsRepository.findAll();
        return list.stream().map(p -> entityMapper.rolePermissionToRolePermissionDto(p)).collect(Collectors.toList());
    }


    @Override
    public List<RolePermissionDto> findAllByRole(Long role) {
        List<RolesPermissions> rolesPermissions = this.rolesPermissionsRepository.findAllByRole(role);
        return rolesPermissions.stream().map(s -> this.entityMapper.rolePermissionToRolePermissionDto(s)).collect(Collectors.toList());
    }

    @Override
    public RolePermissionDto save(RolePermissionDto rolePermissionDto) {
        User utilisateur = this.getCurrentUser();
        RolesPermissions rolePermission = this.entityMapper.rolePermissionDtoToRolePermission(rolePermissionDto);

        rolePermission.setDebut(new Date());
        rolePermission.setActif(Boolean.TRUE);
        RolesPermissions rolePermissionSaved = this.rolesPermissionsRepository.save(rolePermission);

        String logAction = "Ajout d'une  nouvelle permission : " + rolePermissionSaved.getPermission().getLabel() + " au rôle " + rolePermissionSaved.getRole().getLabel();
      this.logService.save(logAction, null, utilisateur.toString(), utilisateur);

        return this.entityMapper.rolePermissionToRolePermissionDto(rolePermissionSaved);
    }


    @Override
    public void delete(RolesPermissionsPK id) {
        try{
            RolesPermissions rp = this.rolesPermissionsRepository.findById(id).orElseThrow(() -> new Exception(String.format("Role permission ", " id ", id)));
            this.rolesPermissionsRepository.delete(rp);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
        }
    }



}
