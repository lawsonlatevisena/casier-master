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
public class Plis {
    private String extappkey;
    private String notes;
    private List<ColisAttachment> attachments = new ArrayList<>();
    private List<String> cextids = new ArrayList<>();
    private String newstep;
    private String actiondate;

    public Plis() {
    }

    public String getExtappkey() {
        return extappkey;
    }

    public void setExtappkey(String extappkey) {
        this.extappkey = extappkey;
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

    public void setAttachments(List<ColisAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<String> getCextids() {
        return cextids;
    }

    public void setCextids(List<String> cextids) {
        this.cextids = cextids;
    }

    public String getNewstep() {
        return newstep;
    }

    public void setNewstep(String newstep) {
        this.newstep = newstep;
    }

    public String getActiondate() {
        return actiondate;
    }

    public void setActiondate(String actiondate) {
        this.actiondate = actiondate;
    }

    @Override
    public String toString() {
        return "Plis{" + "extappkey=" + extappkey + ", notes=" + notes + ", attachments=" + attachments + ", cextids=" + cextids + ", newstep=" + newstep + ", actiondate=" + actiondate + '}';
    }
}
