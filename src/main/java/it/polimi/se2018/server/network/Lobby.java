package it.polimi.se2018.server.network;

import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.network.fake_client.FakeClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta una stanza di gioco ed ha il compito di raccogliere tutti i fake client che gestiscono le connessioni dei client connessi
 * al server.
 * Oltre a questo fa da tramite tra view (client) e fake view (server).
 * @author Marazzi Paolo
 */
public class Lobby {
    private ArrayList<FakeClient> connections;
    //TODO Aggiungere il timer e la sua gestione.

    /**
     * Costruttore della classe.
     */
    public Lobby(){
        connections = new ArrayList<>();
    }

    /**
     * Il metodo aggiunge un fake client alla lobby solo se il suo nickname non è già utilizzato da un'altro fake client già inserito.
     * @param connection fake client da inserire.
     * @throws InvalidNicknameException viene sollevata se il nickname del fake client da inserire è già utilizzato da un altro fake client contenuto nella lobby.
     */
    public synchronized void add (FakeClient connection) throws InvalidNicknameException {
        if(getNicknames().contains(connection.getNickname()))
            throw new InvalidNicknameException();
        else
            connections.add(connection);
    }

    /**
     * Il metodo rimuove dalla lobby il fake client specificato tramite il nickname e chiude la connessione ad esso dedicata.
     * @param nickname nickname del fake client da rimuovere.
     */
    public synchronized void remove(String nickname){
        for(FakeClient client: connections){
            if(client.getNickname().equals(nickname)) {
                client.closeConnection();
                connections.remove(client);
                System.out.println("[*]" + client.getNickname() + " rimosso dalla lobby!");
                return;
            }
        }

        //TODO Gestire il caso in cui il nickname passato non esiste!
    }

    /**
     * Il metodo richiama il metodo update di tutti o alcuni fake client contenuti nella lobby.
     * @param message messaggio da passare al vero client tramite il metodo update.
     */
    public synchronized void notifica(String message){
        for(FakeClient connection : connections){
            connection.update(message);
        }
    }

    /**
     * Il metodo restituisce la lista dei nickname dei fake client contenuti nella lobby
     * @return lista dei nickname.
     */
    public synchronized List<String> getNicknames(){
        ArrayList<String> nicknames = new ArrayList<>();

        for(FakeClient client: connections)
            nicknames.add(client.getNickname());

        return nicknames;
    }

}
