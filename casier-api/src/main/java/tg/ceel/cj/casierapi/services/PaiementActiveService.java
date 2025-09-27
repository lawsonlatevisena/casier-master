package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.LocaliteDto;
import tg.ceel.cj.casierapi.dto.PaiementActiveDto;
import tg.ceel.cj.casierapi.utils.ModelPaiementActive;

import java.util.List;

public interface PaiementActiveService {

    List<PaiementActiveDto> getAll();
    PaiementActiveDto save(PaiementActiveDto paiementActiveDto);
    PaiementActiveDto findById(Integer id);

    ModelPaiementActive activerPaiement(String numeroDemande);

}
