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
@Table(name = "core_log_levels")
public class LogLevel extends BaseEntity {

    public static final Integer LOG_LEVEL_TRACE_ID = 1;
    public static final Integer LOG_LEVEL_DEBUG_ID = 2;
    public static final Integer LOG_LEVEL_INFO_ID = 3;
    public static final Integer LOG_LEVEL_WARNING_ID = 4;
    public static final Integer LOG_LEVEL_ERROR_ID = 5;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "label", nullable = false)
    private String label;

    /* ici on a pas besoin d'avoir la liste des differents logEvent qui ont
     utilisé le logLevel */
    public LogLevel() {
    }

    public LogLevel(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
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
        final LogLevel other = (LogLevel) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "LogLevel{" + "id=" + id + ", label=" + label + '}';
    }
}
