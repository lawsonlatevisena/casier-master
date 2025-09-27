/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


@XmlEnum(String.class)
public enum CanalPayement {

    @XmlEnumValue("FLOOZ")
    FLOOZ,
    @XmlEnumValue("TMONEY")
    TMONEY,
    @XmlEnumValue("CARTE_BANCAIRE")
    CARTE_BANCAIRE,
    @XmlEnumValue("CAISSE")
    CAISSE,
    @XmlEnumValue("ZPAY")
    ZPAY;

    public String getCanalPayement() {
        switch (this) {
            case FLOOZ:
                return "FLOOZ";
            case TMONEY:
                return "TMONEY";
            case CARTE_BANCAIRE:
                return "CARTE_BANCAIRE";
            case CAISSE:
                return "CAISSE";
            case ZPAY:
                return "ZPAY";
            default:
                return null;
        }
    }

}
