package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "core_roles_permissions")
public class RolesPermissions extends BaseEntity {

    @EmbeddedId
    private RolesPermissionsPK id;
  /*  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private Permission permission;
    @ManyToOne
   @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(columnDefinition = "boolean default(true)")
    private  Boolean actif;
    @Temporal(value = TemporalType.TIME)
    private Date debut;
    @Temporal(value = TemporalType.TIME)
    private Date fin;
}
