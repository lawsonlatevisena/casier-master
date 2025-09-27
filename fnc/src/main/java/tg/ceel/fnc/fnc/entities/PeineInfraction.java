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
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "peineinfraction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeineInfraction extends BaseEntity {

    @EmbeddedId
    @XmlElement(name = "id")
    PeineInfractionId id;

    @XmlElement(name = "datecreation")
    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreation;

    @XmlElement(name = "dateinfraction")
    @Column(name = "dateinfraction")
    @Temporal(TemporalType.DATE)
    private Date dateinfraction;

    @XmlElement(name = "quantumpeine")
    @Column(name = "quantumpeine")
    private Integer quantumpeine;

    @XmlElement(name = "rowvers")
    @Column(name = "rowvers")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowvers;

    @XmlElement(name = "peine")
    @JoinColumn(name = "peine", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("peine")
    private Peine peine;

    @XmlElement(name = "infraction")
    @JoinColumn(name = "infraction", insertable = true, updatable = true)
    @MapsId("infraction")
    @ManyToOne(fetch = FetchType.EAGER)
    private Infraction infraction;



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.infraction.getLibelle());
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
        final PeineInfraction other = (PeineInfraction) obj;
        if (!Objects.equals(this.infraction.getLibelle(), other.infraction.getLibelle())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PeineInfraction{" + "id=" + id + ", datecreation=" + datecreation + ", dateinfraction=" + dateinfraction + ", quantumpeine=" + quantumpeine + ", rowvers=" + rowvers + ", peine=" + peine + ", infraction=" + infraction + '}';
    }

}
