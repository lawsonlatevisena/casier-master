/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "retraits")
public class Retrait extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "nom", nullable = false, length = 31)
    private String nom;
    
    @Column(name = "prenom", nullable = false, length = 51)
    private String prenom;
    
    @Column(name = "numero_piece", nullable = false, length = 21)
    private String numeroPiece;
    
    @Column(name = "telephone", nullable = false, length = 21)
    private String telephone;
    
    @Column(name = "adresse", nullable = true, length = 51)
    private String adresse;
    
    @Column(name = "date_retrait", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRetrait;
    
    @Column(name = "file_name", nullable = true, length = 251)
    private String fileName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_piece", nullable = false)
    private TypePiece typePiece;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_casier", nullable = false)
    private UtilisateurCasier utilisateurCasier;
    
    private void writeObject(ObjectOutputStream stream) throws IOException {
        List<TypePiece> typePieces = new ArrayList<>();
        List<UtilisateurCasier> utilisateurCasiers = new ArrayList<>();
        List<Demande> demandes = new ArrayList<>();
        typePieces.add(typePiece);
        utilisateurCasiers.add(utilisateurCasier);
        demandes.add(demande);
        typePieces.isEmpty();
        utilisateurCasiers.isEmpty();
        demandes.isEmpty();
        stream.defaultWriteObject();
    }

    public Retrait() {
    }

    public Retrait(Integer id, UtilisateurCasier utilisateurCasier, String nom, String prenom, String numeroPiece, String telephone, String adresse, Date dateRetrait, TypePiece typePiece, Demande demande) {
        this.id = id;
        this.utilisateurCasier = utilisateurCasier;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPiece = numeroPiece;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateRetrait = dateRetrait;
        this.typePiece = typePiece;
        this.demande = demande;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UtilisateurCasier getUtilisateurCasier() {
        return utilisateurCasier;
    }

    public void setUtilisateurCasier(UtilisateurCasier utilisateurCasier) {
        this.utilisateurCasier = utilisateurCasier;
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

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(Date dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public TypePiece getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(TypePiece typePiece) {
        this.typePiece = typePiece;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Retrait other = (Retrait) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Retrait{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom 
                + ", numeroPiece=" + numeroPiece + ", telephone=" + telephone 
                + ", adresse=" + adresse + ", dateRetrait=" + dateRetrait + '}';
    }
    
}
