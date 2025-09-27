package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCategoryDto implements Serializable {
    private  int version;
    private  String code;
    private  String label;
    private  String description;
    private  List<PermissionDto> permissions;
}
