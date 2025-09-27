/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "core_permission_categories")
public class PermissionCategory extends BaseEntity {

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "label", nullable = false, length = 255)
    private String label;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Permission> permissions ;

    public PermissionCategory() {
    }

    public PermissionCategory(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public PermissionCategory(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        permissions.isEmpty();
        stream.defaultWriteObject();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.code);
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
        final PermissionCategory other = (PermissionCategory) obj;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public String toString() {
        return "PermissionCategory{" + "id=" + code + '}';
    }
}
