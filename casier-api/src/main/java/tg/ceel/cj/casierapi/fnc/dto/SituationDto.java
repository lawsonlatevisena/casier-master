package tg.ceel.cj.casierapi.fnc.dto;

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
public class SituationDto implements Serializable {
    private String typesituation;
    private String numMandatArret;
    private String numMandatDepot;
    private String numDecisionLp;
    private Date dateMandatArret;
    private Date dateMandatDepot;
    private Date dateDecisionLp;
    private String numEcrou;
    private Date datecreation;
    private Date rowvers;
    private String prisonLibellecourt;
    private String prisonLibellelong;
}
