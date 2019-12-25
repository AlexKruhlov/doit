package ua.com.rafael.doit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ua.com.rafael.doit.feature.controller.DoitManager;

@SpringBootApplication
public class DoitApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContex = SpringApplication.run(DoitApplication.class, args);
        applicationContex.getBean(DoitManager.class).runManager();
    }
}
