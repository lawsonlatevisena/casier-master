package tg.ceel.cj.casierapi.utils;

public enum Extentions {

    PDF("PDF"),
    PNG("PNG"),
    JPEG("JPEG"),
    JPG("JPG");

    private String ext;

    Extentions(String ext) {
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
