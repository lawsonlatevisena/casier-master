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
@Table(name = "recours")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recours extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @Column(name = "id")
    private Long id;

    @XmlElement(name = "typeRecours")
    @Column(name = "typeRecours")
    private String typeRecours;

    @Temporal(TemporalType.DATE)
    @XmlElement(name = "dateRecours")
    @Column(name = "dateRecours")
    private Date dateRecours;

    @Temporal(TemporalType.DATE)
    @XmlElement(name = "dateTraitementRecours")
    @Column(name = "dateTraitementRecours")
    private Date dateTraitementRecours;

    @XmlElement(name = "etatPouvoir")
    @Column(name = "etatPouvoir")
    private Boolean etatPouvoir = false;

    @XmlElement(name = "etatAppel")
    @Column(name = "etatAppel")
    private Boolean etatAppel = false;

    @XmlElement(name = "etatOpposition")
    @Column(name = "etatOpposition")
    private Boolean etatOpposition = false;

    @XmlElement(name = "datecreation")
    @Column(name = "datecreation")
    @Temporal(TemporalType.DATE)
    private Date datecreation;

    @XmlElement(name = "rowvers")
    @Column(name = "rowvers")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowvers;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlElement(name = "condamnation")
    @JoinColumn(name = "condamnation", insertable = true, updatable = true)
    private Condamnation condamnation;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlElement(name = "juridiction")
    @JoinColumn(name = "juridiction", insertable = true, updatable = true)
    private Juridiction juridiction;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlElement(name = "courtappel")
    @JoinColumn(name = "courtappel", insertable = true, updatable = true)
    private CourtAppel courtappel;


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Recours other = (Recours) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recours{" + "id=" + id + ", typeRecours=" + typeRecours + ", dateRecours=" + dateRecours + ", dateTraitementRecours=" + dateTraitementRecours + ", etatPouvoir=" + etatPouvoir + ", etatAppel=" + etatAppel + ", etatOpposition=" + etatOpposition + ", datecreation=" + datecreation + ", rowvers=" + rowvers + ", condamnation=" + condamnation + ", juridiction=" + juridiction + ", courtappel=" + courtappel + '}';
    }

}
