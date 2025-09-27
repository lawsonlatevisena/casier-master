package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.PermissionDto;
import tg.ceel.cj.casierapi.dto.UtilisateurCasierPointRetraitDto;

import java.util.List;

public interface UtilisateurCasierPointRetraitService {

    UtilisateurCasierPointRetraitDto save(UtilisateurCasierPointRetraitDto utilisateurCasierPointRetraitDto);
    UtilisateurCasierPointRetraitDto findActive(Long id);
    List<UtilisateurCasierPointRetraitDto> findAllByUserId(Long id);
    List<UtilisateurCasierPointRetraitDto> findAllNonActive();
    List<UtilisateurCasierPointRetraitDto> findAll();



}
