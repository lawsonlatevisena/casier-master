
package tg.ceel.cj.casierapi.utils;

import java.io.Serializable;
import java.util.Objects;


public class JRDSModel implements Serializable {
    private String dateCondamnation;
    private String tribunal;
    private String natureCrimes;
    private String dateCrimes;
    private String naturePeines;
    private String dateMandat;
    private String observations;

    public JRDSModel() {
    }

    public JRDSModel(String dateCondamnation, String tribunal, String natureCrimes, String dateCrimes, String naturePeines, String dateMandat, String observations) {
        this.dateCondamnation = dateCondamnation;
        this.tribunal = tribunal;
        this.natureCrimes = natureCrimes;
        this.dateCrimes = dateCrimes;
        this.naturePeines = naturePeines;
        this.dateMandat = dateMandat;
        this.observations = observations;
    }

    public String getDateCondamnation() {
        return dateCondamnation;
    }

    public void setDateCondamnation(String dateCondamnation) {
        this.dateCondamnation = dateCondamnation;
    }

    public String getTribunal() {
        return tribunal;
    }

    public void setTribunal(String tribunal) {
        this.tribunal = tribunal;
    }

    public String getNatureCrimes() {
        return natureCrimes;
    }

    public void setNatureCrimes(String natureCrimes) {
        this.natureCrimes = natureCrimes;
    }

    public String getDateCrimes() {
        return dateCrimes;
    }

    public void setDateCrimes(String dateCrimes) {
        this.dateCrimes = dateCrimes;
    }

    public String getNaturePeines() {
        return naturePeines;
    }

    public void setNaturePeines(String naturePeines) {
        this.naturePeines = naturePeines;
    }

    public String getDateMandat() {
        return dateMandat;
    }

    public void setDateMandat(String dateMandat) {
        this.dateMandat = dateMandat;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.dateCondamnation);
        hash = 73 * hash + Objects.hashCode(this.tribunal);
        hash = 73 * hash + Objects.hashCode(this.natureCrimes);
        hash = 73 * hash + Objects.hashCode(this.dateCrimes);
        hash = 73 * hash + Objects.hashCode(this.naturePeines);
        hash = 73 * hash + Objects.hashCode(this.dateMandat);
        hash = 73 * hash + Objects.hashCode(this.observations);
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
        final JRDSModel other = (JRDSModel) obj;
        if (!Objects.equals(this.dateCondamnation, other.dateCondamnation)) {
            return false;
        }
        if (!Objects.equals(this.tribunal, other.tribunal)) {
            return false;
        }
        if (!Objects.equals(this.natureCrimes, other.natureCrimes)) {
            return false;
        }
        if (!Objects.equals(this.dateCrimes, other.dateCrimes)) {
            return false;
        }
        if (!Objects.equals(this.naturePeines, other.naturePeines)) {
            return false;
        }
        if (!Objects.equals(this.dateMandat, other.dateMandat)) {
            return false;
        }
        if (!Objects.equals(this.observations, other.observations)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JRDSModel{" + "dateCondamnation=" + dateCondamnation + ", tribunal=" + tribunal + ", natureCrimes=" + natureCrimes + ", dateCrimes=" + dateCrimes + ", naturePeines=" + naturePeines + ", dateMandat=" + dateMandat + ", observations=" + observations + '}';
    }
}
