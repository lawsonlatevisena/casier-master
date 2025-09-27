
package tg.ceel.cj.casierapi.fnc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;


@Embeddable
@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CondamnationDemandePK implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "id_condamnation")
    private Long idCondamnation;
    
    @Basic(optional = false)
    @Column(name = "id_demande")
    private Long idDemande;


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.idDemande);
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
        final CondamnationDemandePK other = (CondamnationDemandePK) obj;
        if (!Objects.equals(this.idDemande, other.idDemande)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CondamnationDemandePK{" + "idCondamnation=" + idCondamnation + ", idDemande=" + idDemande + '}';
    }
}
