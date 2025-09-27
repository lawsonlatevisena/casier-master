package tg.ceel.fnc.fnc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CondamnationDto implements Serializable {
    private Date datejugement;
    private Date datecreation;
    private Date rowvers;
    private String numeroRp;
    private String numeroOrdre;
    private String etatcondamne;
    private String estInscriteAuCasier;
    private AnneeDto annee;
    private JuridictionDto juridiction;
    private PeineDto peine;
    private PersonneDto personne;
    private SituationDto situation;
}
