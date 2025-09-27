
package tg.ceel.cj.casierapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;


@MappedSuperclass
public class BaseEntity implements Serializable {

    protected static final long serialVersionUID = 1L;
    
   @XmlTransient
    @Column(name = "version", nullable = true)
    private Integer version = 1;

    @JsonIgnore
    @CreatedBy
    @Column(name = "created_by", length = 50, updatable = false)
    private Long createdBy;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();


    @XmlTransient
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
