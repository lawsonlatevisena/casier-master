package tg.ceel.cj.casierapi.servicesimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PrintService {
    @Value("${print.api.url}")
    private String printApiUrl;
    public String printOnDefaultPrinter(byte[] fileData) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> entity = new HttpEntity<>(fileData,httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(printApiUrl, HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "Impression réussie : " + response.getBody();
        } else {
            throw new Exception("Echec de l'impression : " + response.getBody());
        }

    }
}
