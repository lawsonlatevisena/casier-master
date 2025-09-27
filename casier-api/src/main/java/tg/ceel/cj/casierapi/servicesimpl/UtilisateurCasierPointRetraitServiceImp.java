package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.UtilisateurCasierPointRetraitDto;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.entities.UtilisateurCasierPointRetrait;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.PointRetraitRepository;
import tg.ceel.cj.casierapi.repositories.UserRepository;
import tg.ceel.cj.casierapi.repositories.UtilisateurCasierPointRetraitRepository;
import tg.ceel.cj.casierapi.repositories.UtilisateurCasierRepository;
import tg.ceel.cj.casierapi.services.UtilisateurCasierPointRetraitService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UtilisateurCasierPointRetraitServiceImp implements UtilisateurCasierPointRetraitService {

    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(PersonneInfoServiceImpl.class);
    private final UtilisateurCasierPointRetraitRepository utilisateurCasierPointRetraitRepository;
    private final EntityMapper entityMapper;
    private final PointRetraitRepository pointRetraitRepository;

    private final UtilisateurCasierRepository utilisateurCasierRepository;

    public UtilisateurCasierPointRetraitServiceImp(UserRepository userRepository, UtilisateurCasierPointRetraitRepository utilisateurCasierPointRetraitRepository, EntityMapper entityMapper, PointRetraitRepository pointRetraitRepository, UtilisateurCasierRepository utilisateurCasierRepository) {
        this.userRepository = userRepository;
        this.utilisateurCasierPointRetraitRepository = utilisateurCasierPointRetraitRepository;
        this.entityMapper = entityMapper;
        this.pointRetraitRepository = pointRetraitRepository;
        this.utilisateurCasierRepository = utilisateurCasierRepository;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userRepository.findByUsername(auth.getName());
        return authUser;
    }


    @Override
    public List<UtilisateurCasierPointRetraitDto> findAll() {
        List<UtilisateurCasierPointRetrait> list = utilisateurCasierPointRetraitRepository.findAll();
        return list.stream().map(s -> entityMapper.utilisateurCasierPointRetraitToUtilisateurCasierPointRetraitDto(s)).collect(Collectors.toList());
    }


    @Override
    public UtilisateurCasierPointRetraitDto save(UtilisateurCasierPointRetraitDto utilisateurCasierPointRetraitDto) {

        try{

            if (utilisateurCasierPointRetraitDto.getId() != null ){
                UtilisateurCasierPointRetrait oldUtilisateurCasierPointRetrait = this.utilisateurCasierPointRetraitRepository.findById(utilisateurCasierPointRetraitDto.getId()).orElse(null);
                oldUtilisateurCasierPointRetrait.setActive(Boolean.FALSE);
                oldUtilisateurCasierPointRetrait.setDateFin(new Date());
                utilisateurCasierPointRetraitRepository.save(oldUtilisateurCasierPointRetrait);
            }

            UtilisateurCasierPointRetrait utilisateurCasierPointRetrait = new UtilisateurCasierPointRetrait();
            utilisateurCasierPointRetrait.setActive(Boolean.TRUE);
            utilisateurCasierPointRetrait.setUsername(getCurrentUser().getUsername());
            utilisateurCasierPointRetrait.setUserNomPrenoms(utilisateurCasierPointRetraitDto.getUserNomPrenoms());
            utilisateurCasierPointRetrait.setDateAjout(new Date());
            utilisateurCasierPointRetrait.setDateFin(null);
            utilisateurCasierPointRetrait.setPointRetrait(this.pointRetraitRepository.findById(utilisateurCasierPointRetraitDto.getPointRetraitId()).orElse(null));
            utilisateurCasierPointRetrait.setUtilisateurCasier(this.utilisateurCasierRepository.findById(utilisateurCasierPointRetraitDto.getUtilisateurCasierCode()).orElse(null));
            UtilisateurCasierPointRetrait utilisateurCasierPointRetraitSaved = this.utilisateurCasierPointRetraitRepository.save(utilisateurCasierPointRetrait);
            return  this.entityMapper.utilisateurCasierPointRetraitToUtilisateurCasierPointRetraitDto(utilisateurCasierPointRetraitSaved);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }


    }

    @Override
    public UtilisateurCasierPointRetraitDto findActive(Long id) {
     UtilisateurCasierPointRetrait rep  = this.utilisateurCasierPointRetraitRepository.findActiveByIdUtilisateurCasier(id);
        return this.entityMapper.utilisateurCasierPointRetraitToUtilisateurCasierPointRetraitDto(rep);
    }

    @Override
    public List<UtilisateurCasierPointRetraitDto> findAllByUserId(Long id) {
        List<UtilisateurCasierPointRetrait> list = utilisateurCasierPointRetraitRepository.findAllByIdUtilisateurCasier(id);
        return list.stream().map(s -> entityMapper.utilisateurCasierPointRetraitToUtilisateurCasierPointRetraitDto(s)).collect(Collectors.toList());
    }


    @Override
    public List<UtilisateurCasierPointRetraitDto> findAllNonActive() {
        List<UtilisateurCasierPointRetrait> list = this.utilisateurCasierPointRetraitRepository.findAllNonActive();
        return list.stream().map(s -> this.entityMapper.utilisateurCasierPointRetraitToUtilisateurCasierPointRetraitDto(s)).collect(Collectors.toList());
    }


}
