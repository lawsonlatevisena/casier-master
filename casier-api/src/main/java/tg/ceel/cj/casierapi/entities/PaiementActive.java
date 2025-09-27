package tg.ceel.cj.casierapi.entities;


import lombok.*;
import tg.ceel.cj.casierapi.entities.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paiement_active")
public class PaiementActive  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private  String numeroDemande;
    private  String numeroPaiement;
    private  String numeroTransaction;
    private  String modePaiement;
    private Date datePaiement;
    @ManyToOne
    @JoinColumn(name = "pointRetraits_id")
    private  PointRetrait pointRetrait;
    private  String userName;
    private String demandeur;

}
