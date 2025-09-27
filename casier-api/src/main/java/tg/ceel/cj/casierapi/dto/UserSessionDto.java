package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.ceel.cj.casierapi.entities.UserSession;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link UserSession}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionDto implements Serializable {
    private int version = 1;
    private Long id;
    private Date startDate;
    private Date endDate;
    private String sessionId;
    private String host;
    private String token;
    private int userVersion = 1;
    private Long userId;
    private String userUsername;
}