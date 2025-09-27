package tg.ceel.fnc.fnc.service;

import org.springframework.http.ResponseEntity;
import tg.ceel.fnc.fnc.model.Demande;

public interface CondamnationService {
    ResponseEntity<?> findCondamnation(Demande demande);
}
