package tg.ceel.cj.gateway;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class GatewayConfig {

    @Bean
    @Order(0)
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

   //     properties.setLocation(new FileSystemResource("D:\\Ceel\\casier\\sncj\\config\\gateway.properties"));

          // properties.setLocation(new FileSystemResource("D:\\JAVA_HOME\\SPRING BOOT\\casier\\config\\gateway.properties"));

        properties.setLocation(new FileSystemResource("D:\\casier\\sncj\\casier\\config\\gateway.properties"));

        //  properties.setLocation(new FileSystemResource("/opt/config/peecs.properties"));

        properties.setIgnoreResourceNotFound(false);
        return properties;
    }

}
