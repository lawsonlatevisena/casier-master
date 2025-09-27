package tg.ceel.cj.casierapi.models;

import lombok.*;
import tg.ceel.cj.casierapi.dto.ProfessionDto;
import tg.ceel.cj.casierapi.dto.SituationMatrimonialeDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormulaireModification {
    private ProfessionDto profession;
    private SituationMatrimonialeDto situationMatrimoniale;
    private String lieuResidence;
    private String autreProfession;
    private String telephone;
    private Integer nombreCopie;
    private String numeroDemande;

    @Override
    public String toString() {
        return "FormulaireModification{" +
                "profession=" + profession +
                ", situationMatrimoniale=" + situationMatrimoniale +
                ", lieuResidence='" + lieuResidence + '\'' +
                ", autreProfession='" + autreProfession + '\'' +
                ", telephone='" + telephone + '\'' +
                ", nombreCopie=" + nombreCopie +
                ", numeroDemande='" + numeroDemande + '\'' +
                '}';
    }
}
