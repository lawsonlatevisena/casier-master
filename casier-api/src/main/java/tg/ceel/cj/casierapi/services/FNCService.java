package tg.ceel.cj.casierapi.services;

import org.springframework.http.ResponseEntity;
import tg.ceel.cj.casierapi.fnc.models.Casier;
import tg.ceel.cj.casierapi.fnc.models.DemandeFnc;

public interface FNCService {
    ResponseEntity<?> getCondamnations(DemandeFnc demande);
}
