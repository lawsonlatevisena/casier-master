/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", length = 3)
@DiscriminatorValue("SE")
@Table(name = "core_sequences")
public class Sequence extends BaseEntity {
    
    public static final long SEQUENCE_START_VALUE = 1;
    
    @Id
    @Column(name = "code", nullable = false, length = 32)
    protected String code;
    
    @Column(name = "sequence_value", nullable = false)
    protected long sequenceValue;
    
    @Column(name = "step", nullable = false)
    protected int step;

    public Sequence() {
        this.sequenceValue = SEQUENCE_START_VALUE;
        this.step = 1;
        
    }
    
    public Sequence(Sequence sequence) {
        this.code = sequence.code;
        this.sequenceValue = sequence.sequenceValue;
        this.step = sequence.step;
    }

    public Sequence(String code) {
        this();
        this.code = code;
    }
    
    public Sequence(String code, long sequenceValue) {
        this();
        this.code = code;
        this.sequenceValue = sequenceValue;
    }

    public Sequence(String code, long sequenceValue, int step) {
        this.code = code;
        this.sequenceValue = sequenceValue;
        this.step = step;
    }
    
    protected Sequence increment() {
        this.sequenceValue += this.step;
        return this;
    }
    
    public Sequence getNext() {
        return new Sequence(this).increment();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getSequenceValue() {
        return sequenceValue;
    }

    public void setSequenceValue(long sequenceValue) {
        this.sequenceValue = sequenceValue;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.code);
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
        final Sequence other = (Sequence) obj;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public String toString() {
        return "Sequence{" + "code=" + code + ", sequenceValue=" + sequenceValue + ", step=" + step + '}';
    }
}
