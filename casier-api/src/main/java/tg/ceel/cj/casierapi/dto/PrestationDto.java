package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrestationDto implements Serializable {
    private  Integer version;
    private  Integer id;
    private  String code;
    private  String libelle;
    private  Integer cout;
    private  Integer typeDemandeId;
    private  String typeDemandeLibelle;
    private  String typeDemandeCode;
}
