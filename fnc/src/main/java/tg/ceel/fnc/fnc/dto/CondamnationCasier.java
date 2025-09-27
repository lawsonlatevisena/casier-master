package tg.ceel.fnc.fnc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CondamnationCasier {
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
    private SituationDto situation;
}
