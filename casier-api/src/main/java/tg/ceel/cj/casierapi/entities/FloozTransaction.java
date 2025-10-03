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

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "flooz_transactions")
@XmlRootElement
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloozTransaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ip_merchant_name")
    private String ipMerchantName;

    @Column(name = "ip_transaction_code")
    private String ipTransactionCode;

    @Column(name = "ip_dest_mobile_number")
    private String ipDestMobileNumber;

    @Column(name = "iop_amount")
    private String iopAmount;

    @Column(name = "op_reference_id")
    private String opReferenceId;

    @Column(name = "op_status")
    private Integer opStatus;

    @Column(name = "op_status_message")
    private String opStatusMessage;

    @Column(name = "op_subscriber_msisdn")
    private String opSubscriberMsisdn;

    @Column(name = "op_flooz_refid")
    private String opFloozRefid;

    @Column(name = "traiter")
    private Boolean traiter = Boolean.FALSE;

  /*  @Column(name = "date_creation")
    private LocalDateTime datecreation;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandes_id", nullable = false)
    private Demande demande;
    @Transient
    private String numeroDemande;
    @Transient
    private String recode;


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final FloozTransaction other = (FloozTransaction) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }



}
