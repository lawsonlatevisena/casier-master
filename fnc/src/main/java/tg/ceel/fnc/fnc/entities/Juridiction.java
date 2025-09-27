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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "juridiction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Juridiction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @Column(name = "id")
    private Long id;

    @XmlElement(name = "code")
    @Column(name = "code")
    private String code;

    @XmlElement(name = "adresse")
    @Column(name = "adresse")
    private String adresse;

    @XmlElement(name = "coderg")
    @Column(name = "coderg")
    private String coderg;

    @XmlElement(name = "datecreation")
    @Column(name = "datecreation")
    @Temporal(TemporalType.DATE)
    private Date datecreation;

    @XmlElement(name = "libellecourt")
    @Column(name = "libellecourt")
    private String libellecourt;

    @XmlElement(name = "libellelong")
    @Column(name = "libellelong")
    private String libellelong;

    @XmlElement(name = "precleprimaire")
    @Column(name = "precleprimaire")
    private String precleprimaire;

    @XmlElement(name = "type")
    @Column(name = "type")
    private String type;

    @XmlElement(name = "ville")
    @Column(name = "ville")
    private String ville;

    @XmlElement(name = "rowvers")
    @Column(name = "rowvers")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowvers;

    @XmlElement(name = "juridiction")
    @JoinColumn(name = "juridiction")
    @ManyToOne(fetch = FetchType.LAZY)
    private Juridiction juridiction;

    @XmlElement(name = "courtappel")
    @JoinColumn(name = "courtappel")
    @ManyToOne(fetch = FetchType.LAZY)
    private CourtAppel courtAppel;

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
        final Juridiction other = (Juridiction) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Juridiction{" + "id=" + id + ", code=" + code + ", adresse=" + adresse + ", coderg=" + coderg + ", datecreation=" + datecreation + ", libellecourt=" + libellecourt + ", libellelong=" + libellelong + ", precleprimaire=" + precleprimaire + ", type=" + type + ", ville=" + ville + ", rowvers=" + rowvers + ", juridiction=" + juridiction + ", courtAppel=" + courtAppel + '}';
    }

}
