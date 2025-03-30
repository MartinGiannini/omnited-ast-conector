package coop.bancocredicoop.asterisk.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import coop.bancocredicoop.omnited.exposition.NewStateDTO;
import coop.bancocredicoop.omnited.message.MessageToRabbit;
import coop.bancocredicoop.omnited.utils.DateUtils;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.event.NewStateEvent;
import org.springframework.stereotype.Component;

@Component
public class NewStateEventHandler implements AsteriskEventHandler<NewStateEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageToRabbit messageToRabbit;
    UUID uuid = UUID.randomUUID();

    public NewStateEventHandler(
            MessageToRabbit messageToRabbit
    ) {
        this.messageToRabbit = messageToRabbit;
    }
    
    @Override
    public boolean supports(ManagerEvent event) {
        return event instanceof NewStateEvent;
    }

    @Override
    public void handle(NewStateEvent event) {
        
        NewStateDTO newState = new NewStateDTO();
        newState.setCallee(event.getExten());
        newState.setPeer(event.getCallerIdName());
        newState.setTime(DateUtils.toTimestamp(event.getDateReceived()));
        
        try {
            
            String newStateJson = objectMapper.writeValueAsString(newState);
            messageToRabbit.processMessage(uuid.toString(), "NewStateAST", newStateJson);
            
        } catch (JsonProcessingException ex) {
            Logger.getLogger(PeerStatusEventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}