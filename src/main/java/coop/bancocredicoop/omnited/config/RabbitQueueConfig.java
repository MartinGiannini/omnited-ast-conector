package coop.bancocredicoop.omnited.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.colaSalida}")
    private String colaSalida;

    @Value("${spring.rabbitmq.routing-key}")
    private String routingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue colaSalida() {
        return new Queue(colaSalida, true); // Cola de salida (duradera)
    }

    /**
     * Binding de la cola de salida al exchange con la clave de enrutamiento.
     *
     * @param colaSalida
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingColaSalida(Queue colaSalida, TopicExchange exchange) {
        return BindingBuilder.bind(colaSalida).to(exchange).with(routingKey + ".ast");
    }
}
