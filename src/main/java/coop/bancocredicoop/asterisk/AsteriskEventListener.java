package coop.bancocredicoop.asterisk;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.event.PeerStatusEvent;
import org.asteriskjava.manager.event.NewStateEvent;
import org.asteriskjava.manager.event.DialEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsteriskEventListener implements ManagerEventListener {

    @Autowired
    private ManagerConnection managerConnection;

    @PostConstruct
    public void init() {
        try {
            // Agregar este componente como listener de eventos
            managerConnection.addEventListener(this);
            // Conectarse y autenticar
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
        // Ejemplo: Eventos de registración o cambio de estado de endpoints
        if (event instanceof PeerStatusEvent) {
            PeerStatusEvent peerStatus = (PeerStatusEvent) event;
            System.out.println("Evento PeerStatus: Endpoint " + peerStatus.getPeer() 
                    + " cambió a estado " + peerStatus.getPeerStatus());
        }
        // Ejemplo: Eventos de cambio de estado de llamadas
        else if (event instanceof NewStateEvent) {
            NewStateEvent newState = (NewStateEvent) event;
            System.out.println("Nuevo estado en canal " + newState.getChannel() 
                    + ": " + newState.getChannelStateDesc());
        }
        // Ejemplo: Eventos de inicio de llamada (Dial)
        else if (event instanceof DialEvent) {
            DialEvent dialEvent = (DialEvent) event;
            System.out.println("DialEvent: " + dialEvent.getCallerIdNum() 
                    + " está llamando a " + dialEvent.getDestination());
        }
        // Ejemplo: Eventos de finalización de llamada (Hangup)
        else if (event instanceof HangupEvent) {
            HangupEvent hangupEvent = (HangupEvent) event;
            System.out.println("HangupEvent en canal: " + hangupEvent.getChannel() 
                    + " con causa " + hangupEvent.getCauseTxt());
        }
        // Otros eventos
        else {
            
        }
    }
}