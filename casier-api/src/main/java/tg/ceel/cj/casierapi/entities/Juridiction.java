/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@DiscriminatorValue(value = "J")
@XmlRootElement(name = "juridiction")
public class Juridiction extends PointRetrait {

    @XmlTransient
    @Column(name = "point_retrait", nullable = true)
    private Boolean pointRetrait;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "types_juridictions_code", nullable = true)
    private TypeJuridiction typeJuridiction;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cours_appel_id", nullable = true)
    private CoursAppel coursAppel;
    
    @XmlTransient
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "entite_demandeur_b2", nullable = true)
    private EntiteDemandeurB2 entiteDemandeurB2;

    private void writeObject(ObjectOutputStream stream) throws IOException {
//        List<EntiteDemandeurB2> entiteDemandeurB2s = new ArrayList<>();
        List<TypeJuridiction> typeJuridictions = new ArrayList<>();
        List<CoursAppel> coursAppels = new ArrayList<>();
        coursAppels.add(coursAppel);
        typeJuridictions.add(typeJuridiction);
//        entiteDemandeurB2s.add(entiteDemandeurB2);
//        entiteDemandeurB2s.isEmpty();
        typeJuridictions.isEmpty();
        coursAppels.isEmpty();
        stream.defaultWriteObject();
    }

    public Juridiction() {
        this.pointRetrait = Boolean.FALSE;
    }

    public Juridiction(CoursAppel coursAppel) {
        this.coursAppel = coursAppel;
    }

    public Juridiction(Boolean pointRetrait, CoursAppel coursAppel) {
        this.pointRetrait = pointRetrait;
        this.coursAppel = coursAppel;
    }

    public Juridiction(CoursAppel coursAppel, TypeJuridiction typeJuridiction) {
        this.coursAppel = coursAppel;
        this.typeJuridiction = typeJuridiction;
    }

    public Juridiction(Boolean pointRetrait, CoursAppel coursAppel, TypeJuridiction typeJuridiction, EntiteDemandeurB2 entiteDemandeurB2) {
        this.pointRetrait = pointRetrait;
        this.coursAppel = coursAppel;
        this.typeJuridiction = typeJuridiction;
        this.entiteDemandeurB2 = entiteDemandeurB2;
    }

    @XmlTransient
    public Boolean getPointRetrait() {
        return pointRetrait;
    }

    public void setPointRetrait(Boolean pointRetrait) {
        this.pointRetrait = pointRetrait;
    }

    @XmlTransient
    public CoursAppel getCoursAppel() {
        return coursAppel;
    }

    public void setCoursAppel(CoursAppel coursAppel) {
        this.coursAppel = coursAppel;
    }

    @XmlTransient
    public TypeJuridiction getTypeJuridiction() {
        return typeJuridiction;
    }

    public void setTypeJuridiction(TypeJuridiction typeJuridiction) {
        this.typeJuridiction = typeJuridiction;
    }

    @XmlTransient
    public EntiteDemandeurB2 getEntiteDemandeurB2() {
        return entiteDemandeurB2;
    }

    public void setEntiteDemandeurB2(EntiteDemandeurB2 entiteDemandeurB2) {
        this.entiteDemandeurB2 = entiteDemandeurB2;
    }

    @Override
    public String toString() {
        return "Juridiction{" + "pointRetrait=" + pointRetrait + '}';
    }

}
