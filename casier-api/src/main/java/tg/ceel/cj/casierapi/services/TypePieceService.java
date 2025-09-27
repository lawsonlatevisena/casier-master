package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.TypePieceDto;

import java.util.List;

public interface TypePieceService {
    List<TypePieceDto> getAll(String typeBulletin, Boolean active);

    List<TypePieceDto> getAll(String typeBulletin);

    void initForPm();

}
