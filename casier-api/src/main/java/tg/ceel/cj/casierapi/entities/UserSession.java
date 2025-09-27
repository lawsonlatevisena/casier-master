/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "core_user_sessions")
public class UserSession extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = true)
    private Date endDate;
    
    @Column(name = "session_id", nullable = true)
    private String sessionId;
    
    @Column(name = "user_host", nullable = false)
    private String host;
    
    @Column(name = "token", nullable = false)
    private String token;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserSession() {
    }

    public UserSession(Date startDate, String host) {
        this.startDate = startDate;
        this.host = host;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public boolean isOpened() {
        return this.endDate == null;
    }
    
    public boolean isClosed() {
        return this.endDate != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final UserSession other = (UserSession) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "UserSession{" + "id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", host=" + host + ", token=" + token + ", user=" + user + '}';
    }
}
