package tg.ceel.fnc.fnc;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;


@Component
@Configurable
public class FncConfig {
    @Bean
    @Order(0)
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        String userDirectory = System.getProperty("user.home");

     //     properties.setLocation(new FileSystemResource("D:/Ceel/casier/sncj/config/fnc.properties"));

        //  properties.setLocation(new FileSystemResource("D:\\JAVA_HOME\\SPRING BOOT\\casier\\config\\fnc.properties"));
       //   properties.setLocation(new FileSystemResource("D:/casier/sncj/casier/config/fnc.properties"));

        properties.setLocation(new FileSystemResource("/opt/microservices/config/fnc.properties"));
        properties.setIgnoreResourceNotFound(false);
        return properties;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
