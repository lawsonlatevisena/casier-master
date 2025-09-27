/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;


@Entity
@Table(name = "pays")
@XmlRootElement
@Builder
@Data
public class Pays extends BaseEntity {

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "libelle", length = 51)
    private String libelle;

    @Column(name = "libelle_nationalite", length = 51)
    private String libelleNationalite;

    @Column(name = "nationale")
    private Boolean nationale;

    public Pays() {
        this.nationale = Boolean.FALSE;
    }

    public Pays(String code) {
        this.code = code;
    }

    public Pays(String code, Boolean national) {
        this.code = code;
        this.nationale = national;
    }

    public Pays(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public Pays(String code, String libelle, Boolean national) {
        this.code = code;
        this.libelle = libelle;
        this.nationale = national;
    }

    public Pays(String code, String libelle, String libelleNationalite) {
        this.code = code;
        this.libelle = libelle;
        this.libelleNationalite = libelleNationalite;
    }

    public Pays(String code, String libelle, String libelleNationalite, Boolean national) {
        this.code = code;
        this.libelle = libelle;
        this.libelleNationalite = libelleNationalite;
        this.nationale = national;
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

    public String getLibelleNationalite() {
        return libelleNationalite;
    }

    public void setLibelleNationalite(String libelleNationalite) {
        this.libelleNationalite = libelleNationalite;
    }

    public Boolean getNationale() {
        return nationale;
    }

    public void setNationale(Boolean nationale) {
        this.nationale = nationale;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.code);
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
        final Pays other = (Pays) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pays{" + "code=" + code + ", libelle=" + libelle + ", libelleNationalite=" + libelleNationalite + ", national=" + nationale + '}';
    }

}
