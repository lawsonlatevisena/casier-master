package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "utilisateurs_casier_point_retrait")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurCasierPointRetrait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "point_retrait_id", nullable = true)
    private PointRetrait pointRetrait;

    @ManyToOne
    @JoinColumn(name = "user_casier_code", nullable = true)
    private UtilisateurCasier utilisateurCasier;

    private Boolean active;

    private Date dateAjout;

    private Date dateFin;

    private  String username;
    private  String userNomPrenoms;
    @Column(columnDefinition = "boolean default false")
    private Boolean defaut;


}
