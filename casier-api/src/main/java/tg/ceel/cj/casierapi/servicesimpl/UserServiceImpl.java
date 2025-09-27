package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tg.ceel.cj.casierapi.dto.UserB2Dto;
import tg.ceel.cj.casierapi.dto.UserDto;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.models.DashboardModel;
import tg.ceel.cj.casierapi.models.Demandeur;
import tg.ceel.cj.casierapi.models.FeedbackModel;
import tg.ceel.cj.casierapi.models.ModelData;
import tg.ceel.cj.casierapi.repositories.*;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.ServiceEnvoyeur;
import tg.ceel.cj.casierapi.services.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EntityMapper entityMapper;
    private final EntiteDemandeurB2Repository entiteDemandeurB2Repository;
    private final ServiceDemandeurB2Repository serviceDemandeurB2Repository;
    private final CategorieDemandeurB2Repository categorieDemandeurB2Repository;
    private final UserB2Repository userB2Repository;
    private final SexeRepository sexeRepository;
    private final PersonneInfoRepository personneInfoRepository;
    private final UtilisateurCasierRepository utilisateurCasierRepository;
    private final ServiceEnvoyeur serviceEnvoyeur;
    private final LogService logService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EntityMapper entityMapper, EntiteDemandeurB2Repository entiteDemandeurB2Repository, ServiceDemandeurB2Repository serviceDemandeurB2Repository, CategorieDemandeurB2Repository categorieDemandeurB2Repository, UserB2Repository userB2Repository, SexeRepository sexeRepository, PersonneInfoRepository personneInfoRepository, UtilisateurCasierRepository utilisateurCasierRepository, ServiceEnvoyeur serviceEnvoyeur, LogService logService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.entityMapper = entityMapper;
        this.entiteDemandeurB2Repository = entiteDemandeurB2Repository;
        this.serviceDemandeurB2Repository = serviceDemandeurB2Repository;
        this.categorieDemandeurB2Repository = categorieDemandeurB2Repository;
        this.userB2Repository = userB2Repository;
        this.sexeRepository = sexeRepository;
        this.personneInfoRepository = personneInfoRepository;
        this.utilisateurCasierRepository = utilisateurCasierRepository;
        this.serviceEnvoyeur = serviceEnvoyeur;
        this.logService = logService;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> list = userRepository.findAll();
        return list.stream().map(s -> entityMapper.userToUserDto(s)).collect(Collectors.toList());
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userRepository.findByUsername(auth.getName());
        return authUser;
    }

    @Override
    public UserDto update(UserDto dto, String code) {
        return null;
    }

    @Override
    public DashboardModel initDashbord() {
        return null;
    }

    @Override
    public List<UserDto> findAllConnectes() {
        List<User> list =  this.userRepository.findAllUtilisateursConnectes();
        return list.stream().map(s -> entityMapper.userToUserDto(s)).collect(Collectors.toList());
    }


    @Override
    public UserDto save(UserDto dto) {
        try {
            User user = entityMapper.userDtoToUser(dto);
            user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            // user.setActive(Boolean.TRUE);
            user.setChangePassword(Boolean.FALSE);
            user.setCreatedBy(getCurrentUser().getId());
            return entityMapper.userToUserDto(userRepository.save(user));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public UserDto findById(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() ->
                    new Exception(String.format("Aucun user n'est trouvé avec le %s", id)));
            return entityMapper.userToUserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public UserDto findByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username);
            return entityMapper.userToUserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        try {
            User user = this.userRepository.findById(id).orElseThrow(() -> new
                    Exception(String.format("Aucun user n'est trouvé avec le %s", id)));
            user.setUsername(userDto.getUsername());
            user.setActive(userDto.getActive());

            User updatedUser = this.userRepository.save(user);
            return entityMapper.userToUserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }


    }

    @Override
    public Boolean existsByLogin(String login) {
        return this.userRepository.existsByUsername(login);
    }

    @Override
    public UserDto changePassword(UserDto userDto) {
        try {
            User user = this.userRepository.findById(userDto.getId()).orElseThrow(() -> new Exception(String.format("Aucun user n'est trouvé avec le %s", userDto.getId())));
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            user.setIsPasswordSet(userDto.getIsPasswordSet());
            user = userRepository.save(user);
            return this.entityMapper.userToUserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public String getPassword() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return generatedString;
    }

    @Override
    @Transactional
    public UserDto activer(Long id) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String username=UUID.randomUUID().toString();
            UserB2 userB2 = userB2Repository.findById(id)
                    .orElseThrow(() -> new Exception(String.format("Aucune demande de compte ne correspond à cet identifiant", id)));
            User user=userRepository.findByUsername(userB2.getLogin());
            UtilisateurCasier utilisateurCasier;
            if (user!=null){
                utilisateurCasier = utilisateurCasierRepository.getFromUsername(user.getUsername()).orElse(null);
                if (utilisateurCasier!=null){
                    notifierAtd(utilisateurCasier, userB2);
                    return entityMapper.userToUserDto(user);
                }
            }
             user = User.builder()
                    .changePassword(false)
                    .isPasswordSet(false)
                    .username(userB2.getLogin())
                    .password(passwordEncoder.encode(username))
                    .active(true)
                    .build();
            user = userRepository.save(user);
            PersonneInfo personneInfo = PersonneInfo.builder()
                    .email(userB2.getEmail())
                    .nom(userB2.getNom())
                    .user(user)
                    .telephone(userB2.getTel())
                    .prenom(userB2.getPrenoms())
                    .sexe(sexeRepository.findById(userB2.getSexe()).orElse(null))
                    .build();
            personneInfo = personneInfoRepository.save(personneInfo);
             utilisateurCasier = UtilisateurCasier.builder()
                    .personneInfo(personneInfo)
                    .serviceDemandeurB2(userB2.getServiceDemandeurB2())
                    .isPosteUser(false)
                    .build();
            utilisateurCasier.setCode_certification(user.getPassword());
            utilisateurCasier = utilisateurCasierRepository.save(utilisateurCasier);
            userB2.setValide(true);
            userB2Repository.save(userB2);
            notifierAtd(utilisateurCasier, userB2);
            return entityMapper.userToUserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    private void notifierAtd(UtilisateurCasier utilisateurCasier, UserB2 userB2) {
        try {
            if (utilisateurCasier == null) {
                return;
            }
            if (utilisateurCasier.getCode_certification() == null) {
                return;
            }
            FeedbackModel feedbackModel = FeedbackModel.builder()
                    .process(userB2.getProcess())
                    .title("Confirmation de certification")
                    .feedbackTaskId(userB2.getFeedbackTaskId())
                    .step(userB2.getStep())
                    .record(userB2.getRecord())
                    .order(userB2.getOrder())
                    .message("Certification pour la demande d'extrait de casier judiciaire B1 et B2")
                    .data(ModelData.builder()
                            .peer("SNCJ")
                            .label("Certification pour la demande d'extrait de casier judiciaire B1 et B2")
                            .slug("sncj")
                            .type("sncj")
                            .token(utilisateurCasier.getCode_certification())
                            .build())
                    .build();
         this.serviceEnvoyeur.envoyerFeadBack(feedbackModel);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
        }
    }
    private void notifierAtd( UserB2 userB2) {
        try {

            FeedbackModel feedbackModel = FeedbackModel.builder()
                    .process(userB2.getProcess())
                    .title("Rejet de certification")
                    .feedbackTaskId(userB2.getFeedbackTaskId())
                    .step(userB2.getStep())
                    .record(userB2.getRecord())
                    .order(userB2.getOrder())
                    .message("Certification du compte pour casier judiciaire rejeté")
                    .data(ModelData.builder()
                            .peer("SNCJ")
                            .label("Rejet de certification pour les utilisateurs du bulletin numéro 1 et 2")
                            .slug("sncj")
                            .type("sncj")
                            .token(null)
                            .build())
                    .build();
            this.serviceEnvoyeur.envoyerFeadBack(feedbackModel);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
        }
    }

    @Override
    public UserDto desactiver(Long id, UserDto userDto) {
        return null;
    }


    @Override
    public List<UserDto> resetPassword(List<UserDto> list) {
        try {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            List<User> users = new ArrayList<>();
            if (list!=null ){

                list.forEach( u -> {
                    User user = userRepository.findByUsername(u.getUsername());
                    if (user != null){
                                user.setChangePassword(false);
                                user.setIsPasswordSet(false);
                                user.setPassword(passwordEncoder.encode("password"));
                                user.setActive(true);
                                user = userRepository.save(user);
                    }
                    users.add(user);

                });
            }
            return users.stream().map(s -> entityMapper.userToUserDto(s)).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public void logout() {
        User user = this.getCurrentUser();
        user.setDateDerniereDeconnexion(new Date());
        userRepository.save(user);
        logService.save("Déconnexion", user);
    }

    @Override
    public UserDto initPassword(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto changeInitialPassword(UserDto userDto) {
        try {
            User user = this.userRepository.findByUsername(userDto.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            user.setIsPasswordSet(Boolean.TRUE);
            user.setChangePassword(true);
            user = userRepository.save(user);
            return this.entityMapper.userToUserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<?> demanderCompte(Demandeur demandeur) {
        try {
            if (demandeur.getEntite_demandeurB2_id() == null && demandeur.getAutre_entite_demandeur()==null ) {
                return new ResponseEntity<>("L'entité d'appartenance de la personne demanderesse est obligatoire", HttpStatus.BAD_REQUEST);
            }
            if (demandeur.getService_demandeurB2_id() == null && demandeur.getAutre_service_demandeur()==null) {
                return new ResponseEntity<>("L'administration d'appartenance de la personne demanderesse est obligatoire", HttpStatus.BAD_REQUEST);
            }
            UserB2 userB2 = UserB2.builder()
                    .autreEntiteDemandeur(demandeur.getAutre_entite_demandeur())
                    .autreCategorieDemandeur(demandeur.getAutre_categorie_demandeur())
                    .cni(demandeur.getCni())
                    .autreServiceDemandeur(demandeur.getAutre_service_demandeur())
                    .dateDemande(new Date())
                    .email(demandeur.getEmail())
                    .contact(demandeur.getContact())
                    .nom(demandeur.getNom())
                    .prenoms(demandeur.getPrenoms())
                    .sexe(demandeur.getSexe())
                    .login(UUID.randomUUID().toString())
                    .password("")
                    .tel(demandeur.getTel())
                    .titre(demandeur.getTitre())
                    .valide(false)
                    .process(demandeur.getProcess())
                    .order(demandeur.getOrder())
                    .feedbackTaskId(demandeur.getFeedbackTaskId())
                    .record(demandeur.getRecord())
                    .step(demandeur.getStep())
                    .build();
           if (demandeur.getEntite_demandeurB2_id()!=null &&  !demandeur.getEntite_demandeurB2_id().equals(0)){
               EntiteDemandeurB2 entiteDemandeurB2 = entiteDemandeurB2Repository.findById(demandeur.getEntite_demandeurB2_id())
                       .orElseThrow(() -> new EntityNotFoundException(String.format("Aucune entité demandeur ne correspond à l'identifiant %s", demandeur.getEntite_demandeurB2_id())));
               userB2.setEntiteDemandeurB2(entiteDemandeurB2);
               if (entiteDemandeurB2.getId() == 2) {
                   userB2.setCategorieDemandeurB2(categorieDemandeurB2Repository.findById(1).get());
               } else {
                   userB2.setCategorieDemandeurB2(categorieDemandeurB2Repository.findById(2).get());
               }
           }
        if (demandeur.getService_demandeurB2_id()!=null && !demandeur.getService_demandeurB2_id().equals(0)){
            ServiceDemandeurB2 serviceDemandeurB2 = serviceDemandeurB2Repository.findById(demandeur.getService_demandeurB2_id())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Aucune administration d'appartenance n'est trouvée avec l'identifiant %s", demandeur.getService_demandeurB2_id())));
            userB2.setServiceDemandeurB2(serviceDemandeurB2);
        }
            userB2Repository.save(userB2);
            return new ResponseEntity<>(demandeur, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Erreur interne " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserB2Dto rejeter(Long id) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String username=UUID.randomUUID().toString();
            UserB2 userB2 = userB2Repository.findById(id)
                    .orElseThrow(() -> new Exception(String.format("Aucune demande de compte ne correspond à cet identifiant", id)));

            notifierAtd(userB2);
            return entityMapper.userB2ToUserB2Dto(userB2);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<UserB2Dto> findAllUserB2() {
        List<UserB2> listB2 = userB2Repository.findAllByValideIsFalse();
        return listB2.stream().map(s -> entityMapper.userB2ToUserB2Dto(s)).collect(Collectors.toList());
    }

    @Override
    public UserB2Dto findUserB2ById(Long id) {
        try {
            return userB2Repository.findById(id).map(entityMapper::userB2ToUserB2Dto)
                    .orElseThrow(() -> new Exception(String.format("Aucune demande de compte ne correspond à cet identifiant", id)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
