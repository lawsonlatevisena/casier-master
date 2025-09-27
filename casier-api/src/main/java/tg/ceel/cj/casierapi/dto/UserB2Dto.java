package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserB2Dto implements Serializable {
    private Long id;
    private String nom;
    private String prenoms;
    private String sexe;
    private String contact;
    private String cni;
    private String titre;
    private String email;
    private String tel;
    private Integer categorieDemandeurB2Id;
    private String categorieDemandeurB2Libelle;
    private Integer entiteDemandeurB2Id;
    private String entiteDemandeurB2Libelle;
    private Integer serviceDemandeurB2Id;
    private String serviceDemandeurB2Libelle;
    private String autreCategorieDemandeur;
    private String autreEntiteDemandeur;
    private String autreServiceDemandeur;
    private Date dateDemande;
}
