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
@Table(name = "personne")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personne extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @Column(name = "id")
    private Long id;
    @XmlElement(name = "nom")
    @Column(name = "nom")
    private String nom;

    @XmlElement(name = "prenom")
    @Column(name = "prenom")
    private String prenom;

    @XmlElement(name = "nompere")
    @Column(name = "nompere")
    private String nomPere;

    @XmlElement(name = "prenompere")
    @Column(name = "prenompere")
    private String prenomPere;

    @XmlElement(name = "nommere")
    @Column(name = "nommere")
    private String nomMere;

    @XmlElement(name = "prenommere")
    @Column(name = "prenommere")
    private String prenomMere;

    @XmlElement(name = "datenaissance")
    @Column(name = "datenaissance")
    @Temporal(TemporalType.DATE)
    private Date datenaissance;

    @XmlElement(name = "numpi")
    @Column(name = "numpi")
    private String numpi;

    @XmlElement(name = "numactenaiss")
    @Column(name = "numactenaiss")
    private String numactenaiss;

    @XmlElement(name = "lieunaissance")
    @Column(name = "lieunaissance")
    private String lieunaissance;

    @XmlElement(name = "sexe")
    @Column(name = "sexe")
    private String sexe;

    @XmlElement(name = "profession")
    @Column(name = "profession")
    private String profession;

    @XmlElement(name = "nomprenom")
    @Column(name = "nomprenom")
    private String nomprenom;

    @XmlElement(name = "telephone")
    @Column(name = "telephone")
    private String telephone;

    @XmlElement(name = "email")
    @Column(name = "email")
    private String email;

    @XmlElement(name = "adresse")
    @Column(name = "adresse")
    private String adresse;

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
        final Personne other = (Personne) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Personne{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", nomPere=" + nomPere + ", prenomPere=" + prenomPere + ", nomMere=" + nomMere + ", prenomMere=" + prenomMere + ", datenaissance=" + datenaissance + ", numpi=" + numpi + ", numactenaiss=" + numactenaiss + ", lieunaissance=" + lieunaissance + ", sexe=" + sexe + ", profession=" + profession + ", nomprenom=" + nomprenom + ", telephone=" + telephone + ", email=" + email + ", adresse=" + adresse + ", datecreation=" + datecreation + ", rowvers=" + rowvers + '}';
    }

}
