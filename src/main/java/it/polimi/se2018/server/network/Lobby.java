package it.polimi.se2018.server.network;


import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.network.fake_client.FakeClient;
import it.polimi.se2018.server.timer.ObserverTimer;
import it.polimi.se2018.server.timer.SagradaTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta una stanza di gioco ed ha il compito di raccogliere tutti i fake client che gestiscono le connessioni dei client connessi
 * al server.
 * Oltre a questo fa da tramite tra view (client) e fake view (server).
 * @author Marazzi Paolo
 */
public class Lobby implements ObserverTimer {
    private int numberOfClient;
    private ArrayList<FakeClient> connections;
    private SagradaTimer timer;

    /**
     * Costruttore della classe.
     */
    public Lobby(int lifeTime){
        if(lifeTime > 0) {
            numberOfClient = 0;
            connections = new ArrayList<>();
            timer = new SagradaTimer(lifeTime);
            timer.add(this); //Mi aggiungo alla lista degli osservatori del timer. TODO Se di decide di togliere la classe astratta observableTimer questo metodo forse sprisce!
        } //TODO gestire il caso in cui viene passato un parametro sbagliato.
    }

    /**
     * Il metodo aggiunge un fake client alla lobby solo se il suo nickname non è già utilizzato da un'altro fake client già inserito.
     * @param connection fake client da inserire.
     * @throws InvalidNicknameException viene sollevata se il nickname del fake client da inserire è già utilizzato da un altro fake client contenuto nella lobby.
     */
    public synchronized void add (FakeClient connection) throws InvalidNicknameException {
        if (numberOfClient < 4) {
            if (getNicknames().contains(connection.getNickname()))
                throw new InvalidNicknameException();
            else {
                connections.add(connection);
                numberOfClient++;

                if (numberOfClient == 4) {
                    //TODO richiamo il metodo privato per cosrtuire la pertita e avviarla
                    timer.stop();
                    System.out.println("[*]Si sono collegati 4 clients --> avvio la partita");
                }

                if (numberOfClient == 1 && !timer.isStarted()) {
                    System.out.println("[*]SI è connesso il primo client --> avvio il timer\n");
                    timer.start();
                }
            }
        }
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
                numberOfClient--;
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

    @Override
    public void timerUpdate() {
        timer.stop();

        if(numberOfClient >= 2){
            //TODO richiamo il metodo privato per costruire e avviare la partita.
            System.out.println("[*]Ci sono " + numberOfClient + " clients --> Avvio la partita");
        }
        else
        {
           //TODO vedere sulle specifiche del progetto cosa si dice di fare.
            System.out.println("[*]Ci sono " + numberOfClient + " clients --> Non avvio la partita");
        }
    }
}
