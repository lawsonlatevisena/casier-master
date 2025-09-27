/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tg.ceel.cj.casierapi.dto.*;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.ints.LocaliteInt;
import tg.ceel.cj.casierapi.ints.PointRetraitInt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "references")
public class ServiceResponse implements Serializable {
    
   /* @JsonProperty("localites")
    private List<LocaliteInt> localites;*/

    @JsonProperty( "professions")
    private List<ProfessionDto> professions;
    
    @JsonProperty( "prefectures")
    private List<PrefectureDto> prefectures;

    @JsonProperty("categorieSocioProfessionnelles")
    private List<CategorieSocioProfessionnelleDto> categorieSocioProfessionnelles;

    @JsonProperty("typePieces")
    private List<TypePieceDto> typePieces;

    @JsonProperty("pays")
    private List<PaysDto> pays;

    @JsonProperty("pointRetraits")
    private List<Point> pointRetraits;

    @JsonProperty("autrePointRetraits")
    private List<AutrePointRetraitDto> autrePointRetraits;

    @JsonProperty("situationMatrimoniale")
    private List<SituationMatrimonialeDto> situationMatrimoniale;

    @JsonProperty("prixUnitaireDemande")
    private Double prixUnitaireDemande;
    
    @JsonProperty("entitesDemandeursB2")
    private List<EntiteDemandeurB2Dto> entititesDemandeurs;
    
    @JsonProperty("categoriesDemandeurs")
    private List<CategorieDemandeurB2Dto> categoriesDemandeurs;

    @JsonProperty("entiteDemandeurs")
    private List<ServiceDemandeurB2Dto> serviceDemandeurB2s;
    @JsonProperty("categoriePersonneMorale")
    private List<CategoriePersonneMoraleDto> categoriePersonneMorale;
    

}
