package coop.bancocredicoop.asterisk.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import coop.bancocredicoop.omnited.exposition.ExtensionDTO;
import coop.bancocredicoop.omnited.message.MessageToRabbit;
import coop.bancocredicoop.omnited.utils.DateUtils;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.event.PeerStatusEvent;
import org.springframework.stereotype.Component;

@Component
public class PeerStatusEventHandler implements AsteriskEventHandler<PeerStatusEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageToRabbit messageToRabbit;
    UUID uuid = UUID.randomUUID();

    public PeerStatusEventHandler(
            MessageToRabbit messageToRabbit
    ) {
        this.messageToRabbit = messageToRabbit;
    }

    @Override
    public boolean supports(ManagerEvent event) {
        return event instanceof PeerStatusEvent;
    }

    @Override
    public void handle(PeerStatusEvent event) {
        
        ExtensionDTO extension = new ExtensionDTO();
        extension.setPeer(event.getPeer().substring(6));
        extension.setPeerStatus(event.getPeerStatus());
        extension.setTime(DateUtils.toTimestamp(event.getDateReceived()));
        extension.setCause(event.getCause());
        
        try {
            
            String extensionJson = objectMapper.writeValueAsString(extension);
            messageToRabbit.processMessage(uuid.toString(), "PeerStatusAST", extensionJson);
            
            System.out.println("Se envía el mensaje de actualización del Peer: "+extension.getPeer());
            
        } catch (JsonProcessingException ex) {
            Logger.getLogger(PeerStatusEventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}