/*
 * Copyright (c) 2010, Oracle. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package tg.ceel.cj.casierapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.DateUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class MethodeUtils {

    public static String LDAPURL, GROUPBASEDN, PEOPLEBASEDN, CURRENTTPICODE, IDTPICODE, RECIPIENTSMAIL, SMTPSERVER, SMTPUSER, SMTPPASSWORD, VERSION;

    private static String[] VOYELLE = {"a", "e", "i", "o", "u", "y"};

    public static <T> Collection<T> arrayToCollection(T[] arr) {
        if (arr == null) {
            return new ArrayList<T>();
        }
        return Arrays.asList(arr);
    }

    public static Object[] collectionToArray(Collection<?> c) {
        if (c == null) {
            return new Object[0];
        }
        return c.toArray();
    }


    public static String getAsString(Object object) {
        if (object instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) object;
            if (collection.isEmpty()) {
                return "(No Items)";
            }
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (Object item : collection) {
                if (i > 0) {
                    sb.append("<br />");
                }
                sb.append(item);
                i++;
            }
            return sb.toString();
        }
        return String.valueOf(object);
    }

    public static String convertDate(Date d, String format) {
        try {
            SimpleDateFormat affiche = new SimpleDateFormat(format, Locale.FRENCH);
            return affiche.format(d);
        } catch (NullPointerException e) {
            return "";
        }
    }

    public static Date convertirDate(String dateString, String format) {
        Date date = null;
        try {
            SimpleDateFormat formatToString = new SimpleDateFormat(format);
            date = formatToString.parse(dateString);
        } catch (ParseException ex) {
            //Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    /**
     * Convertir une date java.util.Date en java.time.LocalDateTime.
     *
     * @param date Date java.util.Date à convertir.
     * @return
     */
    public static LocalDateTime convertirEnLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Convertir une date java.util.Date en java.time.LocalDate.
     *
     * @param date Date java.util.Date à convertir.
     * @return
     */
    public static LocalDate convertirEnLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Convertir une date java.time.LocalDateTime en java.util.Date.
     *
     * @param dateTime Date java.time.LocalDateTime à convertir.
     * @return
     */
    public static Date convertirEnDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * Returns a Date of the day at the time of midnight, 00:00, at the start of
     * this date.
     *
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime ldt = MethodeUtils.convertirEnLocalDate(date).atStartOfDay();
        return MethodeUtils.convertirEnDate(ldt);
    }

    /**
     * Returns a Date set to the last possible millisecond of the day, just
     * before midnight.
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime ldt = MethodeUtils.convertirEnLocalDate(date).atStartOfDay().plusDays(1).minusNanos(1);
        return MethodeUtils.convertirEnDate(ldt);
    }

    public static Long getYearsBetween(Date debut, Date fin) {
        long diffInMillies = Math.abs(fin.getTime() - debut.getTime());
        long diff = (diffInMillies / (1000l * 60 * 60 * 24 * 365));
        return diff;
    }


    public static Long getDaysBetween(Date debut, Date fin) {
        LocalDate ldebut = convertirEnLocalDate(debut);
        LocalDate lfin = convertirEnLocalDate(fin);
        return ChronoUnit.DAYS.between(ldebut, lfin);
    }


    public static String getFileName(String extension) {
        String retour = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date());
        if (!((extension == null) || extension.equals(""))) {
            retour += "." + extension.toUpperCase();
        }
        return retour;
    }

    public static String getFileName(String extension, String prefixe) {
        String retour = new SimpleDateFormat("ddMMyyyyHHmmSSsss", Locale.FRENCH).format(new Date());
        String pre = prefixe;
        pre = pre.replaceAll(" ", "_");
        pre = sansAccent(pre);
        pre = pre.toUpperCase();
        if (!((extension == null) || extension.equals(""))) {
            retour += "." + extension.toUpperCase();
        }
        retour = pre + "_" + retour;

        return retour;
    }

    public static String getFileNameWithDate(String extension, String prefixe) {
        Date myDate = new Date();
        String retour = convertDate(myDate, "EEEEE dd MMM yyyy 'A' HH:mm:ss");

        String pre = prefixe;
        pre = pre.replaceAll(" ", "_");
        pre = sansAccent(pre);
        pre = pre.toUpperCase();
        if (!((extension == null) || extension.equals(""))) {
            retour += "." + extension.toUpperCase();
        }
        retour = pre + "_" + retour;

        System.out.println("name est: " + retour);
        return retour;
    }

    public static String getExtension(String filename) {
        String[] ext = filename.split("\\.");
        String extension;
        if (ext.length > 0) {
            extension = ext[ext.length - 1];
        } else {
            extension = "";
        }
        return extension;
    }

    public static String sansAccent(String chaine) {
        return Normalizer.normalize(chaine, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }


    /**
     * Convertit l'objet spécifiée en chaîne de caractères JSON.
     *
     * @param object Objet à convertir en JSON.
     * @return Représentation en notation JSON de l'objet.
     * @author Raman
     */
    public static String toJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (IOException ex) {
            System.out.println("ERROR MSG :" + ex.getMessage());
            return null;
        }
    }

    /**
     * Convertit chaîne de caractères JSON en un objet de type spécifié.
     *
     * @param <T>  Type de l'objet.
     * @param json Chaîne de caractères JSON.
     * @param type Class du type de l'objet
     * @return Objet obtenu.
     * @author Raman
     */
    public static <T> T fromJson(String json, Class<T> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, type);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static void sendFile(ByteArrayOutputStream out, String fileName) throws Exception {
        /*
         * FacesContext facesContext = FacesContext.getCurrentInstance();
         * HttpServletResponse response = (HttpServletResponse)
         * facesContext.getExternalContext().getResponse(); response.setContentType(
         * "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
         * response.setHeader("Content-disposition", "attachment;filename=" + fileName);
         * response.setHeader("Pragma", "no-cache"); response.setHeader("Expires", "0");
         * try { response.getOutputStream().write(out.toByteArray());
         * response.getOutputStream().flush(); response.getOutputStream().close();
         * facesContext.responseComplete(); facesContext.renderResponse();
         */


    }

    public static void sendFileUtf8(ByteArrayOutputStream out, String fileName) throws Exception {
        /*
         * FacesContext facesContext = FacesContext.getCurrentInstance();
         * HttpServletResponse response = (HttpServletResponse)
         * facesContext.getExternalContext().getResponse(); response.setContentType(
         * "application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8"
         * ); response.setHeader("Content-disposition", "attachment;filename=" +
         * fileName); response.setHeader("Pragma", "no-cache");
         * response.setHeader("Expires", "0"); try {
         * response.getOutputStream().write(out.toByteArray());
         * response.getOutputStream().flush(); response.getOutputStream().close();
         * facesContext.responseComplete(); facesContext.renderResponse(); } catch
         * (Exception exception) { throw new Exception(exception.getMessage()); }
         * finally { }
         */
    }

    public static String encoderBase64(String str) throws UnsupportedEncodingException {
        Base64.Encoder encodeur = Base64.getEncoder();
        byte[] raw = str.getBytes(StandardCharsets.UTF_8);
        return encodeur.encodeToString(raw);
    }

    public static String decoderBase64(String base64) throws UnsupportedEncodingException {
        Base64.Decoder decodeur = Base64.getDecoder();
        byte[] raw = decodeur.decode(base64);
        return new String(raw, StandardCharsets.UTF_8);
    }

    public static boolean sameDate(Date d1, Date d2) {
        return DateUtils.isSameDay(d1, d2);
    }

    public static Long getDureeEnJours(Date debut, Date fin) {
        return MethodeUtils.getDaysBetween(debut, fin);
//        LocalDate ldebut = JsfUtil.convertirEnLocalDate(debut);
//        LocalDate lfin = JsfUtil.convertirEnLocalDate(fin);
//        return ChronoUnit.DAYS.between(ldebut, lfin);
    }

    public static String arrondir(Double nbre) {
        try {
            return arrondir(nbre, 2);
        } catch (NullPointerException e) {
            return "";
        }
    }

    public static String arrondir(Double nbre, int dec) {
        try {
            return String.format("%.2f", nbre);
        } catch (NullPointerException e) {
            return "0.0";
        }
    }

    public static String convertDate(Date d) {
        return convertDate(d, "EEEEE dd MMMM yyyy");
    }

    public static String convertDateHeure(Date d) {
        return convertDate(d, "EEEEE dd MMMM yyyy HH:mm");
    }

    public static String convertDateHeureA(Date d) {
        return convertDate(d, "EEEEE dd MMMM yyyy à HH:mm");
    }

    public static String convertDateFormatSimple(Date d) {
        return convertDate(d, " dd/MM/yyyy 'à' hh:mm");
    }


    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);  //yyyy-MM-dd
        return simpleDateFormat.format(date);

    }

    public static Date[] getDeadline(int mois, int year) {
        Calendar calendar = Calendar.getInstance();
        Date[] dates = new Date[2];

        switch (mois) {
            case 0:
                calendar.set(year, Calendar.JANUARY, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.JANUARY, 31);
                dates[1] = calendar.getTime();
                break;

            case 1:
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
                calendar.set(Calendar.DATE, 1);
                dates[0] = calendar.getTime();
                if (year % 4 == 0) {
                    calendar.set(Calendar.DATE, 29);
                } else {
                    calendar.set(Calendar.DATE, 28);
                }
                dates[1] = calendar.getTime();
                break;

            case 2:
                calendar.set(year, Calendar.MARCH, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.MARCH, 31);
                dates[1] = calendar.getTime();
                break;

            case 3:
                calendar.set(year, Calendar.APRIL, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.APRIL, 30);
                dates[1] = calendar.getTime();
                break;

            case 4:
                calendar.set(year, Calendar.MAY, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.MAY, 31);
                dates[1] = calendar.getTime();
                break;

            case 5:
                calendar.set(year, Calendar.JUNE, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.JUNE, 30);
                dates[1] = calendar.getTime();
                break;

            case 6:
                calendar.set(year, Calendar.JULY, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.JULY, 31);
                dates[1] = calendar.getTime();
                break;

            case 7:
                calendar.set(year, Calendar.AUGUST, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.AUGUST, 31);
                dates[1] = calendar.getTime();
                break;

            case 8:
                calendar.set(year, Calendar.SEPTEMBER, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.SEPTEMBER, 30);
                dates[1] = calendar.getTime();
                break;

            case 9:
                calendar.set(year, Calendar.OCTOBER, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.OCTOBER, 31);
                dates[1] = calendar.getTime();
                break;

            case 10:
                calendar.set(year, Calendar.NOVEMBER, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.NOVEMBER, 30);
                dates[1] = calendar.getTime();
                break;

            case 11:
                calendar.set(year, Calendar.DECEMBER, 1);
                dates[0] = calendar.getTime();
                calendar.set(year, Calendar.DECEMBER, 31);
                dates[1] = calendar.getTime();
                break;
        }
        return dates;
    }

    public static boolean checkIfStatsWithVowel(String str) {
        if (str == null) {
            return false;
        }
        String firstCharcter = String.valueOf(str.charAt(0)).toLowerCase();
        Long nombre = Arrays.stream(VOYELLE).filter(s -> s.equals(firstCharcter)).count();
        return nombre > 0;
    }

    public static String getPurchaseref(Long numero) {
        String ref = "" + numero + System.currentTimeMillis() + "";
        return ref;
    }
}
