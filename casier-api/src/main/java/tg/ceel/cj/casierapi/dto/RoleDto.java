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
public class RoleDto implements Serializable {
    private  int version;
    private  Long id;
    private  String label;
    private  Boolean active;
    private  List<PermissionDto> permissions;

}
