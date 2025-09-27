package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserRolePK  implements Serializable {

    private Long userId;

    private Long roleId;
}
