/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.fnc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CondamnationCouleur implements Serializable {

    @XmlElement(name = "id")
    @Id
    private Long id;

    @XmlElement(name = "etape1")
    private boolean etape1;

    @XmlElement(name = "etape2")
    private boolean etape2;

    @XmlElement(name = "etape3")
    private boolean etape3;

    @XmlElement(name = "couleur")
    private String couleur;

    @XmlElementWrapper(name = "condamnations")
    @XmlElement(name = "condamnation")
    private List<Condamnation> condamnations;

}
