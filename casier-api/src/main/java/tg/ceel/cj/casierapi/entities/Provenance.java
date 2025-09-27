/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


@XmlEnum(String.class)
public enum Provenance {
    @XmlEnumValue("MOBILE")
    MOBILE,
    @XmlEnumValue("SITEWEB")
    SITEWEB,
    @XmlEnumValue("ATD")
    ATD
}
