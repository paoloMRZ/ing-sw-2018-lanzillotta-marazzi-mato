package it.polimi.se2018.server.network;


import it.polimi.se2018.server.exceptions.ConnectionCloseException;
import it.polimi.se2018.server.exceptions.GameStartedException;
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
    private boolean isOpen; //Indica lo stato della lobby, cioè se un client può connettersi o no.

    /**
     * Costruttore della classe.
     */
    public Lobby(int lifeTime) {
        if (lifeTime > 0)
            timer = new SagradaTimer(lifeTime);
        else
            timer = new SagradaTimer(120);

        timer.add(this); //Mi aggiungo alla lista degli osservatori del timer.
        isOpen = true;
        numberOfClient = 0;
        connections = new ArrayList<>();
    }


//----------------------------------------------------------------------------------------------------------------------
    //TODO Sarebbe comodo raccogliere tutti i metodi di parsing in una classe statica!

    private boolean isDisconnectMessage(String message){
        String[] token = message.replace("\n","").split("/");

        return token[3].equals("rete") && token[5].equals("disconnect");
    }

    private boolean isTimeoutMessage(String message){
        String[] token = message.split("/");

        return token[3].equals("rete") && token[5].equals("timeout");
    }

    private String getSenderOfMessage(String message){
        return message.split("/")[1];
    }

    private String getAddresseeFromMessage(String message){
        return message.split("/")[2];
    }
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Questa classe crea una nuova partita. Per fare questo crea FakeView, Controller e Model e li collega fra loro.
     */
    private void createGame(){
        this.isOpen = false; //Come prima cosa chiudo la lobby.
        this.timer.stop();
        //TODO Implementare il metodo.
    }

    /**
     * Il metodo aggiunge un fake client alla lobby solo se il suo nickname non è già utilizzato da un'altro fake client già inserito.
     * @param connection fake client da inserire.
     * @throws InvalidNicknameException viene sollevata se il nickname del fake client da inserire è già utilizzato da un altro fake client contenuto nella lobby.
     */
    public synchronized void add (FakeClient connection) throws InvalidNicknameException, GameStartedException {

        if (getNicknames().contains(connection.getNickname())) { //Controlle se esiste un client con lo stesso nick.

            if (this.isFreezedFakeClient(connection.getNickname())) { //Se il client con lo stesso nick è congelato allora lo scongelo.

                this.unfreezeFakeClient(connection); //Richiamo il metodo privato per scongelare il client.
            } else
                throw new InvalidNicknameException(); //Se il client con lo stesso nick non è congelato allora sollevao un'eccezione.

        } else if (isOpen) { //Se il nick non è già utilizzato aggiungo il client alla lobby solo se la partita non è ancora iniziata.

            connections.add(connection);
            numberOfClient++; //Incremento il contatore dei client.

            if (numberOfClient == 4) { //Se è stato raggiunto il numero massimo di giocatori viene fermato il timer, chiusa la lobby e avviata la partita.
                createGame(); //Creazione della partita.
            }

            if (numberOfClient == 1 && !timer.isStarted()) { //Quando si connette il primo client avvio il timer.
                timer.start();
            }
        } else {
            throw new GameStartedException();
        }
    }

    /**
     * Il metodo rimuove dalla lobby il fake client specificato tramite il nickname e chiude la connessione ad esso dedicata.
     * Nel caso in cui il nickname passato non è presente nella lobby il metodo non effettua nessuna operazione.
     * @param nickname nickname del fake client da rimuovere.
     */
    public synchronized void remove(String nickname){
        for(FakeClient client: connections){ //Cerco il client darimuovere.
            if(client.getNickname().equals(nickname)) {
                client.closeConnection(); //Chiudo la sua connessione.
                connections.remove(client); //Lo rimuovo dalla lobby.
                numberOfClient--; //Decremento il numero di giocatori.

                if(numberOfClient + 1 == 2 && isOpen){ //Se la partita non è ancora stata avviata (lobby aperta) ed il numero di giocatori scende sotto i due resetto il timer.
                    timer.reset();
                }

                return;
            }
        }
    }

    private void freezeFakeClient(String nickname){ //Congelare un client significa chiudere la sua connessione senza toglierlo dalla lobby.
        for(FakeClient client : connections){
            if(client.getNickname().equals(nickname))
                client.closeConnection();
        }
    }

    private void unfreezeFakeClient(FakeClient newConnection){ //Scongelare un client significa togliere dalla lobby il fake client congelato e sostituirlo con quello nuovo.
        for(FakeClient client : connections){ //Individuo il vecchio client tramite il nickname.
            if(client.getNickname().equals(newConnection.getNickname())) {
                this.connections.set(connections.indexOf(client), newConnection); //Sostituisco il client congelato con quello nuovo.
            }
        }
    }

    private boolean isFreezedFakeClient(String nickname){
        for(FakeClient client : connections){
            if(client.getNickname().equals(nickname))
                return client.isFreezed();
        }

        return false;
    }

    /**
     * Richiamato da un fake client qundo riceve un messaggio.
     * @param message
     */

    public synchronized void receiveNotify(String message){
        if(isDisconnectMessage(message)){ //Controllo se il messaggio ricevuto è una richiesta di disconnessione.
            remove(getSenderOfMessage(message));
        } else if( isTimeoutMessage(message)){ //Controllo se il messaggio ricevuto è una richiesta di congelamento.
            freezeFakeClient(getSenderOfMessage(message));
        } else {
            //TODO in tutti gli altri casi devo girare il messaggio al controller.
        }
    }

    /**
     * Il metodo richiama il metodo update di tutti o alcuni fake client contenuti nella lobby.
     * @param message messaggio da passare al vero client tramite il metodo update.
     */

    public void sendNotify(String message){ //TODO sarà da gestire bene in base a cosa si decide di fare col timer.

        if(getAddresseeFromMessage(message).equals("!")){ //Controllo se è un messaggio di broadcast, se si lo mando a tutti.
            for(FakeClient client : connections){ //Mando il messaggio ad ogni client non congelato.
                if(!client.isFreezed()) {
                    try {
                        client.update(message);
                    } catch (ConnectionCloseException e) {
                        if(this.isOpen)
                            remove(client.getNickname());
                        else
                            freezeFakeClient(client.getNickname());
                    }
                }
            }
        }else{ //Se il messaggio non è broadcast lo mando solo al destinatario.
            for(FakeClient client : connections){
                if(!client.isFreezed() && client.getNickname().equals(getAddresseeFromMessage(message))) {
                    try {
                        client.update(message);
                    } catch (ConnectionCloseException e) {
                        if(this.isOpen)
                            remove(client.getNickname());
                        else
                            freezeFakeClient(client.getNickname());
                    }
                }
            }
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

        if(numberOfClient >= 2){
            createGame();
            System.out.println("[*]Ci sono " + numberOfClient + " client --> Avvio la partita"); //TODO da rimuovere.
            this.sendNotify("/###/!/Partita creata\n"); //TODO da rimuovere.
        }
        else
        {
            //TODO vedere sulle specifiche del progetto cosa si dice di fare. --> Non è specificato ... io svuoterei la lobby e resetterei il timer.
            System.out.println("[*]Ci sono " + numberOfClient + " clients --> Non avvio la partita"); //TODO da rimuovere.
            timer.stop();
            for(FakeClient client: connections)
                client.closeConnection();
        }
    }
}
