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
public class RecoursDto implements Serializable {
    private String typeRecours;
    private Date dateRecours;
    private Date dateTraitementRecours;
    private Boolean etatPouvoir = false;
    private Boolean etatAppel = false;
    private Boolean etatOpposition = false;
    private Date datecreation;
    private Date rowvers;
    private CondamnationDto condamnation;
    private JuridictionDto juridiction;
    private CourtAppelDto courtappel;
}
