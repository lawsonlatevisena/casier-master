package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntiteDemandeurB2Dto implements Serializable {

    private  Integer id;
    private  String libelle;
    private  int categorieDemandeurB2Version;
    private  Integer categorieDemandeurB2Id;
    private  String categorieDemandeurB2Libelle;
}
