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
import java.util.Date;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_b2")
@Builder
public class UserB2 extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenoms")
    private String prenoms;

    @Column(name = "contact")
    private String contact;

    @Column(name = "cni")
    private String cni;

    @Column(name = "titre")
    private String titre;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_categorie_demandeur_b2", nullable = true)
    private CategorieDemandeurB2 categorieDemandeurB2;

    @ManyToOne
    @JoinColumn(name = "id_entite_demandeur_b2", nullable = true)
    private EntiteDemandeurB2 entiteDemandeurB2;

    @ManyToOne
    @JoinColumn(name = "id_service_demandeur_b2", nullable = true)
    private ServiceDemandeurB2 serviceDemandeurB2;

    @Column(name = "autre_categorie_demandeur_b2", nullable = true)
    private String autreCategorieDemandeur;

    @Column(name = "autre_entite_demandeur_b2", nullable = true)
    private String autreEntiteDemandeur;

    @Column(name = "autre_service_demandeur_b2", nullable = true)
    private String autreServiceDemandeur;

    @Column(name = "valide")
    private Boolean valide;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_validation", nullable = true)
    private Date dateValidation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_demande")
    private Date dateDemande;
    private String sexe;
    private String process;
    private String record;
    private String step;
    @Column(name = "ordre")
    private Integer order;
    private String feedbackTaskId;

    @Override
    public int hashCode() {
        int hash = 3;
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
        final UserB2 other = (UserB2) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserB2{" + "id=" + id + ", nom=" + nom + ", prenoms=" + prenoms + ", contact=" + contact + ", cni=" + cni + ", titre=" + titre + ", email=" + email + ", tel=" + tel + ", login=" + login + ", password=" + password + ", categorieDemandeurB2=" + categorieDemandeurB2 + ", entiteDemandeurB2=" + entiteDemandeurB2 + ", serviceDemandeurB2=" + serviceDemandeurB2 + ", autreCategorieDemandeur=" + autreCategorieDemandeur + ", autreEntiteDemandeur=" + autreEntiteDemandeur + ", autreServiceDemandeur=" + autreServiceDemandeur + ", valide=" + valide + ", dateValidation=" + dateValidation + ", dateDemande=" + dateDemande + '}';
    }
}
