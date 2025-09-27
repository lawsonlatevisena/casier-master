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
public class PeineInfractionDto implements Serializable {
    private Date datecreation;
    private Date dateinfraction;
    private Integer quantumpeine;
    private Date rowvers;
    private PeineDto peine;
    private InfractionDto infraction;
}
