package tg.ceel.cj.casierapi.utils.stats;

import java.io.Serializable;


public class StatisticAggregateModel implements Serializable {
    private Integer codePointRetrait;
    private String libellePointRetrait;
    private String typeDemande;
    private Long payes;
    private Long attentesValidation;
    private Long valides;
    private Long rejetes;
    private Long disponibles;
    private Long retires;

    public StatisticAggregateModel() {
    }

    public Integer getCodePointRetrait() {
        return codePointRetrait;
    }

    public void setCodePointRetrait(Integer codePointRetrait) {
        this.codePointRetrait = codePointRetrait;
    }

    public String getLibellePointRetrait() {
        return libellePointRetrait;
    }

    public void setLibellePointRetrait(String libellePointRetrait) {
        this.libellePointRetrait = libellePointRetrait;
    }

    public String getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(String typeDemande) {
        this.typeDemande = typeDemande;
    }

    public Long getPayes() {
        return payes;
    }

    public void setPayes(Long payes) {
        this.payes = payes;
    }

    public Long getAttentesValidation() {
        return attentesValidation;
    }

    public void setAttentesValidation(Long attentesValidation) {
        this.attentesValidation = attentesValidation;
    }

    public Long getValides() {
        return valides;
    }

    public void setValides(Long valides) {
        this.valides = valides;
    }

    public Long getRejetes() {
        return rejetes;
    }

    public void setRejetes(Long rejetes) {
        this.rejetes = rejetes;
    }

    public Long getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(Long disponibles) {
        this.disponibles = disponibles;
    }

    public Long getRetires() {
        return retires;
    }

    public void setRetires(Long retires) {
        this.retires = retires;
    }

    @Override
    public String toString() {
        return "StatisticAggregateModel{" + "codePointRetrait=" + codePointRetrait + ", libellePointRetrait=" + libellePointRetrait + ", typeDemande=" + typeDemande + ", payes=" + payes + ", attentesValidation=" + attentesValidation + ", valides=" + valides + ", rejetes=" + rejetes + ", disponibles=" + disponibles + ", retires=" + retires + '}';
    }
}