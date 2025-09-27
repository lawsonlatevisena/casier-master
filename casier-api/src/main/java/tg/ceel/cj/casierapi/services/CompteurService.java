package tg.ceel.cj.casierapi.services;

import org.springframework.data.jpa.repository.Lock;
import tg.ceel.cj.casierapi.entities.Demande;


import javax.persistence.LockModeType;

public interface CompteurService {
    String nextFormated(Demande demande);

    @Lock(LockModeType.WRITE)
    Long next(Demande demande);
}
