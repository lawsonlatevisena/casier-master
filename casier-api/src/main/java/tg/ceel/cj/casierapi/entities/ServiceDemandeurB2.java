/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "services_demandeurs_b2")
@XmlRootElement
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDemandeurB2 extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "libelle", nullable = false, length = 101)
    private String libelle;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "entite_demandeur_b2_code")
    private EntiteDemandeurB2 entiteDemandeurB2;

    private void writeObject(ObjectOutputStream stream) throws IOException {
        List<EntiteDemandeurB2> entiteDemandeurB2s = new ArrayList<>();
        entiteDemandeurB2s.add(entiteDemandeurB2);
        entiteDemandeurB2s.isEmpty();
        stream.defaultWriteObject();
    }

    public ServiceDemandeurB2(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final ServiceDemandeurB2 other = (ServiceDemandeurB2) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ServiceDemandeurB2{" + "id=" + id + ", libelle=" + libelle + '}';
    }

}
