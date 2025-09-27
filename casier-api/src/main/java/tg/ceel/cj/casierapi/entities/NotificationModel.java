package tg.ceel.cj.casierapi.entities;


import org.springframework.lang.Nullable;
import tg.ceel.cj.casierapi.models.Attachment;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String type;
    private String email;
    private String record;
    @Nullable
    private String subject;
    @Nullable
    private String content;

    /**
     * Attributs for type IN-APP, SMS,MILESTONE
     */
    @Nullable
    private String title;
    @Nullable
    private String message;
    @Nullable
    @Transient
    private String[] cc;
    private String enCopie;
    /**
     * Phone number for SMS
     */
    private String number;
    @Column(columnDefinition = "boolean default false")
    Boolean envoyeAvecSucces;
    private Integer nombreTentantive;
    @Nullable
    @Transient
    private Attachment[] attachments;

    @Nullable
    @Transient
    private Attachment[] files;

    private String link;
    private String code;

    private String ticketLink;
    private Date dateCreation;
    private Date dateEnvoie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    @Nullable
    public String getSubject() {
        return subject;
    }

    public void setSubject(@Nullable String subject) {
        this.subject = subject;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Nullable
    public Attachment[] getAttachments() {
        return attachments;
    }

    public void setAttachments(@Nullable Attachment[] attachments) {
        this.attachments = attachments;
    }

    @Nullable
    public Attachment[] getFiles() {
        return files;
    }

    public void setFiles(@Nullable Attachment[] files) {
        this.files = files;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTicketLink() {
        return ticketLink;
    }

    public void setTicketLink(String ticketLink) {
        this.ticketLink = ticketLink;
    }

    @Nullable
    public String[] getCc() {
        return cc;
    }

    public void setCc(@Nullable String[] cc) {
        this.cc = cc;
    }

    public String getEnCopie() {
        return enCopie;
    }

    public void setEnCopie(String enCopie) {
        this.enCopie = enCopie;
    }

    public Boolean getEnvoyeAvecSucces() {
        return envoyeAvecSucces;
    }

    public void setEnvoyeAvecSucces(Boolean envoyeAvecSucces) {
        this.envoyeAvecSucces = envoyeAvecSucces;
    }

    public Integer getNombreTentantive() {
        return nombreTentantive;
    }

    public void setNombreTentantive(Integer nombreTentantive) {
        this.nombreTentantive = nombreTentantive;
    }

    public String ccToString() {
        if (cc == null) {
            return null;
        }
        return String.join(",", cc);
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(Date dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    @Override
    public String toString() {
        return "NotificationModel{" +
                "type='" + type + '\'' +
                ", email='" + email + '\'' +
                ", record='" + record + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", cc=" + Arrays.toString(cc) +
                ", number='" + number + '\'' +
                ", attachments=" + Arrays.toString(attachments) +
                ", files=" + Arrays.toString(files) +
                ", link='" + link + '\'' +
                ", code='" + code + '\'' +
                ", ticketLink='" + ticketLink + '\'' +
                '}';
    }
}
