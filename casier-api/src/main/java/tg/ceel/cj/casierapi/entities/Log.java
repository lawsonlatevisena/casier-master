package tg.ceel.cj.casierapi.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "logs")
@Builder
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    @Builder.Default
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();
    private String action;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "date_action")
    private Date dateAction;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "auteur", columnDefinition = "TEXT")
    private String auteur;
    @Column(name = "cible", columnDefinition = "TEXT")
    private String cible;
    @Column(name = "destination", columnDefinition = "TEXT")
    private String destination;
    @ManyToOne
    @JoinColumn(name = "comptes_id")
    private User user;
    @Column(name = "user_ip_address")
    private String userIpAddress;
    @Column(name = "user_mac_address")
    private String userMacAddress;

    @ManyToOne
    @JoinColumn(name = "demande_id", nullable = true)
    private Demande demande;

    @PrePersist
    public void prePersist() {
        this.dateAction = new Date();
    }
}
