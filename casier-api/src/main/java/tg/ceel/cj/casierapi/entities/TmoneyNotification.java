
package tg.ceel.cj.casierapi.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;


@Entity
@Table(name = "tmoney_notifications")
@XmlRootElement()
public class TmoneyNotification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "currency")
    private Integer currency;

    @Column(name = "purchaseref")
    private String purchaseref;

    @Column(name = "status")
    private String status;

    @Column(name = "clientid")
    private String clientid;

    @Column(name = "cname")
    private String cname;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "paymentref")
    private String paymentref;

    @Column(name = "payid")
    private String payid;

    @Column(name = "tmn_timestamp")
    private Long tmnTimestamp;

    @XmlElement(name = "ipaddr")
    @Column(name = "ipaddr")
    private String ipaddr;

    @Column(name = "error")
    private String error;

    @Column(name = "traiter")
    private boolean traiter;

    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreation;

    @Column(name = "rowvers")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowvers;
    @Transient
    private String numeroDemande;
    @Transient
    private String recode;

    public TmoneyNotification() {
        traiter = Boolean.FALSE;
        datecreation = new Date();
    }

    public TmoneyNotification(int amount, int currency, String status, String purchaseref, String clientid, String cname, String mobile, String paymentref, String payid, long tmnTimestamp, String ipaddr, String error) {
        this();
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.purchaseref = purchaseref;
        this.clientid = clientid;
        this.cname = cname;
        this.mobile = mobile;
        this.paymentref = paymentref;
        this.payid = payid;
        this.tmnTimestamp = tmnTimestamp;
        this.ipaddr = ipaddr;
        this.error = error;
    }

    public TmoneyNotification(Long id, Integer currency, String purchaseref, String clientid, String cname, String mobile, String paymentref, String payid) {
        this.id = id;
        this.currency = currency;
        this.purchaseref = purchaseref;
        this.clientid = clientid;
        this.cname = cname;
        this.mobile = mobile;
        this.paymentref = paymentref;
        this.payid = payid;
    }

    @XmlTransient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @XmlTransient
    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    @XmlTransient
    public String getPurchaseref() {
        return purchaseref;
    }

    public void setPurchaseref(String purchaseref) {
        this.purchaseref = purchaseref;
    }

    @XmlTransient
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    @XmlTransient
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @XmlTransient
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @XmlTransient
    public String getPaymentref() {
        return paymentref;
    }

    public void setPaymentref(String paymentref) {
        this.paymentref = paymentref;
    }

    @XmlTransient
    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    @XmlTransient
    public Long getTmnTimestamp() {
        return tmnTimestamp;
    }

    public void setTmnTimestamp(Long tmnTimestamp) {
        this.tmnTimestamp = tmnTimestamp;
    }

    @XmlTransient
    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    @XmlTransient
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the traiter
     */
    @XmlTransient
    public boolean isTraiter() {
        return traiter;
    }

    /**
     * @param traiter the traiter to set
     */
    public void setTraiter(boolean traiter) {
        this.traiter = traiter;
    }

    @XmlTransient
    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    @XmlTransient
    public Date getRowvers() {
        return rowvers;
    }

    public void setRowvers(Date rowvers) {
        this.rowvers = rowvers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmoneyNotification)) {
            return false;
        }
        TmoneyNotification other = (TmoneyNotification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TmoneyNotification{" + "id=" + id + ", amount=" + amount + ", currency=" + currency + ", purchaseref=" + purchaseref + ", status=" + status + ", clientid=" + clientid + ", cname=" + cname + ", mobile=" + mobile + ", paymentref=" + paymentref + ", payid=" + payid + ", tmnTimestamp=" + tmnTimestamp + ", ipaddr=" + ipaddr + ", error=" + error + ", traiter=" + traiter + ", datecreation=" + datecreation + ", rowvers=" + rowvers + '}';
    }

}
