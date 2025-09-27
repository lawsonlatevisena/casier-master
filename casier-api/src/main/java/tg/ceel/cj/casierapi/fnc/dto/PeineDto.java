package tg.ceel.cj.casierapi.fnc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeineDto implements Serializable {
    private String libelle;
    private String amande;
    private String peineIs;
    private String sursis;
    private String is_sejour;
    private Date datecreation;
    private Date rowvers;
}
