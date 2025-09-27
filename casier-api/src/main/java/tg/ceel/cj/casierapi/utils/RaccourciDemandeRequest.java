package tg.ceel.cj.casierapi.utils;

/**
 *
 * @author lkpeto
 */
public class RaccourciDemandeRequest {
    private String  nom;
    private String  prenom;
    private String  numeroDemande;
    private String  numeroPiece;
    private Boolean namesProvided;
    private String  dateNaissance;

    public RaccourciDemandeRequest() {}

    public RaccourciDemandeRequest(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.namesProvided = true;
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

    public String getNumeroDemande() {
        return numeroDemande;
    }

    public void setNumeroDemande(String numeroDemande) {
        this.numeroDemande = numeroDemande;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public Boolean getNamesProvided() {
        return namesProvided;
    }

    public void setNamesProvided(Boolean namesProvided) {
        this.namesProvided = namesProvided;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "RaccourciDemandeRequest{" + "nom=" + nom + ", prenom=" + prenom + ", numeroDemande=" + numeroDemande + ", numeroPiece=" + numeroPiece + ", namesProvided=" + namesProvided + ", dateNaissance=" + dateNaissance + '}';
    }
}