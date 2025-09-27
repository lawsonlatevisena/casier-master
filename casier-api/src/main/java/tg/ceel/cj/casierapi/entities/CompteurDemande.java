package tg.ceel.cj.casierapi.entities;



import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "compteur_demandes")
public class CompteurDemande extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "numero", nullable = false)
    private Long numero;
    
    @Column(name = "annee", nullable = false)
    private Integer annee;
    
    @ManyToOne
    @JoinColumn(name = "id_type_demande", nullable = false)
    private TypeDemande typeDemande;

    public CompteurDemande() {
    }

    public CompteurDemande(Long numero, Integer annee, TypeDemande typeDemande) {
        this.numero = numero;
        this.annee = annee;
        this.typeDemande = typeDemande;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public TypeDemande getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDemande typeDemande) {
        this.typeDemande = typeDemande;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final CompteurDemande other = (CompteurDemande) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "CompteurDemande{" + "id=" + id + ", numero=" + numero + ", annee=" + annee + ", typeDemande=" + typeDemande + '}';
    }
}
