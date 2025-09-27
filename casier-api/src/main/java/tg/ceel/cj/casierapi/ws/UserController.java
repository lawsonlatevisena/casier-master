package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.UserB2Dto;
import tg.ceel.cj.casierapi.dto.UserDto;
import tg.ceel.cj.casierapi.models.Demandeur;
import tg.ceel.cj.casierapi.models.RequestObject;
import tg.ceel.cj.casierapi.security.AccountService;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;

@RestController
@RequestMapping("utilisateurs")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AccountService accountService;

    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> initDashboard() {
        return new ResponseEntity<>(this.userService.initDashbord(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(this.userService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/userB2")
    public ResponseEntity<?> findAllUserB2() {
        return new ResponseEntity<>(this.userService.findAllUserB2(), HttpStatus.OK);
    }

    @GetMapping("/connectes")
    public ResponseEntity<?> findAllConnectes() {
        return new ResponseEntity<>(this.userService.findAllConnectes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        UserDto user = this.userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/userB2/{id}")
    public ResponseEntity<?> findUserB2ById(@PathVariable("id") Long id) {
        UserB2Dto user = this.userService.findUserB2ById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/generate-password")
    public ResponseEntity<?> generatePassword() {
        String mdp = this.userService.getPassword();
        UserDto userDto = new UserDto();
        userDto.setPassword(mdp);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/deconnexion")
    public ResponseEntity<?> logout() {
        this.userService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        if (userDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        } else if (this.userService.existsByLogin(userDto.getUsername())) {
            return new ResponseEntity<>("L'identifiant existe déjà", HttpStatus.CONFLICT);
        }
        UserDto createdUser = this.userService.save(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestObject user) {

        return new ResponseEntity<>(this.accountService.seConnecter(user.getUsername(), user.getPassword()), HttpStatus.OK);

    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto userDto,
                                    @PathVariable("id") Long id) {
        if (userDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        UserDto updatedUser = this.userService.update(id, userDto);
        return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("activer/{id}")
    public ResponseEntity<?> activer(
            @PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        UserDto updatedUser = this.userService.activer(id);
        return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
    }
    @GetMapping("rejeter/{id}")
    public ResponseEntity<?> rejeter(
            @PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        UserB2Dto updatedUser = this.userService.rejeter(id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("desactiver/{id}")
    public ResponseEntity<?> desactiver(@RequestBody UserDto userDto,
                                        @PathVariable("id") Long id) {
        if (userDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        UserDto updatedUtilisateur = this.userService.desactiver(id, userDto);
        return new ResponseEntity<UserDto>(updatedUtilisateur, HttpStatus.OK);
    }

    @PutMapping("init-password")
    public ResponseEntity<?> initPassword(@RequestBody UserDto userDto) {
        if (userDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        UserDto user = this.userService.initPassword(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserDto userDto) {
        if (userDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        UserDto user = this.userService.changePassword(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody List<UserDto> list) {
        if (list == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        List<UserDto> userDtos = this.userService.resetPassword(list);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PostMapping("change-initial-password")
    public ResponseEntity<?> changeInitialPassword(@RequestBody UserDto userDto ) {
        if (userDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        UserDto user = this.userService.changeInitialPassword(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



    @PostMapping("demande/certification")
    public ResponseEntity<?> demandeCompte(@RequestBody Demandeur demandeur) {
        try {
            return this.userService.demanderCompte(demandeur);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
