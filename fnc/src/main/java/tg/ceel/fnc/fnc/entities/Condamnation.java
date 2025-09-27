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
@Table(name = "condamnation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condamnation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @Column(name = "id")
    private Long id;

    @XmlElement(name = "datejugement")
    @Column(name = "datejugement")
    @Temporal(TemporalType.DATE)
    private Date datejugement;

    @XmlElement(name = "datecreation")
    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreation;

    @XmlElement(name = "rowvers")
    @Column(name = "rowvers")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowvers;

    @XmlElement(name = "numero_rp")
    @Column(name = "numero_rp")
    private String numeroRp;

    @XmlElement(name = "numero_ordre")
    @Column(name = "numero_ordre")
    private String numeroOrdre;

    @XmlElement(name = "etatcondamne")
    @Column(name = "etatcondamne")
    private String etatcondamne;

    @XmlElement(name = "estinscriteaucasier")
    @Column(name = "estinscriteaucasier")
    private String estInscriteAuCasier;

    @XmlElement(name = "annee")
    @JoinColumn(name = "annee", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Annee annee;

    @XmlElement(name = "juridiction")
    @JoinColumn(name = "juridiction", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Juridiction juridiction;

    @XmlElement(name = "peine")
    @JoinColumn(name = "peine", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Peine peine;

    @XmlElement(name = "personne")
    @JoinColumn(name = "personne", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Personne personne;

    @XmlElement(name = "situation")
    @JoinColumn(name = "situation", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Situation situation;


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final Condamnation other = (Condamnation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Condamnation{" + "id=" + id + ", datejugement=" + datejugement + ", datecreation=" + datecreation + ", rowvers=" + rowvers + ", numeroRp=" + numeroRp + ", numeroOrdre=" + numeroOrdre + ", etatcondamne=" + etatcondamne + ", estInscriteAuCasier=" + estInscriteAuCasier + ", annee=" + annee + ", juridiction=" + juridiction + ", peine=" + peine + ", personne=" + personne + ", situation=" + situation + '}';
    }

}
