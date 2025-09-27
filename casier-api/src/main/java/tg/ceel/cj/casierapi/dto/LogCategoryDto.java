package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogCategoryDto implements Serializable {
    private  int version;
    private  String code;
    private  String label;

}
