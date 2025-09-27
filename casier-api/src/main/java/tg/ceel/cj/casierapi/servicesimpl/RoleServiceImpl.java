package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.RoleDto;
import tg.ceel.cj.casierapi.dto.UsersRolesDto;
import tg.ceel.cj.casierapi.entities.Role;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.entities.UsersRoles;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.RoleRepository;
import tg.ceel.cj.casierapi.repositories.UsersRolesRepository;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.RoleService;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final RoleRepository roleRepository;
    private final UsersRolesRepository usersRolesRepository;
    private final EntityMapper entityMapper;
    private final UserService userService;

    private final LogService logService;

    public RoleServiceImpl(RoleRepository roleRepository, UsersRolesRepository usersRolesRepository, EntityMapper entityMapper, UserService userService, LogService logService) {
        this.roleRepository = roleRepository;
        this.usersRolesRepository = usersRolesRepository;
        this.entityMapper = entityMapper;
        this.userService = userService;
        this.logService = logService;
    }


    @Override
    public List<RoleDto> findAll() {
        try{
        List<Role> list = roleRepository.findAll();
        return list.stream().map(p -> entityMapper.roleToRoleDto(p)).collect(Collectors.toList());
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }


    @Override
    public List<UsersRolesDto>  findAllByUser(Long userId) {
        try {
            List<UsersRoles> usersRolesDts = this.usersRolesRepository.findAllByUserId(userId);
            return usersRolesDts.stream().map(s -> entityMapper.userRoleToUserRoleDto(s)).collect(Collectors.toList());

        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }


    @Override
    public void delete(Long roleId, Long userId) {
        try{
            UsersRoles usersRoles = this.usersRolesRepository.findByRoleIdAndUserId(roleId, userId);
            usersRolesRepository.delete(usersRoles);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
        }
    }

    @Override
    public List<RoleDto> findAllActifs() {
        List<Role> list = roleRepository.findAllRoles();
        return list.stream().map(p -> entityMapper.roleToRoleDto(p)).collect(Collectors.toList());
    }


    @Override
    public RoleDto save(RoleDto roleDto) {

        try{

            User user = this.userService.getCurrentUser();
            Role role = this.entityMapper.roleDtoToRole(roleDto);
            role.setActive(Boolean.TRUE);
            role.setCreatedBy(user.getId());
            Role roleSaved = this.roleRepository.save(role);
            String logAction = "Ajout d'un nouveau rôle : " + role.getLabel() ;
            this.logService.save(logAction, null, roleSaved.toString(), user);
            return this.entityMapper.roleToRoleDto(role);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }

    }


    @Override
    public RoleDto update(Long id, RoleDto roleDto) {

        try{
            Role role = this.roleRepository.findById(id).orElseThrow(() -> new Exception(String.format("Rolde %s n'est pas trouvé", id)));
            User user = this.userService.getCurrentUser();
            role.setLabel(roleDto.getLabel());
            role.setActive(roleDto.getActive());
            Role roleUpdate = this.roleRepository.save(role);
            String logAction = "Modification du rôle : " + roleDto.getLabel() + " à "  + roleUpdate.getLabel() ;
            this.logService.save(logAction, null, role.toString(), user);
            return this.entityMapper.roleToRoleDto(role);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }

    }


    @Override
    public UsersRolesDto saveUserRole(UsersRoles usersRole) {
       
         try{

             User user = this.userService.getCurrentUser();

             usersRole.setActive(Boolean.TRUE);
             usersRole.setCreatedBy(user.getId());
             UsersRoles usersRoleSaved = this.usersRolesRepository.save(usersRole);
             String logAction = "Ajout d'un nouveau rôle : " + usersRoleSaved.getRole().getLabel() + " à l'utilisateur " + usersRoleSaved.getUser().getUsername();
             this.logService.save(logAction, null, usersRole.toString(), user);

             return this.entityMapper.userRoleToUserRoleDto(usersRoleSaved);
         }catch ( Exception e){
             e.printStackTrace();
             logger.error("Erreur interne", e);
             return null;
         }

    }






}
