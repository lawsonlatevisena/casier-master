package tg.ceel.cj.casierapi.models;

import java.io.Serializable;
public class EtatDemande implements Serializable {
    private String date;
    private String etat;

    public EtatDemande() {
    }
    public EtatDemande(String date, String etat) {
        this.date = date;
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
 }
}
