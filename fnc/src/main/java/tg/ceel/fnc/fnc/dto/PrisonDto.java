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
public class PrisonDto implements Serializable {
    private String libellecourt;
    private String libellelong;
    private String adresse;
    private Date datecreation;
    private Date rowvers;
}
