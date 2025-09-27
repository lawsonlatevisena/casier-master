package tg.ceel.cj.casierapi.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recu {
    private String prestation;
    private String prix;
    private String prixReviens;

    @Override
    public String toString() {
        return "Recu{" +
                "prestation='" + prestation + '\'' +
                ", prix='" + prix + '\'' +
                ", prixReviens='" + prixReviens + '\'' +
                '}';
    }
}
