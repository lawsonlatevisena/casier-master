
package tg.ceel.cj.casierapi.fnc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.cj.casierapi.entities.BaseEntity;
import tg.ceel.cj.casierapi.entities.Infraction;
import tg.ceel.cj.casierapi.utils.CasierUtils;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "condamnations")
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Condamnation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @XmlElement(name = "date_condamnation")
    @Column(name = "date_condamnation", nullable = false)
    private Date dateCondamnation;

    @XmlTransient
    @Transient
    private String stringDateCondamnation;

    @XmlTransient
    @Transient
    private String stringDateMandatDepot;

    @XmlElement(name = "cours")
    @Column(name = "cours", nullable = false, length = 61)
    private String cours;

    @XmlElement(name = "quantum_peine")
    @Column(name = "quantum_peine", nullable = false)
    private int quantumPeine;

    @Temporal(TemporalType.DATE)
    @XmlElement(name = "date_mandat_depot")
    @Column(name = "date_mandat_depot", nullable = false)
    private Date dateMandatDepot;

    @XmlElement(name = "observation")
    @Column(name = "observation", nullable = true)
    private String observation;

    @XmlElementWrapper(name = "infractions")
    @XmlElement(name = "infraction")
    @OneToMany(cascade = {CascadeType.MERGE})
    private Set<Infraction> infractions = new LinkedHashSet();

    @XmlTransient
    public String getStringDateCondamnation() {
        if (this.dateCondamnation != null) {
            return CasierUtils.dateToFrString(this.dateCondamnation);
        }
        return "";
    }

    public void setStringDateCondamnation(String stringDateCondamnation) {
        this.stringDateCondamnation = stringDateCondamnation;
    }

    @XmlTransient
    public String getStringDateMandatDepot() {
        if (this.dateMandatDepot != null) {
            return CasierUtils.dateToFrString(this.dateMandatDepot);
        }
        return "";
    }

    public void setStringDateMandatDepot(String stringDateMandatDepot) {
        this.stringDateMandatDepot = stringDateMandatDepot;
    }

    public String linearInfractions() {
        StringBuilder sb = new StringBuilder();
        infractions.stream().map(i -> {
            sb.append("* ");
            sb.append(i.getLibelle());
            return i;
        }).forEachOrdered(_item -> {
            sb.append("\n");
        });
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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


}
