package tg.ceel.ftp.ftpapi.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tg.ceel.ftp.ftpapi.models.MetaData;
import tg.ceel.ftp.ftpapi.services.FileManager;

@RestController
@RequestMapping("storages")

public class FtpController {
    private final FileManager fileManager;
    Logger logger = LoggerFactory.getLogger(FileManager.class);

    public FtpController(FileManager fileManager) {
        this.fileManager = fileManager;
    }
@PostMapping("save")
    public ResponseEntity<?> transfert(@RequestParam("info") String meta_data, @RequestParam("fichier") MultipartFile multipartFile) {
        try {
            if (multipartFile == null) {
                return new ResponseEntity<>("Aucun fichier n'est envoyé", HttpStatus.BAD_REQUEST);
            }
            if (meta_data == null) {
                return new ResponseEntity<>("Les méta données du fichier ne sont pas envoyé", HttpStatus.BAD_REQUEST);
            }
            MetaData data = new ObjectMapper().readValue(meta_data, MetaData.class);
            Boolean resultat = fileManager.saveImage(data.getRacine(), data.getFolder(), data.getSubmittedFileName(), multipartFile);
            if (resultat) {
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            }
            return new ResponseEntity<>("FAILED", HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("update")
    public ResponseEntity<?> transfertUpdate(@RequestParam("info") String meta_data, @RequestParam("fichier") MultipartFile multipartFile) {
        try {
            if (multipartFile == null) {
                return new ResponseEntity<>("Aucun fichier n'est envoyé", HttpStatus.BAD_REQUEST);
            }
            if (meta_data == null) {
                return new ResponseEntity<>("Les méta données du fichier ne sont pas envoyé", HttpStatus.BAD_REQUEST);
            }
            MetaData data = new ObjectMapper().readValue(meta_data, MetaData.class);
            Boolean resultat = fileManager.saveImageUpdate(data.getRacine(), data.getFolder(), data.getSubmittedFileName(), multipartFile);
            if (resultat) {
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            }
            return new ResponseEntity<>("FAILED", HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}