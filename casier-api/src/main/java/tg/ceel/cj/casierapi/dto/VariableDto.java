package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.Variable}
 */

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariableDto implements Serializable {
    private int version = 1;
    private String name;
    private String value;
}