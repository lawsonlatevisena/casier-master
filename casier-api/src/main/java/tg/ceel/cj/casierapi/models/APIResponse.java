package tg.ceel.cj.casierapi.models;

import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse {
    private Boolean success;


    private  String message;
    private String responseCode;

    @Nullable
    private ResponseData data;

    public APIResponse(boolean b, String notificationEnvoyéAvecSuccès) {
    }


    @Override
    public String toString() {
        return "APIResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
