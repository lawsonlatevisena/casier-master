package tg.ceel.cj.casierapi.foreign.entities.access;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tg.ceel.cj.casierapi.dto.LogDto;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.entities.User;


import java.util.List;

//@FeignClient(name = "user-management", configuration = FeignConfiguration.class)
public interface SecurityFeigManagementService {

  //  @RequestMapping(method = RequestMethod.GET, value = "/centres/{id}", consumes = "application/json")
     PointRetrait getPointRetraitById(@PathVariable("id") Long id, String auth);

   // @RequestMapping(method = RequestMethod.POST, value = "logs/logger", consumes = "application/json")

    LogDto saveLog(@RequestBody LogDto log, String auth);
   // @GetMapping("centres/type/{type}")


    // @RequestMapping(method = RequestMethod.GET, value = "centres/type/{type}", consumes = "application/json")
    List<PointRetrait> getCentreAll(@PathVariable("type") String type, String auth);


  //  @RequestMapping(method = RequestMethod.GET, value = "login/{login}", consumes = "application/json")
    User getByLogin(@PathVariable String login, String auth);

    String getToken();
}
