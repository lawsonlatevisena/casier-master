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
import java.util.Objects;


@Entity
@Table(name = "types_pieces")
@XmlRootElement
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TypePiece extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "libelle", nullable = false)
    private String libelle;
    
    @Column(name = "bulletin", nullable = true)
    private String bulletin;
    
    @Column(name = "active", nullable = true)
    private Boolean active;
    @Column(columnDefinition = "boolean default true")
    private Boolean personnePhysique;


    @Override
    public int hashCode() {
        int hash = 7;
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
        final TypePiece other = (TypePiece) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TypePiece{" + "id=" + id + ", libelle=" + libelle + '}';
    }
    
}
