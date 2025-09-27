package tg.ceel.cj.casierapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "historique")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String utilisateur;
    private Date datecreation;
    private String utilisateurs;
    private String numeroDemande;
    private Long idDemande;
    private String username;
    private String action_realisee;

}