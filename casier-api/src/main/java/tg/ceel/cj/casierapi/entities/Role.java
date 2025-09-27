/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name = "core_roles")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

 //   @Size(max = 255)
    @Column(name = "label", nullable = false, length = 54)
    private String label;

    @Column(columnDefinition = "boolean default(true)")
    private  Boolean active;


    @ManyToMany
    @JoinTable(name = "core_users_roles",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users = new LinkedList<>();
   @ManyToMany
    @JoinTable(name = "core_roles_permissions",
            joinColumns = {
                @JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "permission_id", referencedColumnName = "code")})
    private List<Permission> permissions = new LinkedList<>();



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + '}';
    }

}
