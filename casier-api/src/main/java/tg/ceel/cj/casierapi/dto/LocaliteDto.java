package tg.ceel.cj.casierapi.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocaliteDto {

    private Integer id;

    private String libelle;

    private Long juridictionId;
    private String juridictionLibelle;
}
