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

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "entite_demandeur_b2")
@XmlRootElement
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntiteDemandeurB2 extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "libelle", nullable = false, length = 31)
    private String libelle;
    
    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_demandeur_b2_code")
    private CategorieDemandeurB2 categorieDemandeurB2;

     private void writeObject(ObjectOutputStream stream) throws IOException {
        List<CategorieDemandeurB2> categorieDemandeurB2s = new ArrayList<>();
        categorieDemandeurB2s.add(categorieDemandeurB2);
        categorieDemandeurB2s.isEmpty();
        stream.defaultWriteObject();
    }

    public EntiteDemandeurB2(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final EntiteDemandeurB2 other = (EntiteDemandeurB2) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntiteDemandeurB2{" + "id=" + id + ", libelle=" + libelle + '}';
    }
    
}
