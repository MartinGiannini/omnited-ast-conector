package coop.bancocredicoop.asterisk.handler;

import org.asteriskjava.manager.event.ManagerEvent;

public interface AsteriskEventHandler<T extends ManagerEvent> {
    /**
     * Indica si el handler soporta el evento recibido.
     * @param event
     * @return 
     */
    boolean supports(ManagerEvent event);

    /**
     * Procesa el evento recibido.
     * @param event
     */
    void handle(T event);
}