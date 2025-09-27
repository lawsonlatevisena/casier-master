package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link tg.ceel.cj.casierapi.entities.TmoneyNotification}
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TmoneyNotificationDto implements Serializable {
    private int version = 1;
    private Long id;
    private String numeroDemande;
    private String recode;
    private Integer amount;
    private Integer currency;
    private String purchaseref;
    private String status;
    private String clientid;
    private String cname;
    private String mobile;
    private String paymentref;
    private String payid;
    private Long tmnTimestamp;
    private String ipaddr;
    private String error;
    private Boolean traiter;
    private Date datecreation;
    private Date rowvers;
}