package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfractionDto implements Serializable {
    private  int version;
    private  Long id;
    private  String libelle;
    private  Date dateInfraction;
}
