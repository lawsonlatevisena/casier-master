package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;


@Entity
@Table(name = "prefectures")
@XmlRootElement

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prefecture extends BaseEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    @Column(name = "chef_lieu", nullable = true, length = 100)
    private String chefLieu;


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Prefecture other = (Prefecture) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}


