package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.AutrePointRetraitDto;
import tg.ceel.cj.casierapi.dto.Point;
import tg.ceel.cj.casierapi.dto.PointRetraitDto;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.ints.IPoste;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.PointRetraitRepository;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.PointRetraitService;
import tg.ceel.cj.casierapi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointRetraitServiceImpl implements PointRetraitService {
    Logger logger = LoggerFactory.getLogger(PointRetraitServiceImpl.class);
    private final PointRetraitRepository pointRetraitRepository;
    private final EntityMapper entityMapper;
    private final UserService userService;

    private final LogService logService;

    public PointRetraitServiceImpl(PointRetraitRepository pointRetraitRepository, EntityMapper entityMapper, UserService userService, LogService logService) {
        this.pointRetraitRepository = pointRetraitRepository;
        this.entityMapper = entityMapper;
        this.userService = userService;
        this.logService = logService;
    }

    @Override
    public List<PointRetraitDto> getAll(TypeDemande typeDemande) {
        List<PointRetrait> list = pointRetraitRepository.getAll(typeDemande.getCode());
        return list.stream().map(entityMapper::pointRetraitToPointRetraitDto).collect(Collectors.toList());
    }

    @Override
    public List<PointRetraitDto> findAll() {
        try{
            List<PointRetrait> list = pointRetraitRepository.findAll();
            return list.stream().map(p -> entityMapper.pointRetraitToPointRetraitDto(p)).collect(Collectors.toList());
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public PointRetraitDto getDemandePointRetrait(Demande demande) {
        return entityMapper.pointRetraitToPointRetraitDto(demande.getPointRetrait());
    }

    @Override
    public List<PointRetraitDto> getAllJuridictions(String typeDemande) {
        List<PointRetrait> list = pointRetraitRepository.getAllJuridictions(TypeAutrePointRetrait.ETATIQUE.toString(),
                typeDemande);
        return list.stream().map(entityMapper::pointRetraitToPointRetraitDto).collect(Collectors.toList());
    }

    @Override
    public List<Point> getAllJuridictionsV1(String typeDemande) {
        return pointRetraitRepository.getAllTribunaux("A",typeDemande).stream()
                .map(pointRetraitInt -> Point
                        .builder()
                        .id(pointRetraitInt.getId())
                        .juridiction_code(pointRetraitInt.getJuridiction_code())
                        .code(pointRetraitInt.getCode())
                        .libelleLong(pointRetraitInt.getLibelleLong())
                        .Libelle_juridiction(pointRetraitInt.getLibelle_juridiction())
                        .localite(pointRetraitInt.getLocalite())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<PointRetraitDto> getAllJuridictions() {
        List<PointRetrait> list = pointRetraitRepository.getAllTribunaux();
        return list.stream().map(entityMapper::pointRetraitToPointRetraitDto).collect(Collectors.toList());
    }
    public List<PointRetraitDto> getAllJuridictionByTypeAutrePointRetrait() {
        List<PointRetrait> list = pointRetraitRepository.getAllJuridictionByTypeAutrePointRetrait(TypeAutrePointRetrait.ETATIQUE.toString());

        return list.stream().map(entityMapper::pointRetraitToPointRetraitDto).collect(Collectors.toList());
    }

    @Override
    public List<PointRetraitDto> getAllPointsRetraits() {
        List<PointRetrait> list = pointRetraitRepository.getAllPointsRetraits();
        return list.stream().map(entityMapper::pointRetraitToPointRetraitDto).collect(Collectors.toList());
    }

        @Override
    public List<PointRetraitDto> getAllANCPointRetraits(TypeDemande typeDemande) {
            List<PointRetrait> list = pointRetraitRepository.getAllANCPointRetraits();
            return list.stream().map(entityMapper::pointRetraitToPointRetraitDto).collect(Collectors.toList());
        }

    @Override
    public List<PointRetraitDto> getAllCasierPointRetraits() {
        List<PointRetrait> list = pointRetraitRepository.getAllCasierPointRetraits();
        return list.stream().map(entityMapper::pointRetraitToPointRetraitDto).collect(Collectors.toList());
    }

    @Override
    public List<AutrePointRetraitDto> getAllBureauxPoste(String typeDemande) {
        List<AutrePointRetrait> list = pointRetraitRepository.getAllBureauxPoste(TypeAutrePointRetrait.LAPOSTE.toString(), typeDemande);
        return list.stream().map(entityMapper::autrePointRetraitToAutrePointRetraitDto).collect(Collectors.toList());
    }

    @Override
    public List<PointRetraitDto> getPosteByIdJuricdiction(Long id) {
        try {
            List<PointRetrait> list;
            PointRetrait pointRetrait = pointRetraitRepository.findById(id).orElseThrow(() -> new Exception(String.format("Aucun point de retrait trouvé avec l'id %s",id)));
            if (!pointRetrait.getId().equals(34L)){
               list = pointRetraitRepository.getAllCPosteByPointRetraitLocalite(pointRetrait.getLocalite());
            }else {
                list = pointRetraitRepository.getAllCPosteByPointRetraitCentreNational();
            }

            return list.stream().map(entityMapper::pointRetraitToPointRetraitDto).collect(Collectors.toList());
        }catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
        }

    @Override
    public PointRetraitDto save(PointRetraitDto pointRetraitDto) {
        try{
            User user = this.userService.getCurrentUser();
            PointRetrait pointRetrait = this.entityMapper.pointRetraitDtoToPointRetrait(pointRetraitDto);
            pointRetrait.setCreatedBy(user.getId());
            PointRetrait pointRetraitSaved = this.pointRetraitRepository.save(pointRetrait);
            String logAction = "Ajout d'un nouveau point de retrait  : " + pointRetraitSaved.getLibelle() ;
            this.logService.save(logAction, null, pointRetraitSaved.toString(), user);
            return this.entityMapper.pointRetraitToPointRetraitDto(pointRetraitSaved);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public PointRetraitDto update(Long id, PointRetraitDto pointRetraitDto) {
        try{
            PointRetrait pointRetrait = this.pointRetraitRepository.findById(id).orElseThrow(() -> new Exception(String.format("Point de retrait %s n'est pas trouvé", id)));
            User user = this.userService.getCurrentUser();
            pointRetrait.setLibelle(pointRetraitDto.getLibelle());
            PointRetrait pointRetraitUpdate = this.pointRetraitRepository.save(pointRetrait);
            String logAction = "Modification de point de retrait : " + pointRetraitDto.getLibelle() + " à "  + pointRetraitUpdate.getLibelle() ;
            this.logService.save(logAction, null, pointRetraitUpdate.toString(), user);
            return this.entityMapper.pointRetraitToPointRetraitDto(pointRetraitUpdate);
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<IPoste> getAllBureauxPoste() {
        return pointRetraitRepository.getAllBureauxIPoste();
    }

}
