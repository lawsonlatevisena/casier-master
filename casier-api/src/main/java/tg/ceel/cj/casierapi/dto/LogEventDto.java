package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.LogEvent}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEventDto implements Serializable {
    private int version = 1;
    private Long id;
    private String message;
    private Date eventDate;
    private String clientHost;
    private Long userId;
    private String userUsername;
    private String categoryCode;
    private String categoryLabel;
    private Integer levelId;
    private String levelLabel;
    private String updatedObject;
    private Long demandeId;
}