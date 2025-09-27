
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
@Table(name = "annee")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Annee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @Column(name = "id")
    private Long id;

    @XmlElement(name = "code")
    @Column(name = "code", unique = true)
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement(name = "datecreation")
    @Column(name = "datecreation")
    private Date datecreation;

    @XmlElement(name = "valeur")
    @Column(name = "valeur")
    private String valeur;

    @XmlElement(name = "rowvers")
    @Column(name = "rowvers")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowvers;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Annee other = (Annee) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Annee{" + "id=" + id + ", code=" + code + ", datecreation=" + datecreation + ", valeur=" + valeur + ", rowvers=" + rowvers + '}';
    }

}
