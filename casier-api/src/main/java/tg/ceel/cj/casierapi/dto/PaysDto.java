package tg.ceel.cj.casierapi.dto;

import lombok.*;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaysDto {
    private String code;
    private String libelle;
    private String libelleNationalite;
    private Boolean nationale;
}
