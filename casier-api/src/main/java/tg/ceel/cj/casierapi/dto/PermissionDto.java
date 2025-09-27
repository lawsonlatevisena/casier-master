package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDto implements Serializable {
    private int version = 1;
    private String code;
    private String label;
    private String categoryCode;
    private String categoryLabel;
    private String categoryDescription;
}
