package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.*;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.TypeDemande;
import tg.ceel.cj.casierapi.ints.IPoste;
import tg.ceel.cj.casierapi.ints.PointRetraitInt;

import java.util.List;

public interface PointRetraitService {
    List<PointRetraitDto> getAll(TypeDemande typeDemande);

    List<PointRetraitDto> findAll();

    PointRetraitDto getDemandePointRetrait(Demande demande);

    List<PointRetraitDto> getAllJuridictions(String typeDemande);
    List<Point> getAllJuridictionsV1(String typeDemande);
    List<PointRetraitDto> getAllJuridictionByTypeAutrePointRetrait();

    List<PointRetraitDto> getAllJuridictions();

    List<PointRetraitDto> getAllPointsRetraits();

    List<PointRetraitDto> getAllANCPointRetraits(TypeDemande typeDemande);

    List<PointRetraitDto> getAllCasierPointRetraits();

    List<AutrePointRetraitDto> getAllBureauxPoste(String typeDemande);

    List<PointRetraitDto> getPosteByIdJuricdiction(Long id);

    PointRetraitDto save(PointRetraitDto pointRetraitDto);

    PointRetraitDto update(Long id, PointRetraitDto pointRetraitDto);

    List<IPoste> getAllBureauxPoste();
}
