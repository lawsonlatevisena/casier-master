package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FonctionDto implements Serializable {
    private  int version;
    private  Integer id;
    private  String libelle;
}
