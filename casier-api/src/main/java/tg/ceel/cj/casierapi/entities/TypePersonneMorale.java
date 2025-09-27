package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "type_personne_morale")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypePersonneMorale {
    @Id
    @Column(name = "id", nullable = false)
   private String code;
   private String type_personne;

}