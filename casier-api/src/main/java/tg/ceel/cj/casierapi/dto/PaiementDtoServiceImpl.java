package tg.ceel.cj.casierapi.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.entities.*;

import java.util.Date;


@Service
public class PaiementDtoServiceImpl implements PaiementDtoService {
    Logger logger = LoggerFactory.getLogger(PaiementDtoServiceImpl.class);

    @Override
    public Payement paiementDtoToPaiement(PaiementDto dto) {
        if (dto == null) {
            return null;
        }
        Payement paiement = new Payement();
        paiement.setMoyenPaiement(dto.getMoyenPaiement());
        paiement.setDatePayement(dto.getDate());
        paiement.setDevisePaiement(dto.getDevise());
        paiement.setMontant(dto.getMontant().doubleValue());
        paiement.setTransactionUUID(dto.getTransactionUUID());
        paiement.setNumeroTransaction(dto.getNumeroTransaction());
        paiement.setSignedAttributeNames(dto.getSignedAttributeNames());
        paiement.setRegler(true);
        paiement.setDateCreation(new Date());
        paiement.setNumero(dto.getTelephone());
        return paiement;
    }

    @Override
    public Payement tmoneyNotificationnToPaiement(TmoneyNotification tmoneyNotification) {
        try {
            Payement payement = Payement.builder()
                    .canalPayement(CanalPayement.TMONEY)
                    .datePayement(new Date(tmoneyNotification.getTmnTimestamp()))
                    .modePayement(ModePayement.ATD)
                    .dateTransaction(tmoneyNotification.getRowvers() + "")
                    .numeroTransaction(tmoneyNotification.getPurchaseref())
                    .numero(tmoneyNotification.getMobile())
                    .montant(tmoneyNotification.getAmount().doubleValue())
                    .devisePaiement(tmoneyNotification.getCurrency() + "")
                    .dateCreation(new Date()).build();
            if (tmoneyNotification.getStatus().equalsIgnoreCase("OK")) {
                payement.setRegler(true);
            }
            return payement;
        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PaiementDto paiementToPaiementDto(Payement paiement) {
        PaiementDto dto = new PaiementDto();
        if (paiement == null) {
            return null;
        }
        dto.setMoyenPaiement(paiement.getMoyenPaiement());
        dto.setDate(paiement.getDatePayement());
        dto.setDevise(paiement.getDevisePaiement());
        dto.setMontant(paiement.getMontant().longValue());
        dto.setTransactionUUID(paiement.getTransactionUUID());
        dto.setNumeroTransaction(paiement.getNumeroTransaction());
        dto.setSignedAttributeNames(paiement.getSignedAttributeNames());
        dto.setTelephone(paiement.getNumero());
        return dto;
    }

    @Override
    public Payement floozTransactionToPayement(FloozTransaction floozTransaction) {
        try {
            Payement payement = Payement.builder()
                    .canalPayement(CanalPayement.FLOOZ)
                    .datePayement(new Date())
                    .modePayement(ModePayement.ATD)
                    .dateTransaction(new Date() + "")
                    .numeroTransaction(floozTransaction.getOpFloozRefid())
                    .numero(floozTransaction.getIpDestMobileNumber())
                    .montant(Double.valueOf(floozTransaction.getIopAmount()))
                    .dateCreation(new Date()).build();
            if (floozTransaction.getOpStatus() == 0) {
                payement.setRegler(true);
            }
            return payement;
        } catch (Exception e) {
            this.logger.error("Erreur interne: ", e);
            e.printStackTrace();
            return null;
        }
    }
}
