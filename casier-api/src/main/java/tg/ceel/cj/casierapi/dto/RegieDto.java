package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.Regie}
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegieDto implements Serializable {
    private int version = 1;
    private Integer id;
    private String code;
    private String libelle;
}