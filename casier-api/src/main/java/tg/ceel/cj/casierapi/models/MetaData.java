package tg.ceel.cj.casierapi.models;

import lombok.Builder;

@Builder
public class MetaData {
    private String racine;
    private String folder;
    private String submittedFileName;

    public MetaData() {
    }

    public MetaData(String racine, String folder, String submittedFileName) {
        this.racine = racine;
        this.folder = folder;
        this.submittedFileName = submittedFileName;
    }

    public String getRacine() {
        return racine;
    }

    public void setRacine(String racine) {
        this.racine = racine;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getSubmittedFileName() {
        return submittedFileName;
    }

    public void setSubmittedFileName(String submittedFileName) {
        this.submittedFileName = submittedFileName;
    }
}
