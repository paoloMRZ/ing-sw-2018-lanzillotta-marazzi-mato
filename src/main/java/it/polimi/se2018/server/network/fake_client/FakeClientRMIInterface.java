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
     * Appene viene ricevuto un messaggio sulla socket la classe lo notifica alla lobby che si occuperà di
     * passare l'informazione ai destinatari.
     *
     * @param message messaggio da inviare.
     * @throws RemoteException viene sollevata se l'interfaccia remota del client non è raggiungibile.
     */
    void sendToserver(String message) throws RemoteException;
}
