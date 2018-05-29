package it.polimi.se2018.server.network.fake_client;


import it.polimi.se2018.client.connection_handler.ClientInterface;
import it.polimi.se2018.server.network.Lobby;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * La classe rappresenta l'entità associata a lato server che si occupa di gestire la comunicazione con uno specifico client
 * che ha scelto di connettersi tramite RMI.
 *
 * @author Marazzi Paolo
 */
public class FakeClientRMI extends FakeClient implements server.fake_client.FakeClientRMIInterface {

    private ClientInterface clientInterface;

    /**
     * Costruttore della classe.
     *
     * @param clientInterface interfaccia remota del client di cui si deve gestire la comunicazione.
     * @param lobby stanza di gioco.
     * @param nickname nickname scelto dal giocatore
     * @throws RemoteException //TODO documentarsi su questa eccezione.
     */
    public FakeClientRMI(ClientInterface clientInterface, Lobby lobby, String nickname) throws RemoteException {
        super(lobby, nickname); //Richiamo il costruttore padre.

        if(clientInterface != null && lobby != null){
            this.clientInterface = clientInterface;

            //Passo al vero client l'interfaccia remota del suo corrispettivo fake client su server (cioè questo oggetto).
            server.fake_client.FakeClientRMIInterface remoteRef = (server.fake_client.FakeClientRMIInterface) UnicastRemoteObject.exportObject(this, 0);
            clientInterface.accept(remoteRef);
        }
    }

    /**
     * Questo metodo si occupa di aggiornare il client tramite un messaggio.
     * La comunicazione col client avviene tramite RMI.
     * @param message messaggio da inviare al client.
     */
    @Override
    public void update(String message) {
        try {
            clientInterface.sendToClient(message.concat("\n"));
        } catch (RemoteException e) {
            System.out.println("[*]ERRORE di connessione: " + e.getMessage() + "!");//TODO definire bene come gestire questa eccezione.
        }
    }

    /**
     * Il metodo chiude la connessione col client inviandogli un messaggio di notifica.
     */
    @Override
    public void closeConnection() {
        try {
            clientInterface.sendToClient("bye\n"); //TODO Sistemare il messaggio passato per la chiusura della connessione.
        } catch (RemoteException e) {
            System.out.println("[*]ERRORE di connessione: " + e.getMessage() + "!");
        }
    }

    /**
     * Metodo esposto tramite RMI (attraverso l'interfaccia FakeClientRMIInterface) al vero client per permettergli di mandare messaggi direttamente
     * al suo corrispettivo fake client sul server.
     *
     * Appene viene ricevuto un messaggio sulla socket la classe lo notifica alla lobby che si occuperà di
     * passare l'informazione ai destinatari.
     *
     * @param message messaggio da inviare.
     */
    @Override
    public void sendToserver(String message){
        if(message.equals("bye")){ //TODO Sistemare il messaggio passato per la chiusura della connessione.
            lobby.remove(super.getNickname());
        }else {
            System.out.println("[*]Ho ricevuto un messaggio\n");
            lobby.notifica(message.concat("\n"));
        }
    }
}
