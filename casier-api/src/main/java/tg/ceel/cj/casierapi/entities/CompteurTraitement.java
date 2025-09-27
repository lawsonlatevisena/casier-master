/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "compteur_traitements")
public class CompteurTraitement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_point_retrait", nullable = false)
    private PointRetrait pointRetrait;
    
    @ManyToOne
    @JoinColumn(name = "id_type_demande", nullable = false)
    private TypeDemande typeDemande;
    
    @Column(name = "numero", nullable = false)
    private Long numero;
    
    @Column(name = "annee", nullable = false)
    private Integer annee;
    

    public CompteurTraitement() {
    }

    public CompteurTraitement(PointRetrait pointRetrait, TypeDemande typeDemande, Long numero, Integer annee) {
        this.pointRetrait = pointRetrait;
        this.typeDemande = typeDemande;
        this.numero = numero;
        this.annee = annee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PointRetrait getPointRetrait() {
        return pointRetrait;
    }

    public void setPointRetrait(PointRetrait pointRetrait) {
        this.pointRetrait = pointRetrait;
    }

    public TypeDemande getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDemande typeDemande) {
        this.typeDemande = typeDemande;
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

    @Override
    public int hashCode() {
        int hash = 3;
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
        final CompteurTraitement other = (CompteurTraitement) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CompteurTraitement{" + "id=" + id + ", pointRetrait=" + pointRetrait + ", typeDemande=" + typeDemande + ", numero=" + numero + '}';
    }
}
