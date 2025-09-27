/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.poste;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lkpeto
 */
public class Colis {
    private String extappkey;
    private String cextid;
    private String notes;
    private String naturalkey;
    private List<ColisAttachment> attachments = new ArrayList<>();
   
    public Colis() {
    }

    public String getExtappkey() {
        return extappkey;
    }

    public void setExtappkey(String extappkey) {
        this.extappkey = extappkey;
    }

    public String getCextid() {
        return cextid;
    }

    public void setCextid(String cextid) {
        this.cextid = cextid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<ColisAttachment> getAttachments() {
        return attachments;
    }

    public String getNaturalkey() {
        return naturalkey;
    }

    public void setNaturalkey(String naturalkey) {
        this.naturalkey = naturalkey;
    }

    public void setAttachments(List<ColisAttachment> attachments) {
        this.attachments = attachments;
    }
}
