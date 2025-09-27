package tg.ceel.cj.casierapi.services;

import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import tg.ceel.cj.casierapi.dto.FloozTransactionDto;
import tg.ceel.cj.casierapi.dto.PaiementDto;
import tg.ceel.cj.casierapi.dto.TmoneyNotificationDto;
import tg.ceel.cj.casierapi.dto.TmoneyRequestDto;
import tg.ceel.cj.casierapi.models.PaymentStatByMonthModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PaiementService {
    PaiementDto savePaiement(PaiementDto dto) throws Exception;

    List<TmoneyNotificationDto> AllTMoney();
    List<FloozTransactionDto> AllFlooz();
    ResponseEntity<?> payerTmoney(TmoneyNotificationDto dto);
    ResponseEntity<?> payerTmoney(TmoneyRequestDto dto);
    ResponseEntity<?> payerTmoney(FloozTransactionDto dto);
    List<PaymentStatByMonthModel> getStatistiqueByMonth(Integer year);
    String printOnDefaultPrinter(Integer year) throws Exception;
    InputStream exporterStatistiqueByMonth(Integer year, String format) throws IOException, JRException;

}
