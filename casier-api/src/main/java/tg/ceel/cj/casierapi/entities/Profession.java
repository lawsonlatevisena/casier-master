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
@Table(name = "professions")
@Data
@Builder
public class Profession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "libelle", nullable = false, length = 521)
    private String libelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categorie_socio_professionnelle", nullable = false)
    private CategorieSocioProfessionnelle categorieSocioProfessionnelle;

    private void writeObject(ObjectOutputStream stream) throws IOException {
        List<CategorieSocioProfessionnelle> categorieSocioProfessionnelles = new ArrayList<>();
        categorieSocioProfessionnelles.add(categorieSocioProfessionnelle);
        categorieSocioProfessionnelles.isEmpty();
        stream.defaultWriteObject();
    }

    public Profession() {
    }

    public Profession(Integer id, String libelle, CategorieSocioProfessionnelle categorieSocioProfessionnelle) {
        this.id = id;
        this.libelle = libelle;
        this.categorieSocioProfessionnelle = categorieSocioProfessionnelle;
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

    public CategorieSocioProfessionnelle getCategorieSocioProfessionnelle() {
        return categorieSocioProfessionnelle;
    }

    public void setCategorieSocioProfessionnelle(CategorieSocioProfessionnelle categorieSocioProfessionnelle) {
        this.categorieSocioProfessionnelle = categorieSocioProfessionnelle;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final Profession other = (Profession) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profession{" + "id=" + id + ", libelle=" + libelle + '}';
    }

}
