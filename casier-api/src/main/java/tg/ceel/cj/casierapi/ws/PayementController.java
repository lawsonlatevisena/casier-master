package tg.ceel.cj.casierapi.ws;

import net.sf.jasperreports.engine.JRException;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tg.ceel.cj.casierapi.dto.FloozTransactionDto;
import tg.ceel.cj.casierapi.dto.TmoneyNotificationDto;
import tg.ceel.cj.casierapi.services.PaiementService;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("paiements")
public class PayementController {
    Logger logger = LoggerFactory.getLogger(PayementController.class);
    private final PaiementService paiementService;

    public PayementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @GetMapping("liste/tmoney")
    public ResponseEntity<?> getAllTMoney() {
        try {
            return new ResponseEntity<>(paiementService.AllTMoney(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("tmoney")
    public ResponseEntity<?> Payer(@RequestBody TmoneyNotificationDto dto) {
        try {
            return paiementService.payerTmoney(dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("flooz")
    public ResponseEntity<?> Payer(@RequestBody FloozTransactionDto dto) {
        try {
            return paiementService.payerTmoney(dto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("liste/flooz")
    public ResponseEntity<?> listeFlooz() {
        try {
            return new ResponseEntity<>(paiementService.AllFlooz(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("statisque-by-month/year/{year}")
    public ResponseEntity<?> getStatistiqueByMonth(@PathVariable("year") Integer year) {
        try {
            System.out.println(year);
            return new ResponseEntity<>(paiementService.getStatistiqueByMonth(year), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/export/statistique/par-mois/annee/{annee}/format/{format}")
    public ResponseEntity<?> exporterStatistiqueByMonth( @PathVariable("annee") Integer annee,
                                                         @PathVariable("format") String format) {
        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream;
        try {
            inputStream = this.paiementService.exporterStatistiqueByMonth(annee,format);
            byte[] media = IOUtils.toByteArray(inputStream);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
            return responseEntity;
        } catch (IOException | JRException e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return new ResponseEntity<>("Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
