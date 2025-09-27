/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
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
@Table(name = "core_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 64)
    private String username;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "active",  columnDefinition = "boolean default false")
    //@Column(name = "active", nullable = false)
    private Boolean active;
    
    @Column(name = "change_password", columnDefinition = "boolean default false")
    private Boolean changePassword;
    private Date dateDerniereConnexion;
    private Date dateDerniereDeconnexion;
    private Boolean isPasswordSet;

    @ManyToMany
    @JoinTable(name = "core_users_roles",
            joinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles = new LinkedList<>();
    private void writeObject(ObjectOutputStream stream) throws IOException {
        roles.isEmpty();
        stream.defaultWriteObject();
    }


}
