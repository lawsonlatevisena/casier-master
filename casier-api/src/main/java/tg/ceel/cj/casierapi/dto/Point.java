package tg.ceel.cj.casierapi.dto;

import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    Long id;

    String Libelle_juridiction;

    String juridiction_code;

    String code;
    String libelleLong;
    String localite;
}
