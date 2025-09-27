/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tg.ceel.cj.casierapi.config.CasierConfig;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.Infraction;
import tg.ceel.cj.casierapi.entities.Payement;
import tg.ceel.cj.casierapi.models.MetaData;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class CasierUtils {
    Logger logger = LoggerFactory.getLogger(CasierUtils.class);

    public static Date stringTodate(String date) throws ParseException {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        try {
            return DateUtils.parseDate(
                    date.trim(),
                    new String[]{"yyyy-MM-dd"}
            );
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPurchaseref(Long numero) {
        String ref = "" + numero + System.currentTimeMillis() + "";
        return ref;
    }

    public static String datetoString(Date date) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            return dateformat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Méthode de formatage du numéro de téléphone
     *
     * @param telephone
     * @return
     */
    public static String telephoneFormatter(String telephone) {
        String fTelephone = "";
        fTelephone = telephone.replace(" ", "")
                .replace("+", "")
                .replace("(", "")
                .replace(")", "");
        while (fTelephone.charAt(0) == '0') {
            fTelephone = fTelephone.replace("0", "");
        }
        if (!fTelephone.startsWith("228")) {
            fTelephone = "228" + fTelephone;
        }
        return fTelephone;
    }

    /**
     * Convertion d'une valeur boolean
     *
     * @param valeur
     * @return
     */
    public static String convertBoolean(boolean valeur) {
        if (valeur == true) {
            return "OUI";
        } else {
            return "NON";
        }
    }

    /**
     * Convertion des valeur de VALIDER
     *
     * @param valeur
     * @return
     */
    public static String convertBooleanValider(Boolean valeur) {
        if (Objects.equals(valeur, Boolean.TRUE)) {
            return "VALIDEE";
        }
        if (Objects.equals(valeur, Boolean.FALSE)) {
            return "REJETEE";
        }
        if (valeur == null) {
            return "NON VISEE";
        }
        return null;
    }

    /**
     * Creates a folder to desired location if it not already exists
     *
     * @param dirName - full path to the folder
     * @throws SecurityException - in case you don't have permission to create
     *                           the folder
     */
    public static void createFolderIfNotExists(String dirName) throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }

    public static String getExtension(String baseurl, String name) {
        if (new File(name + ".pdf").exists()) {
            return "pdf";
        }
        if (new File(baseurl + ".png").exists()) {
            return "png";
        }
        if (new File(baseurl + name + ".jpg").exists()) {
            return "jpg";
        }
        if (new File(baseurl + name + ".JPG").exists()) {
            return "JPG";
        }
        if (new File(baseurl + name + ".JPEG").exists()) {
            return "JPEG";
        }
        if (new File(baseurl + name + ".jpeg").exists()) {
            return "jpeg";
        }
        if (new File(baseurl + name + ".PNG").exists()) {
            return "PNG";
        }
        return null;
    }

    public static void createFolders(String path) throws IOException {
        String base[] = path.split("/");
        String tree = "";
        for (String folder : base) {
            if (folder != "") {
                tree = tree + folder + "/";
                File theDir = new File(tree);
                if (!theDir.exists()) {
                    theDir.mkdir();
                }

            }
        }
    }

    /**
     * Utility method to save InputStream data to target location/file
     *
     * @param inStream - InputStream to be saved
     * @param target   - full path to destination file
     */
    public static void saveToFile(InputStream inStream, String target) throws IOException {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];
        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }

    public static String dateToFrString(Date date) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
            return dateformat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateToFrStringWithBars(Date date) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            return dateformat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String linearizeInfractions(Collection<Infraction> infractions) {
        String output = "";
        for (Infraction infraction : infractions) {
            output += infraction.getLibelle() + ".\n";
        }
        return output;
    }

    public static String dateToISO8601(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static boolean isReachable(String addr, int openPort, int timeOutMillis) {
        // Any Open port on other machine
        // openPort = 22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static String getProfession(Demande demande) {
        if (demande == null) {
            return null;
        }
        if (demande.getAutreProfession() != null) {
            return demande.getAutreProfession();
        }
        return demande.getProfession().getLibelle();
    }

    public static String esimerQuantumPeine(int quantumPeine) {
        int annees = quantumPeine / 12;
        int reste = quantumPeine % 12;
        if (annees > 0) {
            if (reste > 0) {
                return String.format("%d an(s) %d mois", annees, reste);
            } else {
                return String.format("%d an(s)", annees);
            }
        } else {
            return String.format("%d mois", reste);
        }
    }

    public static Integer getYearOfDate(Date date) {
        // Choose time zone in which you want to interpret your Date
        //Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getAnneeDemande(Demande demande) {
        return CasierUtils.getYearOfDate(demande.getDateDemande());
    }

    /**
     * Convert Base64 encoded message to plain text
     *
     * @param message
     * @return
     */
    public static String base64Decode(String message) {
        System.err.println(message);
        byte[] decodedBytes = Base64.decodeBase64(message);
        return new String(decodedBytes);
    }

    /**
     * Get document number of a demande
     *
     * @param demande
     * @return
     */
    public static String getNumeroPiece(Demande demande) {
        if (demande == null)
            return null;
        if (demande.getTypePiece() == null) {
            return "   ";
        }
        Integer typePieceId = demande.getTypePiece().getId();

        switch (typePieceId) {
            case 1:
                // Cas 1: Acte de naissance
                if (demande.getNumeroActe() == null) {
                    return demande.getNumeroCarte();
                }
                return demande.getNumeroActe();
            case 4:
                // Cas 4: Passport
                return demande.getNumeroPasseport();
            case 5:
                // Cas 5: Carte de séjour
                return demande.getNumeroCarte();
            case 6:
            case 7:
                // Cas 6, 7: Jugement supplétif ou rectificatif
                return demande.getNumeroJugement();
            case 8:
            case 9:
            case 10:
            case 11:
                return demande.getNumeroCarte();
            default:
                break;
        }
        return null;
    }

    public static Boolean isLivraisonPoste(Demande demande) {
        if (demande.getTracked() == null) {
            return false;
        }
        return demande.getTracked();
    }

    public static Boolean isEtabliNotified(Demande demande) {
        if (!isLivraisonPoste(demande)) {
            return false;
        }
        return demande.getTrackingDeliverySuccess() != null
                && demande.getTrackingDeliverySuccess();
    }

    public static Boolean isTrackingNotified(Demande demande) {
        if (!isLivraisonPoste(demande)) {
            return false;
        }
        return demande.getTrackingNotificationSuccess() != null
                && demande.getTrackingDeliverySuccess();
    }


    public static String signPaymentData(Demande demande) {
        Payement paiement = demande.getPayement();
        // Todo: Make sure demande.paiement is not null 
        StringBuilder str = new StringBuilder();
        str.append("transactionUUID=").append(demande.getPayement().getTransactionUUID()).append(",");
        str.append("devise=").append(CasierConfig.getConfigValue("DEVISE")).append(",");
        str.append("total=").append(paiement.getMontant().intValue()).append(",");
        str.append("apiKey=").append(CasierConfig.getConfigValue("API_KEY"));
        String dataToSign = str.toString();
        System.out.println("Data to sign: " + dataToSign);
        try {
            String algo = "HmacSHA256";
            Mac mac = Mac.getInstance(algo);
            SecretKeySpec secret_key = new SecretKeySpec(CasierConfig.getConfigValue("SECRET_KEY").getBytes(), algo);
            mac.init(secret_key);
            String hash = Base64.encodeBase64String(mac.doFinal(dataToSign.getBytes()));
            System.err.println("Signature " + hash);
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String checkSignedResponse(Map<String, String> response) {
        String attributes[] = response.get("signedAttributeNames").split(",");
        StringBuilder strb = new StringBuilder();
        int compteur = 0;
        for (String string : attributes) {
            compteur++;
            strb.append(string).append("=").append(response.get(string));
            if (compteur < attributes.length) {
                strb.append(",");
            }
        }

        String data = strb.toString();
        System.err.println("Data: " + data);
        String algo = "HmacSHA256";
        try {
            Mac mac = Mac.getInstance(algo);
            SecretKeySpec secret_key = new SecretKeySpec(CasierConfig.getConfigValue("SECRET_KEY").getBytes(), algo);
            mac.init(secret_key);
            String hash = Base64.encodeBase64String(mac.doFinal(data.getBytes()));
            return hash;
        } catch (IllegalStateException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check whether a received response is valid or not
     *
     * @param receivedData
     * @return
     */
    public static boolean zpayResponseIsValid(Map<String, String> receivedData) {
        return receivedData.get("signature").equals(checkSignedResponse(receivedData));
    }


    /**
     * Converts a formatted string to a valid date
     *
     * @param format
     * @param dateString
     * @return
     * @see <a href="https://www.baeldung.com/java-string-to-date">...</a>
     */
    public static Date parseDate(String format, String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException ex) {
            return null;
        }
    }


    /**
     * Add days to a given Date object
     *
     * @param originalDate
     * @param numberOfDays
     * @return
     * @see <a href="https://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date">...</a>
     */
    public static Date addDays(Date originalDate, Integer numberOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(originalDate);
        cal.add(Calendar.DATE, numberOfDays);
        return cal.getTime();
    }

    public static String transfert(MultipartFile multipartFile, MetaData metaData, String ftpApiUrl) {
        Logger logger = LoggerFactory.getLogger(CasierUtils.class);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String info = mapper.writeValueAsString(metaData);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("Content-Type", "multipart/form-data");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            MultiValueMap<String, Object> corps = new LinkedMultiValueMap<>();
            corps.add("info", info);
            corps.add("fichier", new MultipartInputStreamFileResource(multipartFile.getInputStream(), multipartFile.getOriginalFilename()));
            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(corps, headers);
            //.postForEntity(ftpApiUrl, requestEntity, Boolean.class);
            ResponseEntity<String> response =
                    restTemplate.exchange(ftpApiUrl, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                return response.getBody();
            }
            logger.error("Une erreur s'est produit lors du dépôt du fichier", response);
            return "FAILLED";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return "FAILLED";
        }
    }


}