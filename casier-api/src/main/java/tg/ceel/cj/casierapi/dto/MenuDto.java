package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.Menu}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto implements Serializable {
    private int version = 1;
    private String code;
    private String label;
    private String description;
    private List<MenuItemDto> items = new ArrayList<>();

}