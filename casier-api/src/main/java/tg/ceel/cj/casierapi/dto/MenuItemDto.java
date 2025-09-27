package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.MenuItem}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto implements Serializable {
    private int version = 1;
    private String code;
    private String label;
    private String itemPath;
    private String iconClass;
    private String parentItemCode;
    private String parentMenuCode;
    private String permissionCode;
    private String permissionCategoryCode;
}