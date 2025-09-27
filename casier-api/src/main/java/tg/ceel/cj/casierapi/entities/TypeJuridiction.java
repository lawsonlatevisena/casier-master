/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;


@Entity
@XmlRootElement(name = "typeJuridiction")
@Table(name = "types_juridictions")
public class TypeJuridiction extends BaseEntity {

    @Id
    @Column(name = "code", nullable = false,length = 4)
    private String code;

    @Column(name = "libelle", length = 51)
    private String libelle;

    public TypeJuridiction() {
    }

    public TypeJuridiction(String code) {
        this.code = code;
    }

    public TypeJuridiction(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.code);
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
        final TypeJuridiction other = (TypeJuridiction) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TypeJuridiction{" + "code=" + code + ", libelle=" + libelle + '}';
    }

}
