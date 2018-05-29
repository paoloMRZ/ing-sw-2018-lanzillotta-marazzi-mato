package it.polimi.se2018.client.connection_handler;

import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.network.implementation.ServerInterface;
import server.fake_client.FakeClientRMIInterface;

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

    private ServerInterface serverInterface;
    private FakeClientRMIInterface fakeClientInterface;

    /**
     * Costruttore della classe.
     * Oltre a costruire l'oggetto recupera l'interfaccia remota del server e per connetersi a quest'ultimo tramite RMI.
     * Se il nickname scelto dall'utente è già utilizzato da un altro utente connesso sul server la connessione viene chiusa e viene
     * sollevata l'eccezione InvalidNicknameException.
     *
     * @param nickname nickname scelto dall'utente.
     * @throws InvalidNicknameException viene sollevata se il nickname scelto è già in uso sul server.
     */
    public ConnectionHandlerRMI(String nickname) throws InvalidNicknameException {

        super();

        try {
            System.out.println("[*]Mi connetto al registry e recupero l'interfaccia del server.");
            serverInterface = (ServerInterface) Naming.lookup("//localhost/MyServer");
            ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(this, 0); //Lo faccio perchè non posso far estendere unicast a questa classe visto che ne estende già una.

            System.out.println("[*]Mi connetto al server tramite RMI.");
            serverInterface.add(remoteRef,nickname);

            System.out.println("[*]Connessione avvenuta con successo! \n");

        } catch (NotBoundException e) {
            System.out.println("[*]ERRORE il riferimento passato non è associato a nulla!"); //TODO Definire bene come gestire quest'eccezione!
        } catch (MalformedURLException e) {
            System.out.println("[*]ERRORE URL non trovato!");//TODO Definire bene come gestire quest'eccezione!
        } catch (RemoteException e) {
            System.out.println("[*]ERRORE di connessione: " + e.getMessage() + "!");//TODO Definire bene come gestire quest'eccezione!
        }
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
            System.out.println("[*]ERRORE di connessione: " + e.getMessage() + "!");//TODO Definire bene come gestire quest'eccezione!
        }
    }

    /**
     * Metodo richiamato dal server per mandare un messaggio al client tramite l'interfaccia remota di quest'ultimo.
     * Quando il client riceve un messaggio lo notifica alla view.
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
    public void accept(FakeClientRMIInterface fakeClientInterface){
        if(fakeClientInterface != null) {
            this.fakeClientInterface = fakeClientInterface;
            System.out.println("[*]Ho ricevuto l'interfaccia del mio corrispettivo fake client");
        }
    }
}
