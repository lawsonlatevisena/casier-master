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
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "utilisateurs_casiers")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurCasier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", nullable = false)
    private Long code;

    @ManyToOne
    @JoinColumn(name = "code_service_demandeur_b2", nullable = true)
    private ServiceDemandeurB2 serviceDemandeurB2;

    @ManyToOne
    @JoinColumn(name = "code_fonction", nullable = true)
    private Fonction fonction;


    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "code_personne_infos", nullable = false)
    private PersonneInfo personneInfo;

    @Column(name = "is_poste_user", nullable = true)
    private Boolean isPosteUser;
    @Column(nullable = true)
    private Long codeJuridiction;
    // Generer le UUID pour certifier l'utilisateur
    private String code_certification;

    @ManyToMany()
    @JoinTable(name = "utilisateurs_casier_point_retrait",
            joinColumns = {
                    @JoinColumn(name = "user_casier_code", referencedColumnName = "code")},
            inverseJoinColumns = {
                    @JoinColumn(name = "point_retrait_id", referencedColumnName = "id")})
    @WhereJoinTable(clause = "active=true")
    private List<PointRetrait> pointRetraits = new LinkedList<>();


    @Override
    public String toString() {
        return "UtilisateurCasier{" + "code=" + code + ", serviceDemandeurB2=" + serviceDemandeurB2 + ", fonction=" + fonction + ", personneInfo=" + personneInfo + '}';
    }
}
