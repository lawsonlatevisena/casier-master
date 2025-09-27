package tg.ceel.cj.casierapi.ws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ApiController {

    @GetMapping("ping")
    public ResponseEntity<?> pingApi(HttpServletRequest httpServletRequest) {
        Map<String, String> map = new HashMap<>();
        map.put("Request URL: ", httpServletRequest.getRequestURL().toString());
        map.put("Service UP: ", "TRUE");
        map.put("Server current_datetime: ", new Date().toString());
        map.put("Remote address: ", httpServletRequest.getRemoteHost());
        map.put("Path info: ", httpServletRequest.getPathInfo());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
