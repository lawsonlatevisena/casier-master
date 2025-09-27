package tg.ceel.cj.casierapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.models.ResponseObject;
import tg.ceel.cj.casierapi.repositories.*;
import tg.ceel.cj.casierapi.utils.Menu;
import tg.ceel.cj.casierapi.utils.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService implements UserDetailsService {
    private final UtilisateurCasierRepository utilisateurRepository;
    private final UserRepository userRepository;
    private final PersonneInfoRepository personneInfoRepository;
    private final EntityMapper entityMapper;
    private final RoleRepository roleRepository;

    private final MenuItemRepository menuItemRepository;
    private final RolesPermissionsRepository rolesPermissionsRepository;
    private final UtilisateurCasierPointRetraitRepository utilisateurCasierPointRetraitRepository;

    public AccountService(UtilisateurCasierRepository utilisateurRepository, UserRepository userRepository, PersonneInfoRepository personneInfoRepository, EntityMapper entityMapper, RoleRepository roleRepository, MenuItemRepository menuItemRepository, RolesPermissionsRepository rolesPermissionsRepository, UtilisateurCasierPointRetraitRepository utilisateurCasierPointRetraitRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.userRepository = userRepository;
        this.personneInfoRepository = personneInfoRepository;
        this.entityMapper = entityMapper;
        this.roleRepository = roleRepository;

        this.menuItemRepository = menuItemRepository;
        this.rolesPermissionsRepository = rolesPermissionsRepository;
        this.utilisateurCasierPointRetraitRepository = utilisateurCasierPointRetraitRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UtilisateurCasier dto = utilisateurRepository.getFromUsername(username).orElse(null);
        System.err.println("dto" + dto);
        if (dto == null) {
            throw new UsernameNotFoundException("L'utilisateur n'existe pas");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        User user = new User(dto.getPersonneInfo().getUser().getUsername(), dto.getPersonneInfo().getUser().getPassword(), grantedAuthorities);

        return user;
    }


    public void initUserATD() {
        UtilisateurCasier dto = utilisateurRepository.getFromUsername("ATD").orElse(null);
         if (dto == null) {
        try {
            System.err.println("****************************************************");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.JUDICIAIREeyJlbWFpbCI6Im1qbGFwaUB4Zmxvdy5nb3V2LnRnIiwiZXhwIjoxNzExNTQ3NzMwLCJpc3MiOiJ4RmxvdyIsImF1ZCI6IklTU1VFUiIsImlhdCI6MTcxMTQ2MTMzMH0.ppvnvywGGiUR-_QUHbp0cEJPdZ87reo-8GEcscJKlr0CASIER";
            tg.ceel.cj.casierapi.entities.User user = new tg.ceel.cj.casierapi.entities.User();
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(password));
            user.setUsername("ATD");
            user.setChangePassword(true);
            user = userRepository.save(user);
            PersonneInfo personneInfo = new PersonneInfo();

            personneInfo.setUser(user);
            personneInfo.setEmail("atd@justice.gouv.tg");
            personneInfo.setNom("AGENCE TOGO DIGITAL");
            personneInfo.setPrenom("ATD");
            personneInfo = personneInfoRepository.save(personneInfo);
            dto = new UtilisateurCasier();
            dto.setPersonneInfo(personneInfo);
            dto.setIsPosteUser(false);
            utilisateurRepository.save(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        }
    }

    public void initDefaultUser() {
        UtilisateurCasier dto = utilisateurRepository.getFromUsername("dev").orElse(null);
        if (dto == null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = "dev";
            tg.ceel.cj.casierapi.entities.User user = new tg.ceel.cj.casierapi.entities.User();
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(password));
            user.setChangePassword(true);
            user.setUsername("dev");
            user = userRepository.save(user);
            PersonneInfo personneInfo = new PersonneInfo();
            personneInfo.setUser(user);
            personneInfo.setEmail("casier@justice.gouv.tg");
            personneInfo.setNom("dev");
            personneInfo.setPrenom("dev");
            personneInfo = personneInfoRepository.save(personneInfo);
            dto = new UtilisateurCasier();
            dto.setPersonneInfo(personneInfo);
            dto.setIsPosteUser(false);
            utilisateurRepository.save(dto);

        }
    }


    public UtilisateurModel initCurrentCompteModel(UtilisateurCasier utilisateurCasier) {
        UtilisateurModel utilisateurModel = new UtilisateurModel();
        utilisateurModel.setCode(utilisateurCasier.getCode());
        PersonneInfo personneInfo = utilisateurCasier.getPersonneInfo();
        utilisateurModel.setPersonneInfoNom(personneInfo.getNom());
        utilisateurModel.setPersonneInfoPrenom(personneInfo.getPrenom());
        utilisateurModel.setPersonneInfoEmail(personneInfo.getEmail());
        Fonction fonction = utilisateurCasier.getFonction();
        System.err.println(utilisateurCasier.getCode());
        UtilisateurCasierPointRetrait pointRetraitDefault = utilisateurCasierPointRetraitRepository.findActiveByIdUtilisateurCasier(utilisateurCasier.getCode());

        if (pointRetraitDefault != null) {
            utilisateurModel.setPointRetrait(entityMapper.pointRetraitToPointRetraitDto(pointRetraitDefault.getPointRetrait()));
        }
        /*     PointRetrait pointRetrait = utilisateurCasier.getPointRetrait();*/
        ServiceDemandeurB2 serviceDemandeurB2 = utilisateurCasier.getServiceDemandeurB2();
        utilisateurModel.setServiceDemandeurB2(entityMapper.serviceDemendeurB2ToServiceDemendeurB2Dto(serviceDemandeurB2));
//        utilisateurModel.setPointRetrait(entityMapper.pointRetraitToPointRetraitDto(pointRetrait));
        utilisateurModel.setFonction(entityMapper.fontionToFonctionDto(fonction));
      /*  if (personneInfo != null) {
            tg.ceel.cj.casierapi.entities.User user = personneInfo.getUser();
            if (user != null) {
                utilisateurModel.setActive(user.getActive());
                utilisateurModel.setChangePassword(user.getChangePassword());
                utilisateurModel.setDateDerniereDeconnexion(user.getDateDerniereDeconnexion());
                user.setDateDerniereConnexion(user.getDateDerniereConnexion());
                utilisateurModel.setUser(this.entityMapper.userToUserDto(user));
                List<Role> roles = roleRepository.findByUser(user.getId());
                System.err.println(checkIfSupAdmin(roles));
                List<Menu> menus = new ArrayList<>();
                if (roles != null && !roles.isEmpty()) {
                    utilisateurModel.setRoles(roles.stream().map(role -> entityMapper.roleToRoleDto(role)).collect(Collectors.toList()));
                    Menu menu;
                    for (Role role : roles) {
                      //  System.err.println(role.getPermissions());
                        menu = new Menu();
                      //  System.err.println(getMains(role.getPermissions()));
                        Set<String> mainMenus = getMains(role.getPermissions());
                        int id = 0;
                        for (String s : mainMenus) {

                            MenuItem main=null;
                            List<MenuItem> mains=null;
                            id++;
                            System.out.println("s="+ s);
                            if(s.equals("*") || s.equals("security") ){
                                main = menuItemRepository.findByCode("admin");
                                mains= menuItemRepository.findByParentItemCode("admin");

                            }
                            else {
                                System.out.println("s");
                                System.out.println(s);
                                main = menuItemRepository.findByCode(s);
                                mains= menuItemRepository.findByParentItemCode(s);

                            }


                            menu = buildeMenu(main, mains, id);

                          //  System.err.println(menu);

                        }
                        menus.add(menu);
                    }
                    utilisateurModel.setMenu(menus.toArray(new Menu[menus.size()]));

                }
            }
        }*/
        if (personneInfo != null) {
            tg.ceel.cj.casierapi.entities.User user = personneInfo.getUser();
            if (user != null) {
                utilisateurModel.setActive(user.getActive());
                utilisateurModel.setChangePassword(user.getChangePassword());
                //utilisateurModel.setDateDerniereDeconnexion(user.getDateDerniereDeconnexion());
                utilisateurModel.setDateDerniereConnexion(user.getDateDerniereConnexion());
                utilisateurModel.setUsername(user.getUsername());
                utilisateurModel.setIsPasswordSet(user.getIsPasswordSet());

                List<Role> roles = roleRepository.findByUser(user.getId());

                if (roles != null && !roles.isEmpty()) {
                    utilisateurModel.setRoles(roles.stream().map(role -> entityMapper.roleToRoleDto(role)).collect(Collectors.toList()));
                    List<String> permissions = new ArrayList<>();
                    for (Role role : roles) {
                        List<String> courants = new ArrayList<>();
                        courants = role.getPermissions().stream().map(p -> p.getCode()).collect(Collectors.toList());
                        if (courants != null || !courants.isEmpty()) {
                            permissions.addAll(courants);
                        }


                    }
                    utilisateurModel.setPermissions(permissions);
                }
            }
        }
        ///  List<UtilisateurCasierPointRetrait> list =utilisateurCasierPointRetraitRepository.findActiveByIdUtilisateurCasier(utilisateurCasier.getCode()) ;
//utilisateurModel.setPointRetraits();
        utilisateurModel.setDateJour(CasierUtils.addDays(new Date(), 0));
        utilisateurModel.setDateDebut(CasierUtils.addDays(new Date(), -30));
        utilisateurModel.setDateFin(CasierUtils.addDays(new Date(), 3));
        System.out.println("utilisateurModel=" + utilisateurModel.getPermissions());
        return utilisateurModel;
    }

    public Boolean checkIfSupAdmin(UtilisateurCasier utilisateurCasier) {
        PersonneInfo personneInfo = utilisateurCasier.getPersonneInfo();
        tg.ceel.cj.casierapi.entities.User user = personneInfo.getUser();
        List<Role> roles = roleRepository.findByUser(user.getId());
        List<String> permissions = new ArrayList<>();
        if (roles != null && !roles.isEmpty()) {

            for (Role role : roles) {
                //   permissions = this.rolesPermissionsRepository.findAllPermissionByRole(role.getId());
            }
        }
        Long nobre = permissions.stream().filter(s -> s.equalsIgnoreCase("administration:*")).count();
        return nobre > 0;
    }

    public Set<String> getMains(List<Permission> mains) {
        Set<String> items = new HashSet<>();

        for (Permission menuItem : mains) {
            if (menuItem.getCode().equals("*")) {
                items.add(menuItem.getCode());
            } else {
                String[] strings = menuItem.getCode().split(":");
                items.add(strings[0]);

            }
        }
        return items;
    }

    public Menu buildeMenu(MenuItem main, List<MenuItem> items, Integer id) {
        Menu menu = new Menu();
        menu.setIcon(main.getIconClass());
        menu.setLabel(main.getLabel());
        menu.setLink("#");

        List<Menu> submenus = items.stream().map(menuItem -> Menu.builder()
                .id(id + "." + (items.indexOf(menuItem) + 1))
                .label(menuItem.getLabel())
                .link(menuItem.getItemPath())
                .parentId(id + "")
                .build()).collect(Collectors.toList());
        menu.setSubItems(submenus.toArray(new Menu[submenus.size()]));
        return menu;
    }

    public String genererToken(User user, String requestURL, Algorithm algorithm) {
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_MILLISECONDS * 1000))
                .withIssuer(requestURL)
                .withClaim("roles", user.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);
        return jwtAccessToken;
    }

    public Authentication loginFromRequest(String username, String password, AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        System.err.println("fffffffffffffffffff");
        System.err.println("ddddddddd " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    public ResponseObject seConnecter(String username, String password) {
        ResponseObject response = new ResponseObject();
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UtilisateurCasier dto = utilisateurRepository.getFromUsername(username).orElse(null);
            if (dto == null) {
                response.setResponseCode(ResponseCode.LOGIN_ERROR);
                response.setDescription("Authentification échouée, nom d'utilisateur ou mot de passe erroné !");
                return response;
            }
            if (!passwordEncoder.matches(password, dto.getPersonneInfo().getUser().getPassword())) {
                response.setResponseCode(ResponseCode.LOGIN_ERROR);
                response.setDescription("Authentification échouée, nom d'utilisateur ou mot de passe erroné !");
                return response;
            }
            if (dto.getServiceDemandeurB2() == null) {
                response.setResponseCode(ResponseCode.LOGIN_ERROR);
                response.setDescription("Authentification échouée, "
                        + "l'utilisateur n'est pas autorisé, il n'appartient à aucun service demandeur !");
                response.setUtilisateurCasier(entityMapper.utilisateurCasierToUtilisateurCasierDto(dto));
                return response;
            }
            response.setResponseCode(ResponseCode.SUCCESS);
            response.setDescription("Authentification réussie !");
            response.setUtilisateurCasier(entityMapper.utilisateurCasierToUtilisateurCasierDto(dto));
            return response;
        } catch (NullPointerException ne) {
            response.setResponseCode(ResponseCode.LOGIN_ERROR);
            response.setDescription("Authentification échouée, "
                    + "l'utilisateur n'est pas autorisé, il n'appartient à aucun service demandeur !");
            return response;
        } catch (Exception e) {
            response.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR);
            response.setDescription("Authentification échouée, réessayer ultérieurement !");
            return response;
        }

    }

    public void initPassword(){
        try {
List<UtilisateurCasier> list = utilisateurRepository.loadAllUserFromResetPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //System.err.println(list);
            if (list!=null ){

                list.forEach( u -> {
                   // System.err.println(u);
                    tg.ceel.cj.casierapi.entities.User user = userRepository.findByUsername(u.getPersonneInfo().getUser().getUsername());
                    if (user != null){
                        user.setChangePassword(false);
                        user.setIsPasswordSet(false);
                        user.setPassword(passwordEncoder.encode("password@sncja"));
                        userRepository.save(user);
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
