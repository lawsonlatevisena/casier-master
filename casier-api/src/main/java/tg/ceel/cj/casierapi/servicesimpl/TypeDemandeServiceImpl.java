package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.TypeDemandeDto;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.Role;
import tg.ceel.cj.casierapi.entities.TypeDemande;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.TypeDemandeRepository;
import tg.ceel.cj.casierapi.services.TypeDemandeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeDemandeServiceImpl implements TypeDemandeService {
    private final TypeDemandeRepository typeDemandeRepository;
    private final EntityMapper entityMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public TypeDemandeServiceImpl(TypeDemandeRepository typeDemandeRepository, EntityMapper entityMapper) {
        this.typeDemandeRepository = typeDemandeRepository;
        this.entityMapper = entityMapper;
    }


    @Override
    public List<TypeDemandeDto> findAll() {
        try{
            List<TypeDemande> list = typeDemandeRepository.findAll();
            return list.stream().map(p -> entityMapper.typeDemandeToTypeDemandeDto(p)).collect(Collectors.toList());
        }catch ( Exception e){
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public TypeDemandeDto getOne(String libelle) {
        TypeDemande typeDemande = typeDemandeRepository.findByLibelle(libelle).orElse(null);
        return entityMapper.typeDemandeToTypeDemandeDto(typeDemande);
    }
    @Override
    public TypeDemandeDto findByCode(String code) {
        TypeDemande typeDemande = typeDemandeRepository.findByCode(code);
        return entityMapper.typeDemandeToTypeDemandeDto(typeDemande);
    }
    @Override
    public TypeDemandeDto findForDemande(Demande demande) {
        return null;
    }
}
