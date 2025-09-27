package tg.ceel.fnc.fnc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.fnc.fnc.dto.CondamnationCasier;
import tg.ceel.fnc.fnc.dto.PeineInfractionDto;
import tg.ceel.fnc.fnc.dto.PersonneDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Casier {
    private PersonneDto personne;
    private List<CondamnationCasier> condamnations;
    private List<PeineInfractionDto> infractions;
    private String couleur;

}
