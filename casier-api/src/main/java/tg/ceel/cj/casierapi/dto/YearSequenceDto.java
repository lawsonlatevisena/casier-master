package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.YearSequence}
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearSequenceDto implements Serializable {
    private int version = 1;
    private String code;
    private long sequenceValue;
    private int step;
    private int sequenceYear;
}