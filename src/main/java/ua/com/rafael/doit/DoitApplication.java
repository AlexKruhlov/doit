package ua.com.rafael.doit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ua.com.rafael.doit.feature.service.HelpService;

@SpringBootApplication
public class DoitApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContex = SpringApplication.run(DoitApplication.class, args);

        HelpService helpService = applicationContex.getBean(HelpService.class);
        helpService.find();
    }
}
