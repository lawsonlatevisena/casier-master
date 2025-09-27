package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.Sequence}
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceDto implements Serializable {
    private int version = 1;
    private String code;
    private long sequenceValue;
    private int step;
}