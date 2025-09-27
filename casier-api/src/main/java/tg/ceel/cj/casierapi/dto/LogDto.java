package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDto implements Serializable {
    private Long id;
    private Instant createdDate = Instant.now();
    private String action;
    private Date dateAction;
    private String description;
    private String auteur;
    private String cible;
    private String destination;
    private Long userId;
    private String userPassword;
    private Boolean userActive;
    private Boolean userChangePassword;
    private String userIpAddress;
    private String userMacAddress;
    private String userUsername;
}
