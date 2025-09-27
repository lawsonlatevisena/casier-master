/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "core_variables")
public class Variable extends BaseEntity {
    
    @Id
    @Column(name = "var_name", nullable = false)
    private String name;
    
    @Column(name = "var_value", nullable = true)
    private String value;

    public Variable() {
    }

    public Variable(String name) {
        this.name = name;
    }

    public Variable(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final Variable other = (Variable) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Variable{" + "name=" + name + ", value=" + value + '}';
    }
}
