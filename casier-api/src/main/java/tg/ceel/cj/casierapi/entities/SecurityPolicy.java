/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "core_security_policies")
public class SecurityPolicy extends BaseEntity {
    
    @Id
    @Column(name = "code", nullable = false, length = 64)
    private String code;
    
    @Column(name = "max_connection_attempt")
    private int maxConnectionAttempt; // Nombre maximal de tentatives de connexion, 0 pour illimité
    
    // @TODO: politique connexions simultanées

    public SecurityPolicy() {
        this.maxConnectionAttempt = 0;
    }

    public SecurityPolicy(String code, int maxConnectionAttempt) {
        this.code = code;
        this.maxConnectionAttempt = maxConnectionAttempt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMaxConnectionAttempt() {
        return maxConnectionAttempt;
    }

    public void setMaxConnectionAttempt(int maxConnectionAttempt) {
        this.maxConnectionAttempt = maxConnectionAttempt;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.code);
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
        final SecurityPolicy other = (SecurityPolicy) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SecurityPolicy{" + "code=" + code + ", maxConnectionAttempt=" + maxConnectionAttempt + '}';
    }
}
