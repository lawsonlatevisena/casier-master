package tg.ceel.cj.casierapi.services;

import org.springframework.http.ResponseEntity;
import tg.ceel.cj.casierapi.dto.UserB2Dto;
import tg.ceel.cj.casierapi.dto.UserDto;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.models.DashboardModel;
import tg.ceel.cj.casierapi.models.Demandeur;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    List<UserB2Dto> findAllUserB2();

    UserDto save(UserDto dto);

    UserDto findById(Long id);
    UserB2Dto findUserB2ById(Long id);

    UserDto findByUsername(String username);

    User getCurrentUser();

    UserDto update(UserDto dto, String code);

    DashboardModel initDashbord();

    List<UserDto> findAllConnectes();

    UserDto update(Long id, UserDto UserDto);

    Boolean existsByLogin(String login);

    UserDto changePassword(UserDto UserDto);

    String getPassword();

    UserDto activer(Long id);

    UserDto desactiver(Long id, UserDto userDto);

    List<UserDto> resetPassword(List<UserDto> list);

    void logout();

    UserDto initPassword(UserDto userDto);

    UserDto changeInitialPassword(UserDto userDto);

    ResponseEntity <?> demanderCompte(Demandeur demandeur);

    UserB2Dto rejeter(Long id);
}
