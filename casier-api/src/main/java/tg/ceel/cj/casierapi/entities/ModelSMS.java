/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;
import java.util.Objects;


@Entity
@Table(name = "model_sms")
public class ModelSMS extends BaseEntity {

    public static final String SEQUENCE_CODE = "MS/";
    public static final String DMDSAV = "DMDSAV";
    public static final String DMDUPD = "DMDUPD";
    public static final String DMDSUP = "DMDSUP";
    public static final String DMDREJ = "DMDREJ";
    public static final String DMDDSP = "DMDDSP";
    public static final String DMDRET = "DMDRET";

    @Id
    @Column(name = "code", nullable = false, length = 31)
    private String code;

    @Column(name = "nom", nullable = false, length = 121)
    private String nom;

    @Column(name = "contenu", nullable = false, length = 251)
    private String contenu;

    public ModelSMS() {
    }

    public ModelSMS(String code, String nom, String contenu) {
        this.code = code;
        this.nom = nom;
        this.contenu = contenu;
    }

    public String traiter(Map<String, String> params) {
        String resultat = this.contenu;
        for (Map.Entry<String, String> e : params.entrySet()) {
            String param = "#[" + e.getKey() + "]";
            resultat = resultat.replace(param, e.getValue());
        }
        return resultat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModelSMS other = (ModelSMS) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModeleSMS{" + "code=" + code + ", nom=" + nom + ", contenu=" + contenu + '}';
    }

}
