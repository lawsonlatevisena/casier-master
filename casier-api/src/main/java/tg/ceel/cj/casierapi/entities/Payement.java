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
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "payements")
@XmlRootElement
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numero", nullable = true)
    private String numero;

    @Column(name = "numero_transaction", nullable = true)
    private String numeroTransaction;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datepayement", nullable = false)
    private Date datePayement;

    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "canal_payement", nullable = false, length = 17)
    private CanalPayement canalPayement;

    @Column(name = "mode_payement", nullable = false, length = 17)
    private ModePayement modePayement;

    @Column(name = "quittance_tresor", nullable = true)
    private String quittanceTresor;

    @Column(name = "regler", nullable = false)
    private Boolean regler;

    @Column(name = "transaction_uuid", nullable = true)
    private String transactionUUID;

    @Column(name = "montant_paye", nullable = true)
    private Double montantPaye;

    @Column(name = "devise_paiement", nullable = true)
    private String devisePaiement;

    @Column(name = "date_transaction", nullable = true)
    private String dateTransaction;

    @Column(name = "response_json", nullable = true)
    private String reponseJson;

    @Column(name = "signed_attributes", nullable = true)
    private String signedAttributeNames;

    private String moyenPaiement;
    private Date dateCreation;


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Payement other = (Payement) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Payement{" + "id=" + id + ", numero=" + numero + ", numeroTransaction=" + numeroTransaction + ", datePayement=" + datePayement + ", montant=" + montant + ", canalPayement=" + canalPayement + ", modePayement=" + modePayement + ", regler=" + regler + '}';
    }
}
