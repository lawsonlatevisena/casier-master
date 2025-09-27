package tg.ceel.fnc.fnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FncApplication {

    public static void main(String[] args) {
        SpringApplication.run(FncApplication.class, args);
    }

}
