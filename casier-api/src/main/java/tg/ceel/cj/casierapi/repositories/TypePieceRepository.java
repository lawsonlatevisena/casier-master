package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.dto.TypePieceDto;
import tg.ceel.cj.casierapi.entities.TypePiece;

import java.util.List;
import java.util.Optional;

public interface TypePieceRepository extends JpaRepository<TypePiece, Integer> {
    @Query(value = "SELECT e FROM TypePiece e WHERE (e.bulletin = ?1 OR e.bulletin = 'BOTH') AND e.active = ?2")
    List<TypePiece> getAll(String typeBulletin, Boolean active);
    List<TypePiece> findByPersonnePhysiqueIsFalse();
    Long countByPersonnePhysiqueIsFalse();
    Optional<TypePiece> findByCode(String code);
}