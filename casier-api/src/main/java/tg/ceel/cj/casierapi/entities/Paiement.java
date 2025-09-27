package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "atd_paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recorde;
    private String paymentid;

    private String gateway;

    private String account;

    private String bill;

    private Double amount;

    private String paymentstatus;

    private String successref;

    private String currency;

    private String transactionuuid;

    private Date transactiondate;

    private Date transactiondateshort;
}
