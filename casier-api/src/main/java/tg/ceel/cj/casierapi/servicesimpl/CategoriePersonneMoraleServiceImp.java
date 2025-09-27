package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.CategoriePersonneMoraleDto;
import tg.ceel.cj.casierapi.entities.CategoriePersonneMorale;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.CategoriePersonneMoraleRepository;
import tg.ceel.cj.casierapi.services.CategoriePersonneMoraleService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriePersonneMoraleServiceImp implements CategoriePersonneMoraleService {
    Logger logger = LoggerFactory.getLogger(CategoriePersonneMoraleServiceImp.class);
    private final EntityMapper entityMapper;
    private final CategoriePersonneMoraleRepository categoriePersonneMoraleRepository;

    public CategoriePersonneMoraleServiceImp(EntityMapper entityMapper, CategoriePersonneMoraleRepository categoriePersonneMoraleRepository) {
        this.entityMapper = entityMapper;
        this.categoriePersonneMoraleRepository = categoriePersonneMoraleRepository;
    }

    @Override
    public List<CategoriePersonneMoraleDto> save(List<CategoriePersonneMoraleDto> categoriePersonneMoraleDtos) {
        try {
            List<CategoriePersonneMorale> list = categoriePersonneMoraleDtos.stream().map(entityMapper::categoriePersonneMoraleDtoToCategoriePersonneMorale).collect(Collectors.toList());
            list = categoriePersonneMoraleRepository.saveAll(list);
            return list.stream().map(entityMapper::categoriePersonneMoraleToCategoriePersonneMoraleDto).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public CategoriePersonneMoraleDto save(CategoriePersonneMoraleDto dto) {
        try {
            CategoriePersonneMorale categoriePersonneMorale = entityMapper.categoriePersonneMoraleDtoToCategoriePersonneMorale(dto);
            return entityMapper.categoriePersonneMoraleToCategoriePersonneMoraleDto(categoriePersonneMoraleRepository.save(categoriePersonneMorale));
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public List<CategoriePersonneMoraleDto> findAll() {
        try {
            List<CategoriePersonneMorale> list = categoriePersonneMoraleRepository.findAll();
            return list.stream().map(entityMapper::categoriePersonneMoraleToCategoriePersonneMoraleDto).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    @Override
    public void init() {
        if (categoriePersonneMoraleRepository.count()==0){
            List<CategoriePersonneMorale> list= new ArrayList<>();
            list.add(CategoriePersonneMorale.builder().code("SC").libelle("Société commerciale").build());
            list.add(CategoriePersonneMorale.builder().code("OBNL").libelle("Société à but non lucratif ").build());
            list.add(CategoriePersonneMorale.builder().code("EPNFD").libelle("Entreprises et professions non financières désignées").build());
            categoriePersonneMoraleRepository.saveAll(list);
        }
    }
}
