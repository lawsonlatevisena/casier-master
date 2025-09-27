package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.TypeJuridiction}
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeJuridictionDto implements Serializable {
    private int version = 1;
    private String code;
    private String libelle;
}