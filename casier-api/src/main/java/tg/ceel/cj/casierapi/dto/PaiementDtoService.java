package tg.ceel.cj.casierapi.dto;


import tg.ceel.cj.casierapi.entities.FloozTransaction;
import tg.ceel.cj.casierapi.entities.Payement;
import tg.ceel.cj.casierapi.entities.TmoneyNotification;

public interface PaiementDtoService {
    Payement paiementDtoToPaiement(PaiementDto dto);
    Payement tmoneyNotificationnToPaiement(TmoneyNotification tmoneyNotification);
    PaiementDto paiementToPaiementDto(Payement paiement);
    Payement floozTransactionToPayement(FloozTransaction floozTransaction);
}
