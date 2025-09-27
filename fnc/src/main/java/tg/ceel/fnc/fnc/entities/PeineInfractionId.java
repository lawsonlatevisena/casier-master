/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.fnc.fnc.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class PeineInfractionId implements Serializable {

    @Column(name = "peine", insertable = false, updatable = false)
    private Long peine;

    @Column(name = "infraction", insertable = false, updatable = false)
    private Long infraction;

    public PeineInfractionId() {
    }

    public PeineInfractionId(Long peine, Long infraction) {
        this.peine = peine;
        this.infraction = infraction;
    }

    public Long getPeine() {
        return peine;
    }

    public void setPeine(Long peine) {
        this.peine = peine;
    }

    public Long getInfraction() {
        return infraction;
    }

    public void setInfraction(Long infraction) {
        this.infraction = infraction;
    }

    @Override
    public String toString() {
        return "PeineInfractionId{" + "peine=" + peine + ", infraction=" + infraction + '}';
    }

}
