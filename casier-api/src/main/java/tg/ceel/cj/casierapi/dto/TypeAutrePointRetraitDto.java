package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.TypeAutrePointRetrait}
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeAutrePointRetraitDto implements Serializable {
    private int version = 1;
    private String code;
    private String libelle;
}