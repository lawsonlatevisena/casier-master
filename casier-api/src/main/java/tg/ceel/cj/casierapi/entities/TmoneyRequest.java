/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "tmoney_requests")
@XmlRootElement()
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TmoneyRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sessionid")
    private String sessionid;

    @Column(name = "merchantid")
    private String merchantid;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "currency")
    private Integer currency;

    @Column(name = "purchaseref")
    private String purchaseref;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "accepturl")
    private String accepturl;

    @Column(name = "declineurl")
    private String declineurl;

    @Column(name = "cancelurl")
    private String cancelurl;

    @Column(name = "texte")
    private String text;

    @Column(name = "tmr_language")
    private String tmrLanguage;

    @Column(name = "date_demande")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandes_id", nullable = false)
    private Demande demande;

    @Column(name = "traiter")
    private Boolean traiter = Boolean.FALSE;

    private void writeObject(ObjectOutputStream stream) throws IOException {
        List<Demande> demandes = new ArrayList<>();
        demandes.add(demande);
        demandes.isEmpty();
        stream.defaultWriteObject();
    }



    public TmoneyRequest(Long requestId) {
        this.id = requestId;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmoneyRequest)) {
            return false;
        }
        TmoneyRequest other = (TmoneyRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TmoneyRequest{" + "id=" + id + ", sessionid=" + sessionid + ", merchantid=" + merchantid + ", amount=" + amount + ", currency=" + currency + ", purchaseref=" + purchaseref + ", phonenumber=" + phonenumber + ", brand=" + brand + ", description=" + description + ", accepturl=" + accepturl + ", declineurl=" + declineurl + ", cancelurl=" + cancelurl + ", text=" + text + ", tmrLanguage=" + tmrLanguage + ", dateDemande=" + dateDemande + ", demande=" + demande + ", traiter=" + traiter + '}';
    }

}
