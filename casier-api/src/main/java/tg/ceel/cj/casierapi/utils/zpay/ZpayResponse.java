package tg.ceel.cj.casierapi.utils.zpay;

import java.io.Serializable;


public class ZpayResponse implements Serializable {

    private String numeroTransaction;
    private String transactionUUID;
    private Long montant;
    private String devise;
    private String date;
    private String statut;
    private String signedAttributeNames;
    private String errorMessage;
    private String message;
    private String signature;

    public ZpayResponse() {
    }

    public String getNumeroTransaction() {
        return numeroTransaction;
    }

    public void setNumeroTransaction(String numeroTransaction) {
        this.numeroTransaction = numeroTransaction;
    }

    public String getTransactionUUID() {
        return transactionUUID;
    }

    public void setTransactionUUID(String transactionUUID) {
        this.transactionUUID = transactionUUID;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getSignedAttributeNames() {
        return signedAttributeNames;
    }

    public void setSignedAttributeNames(String signedAttributeNames) {
        this.signedAttributeNames = signedAttributeNames;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
