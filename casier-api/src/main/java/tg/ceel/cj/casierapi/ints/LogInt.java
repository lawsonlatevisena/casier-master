package tg.ceel.cj.casierapi.ints;

import java.time.Instant;
import java.util.Date;

public interface LogInt {
    Long getId();

    Instant getCreated_date();

    String getAction();

    Date getDate_action();

    String getDescription();

    String getAuteur();

    String getUtilisateur();

    Long getUtilisateur_id();

    String getCible();

    String getDestination();

    Long getUser_id();

    String getUsername();

    Long getDemande_id();

    String userIpAddress();

    String userMacAddress();
}
