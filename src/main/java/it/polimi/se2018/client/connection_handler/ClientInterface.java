package it.polimi.se2018.client.connection_handler;


import it.polimi.se2018.server.network.fake_client.FakeClientRMIInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia remota implementata dal gestore di connessioni RMI.
 *
 */
public interface ClientInterface extends Remote {
    /**
     * Metodo richiamato dal server per mandare un messaggio al client tramite l'interfaccia remota di quest'ultimo.
     * Quando il client riceve un messaggio lo receiveNotify alla view.
     * @param message messaggio da inviare.
     */
    void sendToClient(String message) throws RemoteException;

    /**
     * Questo metodo viene richiamato dal fake client sul server per passare al vero client con cui comunica (cioè queso oggetto) la sua interfaccia remota
     * in modo da creare una comunicazione diretta tra client e fake client.
     * @param fakeClientInterface interfaccia remota del fake client che chiama questo metodo.
     */
    void accept(FakeClientRMIInterface fakeClientInterface) throws RemoteException;
}
