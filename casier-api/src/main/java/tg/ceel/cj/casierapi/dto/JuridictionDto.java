package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuridictionDto implements Serializable {
    private  int version;
    private  Long id;
    private  String libelle;
    private  String libelleLong;
    private  Double longitude;
    private  Double latitude;
    private  String localite;
    private  String code;
    private  String greffierEnChef;
    private  List<TypeDemandeDto> typeDemandes;
    private  Boolean isActive;
    private  Boolean separatePosteDemandes;
    private  Boolean pointRetrait;
    private  String typeJuridictionCode;
    private  String typeJuridictionLibelle;
    private  int coursAppelVersion;
    private  Integer coursAppelId;
    private  String coursAppelLibelle;
    private  int entiteDemandeurB2Version;
    private  Integer entiteDemandeurB2Id;
    private  String entiteDemandeurB2Libelle;

}
