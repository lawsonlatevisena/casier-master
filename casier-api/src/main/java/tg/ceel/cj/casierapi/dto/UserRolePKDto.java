package tg.ceel.cj.casierapi.dto;

import lombok.*;
import tg.ceel.cj.casierapi.entities.UserRolePK;

import java.io.Serializable;

/**
 * DTO for {@link UserRolePK}
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolePKDto implements Serializable {
    private Long userId;
    private Long roleId;
}