package tg.ceel.cj.casierapi.models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelData {
    private String peer;
    private String label;
    private String type;
    private String slug;
    private String token;

    @Override
    public String toString() {
        return "ModelData{" +
                "peer='" + peer + '\'' +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", slug='" + slug + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
