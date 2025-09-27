package tg.ceel.cj.casierapi.servicesimpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tg.ceel.cj.casierapi.entities.TypePersonneMorale;
import tg.ceel.cj.casierapi.ints.ITypePersonneMorale;
import tg.ceel.cj.casierapi.repositories.TypePersonneMoraleRepository;
import tg.ceel.cj.casierapi.services.TypePsersonneMoraleService;

import java.util.ArrayList;
import java.util.List;
@Transactional
@Service
public class TypePsersonneMoraleServiceImpl implements TypePsersonneMoraleService {
    private final TypePersonneMoraleRepository typePersonneMoraleRepository;

    public TypePsersonneMoraleServiceImpl(TypePersonneMoraleRepository typePersonneMoraleRepository) {
        this.typePersonneMoraleRepository = typePersonneMoraleRepository;
    }


    @Override
    public void initTypePersonne() {
        if (typePersonneMoraleRepository.count() == 0) {
            List<TypePersonneMorale> typePersonneMorales =new ArrayList<>();
            typePersonneMorales.add(TypePersonneMorale.builder().code("SC").type_personne("Société commerciale").build());
            typePersonneMorales.add(TypePersonneMorale.builder().code("OBNL").type_personne("Organisation à but non lucratif").build());
            typePersonneMorales.add(TypePersonneMorale.builder().code("EPNFD").type_personne("OEntreprise et profession non financières désignées").build());
            typePersonneMoraleRepository.saveAll(typePersonneMorales);
        }
    }

    @Override
    public List<ITypePersonneMorale> getTypePersonneMorale() {
        return null;
    }
}
