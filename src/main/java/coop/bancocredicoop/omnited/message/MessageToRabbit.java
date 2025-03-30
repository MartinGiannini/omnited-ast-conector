package coop.bancocredicoop.omnited.message;

import coop.bancocredicoop.omnited.config.MessageOut.MensajeJSON;
import coop.bancocredicoop.omnited.service.rabbit.RabbitSenderService;
import org.springframework.stereotype.Component;

@Component
public class MessageToRabbit {

    private final RabbitSenderService rabbitSenderService;

    public MessageToRabbit(
            RabbitSenderService rabbitSenderService
    ) {
        this.rabbitSenderService = rabbitSenderService;
    }

    public void processMessage(String idMensaje, String mensajeType, String mensajeJson) {
        
        try {
            MensajeJSON message = MensajeJSON.newBuilder()
                    .setIdMensaje(idMensaje)
                    .setMensajeType(mensajeType)
                    .setMensajeJson(mensajeJson)
                    .build();

            // Usar el servicio RabbitSenderService para enviar el mensaje
            rabbitSenderService.sendMessage(message);

        } catch (Exception e) {
            System.out.println("Error en el processMessage" + e);
        }
    }
}