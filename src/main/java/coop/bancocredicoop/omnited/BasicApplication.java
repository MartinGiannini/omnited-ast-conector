package coop.bancocredicoop.omnited;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "coop.bancocredicoop.omnited", 
    "coop.bancocredicoop.asterisk"  // Incluí este paquete para que se detecte AsteriskEventListener
})
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }

}
