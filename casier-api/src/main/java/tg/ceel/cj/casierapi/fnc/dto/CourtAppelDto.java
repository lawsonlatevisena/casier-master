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
public class CourtAppelDto implements Serializable {
    private Date datecreation;
    private String libelle;
    private Date rowvers;
}
