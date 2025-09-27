package tg.ceel.ftp.ftpapi.config;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;


@Component
@Configurable
public class AppConfig {
    @Bean
    @Order(0)
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        String userDirectory = System.getProperty("user.home");

        properties.setLocation(new FileSystemResource("/home/lawson/Téléchargements/casier-master/config/ftp.properties"));
        //  properties.setLocation(new FileSystemResource("D:\\JAVA_HOME\\SPRING BOOT\\casier\\config\\casier.properties"));

        //  String userDirectory = System.getProperty("user.home");
     //  properties.setLocation(new FileSystemResource("/opt/services/config/ftp.properties"));
        properties.setIgnoreResourceNotFound(false);
        properties.setIgnoreResourceNotFound(false);
        return properties;
    }


}
