package tg.ceel.cj.casierapi.services;

import org.springframework.http.ResponseEntity;
import tg.ceel.cj.casierapi.dto.UtilisateurCasierDto;
import tg.ceel.cj.casierapi.entities.User;
import tg.ceel.cj.casierapi.models.DashboardModel;

import java.util.List;

public interface UtilisateurCasierService {
    List<UtilisateurCasierDto> findAll();
    UtilisateurCasierDto findById(Long code);
    UtilisateurCasierDto save(UtilisateurCasierDto dto);

    UtilisateurCasierDto update(Long code, UtilisateurCasierDto dto);
    UtilisateurCasierDto changerPassword(UtilisateurCasierDto dto);
    Boolean existsByPersonInfoNom(String nom);
    Boolean existsByPersonInfoPrenom(String prenom);
    ResponseEntity initPointRetrait();



}
