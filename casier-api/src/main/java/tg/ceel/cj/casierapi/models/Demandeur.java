package tg.ceel.cj.casierapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Demandeur {
    private Long id;
    private String nom;
    private String prenoms;
    private String sexe;
    private String contact;
    private String cni;
    private String titre;
    private String email;
    private String tel;
    private Integer categorie_demandeurB2_id;
    private Integer entite_demandeurB2_id;
    private Integer service_demandeurB2_id;
    private String autre_categorie_demandeur;
    private String autre_entite_demandeur;
    private String autre_service_demandeur;

    private String process;
    private String record;
    private String step;
    private Integer order;
    private String feedbackTaskId;
}
