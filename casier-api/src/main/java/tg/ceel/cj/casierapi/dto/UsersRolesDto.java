package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.UsersRoles}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRolesDto implements Serializable {
    private Long userId;
    private String userUsername;
    private Long roleId;
    private String roleLabel;
    private Boolean active;
    private List<PermissionDto1> rolePermissions = new LinkedList<>();

    /**
     * DTO for {@link tg.ceel.cj.casierapi.entities.Permission}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PermissionDto1 implements Serializable {
        private String code;
        private String label;
        private String categoryCode;
        private String categoryLabel;
    }
}