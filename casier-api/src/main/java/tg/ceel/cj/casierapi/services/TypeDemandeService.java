package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.TypeDemandeDto;
import tg.ceel.cj.casierapi.entities.Demande;

import java.util.List;


public interface TypeDemandeService {
    List<TypeDemandeDto> findAll();

    TypeDemandeDto getOne(String libelle);

     TypeDemandeDto findByCode(String code);

     TypeDemandeDto findForDemande(Demande demande);

}
