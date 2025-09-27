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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "points_retraits")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING, length = 1)
@XmlRootElement
@XmlSeeAlso({
    AutrePointRetrait.class,
    Juridiction.class
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointRetrait extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle", length = 100)
    protected String libelle;

    @Column(name = "libelle_long", length = 255)
    protected String libelleLong;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "localites_code", nullable = false, length = 51)
    protected String localite;

    @Column(name = "code", nullable = true)
    protected String code;

    @Column(name = "greffier_en_chef", nullable = true)
    private String greffierEnChef;
    private String labelle_affichage;

    @ManyToOne
    @JoinColumn(name = "id_regie", nullable = true)
    protected Regie regie;

    @XmlTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "types_demandes_points_retraits",
            joinColumns = {
                @JoinColumn(name = "id_point_retrait", nullable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_type_demande", nullable = false)})
    protected List<TypeDemande> typeDemandes = new ArrayList<>();


    @Column(name = "is_active", nullable = true)
    protected Boolean isActive;

    @Column(name = "separate_poste_demandes", nullable = true)
    protected Boolean separatePosteDemandes;

    @Column(name = "point_retrait")
    private Boolean pointRetrait;



    public PointRetrait(String libelle) {
        this.libelle = libelle;
    }

    public PointRetrait(String libelle, Double longitude, Double latitude) {
        this.libelle = libelle;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public PointRetrait(Long id, String libelle, Double longitude, Double latitude, String localite) {
        this.id = id;
        this.libelle = libelle;
        this.longitude = longitude;
        this.latitude = latitude;
        this.localite = localite;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getPointRetrait() {
        return pointRetrait;
    }

    public void setPointRetrait(Boolean pointRetrait) {
        this.pointRetrait = pointRetrait;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelleLong() {
        return libelleLong;
    }

    public void setLibelleLong(String libelleLong) {
        this.libelleLong = libelleLong;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGreffierEnChef() {
        return greffierEnChef;
    }

    public void setGreffierEnChef(String greffierEnChef) {
        this.greffierEnChef = greffierEnChef;
    }

    public Regie getRegie() {
        return regie;
    }

    public void setRegie(Regie regie) {
        this.regie = regie;
    }

    public List<TypeDemande> getTypeDemandes() {
        return typeDemandes;
    }

    public void setTypeDemandes(List<TypeDemande> typeDemandes) {
        this.typeDemandes = typeDemandes;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getSeparatePosteDemandes() {
        return separatePosteDemandes;
    }

    public void setSeparatePosteDemandes(Boolean separatePosteDemandes) {
        this.separatePosteDemandes = separatePosteDemandes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final PointRetrait other = (PointRetrait) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PointRetrait{" + "id=" + id + ", libelle=" + libelle + ", longitude=" + longitude + ", latitude=" + latitude + ", localite=" + localite + ", code=" + code + ", regie=" + regie + ", typeDemandes=" + typeDemandes + '}';
    }
}
