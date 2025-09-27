package tg.ceel.cj.casierapi.utils;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Menu {
    private String id="1";
    private String label;
    private String icon;
    private String link;
    private Menu[] subItems;
    private Boolean isTitle;
    private String badge;
    private String parentId;
    private boolean isLayout;
}
