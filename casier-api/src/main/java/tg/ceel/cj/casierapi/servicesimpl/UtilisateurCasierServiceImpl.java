package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.UtilisateurCasierDto;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.*;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.RoleService;
import tg.ceel.cj.casierapi.services.UtilisateurCasierPointRetraitService;
import tg.ceel.cj.casierapi.services.UtilisateurCasierService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurCasierServiceImpl implements UtilisateurCasierService {
    Logger logger = LoggerFactory.getLogger(UtilisateurCasierServiceImpl.class);
    private final UserRepository userRepository;
    private final UtilisateurCasierRepository utilisateurCasierRepository;
    private final PersonneInfoRepository personneInfoRepository;

    private final SexeRepository sexeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final FonctionRepository fonctionRepository;
    private final ServiceDemandeurB2Repository serviceDemandeurB2Repository;

    private final PointRetraitRepository pointRetraitRepository;
    private final EntityMapper entityMapper;

    private final RoleService roleService;
    private final UtilisateurCasierPointRetraitRepository utilisateurCasierPointRetraitRepository;

    private final LogService logService;

    public UtilisateurCasierServiceImpl(UserRepository userRepository, UtilisateurCasierRepository utilisateurCasierRepository, PersonneInfoRepository personneInfoRepository, SexeRepository sexeRepository, BCryptPasswordEncoder bCryptPasswordEncoder, FonctionRepository fonctionRepository, ServiceDemandeurB2Repository serviceDemandeurB2Repository, PointRetraitRepository pointRetraitRepository, EntityMapper entityMapper, RoleService roleService, UtilisateurCasierPointRetraitService utilisateurCasierPointRetraitService, UtilisateurCasierPointRetraitRepository utilisateurCasierPointRetraitRepository, LogService logService) {
        this.userRepository = userRepository;
        this.utilisateurCasierRepository = utilisateurCasierRepository;
        this.personneInfoRepository = personneInfoRepository;
        this.sexeRepository = sexeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fonctionRepository = fonctionRepository;
        this.serviceDemandeurB2Repository = serviceDemandeurB2Repository;
        this.pointRetraitRepository = pointRetraitRepository;
        this.entityMapper = entityMapper;
        this.roleService = roleService;
        this.utilisateurCasierPointRetraitRepository = utilisateurCasierPointRetraitRepository;
        this.logService = logService;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userRepository.findByUsername(auth.getName());
        return authUser;
    }

    @Override
    public List<UtilisateurCasierDto> findAll() {
        List<UtilisateurCasier> list = utilisateurCasierRepository.findAll();
        return list.stream().map(s -> entityMapper.utilisateurCasierToUtilisateurCasierDto(s)).collect(Collectors.toList());
    }

    @Override
    public UtilisateurCasierDto findById(Long code) {
        try {
            UtilisateurCasier utilisateurCasier = this.utilisateurCasierRepository.findById(code).orElseThrow(() -> new Exception(String.format("Utilisateur casier non trouvé")));
            return this.entityMapper.utilisateurCasierToUtilisateurCasierDto(utilisateurCasier);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public UtilisateurCasierDto save(UtilisateurCasierDto dto) {
        try {
            UtilisateurCasier utilisateurCasier = entityMapper.utilisateurCasierDtoToUtilisateurCasier(dto);
            utilisateurCasier.getPersonneInfo().setSexe(this.sexeRepository.findById(dto.getPersonneInfo().getSexeCode()).orElse(null));
            utilisateurCasier.getPersonneInfo().getUser().setActive(dto.getPersonneInfo().getUser().getActive());
            utilisateurCasier.getPersonneInfo().getUser().setPassword(bCryptPasswordEncoder.encode(dto.getPersonneInfo().getUser().getPassword()));
            utilisateurCasier.getPersonneInfo().getUser().setChangePassword(Boolean.FALSE);
            utilisateurCasier.setCreatedBy(getCurrentUser().getId());
            if (dto.getFonctionId() != null) {
                utilisateurCasier.setFonction(this.fonctionRepository.findById(dto.getFonctionId()).orElse(null));
            }

            if (dto.getServiceDemandeurB2Id() != null) {
                utilisateurCasier.setServiceDemandeurB2(this.serviceDemandeurB2Repository.findById(dto.getServiceDemandeurB2Id()).orElse(null));

            }
            UtilisateurCasier utilisateurCasierSaved = utilisateurCasierRepository.save(utilisateurCasier);
            dto.getPersonneInfo().getUser().getRoles().forEach(r -> {
                UserRolePK userRolePK = new UserRolePK();
                userRolePK.setRoleId(r.getId());
                userRolePK.setUserId(utilisateurCasierSaved.getPersonneInfo().getUser().getId());

                UsersRoles userRole = new UsersRoles();
                userRole.setId(userRolePK);
                userRole.setUser(utilisateurCasierSaved.getPersonneInfo().getUser());
                userRole.setRole(this.entityMapper.roleDtoToRole(r));
                this.roleService.saveUserRole(userRole);
            });
      /*      if ( dto.getPointRetraits() != null) {
                dto.getPointRetraits().forEach( el -> {
                    UtilisateurCasierPointRetrait utilisateurCasierPointRetrait = new UtilisateurCasierPointRetrait();
                    utilisateurCasierPointRetrait.setPointRetrait(this.entityMapper.pointRetraitDtoToPointRetrait(el));
                    utilisateurCasierPointRetrait.setActive(Boolean.TRUE);
                    utilisateurCasierPointRetrait.setUsername(getCurrentUser().getUsername());
                    utilisateurCasierPointRetrait.setDateAjout(new Date());
                    utilisateurCasierPointRetrait.setDateFin(null);
                    utilisateurCasierPointRetrait.setUtilisateurCasier(utilisateurCasierSaved);
                    this.utilisateurCasierPointRetraitRepository.save(utilisateurCasierPointRetrait);
                });
            }*/
            return entityMapper.utilisateurCasierToUtilisateurCasierDto(utilisateurCasierSaved);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }


    @Override
    public UtilisateurCasierDto update(Long code, UtilisateurCasierDto dto) {
        UtilisateurCasier utilisateurCasier = null;
        try {
            utilisateurCasier = this.utilisateurCasierRepository.findById(code).orElseThrow(() ->
                    new Exception(String.format("Aucun casier utilisateur trouvé avec le code %s", code)));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
        if (utilisateurCasier != null) {
            try {
                utilisateurCasier.getPersonneInfo().setNom(dto.getPersonneInfo().getNom());
                utilisateurCasier.getPersonneInfo().setPrenom(dto.getPersonneInfo().getPrenom());
                utilisateurCasier.getPersonneInfo().setEmail(dto.getPersonneInfo().getEmail());
                utilisateurCasier.getPersonneInfo().setTelephone(dto.getPersonneInfo().getTelephone());
                utilisateurCasier.getPersonneInfo().setLocalite(dto.getPersonneInfo().getLocalite());
                if (dto.getPersonneInfo().getSexeCode() != null){
                    utilisateurCasier.getPersonneInfo().setSexe(this.sexeRepository.findById(dto.getPersonneInfo().getSexeCode()).orElse(null));
                }
                utilisateurCasier.getPersonneInfo().getUser().setActive(dto.getPersonneInfo().getUser().getActive());
                utilisateurCasier.setCreatedBy(getCurrentUser().getId());
                if (dto.getFonctionId() != null) {
                    utilisateurCasier.setFonction(this.fonctionRepository.findById(dto.getFonctionId()).orElse(null));
                }
                if (dto.getServiceDemandeurB2Id() != null) {
                    utilisateurCasier.setServiceDemandeurB2(this.serviceDemandeurB2Repository.findById(dto.getServiceDemandeurB2Id()).orElse(null));

                }
                UtilisateurCasier utilisateurCasierUpdate = utilisateurCasierRepository.save(utilisateurCasier);
                dto.getPersonneInfo().getUser().getRoles().forEach(r -> {
                    UserRolePK userRolePK = new UserRolePK();
                    userRolePK.setRoleId(r.getId());
                    userRolePK.setUserId(utilisateurCasierUpdate.getPersonneInfo().getUser().getId());

                    UsersRoles userRole = new UsersRoles();
                    userRole.setId(userRolePK);
                    userRole.setUser(utilisateurCasierUpdate.getPersonneInfo().getUser());
                    userRole.setRole(this.entityMapper.roleDtoToRole(r));
                    this.roleService.saveUserRole(userRole);
                });
                return entityMapper.utilisateurCasierToUtilisateurCasierDto(utilisateurCasierUpdate);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Erreur interne", e);
                return null;
            }
        }
        return null;
    }

    @Override
    public UtilisateurCasierDto changerPassword(UtilisateurCasierDto dto) {
        UtilisateurCasier utilisateurCasier = null;
        try {
            utilisateurCasier = this.utilisateurCasierRepository.getFromUsername(dto.getPersonneInfo().getUser().getUsername()).orElseThrow(() ->
                    new Exception(String.format("Aucun casier utilisateur trouvé avec le login %s", dto.getPersonneInfo().getUser().getUsername())));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
        if (utilisateurCasier != null) {
            try {
                utilisateurCasier.getPersonneInfo().getUser().setPassword(bCryptPasswordEncoder.encode(dto.getPersonneInfo().getUser().getPassword()));
                return entityMapper.utilisateurCasierToUtilisateurCasierDto(utilisateurCasierRepository.save(utilisateurCasier));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Erreur interne", e);
                return null;
            }
        }
        return null;
    }

    @Override
    public Boolean existsByPersonInfoNom(String nom) {
        return null;
    }

    @Override
    public Boolean existsByPersonInfoPrenom(String prenom) {
        return null;
    }

    @Override
    public ResponseEntity initPointRetrait() {
        try {
            utilisateurCasierRepository.findAll().forEach(utilisateurCasier -> {
                if (utilisateurCasier.getCodeJuridiction() != null) {
                    PointRetrait p = pointRetraitRepository.findById(utilisateurCasier.getCodeJuridiction()).orElse(null);
                    if (p != null) {
                        List<UtilisateurCasierPointRetrait> list=utilisateurCasierPointRetraitRepository.findByPointRetraitEqualsAndUtilisateurCasierEquals(p,utilisateurCasier);
                       if (list==null || list.isEmpty()) {
                           UtilisateurCasierPointRetrait utilisateurCasierPointRetrait = new UtilisateurCasierPointRetrait();
                           utilisateurCasierPointRetrait.setPointRetrait(p);
                           utilisateurCasierPointRetrait.setUtilisateurCasier(utilisateurCasier);
                           utilisateurCasierPointRetrait.setActive(true);
                           utilisateurCasierPointRetrait.setDateAjout(new Date());
                           utilisateurCasierPointRetraitRepository.save(utilisateurCasierPointRetrait);
                       }
                    }
                }
            });
            return new ResponseEntity("Initialisation des points retraits effectués avec succès", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }


}
