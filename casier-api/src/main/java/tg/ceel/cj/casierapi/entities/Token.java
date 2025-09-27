package tg.ceel.cj.casierapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String api;
    private String token_value;
    private Long expiry;
    private Date expire_date;
    private Date last_recuperation_date=new Date();

}