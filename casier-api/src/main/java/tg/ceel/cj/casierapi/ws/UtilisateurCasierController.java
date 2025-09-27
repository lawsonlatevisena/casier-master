package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.UserDto;
import tg.ceel.cj.casierapi.dto.UtilisateurCasierDto;
import tg.ceel.cj.casierapi.models.RequestObject;
import tg.ceel.cj.casierapi.models.ResponseObject;
import tg.ceel.cj.casierapi.services.UserService;
import tg.ceel.cj.casierapi.services.UtilisateurCasierService;

@RestController
@RequestMapping("utilisateur-casiers")
public class UtilisateurCasierController {
    Logger logger = LoggerFactory.getLogger(UtilisateurCasierController.class);
    private final UtilisateurCasierService utilisateurCasierService;

    private  final UserService userService;

    public UtilisateurCasierController(UtilisateurCasierService utilisateurCasierService, UserService userService) {
        this.utilisateurCasierService = utilisateurCasierService;
        this.userService = userService;
    }



    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(this.utilisateurCasierService.findAll(), HttpStatus.OK);
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<?> findById(@PathVariable("id")Long id ) {
        return new ResponseEntity<>(this.utilisateurCasierService.findById(id), HttpStatus.OK);
    }



    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody UtilisateurCasierDto utilisateurCasierDto) {
        if (utilisateurCasierDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        } else if (this.userService.existsByLogin(utilisateurCasierDto.getPersonneInfo().getUser().getUsername())) {
            return new ResponseEntity<>("Ce  nom  d'utilisateur existe déjà", HttpStatus.CONFLICT);
        }
        UtilisateurCasierDto createdUser = this.utilisateurCasierService.save(utilisateurCasierDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> update(@RequestBody UtilisateurCasierDto utilisateurCasierDto,
                                    @PathVariable("code") Long code) {
        if (utilisateurCasierDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        }
        UtilisateurCasierDto utilisateurCasierUpdate = this.utilisateurCasierService.update(code, utilisateurCasierDto);
        return new ResponseEntity<UtilisateurCasierDto>(utilisateurCasierUpdate, HttpStatus.OK);
    }

    public ResponseObject login(RequestObject objectRequest) {
        /*ResponseObject response = new ResponseObject();
        UtilisateurCasierDto currentUtilisateur;
        try {
            if (this.userService.login(objectRequest.getUsername(), objectRequest.getPassword())) {
                User user = userService.findByUsername(objectRequest.getUsername());

                currentUtilisateur = this.utilisateurCasierService.getOneWithoutJoin(user);
                if (currentUtilisateur.getServiceDemandeurB2() != null) {
                    response.setResponseCode(ResponseCode.SUCCESS);
                    response.setDescription("Authentification réussie !");
                    response.setUtilisateurCasier(currentUtilisateur);
                } else {
                    response.setResponseCode(ResponseCode.LOGIN_ERROR);
                    response.setDescription("Authentification échouée, "
                            + "l'utilisateur n'est pas autorisé, il n'appartient à aucun service demandeur !");
                    response.setUtilisateurCasier(currentUtilisateur);
                }
            } else {
                response.setResponseCode(ResponseCode.LOGIN_ERROR);
                response.setDescription("Authentification échouée, nom d'utilisateur ou mot de passe erroné !");
            }
            System.out.println("user ");
            return response;
        } catch (BusinessException be) {
            response.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR);
            response.setDescription("Authentification échouée, nom d'utilisateur ou mot de passe erroné !");
            return response;
        } catch (NullPointerException ne) {
            response.setResponseCode(ResponseCode.LOGIN_ERROR);
            response.setDescription("Authentification échouée, "
                    + "l'utilisateur n'est pas autorisé, il n'appartient à aucun service demandeur !");
            return response;
        } catch (Exception e) {
            response.setResponseCode(ResponseCode.UNKNOW_ERROR);
            response.setDescription("Authentification échouée, réessayer ultérieurement !");
            return response;
        }*/
        return null;
    }
    @PostMapping("changer-password")
    public ResponseEntity<?> changePassword(@RequestBody UtilisateurCasierDto utilisateurCasierDto) {
        if (utilisateurCasierDto == null) {
            return new ResponseEntity<>("Données requises manquantes!", HttpStatus.BAD_REQUEST);
        } else if (this.userService.existsByLogin(utilisateurCasierDto.getPersonneInfo().getUser().getUsername())) {
            return new ResponseEntity<>("Ce  nom  d'utilisateur existe déjà", HttpStatus.CONFLICT);
        }
        UtilisateurCasierDto createdUser = this.utilisateurCasierService.save(utilisateurCasierDto);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

}
