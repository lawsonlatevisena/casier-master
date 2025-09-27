/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;


@Entity
@Table(name = "personne_infos")
@XmlRootElement(name = "personne_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonneInfo extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "nom", nullable = false, length = 127)
    private String nom;
    
    @Column(name = "prenom", nullable = false, length = 127)
    private String prenom;
    
    @Column(name = "email", nullable = true, length = 255)
    private String email;
    
    @Column(name = "telephone", nullable = true, length = 31)
    private String telephone;
    
    @XmlTransient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_sexe", nullable = true)
    private Sexe sexe;
    
    @Column(name = "localite", nullable = true, length = 51)
    private String localite;
    
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = true)
    private User user;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @XmlTransient
    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitre() {
        return sexe.getCode().equals("M") ? "Monsieur" : "Madame";
    }

    public String getNomComplet() {
        return this.nom + " " + this.prenom;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final PersonneInfo other = (PersonneInfo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "PersonneInfo{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", telephone=" + telephone + '}';
//    }

    @Override
    public String toString() {
        return "PersonneInfo{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", telephone=" + telephone + ", sexe=" + sexe + ", localite=" + localite + ", user=" + user + '}';
    }
}
