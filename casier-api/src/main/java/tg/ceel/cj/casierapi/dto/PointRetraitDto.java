package tg.ceel.cj.casierapi.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointRetraitDto {

    private Long id;

    private String libelle;

    private String libelleLong;

    private Double longitude;

    private Double latitude;

    private String localite;

    private String code;

    private String labelle_affichage;

}
