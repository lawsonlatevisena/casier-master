package tg.ceel.cj.casierapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.cj.casierapi.utils.CasierUtils;


import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CondamnationModel {

    private Long id;

    private Date dateCondamnation;

    private String stringDateCondamnation;

    private String stringDateMandatDepot;

    private String cours;

    private int quantumPeine;

    private Date dateMandatDepot;

    private String observation;

    private Set<InfractionDto> infractions = new LinkedHashSet();
    private String naturePeines;
    private String observations;

    public String getStringDateCondamnation() {
        if (this.dateCondamnation != null) {
            return CasierUtils.dateToFrString(this.dateCondamnation);
        }
        return "";
    }

    public String getStringDateMandatDepot() {
        if (this.dateMandatDepot != null) {
            return CasierUtils.dateToFrString(this.dateMandatDepot);
        }
        return "";
    }

}
