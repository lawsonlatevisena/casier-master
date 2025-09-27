/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.fnc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.UtilisateurCasier;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@Table(name = "condamnations_demandes")
@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CondamnationDemande implements Serializable {
    
    @EmbeddedId
    private CondamnationDemandePK condamnationDemandePK;
    
    @Column(name = "date_casier", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCasier;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JoinColumn(name = "id_utilisateur_casier", nullable = true)
    private UtilisateurCasier utilisateurCasier;
    
    @ManyToOne(optional = false, cascade = {})
    @JoinColumn(name = "id_condamnation")
    @MapsId("idCondamnation")
    private Condamnation condamnation;
    
    @ManyToOne(optional = false, cascade = {})
    @JoinColumn(name = "id_demande")
    @MapsId("idDemande")
    private Demande demande;


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.condamnationDemandePK);
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
        final CondamnationDemande other = (CondamnationDemande) obj;
        if (!Objects.equals(this.condamnationDemandePK, other.condamnationDemandePK)) {
            return false;
        }
        return true;
    }



}
