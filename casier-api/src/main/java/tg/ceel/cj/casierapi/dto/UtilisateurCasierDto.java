package tg.ceel.cj.casierapi.dto;

import lombok.*;
import tg.ceel.cj.casierapi.entities.PointRetrait;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UtilisateurCasierDto implements Serializable {
    private  int version;
    private  Long code;
    private  Long pointRetraitId;
    private  String pointRetraitLibelle;
    private  Integer serviceDemandeurB2Id;
    private  String serviceDemandeurB2Libelle;
    private  Integer fonctionId;
    private  String fonctionLibelle;
    private PersonneInfoDto personneInfo;
    private  Boolean isPosteUser;


}
