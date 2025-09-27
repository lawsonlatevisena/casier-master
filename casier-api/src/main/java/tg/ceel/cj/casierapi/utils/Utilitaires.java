package tg.ceel.cj.casierapi.utils;

public class Utilitaires {

    public static Boolean isAccepted(String ext) {
        return (ext.equalsIgnoreCase(Extentions.PNG.getExt()) || ext.equalsIgnoreCase(Extentions.PDF.getExt()) || ext.equalsIgnoreCase(Extentions.JPEG.getExt()) || ext.equalsIgnoreCase(Extentions.JPG.getExt()));
    }
}
