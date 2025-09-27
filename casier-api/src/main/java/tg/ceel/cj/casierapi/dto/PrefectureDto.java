package tg.ceel.cj.casierapi.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrefectureDto {
    private Integer id;

    private String  libelle;

    private String chefLieu;
    private String printLabel;
}
