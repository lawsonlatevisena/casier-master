package tg.ceel.cj.casierapi.utils.stats;


public class StatisticDelayedModel {
    private String typeDemande;
    private Long count;
    private Long pointRetraitId;
    private String pointRetrait;

    public StatisticDelayedModel() {
    }

    public StatisticDelayedModel(String typeDemande, Long count) {
        this.typeDemande = typeDemande;
        this.count = count;
    }

    public StatisticDelayedModel(String typeDemande, Long count, Long pointRetraitId, String pointRetrait) {
        this.typeDemande = typeDemande;
        this.count = count;
        this.pointRetraitId = pointRetraitId;
        this.pointRetrait = pointRetrait;
    }

    public String getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(String typeDemande) {
        this.typeDemande = typeDemande;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPointRetraitId() {
        return pointRetraitId;
    }

    public void setPointRetraitId(Long pointRetraitId) {
        this.pointRetraitId = pointRetraitId;
    }

    public String getPointRetrait() {
        return pointRetrait;
    }

    public void setPointRetrait(String pointRetrait) {
        this.pointRetrait = pointRetrait;
    }

    @Override
    public String toString() {
        return "StatisticDelayedModel{" + "typeDemande=" + typeDemande + ", count=" + count + ", pointRetraitId=" + pointRetraitId + ", pointRetrait=" + pointRetrait + '}';
    }
}