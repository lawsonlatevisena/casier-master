package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RolesPermissionsPK  implements Serializable {
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_id")
    private String permissionId;

}
