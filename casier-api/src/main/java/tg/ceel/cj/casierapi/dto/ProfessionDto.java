package tg.ceel.cj.casierapi.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionDto {
    private Integer id;
    private String libelle;
    private Integer categorieSocioProfessionnelleId;
    private String categorieSocioProfessionnelleLibelle;
}
