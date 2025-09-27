/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.poste;

import java.io.Serializable;

/**
 *
 * @author lkpeto
 */
public class ColisAttachment implements Serializable{
    private String kind;
    private String val;
    private Boolean internal;

    public ColisAttachment() {
    }

    public ColisAttachment(String kind, String val) {
        this.kind = kind;
        this.val = val;
    }

    public ColisAttachment(String kind, String val, Boolean internal) {
        this.kind = kind;
        this.val = val;
        this.internal = internal;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Boolean getInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }
}
