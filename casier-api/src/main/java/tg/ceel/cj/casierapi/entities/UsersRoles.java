package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "core_users_roles")
public class UsersRoles  extends BaseEntity{
    @EmbeddedId
   private UserRolePK id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(columnDefinition = "boolean default(true)")
    private Boolean active ;
}