package tg.ceel.cj.casierapi.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.cj.casierapi.dto.*;
import tg.ceel.cj.casierapi.entities.PersonneInfo;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.entities.ServiceDemandeurB2;
import tg.ceel.cj.casierapi.entities.User;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurModel {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateJour;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;
    private  Long code;
    private  Boolean isPosteUser;
    private Boolean isPasswordSet;
    private Boolean active;
    private Boolean changePassword;
    private Date dateDerniereConnexion;
    private Date dateDerniereDeconnexion;
    private List<RoleDto> roles;
    private String  username;
    private PointRetraitDto pointRetrait;
    private String personneInfoNom;
    private String personneInfoPrenom;
    private String personneInfoEmail;
    private FonctionDto fonction;
    private ServiceDemandeurB2Dto serviceDemandeurB2;
    private Menu[] menu;
    private List<String> permissions;
    private List<PointRetraitDto> pointRetraits;
    private PointRetraitDto pointRetait;

}
