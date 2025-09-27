package tg.ceel.cj.casierapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Traitement {
    private Integer nombre_traite;
    private Integer nombre_attendu;
}
