/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("YS")
public class YearSequence extends Sequence {
    
    @Column(name = "sequence_year", nullable = true)
    private int sequenceYear;

    public YearSequence() {
        super();
        this.sequenceYear = LocalDate.now().getYear();
    }
    
    public YearSequence(YearSequence sequence) {
        super(sequence);
        this.sequenceYear = sequence.sequenceYear;
    }

    public YearSequence(int sequenceYear, String code, long sequenceValue) {
        super(code, sequenceValue);
        this.sequenceYear = sequenceYear;
    }

    public YearSequence(int sequenceYear, String code, long sequenceValue, int step) {
        super(code, sequenceValue, step);
        this.sequenceYear = sequenceYear;
    }

    @Override
    public Sequence increment() {
        int year = LocalDate.now().getYear();
        if (year > this.getSequenceYear()) {
            this.sequenceYear = year;
            this.sequenceValue = Sequence.SEQUENCE_START_VALUE;
        } else {
            this.sequenceValue += this.step;
        }
        return this;
    }

    public int getSequenceYear() {
        return sequenceYear;
    }

    public void setSequenceYear(int sequenceYear) {
        this.sequenceYear = sequenceYear;
    }
}
