package tg.ceel.cj.casierapi.servicesimpl;

import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.TypePieceDto;
import tg.ceel.cj.casierapi.entities.TypePiece;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.TypePieceRepository;
import tg.ceel.cj.casierapi.services.TypePieceService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypePieceServiceImpl implements TypePieceService {
    private final TypePieceRepository typePieceRepository;
    private final EntityMapper entityMapper;

    public TypePieceServiceImpl(TypePieceRepository typePieceRepository, EntityMapper entityMapper) {
        this.typePieceRepository = typePieceRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<TypePieceDto> getAll(String typeBulletin, Boolean active) {
        List<TypePiece> list = typePieceRepository.getAll(typeBulletin, active);
        return list.stream().map(p -> entityMapper.typePieceToTypePieceDto(p)).collect(Collectors.toList());
    }
    @Override
    public List<TypePieceDto> getAll(String typeBulletin) {
        List<TypePiece> list = typePieceRepository.findByPersonnePhysiqueIsFalse();
        return list.stream().map(p -> entityMapper.typePieceToTypePieceDto(p)).collect(Collectors.toList());
    }

    @Override
    public void initForPm() {
        if (typePieceRepository.countByPersonnePhysiqueIsFalse() == 0) {
            List<TypePiece> typePieces = new ArrayList<>();
            typePieces.add(TypePiece.builder().active(true).code("").libelle("Carte d'immatriculation CFE").personnePhysique(false).build());
            typePieces.add(TypePiece.builder().active(true).code("").libelle("Carte d'immatriculation fiscale").personnePhysique(false).build());
            typePieces.add(TypePiece.builder().active(true).code("").libelle("Récipicé d'enregistrement").personnePhysique(false).build());
            typePieces.add(TypePiece.builder().active(true).code("CN").libelle("Certificat de nationalité").personnePhysique(false).build());
            typePieces.add(TypePiece.builder().active(true).code("ADN").libelle(" Déclaration de naissance").personnePhysique(false).build());
            typePieces.add(TypePiece.builder().active(true).code("JR").libelle("Jugement rectificatif").personnePhysique(false).build());
            typePieceRepository.saveAll(typePieces);
        }
    }

}
