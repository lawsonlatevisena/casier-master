package tg.ceel.cj.casierapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtdPaiement {
    @JsonProperty("PAYMENTID")
    private String payementid;
    @JsonProperty("GATEWAY")
    private String gateway;
    @JsonProperty("ACCOUNT")
    private String account;
    @JsonProperty("BILL")
    private String bill;
    @JsonProperty("AMOUNT")
    private int amount;
    @JsonProperty("PAYMENTSTATUS")
    private String payementstatus;
    @JsonProperty("SUCCESSREF")
    private String successref;
    @JsonProperty("CURRENCY")
    private String currency;
    @JsonProperty("TRANSACTIONUUID")
    private String transactionuuid;
    @JsonProperty("TRANSACTIONDATE")
    private Date transactiondate;
    @JsonProperty("TRANSACTIONDATESHORT")
    private Date transactiondateshort;
}
