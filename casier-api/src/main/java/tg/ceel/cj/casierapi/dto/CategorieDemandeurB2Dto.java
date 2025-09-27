package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorieDemandeurB2Dto implements Serializable {
    private  Integer id;
    private  String libelle;
}
