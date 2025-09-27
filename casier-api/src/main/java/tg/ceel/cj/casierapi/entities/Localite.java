/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "localites")
@Builder
@Data

public class Localite extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "libelle", nullable = false, length = 521)
    private String libelle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_juridiction", nullable = false)
    private Juridiction juridiction;

    private void writeObject(ObjectOutputStream stream) throws IOException {
        List<Juridiction> juridictions = new ArrayList<>();
        juridictions.add(juridiction);
        juridictions.isEmpty();
        stream.defaultWriteObject();
    }
    
    public Localite() {
    }

    public Localite(Integer id, String libelle, Juridiction juridiction) {
        this.id = id;
        this.libelle = libelle;
        this.juridiction = juridiction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Juridiction getJuridiction() {
        return juridiction;
    }

    public void setJuridiction(Juridiction juridiction) {
        this.juridiction = juridiction;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final Localite other = (Localite) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localite{" + "id=" + id + ", libelle=" + libelle + '}';
    }
    
}
