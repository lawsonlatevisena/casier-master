package tg.ceel.cj.casierapi.fnc;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ReportService {
    Logger logger = LoggerFactory.getLogger(ReportService.class);
    @Value("${report.folder}")
    private String reportFolder;

    SimpleDateFormat sf = new SimpleDateFormat("EEEE dd MMMM yyyy");
    SimpleDateFormat sfDate = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sfDateHeure = new SimpleDateFormat("dd/MM/yyyy à HH:mm");


    public byte[] exportQuestionnaire(Long questionnaireId, String format) throws IOException, JRException {
       return  null;
    }

}
