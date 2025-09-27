package tg.ceel.cj.casierapi.models;

import tg.ceel.cj.casierapi.dto.PointRetraitDto;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.Payement;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.utils.CasierUtils;

import java.io.Serializable;

public class DemandeUtils implements Serializable {

    private String numero;
    private String nom;
    private String prenom;
    private String typePiece;
    private String numeroTypePiece;
    private String dateDemande;
    private String dateDisponibilites;
    private String dateArriveeTogo;
    private EtatDemande validation;
    private EtatDemande traiter;
    private EtatDemande disponible;
    private EtatDemande retirer;
    private EtatDemande rejeter;
    private String motifRejetIdentite;
    private String motifRejetInfoPere;
    private String motifRejetInfoMere;
    private boolean payer;
    private double montant;
    private String deliveryData;
    private PointRetraitDto pointRetrait;
    private boolean isColisPoste;
    private PointRetraitDto bureauPosteRetrait;


    public DemandeUtils() {
    }

    public DemandeUtils(Demande demandeB3, String numeroPiece) {
        System.err.println(demandeB3);
        if (demandeB3 != null
                && ((demandeB3.getNumeroActe() != null && demandeB3.getNumeroActe().compareToIgnoreCase(numeroPiece) == 0)
                || (demandeB3.getNumeroCarte() != null && demandeB3.getNumeroCarte().compareToIgnoreCase(numeroPiece) == 0)
                || (demandeB3.getNumeroPasseport() != null && demandeB3.getNumeroPasseport().compareToIgnoreCase(numeroPiece) == 0)
                || (demandeB3.getNumeroJugement() != null && demandeB3.getNumeroJugement().compareTo(numeroPiece) == 0)
        )) {
            DemandeUtils demandeUtils = new DemandeUtils();
            this.nom = demandeB3.getNom();
            this.prenom = demandeB3.getPrenom();
            this.dateDemande = CasierUtils.datetoString(demandeB3.getDateDemande());
            this.typePiece = demandeB3.getTypePiece().getLibelle();
            this.numeroTypePiece = numeroPiece;
            this.numero = demandeB3.getNumeroDemande();
            this.motifRejetIdentite = demandeB3.getEtape1Motif() == null ? "" : demandeB3.getEtape1Motif();
            this.motifRejetInfoMere = demandeB3.getEtape3Motif() == null ? "" : demandeB3.getEtape3Motif();
            this.motifRejetInfoPere = demandeB3.getEtape2Motif() == null ? "" : demandeB3.getEtape2Motif();
            if (!demandeB3.getInvalidee()) {
                this.validation = new EtatDemande("", "FALSE");
                this.rejeter = new EtatDemande("", "FALSE");
            } else {
                this.validation = demandeB3.getValider() == false
                        ? new EtatDemande((demandeB3.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB3.getDateValidation())),
                        "FALSE") : new EtatDemande((demandeB3.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB3.getDateValidation())), "TRUE");

                this.rejeter = demandeB3.getInvalidee() == true
                        ? new EtatDemande((demandeB3.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB3.getDateValidation())),
                        "TRUE") : new EtatDemande((demandeB3.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB3.getDateValidation())), "FALSE");
            }

            if (demandeB3.getDisponible() == null) {
                this.traiter = new EtatDemande((demandeB3.getDateEdition() == null ? "" : CasierUtils.datetoString(demandeB3.getDateEdition())), "FALSE");
                this.disponible = new EtatDemande("", "FALSE");
            } else if (demandeB3.getTraitee() == false) {
                this.traiter = new EtatDemande((demandeB3.getDateEdition() == null ? "" : CasierUtils.datetoString(demandeB3.getDateEdition())), "FALSE");
                this.disponible = new EtatDemande("", "FALSE");
            } else {
                this.traiter = new EtatDemande((demandeB3.getDateEdition() == null ? "" : CasierUtils.datetoString(demandeB3.getDateEdition())), "TRUE");
                this.disponible = demandeB3.getDisponible()
                        ? new EtatDemande((demandeB3.getDateDisponibilite() == null ? "" : CasierUtils.datetoString(demandeB3.getDateDisponibilite())), "TRUE")
                        : new EtatDemande("", "FALSE");
            }

            Payement payement = demandeB3.getPayement();
            this.payer = payement == null ? false : payement.getRegler();

            this.retirer = demandeB3.getRetirer() ? new EtatDemande(CasierUtils.datetoString(demandeB3.getDateRetrait()), "TRUE")
                    : new EtatDemande("", "FALSE");
            this.pointRetrait = this.convertToPointDto(demandeB3.getPointRetrait());
            this.isColisPoste = CasierUtils.isLivraisonPoste(demandeB3);
            this.bureauPosteRetrait = this.convertToPointDto(demandeB3.getBureauPosteRetrait());
        }
    }

    /*public DemandeUtils(DemandeB2 demandeB2) {
        if (demandeB2 != null) {
            DemandeUtils demandeUtils = new DemandeUtils();
            this.nom = demandeB2.getNom();
            this.prenom = demandeB2.getPrenom();
            this.dateDemande = (CasierUtils.datetoString(demandeB2.getDateDemande()));
            this.numero = demandeB2.getNumeroDemande();
            this.motifRejetIdentite = demandeB2.getEtape1Motif() == null ? "" : demandeB2.getEtape1Motif();
            this.motifRejetInfoMere = demandeB2.getEtape3Motif() == null ? "" : demandeB2.getEtape3Motif();
            this.motifRejetInfoPere = demandeB2.getEtape2Motif() == null ? "" : demandeB2.getEtape3Motif();
            if (demandeB2.getValider() == null) {
                this.validation = new EtatDemande("", "FALSE");
                this.rejeter = new EtatDemande("", "FALSE");
            } else {
                this.validation = demandeB2.getValider() == false
                        ? new EtatDemande((demandeB2.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB2.getDateValidation())),
                        "FALSE") : new EtatDemande((demandeB2.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB2.getDateValidation())), "TRUE");

                this.rejeter = demandeB2.getValider() == false
                        ? new EtatDemande((demandeB2.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB2.getDateValidation())),
                        "TRUE") : new EtatDemande((demandeB2.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB2.getDateValidation())), "FALSE");
            }



            if (demandeB2.getDisponible() == null) {
                this.traiter = new EtatDemande((demandeB2.getDateEdition() == null ? "" : CasierUtils.datetoString(demandeB2.getDateEdition())), "FALSE");
                this.disponible = new EtatDemande("", "FALSE");
            } else if (demandeB2.getDisponible() == false) {
                this.traiter = new EtatDemande((demandeB2.getDateEdition() == null ? "" : CasierUtils.datetoString(demandeB2.getDateEdition())), "TRUE");
                this.disponible = new EtatDemande("", "FALSE");
            } else {
                this.traiter = new EtatDemande((demandeB2.getDateEdition() == null ? "" : CasierUtils.datetoString(demandeB2.getDateEdition())), "TRUE");
                this.disponible = demandeB2.getDisponible()
                        ? new EtatDemande((demandeB2.getDateDisponibilite() == null ? "" : CasierUtils.datetoString(demandeB2.getDateDisponibilite())), "TRUE")
                        : new EtatDemande("", "FALSE");
            }

            Payement payement = demandeB2.getPayement();
            this.payer = payement == null ? false : payement.isRegler();

            this.retirer = demandeB2.isRetirer() ? new EtatDemande(CasierUtils.datetoString(demandeB2.getDateRetrait()), "TRUE")
                    : new EtatDemande("", "FALSE");
            this.pointRetrait = demandeB2.getPointRetrait();
            this.isColisPoste = CasierUtils.isLivraisonPoste(demandeB2);
            this.bureauPosteRetrait = demandeB2.getBureauPosteRetrait();
        }
    }

    public DemandeUtils(DemandeB1 demandeB1) {
        if (demandeB1 != null) {
            DemandeUtils demandeUtils = new DemandeUtils();
            this.nom = demandeB1.getNom();
            this.prenom = demandeB1.getPrenom();
            this.dateDemande = (CasierUtils.datetoString(demandeB1.getDateDemande()));
//            this.numero = demandeB1.getId();
            this.numero = demandeB1.getNumeroDemande();
            this.motifRejetIdentite = demandeB1.getEtape1Motif() == null ? "" : demandeB1.getEtape1Motif();
            this.motifRejetInfoMere = demandeB1.getEtape3Motif() == null ? "" : demandeB1.getEtape3Motif();
            this.motifRejetInfoPere = demandeB1.getEtape2Motif() == null ? "" : demandeB1.getEtape3Motif();
            if (demandeB1.getValider() == null) {
                this.validation = new EtatDemande("", "FALSE");
                this.rejeter = new EtatDemande("", "FALSE");
            } else {
                this.validation = demandeB1.getValider() == false
                        ? new EtatDemande((demandeB1.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB1.getDateValidation())),
                        "FALSE") : new EtatDemande((demandeB1.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB1.getDateValidation())), "TRUE");

                this.rejeter = demandeB1.getValider() == false
                        ? new EtatDemande((demandeB1.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB1.getDateValidation())),
                        "TRUE") : new EtatDemande((demandeB1.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeB1.getDateValidation())), "FALSE");
            }



            if (demandeB1.getDisponible() == null) {
                this.traiter = new EtatDemande((demandeB1.getDateEdition() == null ? "" : CasierUtils.datetoString(demandeB1.getDateEdition())), "FALSE");
                this.disponible = new EtatDemande("", "FALSE");
            } else if (demandeB1.getDisponible() == false) {
                this.traiter = new EtatDemande((demandeB1.getDateEdition() == null ? "" : CasierUtils.datetoString(demandeB1.getDateEdition())), "TRUE");
                this.disponible = new EtatDemande("", "FALSE");
            } else {
                this.traiter = new EtatDemande((demandeB1.getDateEdition() == null ? "" : CasierUtils.datetoString(demandeB1.getDateEdition())), "TRUE");
                this.disponible = demandeB1.getDisponible()
                        ? new EtatDemande((demandeB1.getDateDisponibilite() == null ? "" : CasierUtils.datetoString(demandeB1.getDateDisponibilite())), "TRUE")
                        : new EtatDemande("", "FALSE");
            }

            this.retirer = demandeB1.isRetirer() ? new EtatDemande(CasierUtils.datetoString(demandeB1.getDateRetrait()), "TRUE")
                    : new EtatDemande("", "FALSE");
            this.pointRetrait = demandeB1.getPointRetrait();
            this.isColisPoste = CasierUtils.isLivraisonPoste(demandeB1);
            this.bureauPosteRetrait = demandeB1.getBureauPosteRetrait();
        }
    }


    public DemandeUtils(DemandeANC demandeANC, String numeroPiece) {
        if (demandeANC != null
                && ((demandeANC.getNumeroActe() != null && demandeANC.getNumeroActe().compareToIgnoreCase(numeroPiece) == 0)
                || (demandeANC.getNumeroCarte() != null && demandeANC.getNumeroCarte().compareToIgnoreCase(numeroPiece) == 0)
                || (demandeANC.getNumeroPasseport() != null && demandeANC.getNumeroPasseport().compareToIgnoreCase(numeroPiece) == 0))) {
            DemandeUtils demandeUtils = new DemandeUtils();
            this.setNom(demandeANC.getNom());
            this.prenom = (demandeANC.getPrenom());
            this.dateDemande = (CasierUtils.datetoString(demandeANC.getDateDemande()));
            this.typePiece = demandeANC.getTypePiece().getLibelle();
            this.numeroTypePiece = numeroPiece;
//            this.numero = demandeANC.getId();
            this.numero = demandeANC.getNumeroDemande();
            this.motifRejetIdentite = demandeANC.getEtape1Motif() == null ? "" : demandeANC.getEtape1Motif();
            this.motifRejetInfoMere = demandeANC.getEtape3Motif() == null ? "" : demandeANC.getEtape3Motif();
            this.motifRejetInfoPere = demandeANC.getEtape2Motif() == null ? "" : demandeANC.getEtape2Motif();
            if (demandeANC.getValider() == null) {
                this.validation = new EtatDemande("", "FALSE");
                this.rejeter = new EtatDemande("", "FALSE");
            } else {
                this.validation = demandeANC.getValider() == false
                        ? new EtatDemande((demandeANC.getDateValidation() == null ? "" : demandeANC.getDateValidation().toString()),
                        "FALSE") : new EtatDemande((demandeANC.getDateValidation() == null ? "" : demandeANC.getDateValidation().toString()), "TRUE");
                this.rejeter = demandeANC.getValider() == false
                        ? new EtatDemande((demandeANC.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeANC.getDateValidation())),
                        "TRUE") : new EtatDemande((demandeANC.getDateValidation() == null ? "" : CasierUtils.datetoString(demandeANC.getDateValidation())), "FALSE");

            }

            if (demandeANC.getDisponible() == null) {
                this.traiter = new EtatDemande((demandeANC.getDateEdition() == null ? "" : demandeANC.getDateEdition().toString()), "FALSE");
                this.disponible = new EtatDemande("", "FALSE");
            } else if (demandeANC.getDisponible() == false) {
                this.traiter = new EtatDemande((demandeANC.getDateEdition() == null ? "" : demandeANC.getDateEdition().toString()), "TRUE");
                this.disponible = new EtatDemande("", "FALSE");
            } else {
                this.traiter = new EtatDemande((demandeANC.getDateEdition() == null ? "" : demandeANC.getDateEdition().toString()), "TRUE");
                this.disponible  = demandeANC.getDisponible()
                        ? new EtatDemande((demandeANC.getDateDisponibilite() == null ? "" : demandeANC.getDateDisponibilite().toString()), "TRUE")
                        : new EtatDemande("", "FALSE");
            }
            this.retirer = demandeANC.isRetirer() ? new EtatDemande((demandeANC.getDateRetrait() == null ? "" : demandeANC.getDateRetrait().toString()), "TRUE")
                    : new EtatDemande("", "FALSE");

            if (demandeANC.getPayement() == null) {
                this.payer = false;
            } else {
                this.payer = demandeANC.getPayement().isRegler();
            }

            this.dateArriveeTogo = CasierUtils.datetoString(demandeANC.getDateArriveeTogo());
            this.pointRetrait = demandeANC.getPointRetrait();
            this.isColisPoste = CasierUtils.isLivraisonPoste(demandeANC);
            this.bureauPosteRetrait = demandeANC.getBureauPosteRetrait();
        }
    }*/

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public boolean isPayer() {
        return payer;
    }

    public void setPayer(boolean payer) {
        this.payer = payer;
    }

    public String getMotifRejetIdentite() {
        return motifRejetIdentite;
    }

    public void setMotifRejetIdentite(String motifRejetIdentite) {
        this.motifRejetIdentite = motifRejetIdentite;
    }

    public String getMotifRejetInfoPere() {
        return motifRejetInfoPere;
    }

    public void setMotifRejetInfoPere(String motifRejetInfoPere) {
        this.motifRejetInfoPere = motifRejetInfoPere;
    }

    public String getMotifRejetInfoMere() {
        return motifRejetInfoMere;
    }

    public void setMotifRejetInfoMere(String motifRejetInfoMere) {
        this.motifRejetInfoMere = motifRejetInfoMere;
    }

    public EtatDemande getRejeter() {
        return rejeter;
    }

    public void setRejeter(EtatDemande rejeter) {
        this.rejeter = rejeter;
    }

    public EtatDemande getValidation() {
        return validation;
    }

    public void setValidation(EtatDemande validation) {
        this.validation = validation;
    }

    public EtatDemande getTraiter() {
        return traiter;
    }

    public void setTraiter(EtatDemande traiter) {
        this.traiter = traiter;
    }

    public EtatDemande getDisponible() {
        return disponible;
    }

    public void setDisponible(EtatDemande disponible) {
        this.disponible = disponible;
    }

    public EtatDemande getRetirer() {
        return retirer;
    }

    public void setRetirer(EtatDemande retirer) {
        this.retirer = retirer;
    }

    public String getDateArriveeTogo() {
        return dateArriveeTogo;
    }

    public void setDateArriveeTogo(String dateArriveeTogo) {
        this.dateArriveeTogo = dateArriveeTogo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }

    public String getNumeroTypePiece() {
        return numeroTypePiece;
    }

    public void setNumeroTypePiece(String numeroTypePiece) {
        this.numeroTypePiece = numeroTypePiece;
    }

    public String getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(String dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getDateDisponibilites() {
        return dateDisponibilites;
    }

    public void setDateDisponibilites(String dateDisponibilites) {
        this.dateDisponibilites = dateDisponibilites;
    }

    public String getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(String deliveryData) {
        this.deliveryData = deliveryData;
    }

    public PointRetraitDto getPointRetrait() {
        return pointRetrait;
    }

    public void setPointRetrait(PointRetraitDto pointRetrait) {
        this.pointRetrait = pointRetrait;
    }

    public boolean isIsColisPoste() {
        return isColisPoste;
    }

    public void setIsColisPoste(boolean isColisPoste) {
        this.isColisPoste = isColisPoste;
    }

    public PointRetraitDto getBureauPosteRetrait() {
        return bureauPosteRetrait;
    }

    public void setBureauPosteRetrait(PointRetraitDto bureauPosteRetrait) {
        this.bureauPosteRetrait = bureauPosteRetrait;
    }
    private PointRetraitDto convertToPointDto(PointRetrait pointRetrait){
        if(pointRetrait == null){
            return null;
        }
        return PointRetraitDto.builder()
                .id(pointRetrait.getId())
                .code(pointRetrait.getCode())
                .localite(pointRetrait.getLocalite())
                .latitude(pointRetrait.getLatitude())
                .longitude(pointRetrait.getLongitude())
                .libelle(pointRetrait.getLibelle())
                .libelleLong(pointRetrait.getLibelleLong())
                .build();
    }
}
