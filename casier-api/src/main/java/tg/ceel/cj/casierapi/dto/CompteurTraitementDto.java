package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.CompteurTraitement}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteurTraitementDto implements Serializable {
    private int version = 1;
    private Long id;
    private int pointRetraitVersion = 1;
    private Long pointRetraitId;
    private String pointRetraitLibelle;
    private String pointRetraitLibelleLong;
    private Double pointRetraitLongitude;
    private Double pointRetraitLatitude;
    private String pointRetraitLocalite;
    private String pointRetraitCode;
    private String pointRetraitGreffierEnChef;
    private int pointRetraitRegieVersion = 1;
    private Integer pointRetraitRegieId;
    private String pointRetraitRegieCode;
    private String pointRetraitRegieLibelle;
    private List<TypeDemandeDto> pointRetraitTypeDemandes = new ArrayList<>();
    private Boolean pointRetraitIsActive;
    private Boolean pointRetraitSeparatePosteDemandes;
    private int typeDemandeVersion = 1;
    private Integer typeDemandeId;
    private String typeDemandeLibelle;
    private String typeDemandeCode;
    private Long numero;
    private Integer annee;

    /**
     * DTO for {@link tg.ceel.cj.casierapi.entities.TypeDemande}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TypeDemandeDto implements Serializable {
        private int version = 1;
        private Integer id;
        private String libelle;
        private String code;
    }
}