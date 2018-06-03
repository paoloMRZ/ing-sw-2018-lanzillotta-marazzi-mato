package it.polimi.se2018.client.connection_handler;

import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.network.fake_client.FakeClientRMIInterface;
import it.polimi.se2018.server.network.implementation.ServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * La classe ha il compito di gestire la connessione tra client e server utilizzando la tecnologia RMI.
 * @author  Marazzi Paolo
 */
public class ConnectionHandlerRMI extends ConnectionHandler implements ClientInterface {

    private FakeClientRMIInterface fakeClientInterface;
    private String nickname;

    /**
     * Costruttore della classe.
     * Oltre a costruire l'oggetto recupera l'interfaccia remota del server e per connetersi a quest'ultimo tramite RMI.
     * Se il nickname scelto dall'utente è già utilizzato da un altro utente connesso sul server la connessione viene chiusa e viene
     * sollevata l'eccezione InvalidNicknameException.
     *
     * @param nickname nickname scelto dall'utente.
     * @throws InvalidNicknameException viene sollevata se il nickname scelto è già in uso sul server.
     */
    public ConnectionHandlerRMI(String nickname) throws InvalidNicknameException, GameStartedException, NotBoundException, MalformedURLException, RemoteException {

        super();

        this.nickname = nickname;
        ServerInterface serverInterface;

        serverInterface = (ServerInterface) Naming.lookup("//localhost/MyServer");
        ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(this, 0); //Lo faccio perchè non posso far estendere unicast a questa classe visto che ne estende già una.

        serverInterface.add(remoteRef, nickname);

        super.notifica("/###/" + nickname + "/rete/?/ok\n");

    }

    /**
     * Il metodo manda un messaggio al server.
     * @param message messaggio da inviare al server.
     */
    @Override
    public void sendToServer(String message) {
        try {
            fakeClientInterface.sendToserver(message);
        } catch (RemoteException e) {
            super.notifica("/###/" + nickname + "/rete/?/disconnect\n");
        }
    }

    /**
     * Metodo richiamato dal server per mandare un messaggio al client tramite l'interfaccia remota di quest'ultimo.
     * Quando il client riceve un messaggio lo receiveNotify alla view.
     * @param message messaggio da inviare.
     */
    @Override
    public void sendToClient(String message) {
        super.notifica(message);
    }

    /**
     * Questo metodo viene richiamato dal fake client sul server per passare al vero client con cui comunica (cioè queso oggetto) la sua interfaccia remota
     * in modo da creare una comunicazione diretta tra client e fake client.
     * @param fakeClientInterface interfaccia remota del fake client che chiama questo metodo.
     */
    @Override
    public void accept(FakeClientRMIInterface fakeClientInterface) {
        this.fakeClientInterface = fakeClientInterface;
        if(this.fakeClientInterface == null)
            super.notifica("/###/" + nickname + "/rete/?/disconnect\n");

    }
}
