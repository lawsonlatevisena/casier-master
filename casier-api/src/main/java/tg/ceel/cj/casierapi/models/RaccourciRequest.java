package tg.ceel.cj.casierapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaccourciRequest {
    private String nom;
    private String prenom;
    private String numero_demande;
    private String numero_piece;
    private Date date_naissance;
    private Boolean names_provided;
}
