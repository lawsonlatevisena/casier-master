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
@Table(name = "situation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Situation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @Column(name = "id")
    private Long id;

    @XmlElement(name = "typesituation")
    @Column(name = "typesituation")
    private String typesituation;

    @XmlElement(name = "num_mandat_arret")
    @Column(name = "num_mandat_arret")
    private String numMandatArret;

    @XmlElement(name = "num_mandat_depot")
    @Column(name = "num_mandat_depot")
    private String numMandatDepot;

    @XmlElement(name = "num_decision_lp")
    @Column(name = "num_decision_lp")
    private String numDecisionLp;

    @XmlElement(name = "id")
    @Column(name = "date_mandat_arret")
    @Temporal(TemporalType.DATE)
    private Date dateMandatArret;

    @XmlElement(name = "date_mandat_depot")
    @Column(name = "date_mandat_depot")
    @Temporal(TemporalType.DATE)
    private Date dateMandatDepot;

    @XmlElement(name = "date_decision_lp")
    @Column(name = "date_decision_lp")
    @Temporal(TemporalType.DATE)
    private Date dateDecisionLp;

    @XmlElement(name = "num_ecrou")
    @Column(name = "num_ecrou")
    private String numEcrou;

    @XmlElement(name = "datecreation")
    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreation;

    @XmlElement(name = "rowvers")
    @Column(name = "rowvers")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowvers;

    @XmlElement(name = "prison")
    @JoinColumn(name = "prison")
    @ManyToOne(fetch = FetchType.LAZY)
    private Prison prison;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Situation other = (Situation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Situation{" + "id=" + id + ", typesituation=" + typesituation + ", numMandatArret=" + numMandatArret + ", numMandatDepot=" + numMandatDepot + ", numDecisionLp=" + numDecisionLp + ", dateMandatArret=" + dateMandatArret + ", dateMandatDepot=" + dateMandatDepot + ", dateDecisionLp=" + dateDecisionLp + ", numEcrou=" + numEcrou + ", datecreation=" + datecreation + ", rowvers=" + rowvers + ", prison=" + prison + '}';
    }

}
