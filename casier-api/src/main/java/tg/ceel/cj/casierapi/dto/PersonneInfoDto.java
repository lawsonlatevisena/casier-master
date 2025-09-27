package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonneInfoDto implements Serializable {
    private  int version;
    private  Long id;
    private  String nom;
    private  String prenom;
    private  String email;
    private  String telephone;
    private  String localite;
    private  String sexeLibelle;
    private  String sexeCode;
    private  UserDto user;

}
