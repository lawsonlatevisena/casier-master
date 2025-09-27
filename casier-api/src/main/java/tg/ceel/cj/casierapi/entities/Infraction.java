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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "infractions")
@XmlRootElement
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Infraction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @Column(name = "id", nullable = false)
    private Long id;

    @XmlElement(name = "libelle")
    @Column(name = "libelle", nullable = false, length = 251)
    private String libelle;

    @Temporal(TemporalType.DATE)
    @XmlElement(name = "date_infraction")
    @Column(name = "date_infraction", nullable = false)
    private Date dateInfraction;


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final Infraction other = (Infraction) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Infraction{" + "id=" + id + ", libelle=" + libelle + ", dateInfraction=" + dateInfraction + '}';
    }

}
