package tg.ceel.cj.casierapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.cj.casierapi.entities.Paiement;
import tg.ceel.cj.casierapi.fnc.dto.PersonneDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeModele {
    private DemandeAtd demande;
    private Paiement paiement;
    private Demandeur demandeur;
}
