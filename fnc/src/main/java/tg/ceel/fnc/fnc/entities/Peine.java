/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.fnc.fnc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "peine")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Peine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @Column(name = "id")
    private Long id;

    @XmlElement(name = "libelle")
    @Column(name = "libelle")
    private String libelle;

    @XmlElement(name = "amande")
    @Column(name = "amande")
    private String amande;

    @XmlElement(name = "peine_is")
    @Column(name = "peine_is")
    private String peineIs;

    @XmlElement(name = "sursis")
    @Column(name = "sursis")
    private String sursis;

    @XmlElement(name = "is_sejour")
    @Column(name = "is_sejour")
    private String is_sejour;

    @XmlElement(name = "datecreation")
    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreation;

    @XmlElement(name = "rowvers")
    @Column(name = "rowvers")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowvers;


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Peine other = (Peine) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Peine{" + "id=" + id + ", libelle=" + libelle + ", amande=" + amande + ", peineIs=" + peineIs + ", sursis=" + sursis + ", is_sejour=" + is_sejour + ", datecreation=" + datecreation + ", rowvers=" + rowvers + '}';
    }

}
