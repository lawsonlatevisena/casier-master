package tg.ceel.cj.casierapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestationDto implements Serializable {
    private int version = 1;
    private Integer id;
    private String code;
    private String libelle;
    private Integer cout;
    private int typeDemandeVersion = 1;
    private Integer typeDemandeId;
    private String typeDemandeLibelle;
    private String typeDemandeCode;
}
