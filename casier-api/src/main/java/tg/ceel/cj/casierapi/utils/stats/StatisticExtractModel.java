package tg.ceel.cj.casierapi.utils.stats;

import java.io.Serializable;


public class StatisticExtractModel implements Serializable {
    private String typeDemande;
    private Integer codePointRetrait;
    private String pointRetrait;
    private Long nombreCopieFlooz;
    private Long nombreCopieTmoney;
    private Long nombreTotalCopie;
    private Long montantFlooz;
    private Long montantTmoney;
    private Long montantTotal;

    public StatisticExtractModel() {
    }

    public String getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(String typeDemande) {
        this.typeDemande = typeDemande;
    }

    public Integer getCodePointRetrait() {
        return codePointRetrait;
    }

    public void setCodePointRetrait(Integer codePointRetrait) {
        this.codePointRetrait = codePointRetrait;
    }

    public String getPointRetrait() {
        return pointRetrait;
    }

    public void setPointRetrait(String pointRetrait) {
        this.pointRetrait = pointRetrait;
    }

    public Long getNombreCopieFlooz() {
        return nombreCopieFlooz;
    }

    public void setNombreCopieFlooz(Long nombreCopieFlooz) {
        this.nombreCopieFlooz = nombreCopieFlooz;
    }

    public Long getNombreCopieTmoney() {
        return nombreCopieTmoney;
    }

    public void setNombreCopieTmoney(Long nombreCopieTmoney) {
        this.nombreCopieTmoney = nombreCopieTmoney;
    }

    public Long getNombreTotalCopie() {
        return nombreTotalCopie;
    }

    public void setNombreTotalCopie(Long nombreTotalCopie) {
        this.nombreTotalCopie = nombreTotalCopie;
    }

    public Long getMontantFlooz() {
        return montantFlooz;
    }

    public void setMontantFlooz(Long montantFlooz) {
        this.montantFlooz = montantFlooz;
    }

    public Long getMontantTmoney() {
        return montantTmoney;
    }

    public void setMontantTmoney(Long montantTmoney) {
        this.montantTmoney = montantTmoney;
    }

    public Long getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Long montantTotal) {
        this.montantTotal = montantTotal;
    }    

    @Override
    public String toString() {
        return "StatisticExtractModel{" + "typeDemande=" + typeDemande + ", codePointRetrait=" + codePointRetrait + ", pointRetrait=" + pointRetrait + ", nombreCopieFlooz=" + nombreCopieFlooz + ", nombreCopieTmoney=" + nombreCopieTmoney + ", nombreTotalCopie=" + nombreTotalCopie + ", montantFlooz=" + montantFlooz + ", montantTmoney=" + montantTmoney + ", montantTotal=" + montantTotal + '}';
    }
}