/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.utils;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author believe
 */
public class EtatDemande implements Serializable{
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
