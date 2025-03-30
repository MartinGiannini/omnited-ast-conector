package coop.bancocredicoop.asterisk;

import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.event.ManagerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import coop.bancocredicoop.asterisk.handler.AsteriskEventHandler;

@Component
public class AsteriskEventListener implements ManagerEventListener {

    @Autowired
    private ManagerConnection managerConnection;

    // Inyectamos todos los handlers definidos en el contexto
    @Autowired
    private List<AsteriskEventHandler<? extends ManagerEvent>> eventHandlers;

    @PostConstruct
    public void init() {
        try {
            managerConnection.addEventListener(this);
            managerConnection.login();
            System.out.println("Conexión a Asterisk establecida exitosamente.");
        } catch (IOException | IllegalStateException | AuthenticationFailedException | TimeoutException e) {
            System.err.println("Error al conectar con Asterisk: " + e.getMessage());
        }
    }

    @PreDestroy
    public void cleanup() {
        managerConnection.logoff();
    }

    @Override
    public void onManagerEvent(ManagerEvent event) {
        boolean handled = false;
        // Delegamos el procesamiento al handler que soporte el evento.
        for (AsteriskEventHandler handler : eventHandlers) {
            if (handler.supports(event)) {
                handler.handle(event);
                handled = true;
            }
        }
        if (!handled) {
            System.out.println("Evento no manejado: " + event.getClass().getSimpleName() + " - " + event);
        }
    }
}