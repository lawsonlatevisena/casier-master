/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;


@Embeddable
@XmlRootElement
public class CondamnationDemandePK implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "id_condamnation",  insertable = false, updatable = false, nullable = false)
    private Long idCondamnation;
    
    @Basic(optional = false)
    @Column(name = "id_demande", insertable = false, updatable = false, nullable = false)
    private Long idDemande;
    
    public CondamnationDemandePK() {
    }

    public CondamnationDemandePK(Long idCondamnation, Long idDemande) {
        this.idCondamnation = idCondamnation;
        this.idDemande = idDemande;
    }

    public Long getIdCondamnation() {
        return idCondamnation;
    }

    public void setIdCondamnation(Long idCondamnation) {
        this.idCondamnation = idCondamnation;
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

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
