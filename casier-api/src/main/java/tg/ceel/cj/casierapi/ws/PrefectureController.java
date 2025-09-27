package tg.ceel.cj.casierapi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tg.ceel.cj.casierapi.services.PrefectureService;

@RestController
@RequestMapping("prefectures")
public class PrefectureController {
    Logger logger = LoggerFactory.getLogger(PrefectureController.class);
    private final PrefectureService prefectureService;

    public PrefectureController(PrefectureService prefectureService) {
        this.prefectureService = prefectureService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(prefectureService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne : ", e);
            return  new ResponseEntity<>("Erreur interne : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
