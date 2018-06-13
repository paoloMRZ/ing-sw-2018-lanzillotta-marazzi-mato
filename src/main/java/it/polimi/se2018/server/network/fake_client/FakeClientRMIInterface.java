package it.polimi.se2018.server.network.fake_client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia remota esposta dalla classe FakeCLientRMI per permettere al vero client di comunicare direttamento col suo corrispettivo
 * fake client tramite RMI.
 * @author Marazzi Paolo
 */
public interface FakeClientRMIInterface extends Remote{
    /**
     * Metodo esposto tramite RMI (attraverso l'interfaccia FakeClientRMIInterface) al vero client per permettergli di mandare messaggi direttamente
     * al suo corrispettivo fake client sul server.
     *
     * Appene viene ricevuto un messaggio sulla socket la classe lo notifyFromFakeView alla lobby che si occuperà di
     * passare l'informazione ai destinatari.
     *
     * @param message messaggio da inviare.
     */
    void sendToserver(String message) throws RemoteException;
}
