/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "core_log_categories")
public class LogCategory extends BaseEntity {

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "label", nullable = false, unique = true)
    private String label;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<LogEvent> events = new LinkedList<>();

    public LogCategory() {
    }

    public LogCategory(String code, String libelle) {
        this.code = code;
        this.label = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<LogEvent> getEvents() {
        return events;
    }

    public void setEvents(List<LogEvent> events) {
        this.events = events;
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
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
        final LogCategory other = (LogCategory) obj;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public String toString() {
        return "LogCategory{" + "code=" + code + ", libelle=" + label + '}';
    }
}
