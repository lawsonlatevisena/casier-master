package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.CompteurDemande}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteurDemandeDto implements Serializable {
    private int version = 1;
    private Long id;
    private Long numero;
    private Integer annee;
    private Integer typeDemandeId;
    private String typeDemandeLibelle;
    private String typeDemandeCode;
}