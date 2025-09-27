package tg.ceel.cj.registre;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class RegistreConfig {

    @Bean
    @Order(0)
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

      //   properties.setLocation(new FileSystemResource("D:\\Ceel\\casier\\sncj\\config\\registre.properties"));

         properties.setLocation(new FileSystemResource("/home/lawson/Téléchargements/casier-master/casier-api/src/main/resources/application.properties"));
        properties.setIgnoreResourceNotFound(false);
        return properties;
    }

}
