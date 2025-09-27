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
public class JuridictionDto implements Serializable {
    private String code;
    private String adresse;
    private String coderg;
    private Date datecreation;
    private String libellecourt;
    private String libellelong;
    private String precleprimaire;
    private String type;
    private String ville;
    private String juridictionCode;
    private String juridictionLibellecourt;
    private CourtAppelDto courtAppel;
}
