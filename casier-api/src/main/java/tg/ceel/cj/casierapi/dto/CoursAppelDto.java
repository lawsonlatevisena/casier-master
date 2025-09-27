package tg.ceel.cj.casierapi.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
public class CoursAppelDto implements Serializable {
    private  int version;
    private  Integer id;
    private  String libelle;
}
