package tg.ceel.cj.casierapi.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypePieceDto {
    private Integer id;
    private String code;
    private String libelle;
/*
    private String bulletin;

    private boolean active;*/
}
