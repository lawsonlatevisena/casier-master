package tg.ceel.cj.casierapi.fnc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.cj.casierapi.dto.CondamnationModel;
import tg.ceel.cj.casierapi.fnc.dto.CondamnationCasier;
import tg.ceel.cj.casierapi.fnc.dto.CondamnationDto;
import tg.ceel.cj.casierapi.fnc.dto.PeineInfractionDto;
import tg.ceel.cj.casierapi.fnc.dto.PersonneDto;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Casier {
    private PersonneDto personne;
    private List<CondamnationCasier> condamnations;
    private List<CondamnationModel> modeles;
    private List<PeineInfractionDto> infractions;
    private String couleur;
}
