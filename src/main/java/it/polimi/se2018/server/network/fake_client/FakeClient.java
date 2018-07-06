package it.polimi.se2018.server.network.fake_client;


import it.polimi.se2018.server.exceptions.ConnectionCloseException;
import it.polimi.se2018.server.network.Lobby;

/**
 * La classe rappresenta l'entità associata a lato server che si occupa di gestire la comunicazione con uno specifico client.
 * @author Marazzi Paolo
 */
public abstract class FakeClient {

    private String nickname; //Nickname del client.
    protected FakeClientObserver lobby; //Stanza che raccoglie i giocatori.

    /**
     * Costruttore della classe.
     *
     * @param nickname nickname scelto dal giocatore.
     */
    FakeClient(String nickname) {
        if (nickname != null) {
            this.lobby = Lobby.getLobby();
            this.nickname = nickname;
        }
    }


    /**
     * @return nickname del giocatore.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Il metodo ha il compito di aggiornare il vero client tramite un messaggio dedicato.
     *
     * @param message messaggio da inviare al client.
     * @throws ConnectionCloseException Sollevata se la connessione risulta chiusa.
     */
    public abstract void update(String message) throws ConnectionCloseException;

    /**
     * Il metodo chiude la connessione con il client.
     */
    public abstract void closeConnection();

    /**
     * Il metodo indica se il fake client è stato congelato.
     *
     * @return stato del fake client.
     */
    public abstract boolean isFreezed();

}