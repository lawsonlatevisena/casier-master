package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDemandeurB2Dto implements Serializable {
    private Integer id;
    private String libelle;
    private Integer entiteDemandeurB2Id;
    private String entiteDemandeurB2Libelle;

}
