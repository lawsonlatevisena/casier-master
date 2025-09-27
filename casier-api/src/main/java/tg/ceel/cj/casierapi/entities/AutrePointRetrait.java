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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@DiscriminatorValue(value = "A")
@XmlRootElement(name = "autrePointRetrait")
public class AutrePointRetrait extends PointRetrait {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "types_autres_points_retraits_code", nullable = true)
    private TypeAutrePointRetrait typeAutrePointRetrait;

    public AutrePointRetrait() {
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        List<TypeAutrePointRetrait> typeAutrePointRetraits = new ArrayList<>();
        typeAutrePointRetraits.add(typeAutrePointRetrait);
        typeAutrePointRetraits.isEmpty();
        stream.defaultWriteObject();
    }

    public AutrePointRetrait(TypeAutrePointRetrait typeAutrePointRetrait) {
        this.typeAutrePointRetrait = typeAutrePointRetrait;
    }

    public TypeAutrePointRetrait getTypeAutrePointRetrait() {
        return typeAutrePointRetrait;
    }

    public void setTypeAutrePointRetrait(TypeAutrePointRetrait typeAutrePointRetrait) {
        this.typeAutrePointRetrait = typeAutrePointRetrait;
    }

    @Override
    public String toString() {
        return "AutrePointRetrait{" +
                ", libelle='" + libelle + '\'' +
                ", libelleLong='" + libelleLong + '\'' +
                ", localite='" + localite + '\'' +
                ", code='" + code + '\'' +
                ", regie=" + regie +
                ", typeDemandes=" + typeDemandes +
                ", isActive=" + isActive +
                ", separatePosteDemandes=" + separatePosteDemandes +
                '}';
    }
}
