package tg.ceel.cj.casierapi.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateObject {
    private Long id_demande;
    private Long id_profession;
    private String autre_profession;
    private Long prefecture_id;
    private String code_pays;
    private Integer id_prefecture;
    private Integer id_situation_matrimoniale;

    @Override
    public String toString() {
        return "UpdateObject{" +
                "id_demande=" + id_demande +
                ", id_profession=" + id_profession +
                ", autre_profession='" + autre_profession + '\'' +
                ", prefecture_id=" + prefecture_id +
                '}';
    }
}
