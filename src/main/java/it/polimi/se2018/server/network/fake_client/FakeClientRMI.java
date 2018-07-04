package it.polimi.se2018.server.network.fake_client;


import it.polimi.se2018.client.connection_handler.ClientInterface;
import it.polimi.se2018.server.exceptions.ConnectionCloseException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidParameterException;


/**
 * La classe rappresenta l'entità associata a lato server che si occupa di gestire la comunicazione con uno specifico client
 * che ha scelto di connettersi tramite RMI.
 *
 * @author Marazzi Paolo
 */
public class FakeClientRMI extends FakeClient implements FakeClientRMIInterface {

    private ClientInterface clientInterface;
    private boolean isFreeze;
    /**
     * Costruttore della classe.
     *
     * @param clientInterface interfaccia remota del client di cui si deve gestire la comunicazione.
     * @param nickname nickname scelto dal giocatore
     * @throws RemoteException viene sollevata se l'interfaccia remota del client non è raggiungibile.
     */
    public FakeClientRMI(ClientInterface clientInterface, String nickname) throws RemoteException {
        super(nickname); //Richiamo il costruttore padre.

        if(clientInterface != null && lobby != null){
            this.clientInterface = clientInterface;
            this.isFreeze = false;

            //Passo al vero client l'interfaccia remota del suo corrispettivo fake client su server (cioè questo oggetto).
            FakeClientRMIInterface remoteRef = (FakeClientRMIInterface) UnicastRemoteObject.exportObject(this, 0);
            clientInterface.accept(remoteRef);
        } else
            throw new InvalidParameterException();
    }

    /**
     * Questo metodo si occupa di aggiornare il client tramite un messaggio.
     * La comunicazione col client avviene tramite RMI.
     * @param message messaggio da inviare al client.
     */
    @Override
    public void update(String message) throws ConnectionCloseException {
        if (!isFreeze) {
            try {
                clientInterface.sendToClient(message);
            } catch (RemoteException e) {
                throw new ConnectionCloseException();
            }
        }
    }

    /**
     * Il metodo chiude la connessione col client inviandogli un messaggio di notifica.
     */
    @Override
    public void closeConnection() {

        try {
            //In pratica vado a mettere a null i rispettivi riferimenti alle interfacce pubbliche.
            clientInterface.accept(null);
        } catch (RemoteException e) {
            //Non si è interessati al fatto che la chiusura può non essere andata a buon fine perché se si sta chiudendo
            //questa connessione significa che non si è più intenzionati ad usarla.
        }

        this.clientInterface = null;
        this.isFreeze = true;
    }

    @Override
    public boolean isFreezed() {
        return isFreeze;
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
    public void sendToserver(String message) {
        if(!isFreeze) {
            lobby.notifyFromFakeClient(message);
        }
    }
}

