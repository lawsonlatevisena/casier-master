package tg.ceel.cj.casierapi.dto;

import lombok.*;
import tg.ceel.cj.casierapi.entities.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private int version = 1;
    private Long id;
    private String username;
    private String password;
    private Boolean active;
    private Boolean changePassword;
    private Date dateDerniereConnexion;
    private Date dateDerniereDeconnexion;
    private Boolean isPasswordSet;
    private List<RoleDto> roles ;
}
