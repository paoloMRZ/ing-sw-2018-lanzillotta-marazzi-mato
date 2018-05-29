package it.polimi.se2018.server.network.fake_client;


import it.polimi.se2018.server.network.Lobby;

/**
 * La classe rappresenta l'entità associata a lato server che si occupa di gestire la comunicazione con uno specifico client.
 * @author Marazzi Paolo
 */
public abstract class FakeClient {

    private String nickname; //Nickname del client.
    protected Lobby lobby; //Stanza che raccoglie i giocatori.

    /**
     * Costruttore della classe.
     *
     * @param lobby stanza di gioco.
     * @param nickname nickname scelto dal giocatore.
     */
    public FakeClient(Lobby lobby, String nickname){
        if(lobby != null && nickname != null){
            this.lobby = lobby;
            this.nickname = nickname;
        }
    }

    /**
     *
     * @return nickname del giocatore.
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * Il metodo ha il compito di aggiornare il vero client tramite un messaggio dedicato.
     * @param message messaggio da inviare al client.
     */
    public abstract void update(String message);

    /**
     * Il metodo chiude la connessione con il client.
     */
    public abstract void closeConnection();
}
