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
public class InfractionDto implements Serializable {
    private String code;
    private Date datecreation;
    private String disposition;
    private String infradeveloppe;
    private String libelle;
    private Date rowvers;
    private String qualification;
}
