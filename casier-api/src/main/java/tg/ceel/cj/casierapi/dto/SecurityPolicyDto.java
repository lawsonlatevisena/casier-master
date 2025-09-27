package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.SecurityPolicy}
 */

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityPolicyDto implements Serializable {
    private int version = 1;
    private String code;
    private int maxConnectionAttempt;
}