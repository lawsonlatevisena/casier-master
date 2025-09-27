
package tg.ceel.cj.casierapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "types_autres_points_retraits")
public class TypeAutrePointRetrait extends BaseEntity {
    
    public static final Integer ETATIQUE = 1;
    public static final Integer PRIVEE = 2;
    public static final Integer LAPOSTE = 3;
    
    
    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "libelle", length = 51)
    private String libelle;

    public TypeAutrePointRetrait() {
    }

    public TypeAutrePointRetrait(String code) {
        this.code = code;
    }

    public TypeAutrePointRetrait(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.code);
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
        final TypeAutrePointRetrait other = (TypeAutrePointRetrait) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TypeAutrePointRetrait{" + "code=" + code + ", libelle=" + libelle + '}';
    }

}
