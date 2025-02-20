package coop.bancocredicoop.omnited.config;

import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsteriskConfig {

    @Value("${asterisk.host}")
    private String asteriskHost;

    @Value("${asterisk.port}")
    private int asteriskPort;

    @Value("${asterisk.username}")
    private String asteriskUsername;

    @Value("${asterisk.password}")
    private String asteriskPassword;

    @Bean
    public ManagerConnection managerConnection() {
        ManagerConnectionFactory factory = new ManagerConnectionFactory(asteriskHost, asteriskPort, asteriskUsername, asteriskPassword);
        return factory.createManagerConnection();
    }
}
