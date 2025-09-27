/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "prestations")
public class Prestation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "libelle")
    private String libelle;
    
    @ManyToOne
    @JoinColumn(name = "id_regie", nullable = false)
    private Regie  regie;
    
    @Column(name = "cout", nullable = true)
    private Integer cout;
    
    @ManyToOne
    @JoinColumn(name = "id_type_demande", nullable = true)
    private TypeDemande typeDemande;

    public Prestation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Regie getRegie() {
        return regie;
    }

    public void setRegie(Regie regie) {
        this.regie = regie;
    }

    public Integer getCout() {
        return cout;
    }

    public void setCout(Integer cout) {
        this.cout = cout;
    }

    public TypeDemande getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDemande typeDemande) {
        this.typeDemande = typeDemande;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Prestation other = (Prestation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Prestation{" + "id=" + id + ", code=" + code + ", libelle=" + libelle + ", regie=" + regie + '}';
    }
}
