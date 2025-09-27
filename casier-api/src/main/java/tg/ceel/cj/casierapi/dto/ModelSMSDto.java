package tg.ceel.cj.casierapi.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.ModelSMS}
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelSMSDto implements Serializable {
    private int version = 1;
    private String code;
    private String nom;
    private String contenu;
}