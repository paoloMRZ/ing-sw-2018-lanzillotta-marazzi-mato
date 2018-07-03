package it.polimi.se2018.server.network;


import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.exceptions.ConnectionCloseException;
import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.message.network_message.NetworkMessageCreator;
import it.polimi.se2018.server.message.network_message.NetworkMessageParser;
import it.polimi.se2018.server.network.fake_client.FakeClient;
import it.polimi.se2018.server.network.fake_client.FakeClientObserver;
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
public class Lobby implements ObserverTimer, FakeClientObserver {
    private FakeView fakeView;
    private Controller controller;
    private int numberOfClient;
    private ArrayList<FakeClient> connections;
    private SagradaTimer timer;
    private boolean isOpen; //Indica lo stato della lobby, cioè se un client può connettersi o no.

    private static Lobby instance = null;



    public static Lobby factoryLobby(int lifeTime){
        if(instance == null)
            instance = new Lobby(lifeTime);

        return instance;
    }

    public static Lobby factoryLobby(){
        if(instance == null)
            instance = new Lobby(60); //Default = 60 sec.

        return instance;
    }

    /**
     * Costruttore della classe.
     */
    private Lobby(int lifeTime) {
        if (lifeTime > 0)
            timer = new SagradaTimer(lifeTime);
        else
            timer = new SagradaTimer(120);

        timer.add(this); //Mi aggiungo alla lista degli osservatori del timer.
        isOpen = true;
        numberOfClient = 0;
        connections = new ArrayList<>();
        fakeView = null;
    }

    /**
     * Questa classe crea il "triangolo MVC" richiamando il costruttore della fake view.
     */
    private void createGame() {
        this.isOpen = false; //Come prima cosa chiudo la lobby.
        this.timer.stop(); //Fermo il timer.
        fakeView = new FakeView(this); //Creo MVC.
        controller = new Controller(getNicknames(), 120);//todo collegare con tempo deisderato
        fakeView.register(controller);
        try {
            controller.START();
        } catch (Exception e) {
            e.printStackTrace(); //TODO Da sistemare!!!!!
        }

    }

    /**
     * Il metodo restituisce i nickname dei fake client contenuti nella lobby.
     *
     * @return lista dei nickname.
     */
    private synchronized List<String> getNicknames(){
        ArrayList<String> nicknames = new ArrayList<>();

        for(FakeClient client: connections)
            nicknames.add(client.getNickname());

        return nicknames;
    }


    /**
     * Il metodo effettua una ricerca per nickname.
     *
     * @param nickname nickname del fakeClient che si vuole trovare.
     * @return riferimento al fakeClient. Se non viene trovate nessun fake client si restituisce null
     */
    private FakeClient serachByNickname(String nickname){
        for(FakeClient client : connections){
            if(client.getNickname().equals(nickname))
                return client;
        }

        return null;
    }

    /**
     * Il metodo congela un fake client.
     *
     * @param nickname nickname del fake client che si vuole congelare.
     */
    private void freezeFakeClient(String nickname) { //Congelare un client significa chiudere la sua connessione senza toglierlo dalla lobby.

        FakeClient fakeClient = serachByNickname(nickname); //Ricerco il client tramite il suo nickname.
        if (fakeClient != null) { //Se l'ho trovato lo congelo, cioè chiudo la sua connessione ma non lo rimuovo dalla lobby.
            fakeClient.closeConnection();
            numberOfClient--;

            sendBroadcast(NetworkMessageCreator.getDisconnectMessage(nickname)); //Notifico tutti gli altri giocatori della disconnessione del client.
        }

    }

    /**
     * Il metodo scongela un fake client precedentemente congelato.
     * Questo metodo non fa altro che sostituire il vecchio fakeClient (quello congelato) con quello nuovo (cioè la
     * nuova connessione ricevuta).
     *
     * @param newConnection nuova connessione da sostituire a quella vecchia.
     */
    private void unfreezeFakeClient(FakeClient newConnection) { //Scongelare un client significa togliere dalla lobby il fake client congelato e sostituirlo con quello nuovo.
        FakeClient fakeClient = serachByNickname(newConnection.getNickname());//Individuo il vecchio client tramite il nickname.
        if (fakeClient != null) {
            this.connections.set(connections.indexOf(fakeClient), newConnection); //Sostituisco il client congelato con quello nuovo.
            numberOfClient++;

            if(fakeView != null) //Notifico la fake view  dello scongelamento di un giocatore.
                fakeView.messageIncoming(NetworkMessageCreator.getUnfreezeMessage(newConnection.getNickname()));

            sendBroadcast(NetworkMessageCreator.getConnectMessage(newConnection.getNickname())); //Notifico tutti i giocatori della connessione appena avvenuta.
        }

    }

    /**
     * Il metodo restituisce lo stato del fake client richiesto.
     * Se il fake client richiesto non è presente nella lobby il metodo restituisce true, cioè il fake client viene
     * considerato congelato.
     *
     * @param nickname nickanme del fake client su cui si vuole conoscere lo stato.
     * @return true se il fake client è congelato, false in caso contrario.
     */
    private boolean isFreezedFakeClient(String nickname){
        FakeClient fakeClient =  serachByNickname(nickname);
        return  fakeClient == null || fakeClient.isFreezed();
    }

    /**
     * Il metodo invia un messaggio in broadcast, cioè invia lo stesso messaggio a tutti i client connessi.
     * Se si trova un fakeClient con la connessione chiusa lo si congela o lo si rimuove in base allo stato della partita,
     * se la partita è iniziata(lobby chiusa) lo si congela, in caso contrario lo si rimuove.
     *
     * @param message mesaggio da inviare.
     */
    private void sendBroadcast(String message){
        for(FakeClient fakeClient: connections){
            if(!fakeClient.isFreezed()) {
                try {
                    fakeClient.update(message);
                } catch (ConnectionCloseException e) {
                    if(isOpen)
                        remove(fakeClient.getNickname());
                    else
                        this.freezeFakeClient(fakeClient.getNickname());
                }
            }
        }
    }

    /**
     * Il metodo invia un messaggio al suo destinatario.
     * Se il destiantario non è raggiungibile si congela o si rimuove il fake client in base allo stato della partita.
     *
     * @param message messaggio da inviare.
     */
    private void sendPrivate(String message){
        FakeClient fakeClient = serachByNickname(NetworkMessageParser.getMessageAddressee(message));

        try {

            if(fakeClient != null)
                fakeClient.update(message);

        } catch (ConnectionCloseException e) {
            if(isOpen)
                remove(fakeClient.getNickname());
            else
                freezeFakeClient(fakeClient.getNickname());
        }


    }


    /**
     * Il metodo rimuove dalla lobby il fake client specificato tramite il nickname e chiude la connessione ad esso dedicata.
     * Nel caso in cui il nickname passato non è presente nella lobby il metodo non effettua nessuna operazione.
     *
     * @param nickname nickname del fake client da rimuovere.
     */
    private synchronized void remove(String nickname) {
        FakeClient fakeClient = serachByNickname(nickname);

        if (fakeClient != null) {
            fakeClient.closeConnection(); //Chiudo la sua connessione.
            connections.remove(fakeClient); //Lo rimuovo dalla lobby.
            numberOfClient--; //Decremento il numero di giocatori.

            sendBroadcast(NetworkMessageCreator.getDisconnectMessage(nickname)); //Notifico i giocatori presenti nella lobby della disconnessione.

            if (numberOfClient + 1 == 2 && isOpen) { //Se la partita non è ancora stata avviata (lobby aperta) ed il numero di giocatori scende sotto i due resetto il timer.
                timer.reset();

            }
        }
    }


    /**
     * Il metodo svuota la stanza.
     * Per eseguire questa procedura invia un messaggio di disconnessione per ogni giocatore e chiude le connessioni
     * di tutti i giocatori.
     */
    private void clearLobby(){

        for(FakeClient client: connections) { //Per ogni client presente invio un messaggio di disconnessione e chiudo la relativa connessione.

            try {
                client.update(NetworkMessageCreator.getDisconnectMessage(client.getNickname()));
                client.closeConnection();
            } catch (ConnectionCloseException e) {
                client.closeConnection();
            }

        }

        connections = new ArrayList<>(); //Ricreo l'array list delle connessioni.
        this.numberOfClient = 0; //Metto a zero il numero di giocatori presenti nella stanza.
    }

    //TODO Aggiornare il commento.
    /**
     * Il metodo aggiunge un fake client alla lobby solo se:
     *  - il suo nickname non è già utilizzato da un'altro fake client già inserito.
     *  - la partita non è ancora iniziata ( cioè la lobby è aperta ).
     *
     * Se si tenta di aggiungere un nuovo client che possiede un nick già presente nella lobby si verifica se il fake client
     * corrispondente a quel nickname è congelato. In caso positivo lo si scongela (si permette al giocatore che si era disconesso di riprendere a giocare).
     * In caso negativo il client che ha fatto richiesta non viene inserito nella lobby.
     *
     * @param connection fake client da inserire.
     */
    public synchronized void add (FakeClient connection) throws InvalidNicknameException, GameStartedException {

        if (getNicknames().contains(connection.getNickname())) { //Controlle se esiste un client congelato con lo stesso nick.

            if(isFreezedFakeClient(connection.getNickname())) //Controllo se il fake client che usa questo nick è congelato, se si lo scongelo.
                this.unfreezeFakeClient(connection); //Richiamo il metodo privato per scongelare il client.
            else
                throw new InvalidNicknameException(); //Se il nickname è utilizzato da un utente attivo sollevo un'eccezinoe.

        } else { //Se il nick non è utilizzato aggiungo il client.

            //Il nuovo client viene aggiunto solo se la lobby è aperta, cioè se la partita non è ancora iniziata.
            if (isOpen) {
                connections.add(connection); //Aggiungo il fake client all'ArrayList.

                numberOfClient++; //Incremento il contatore dei client.

                notifyFromFakeView(NetworkMessageCreator.getConnectMessage(connection.getNickname())); //Notifico tutti i client già presenti della connessione di un nuovo giocatore.

                if (numberOfClient == 4) { //Se è stato raggiunto il numero massimo di giocatori viene fermato il timer, chiusa la lobby e avviata la partita.
                    createGame(); //Creazione della partita.
                }

                if (numberOfClient == 1 && !timer.isStarted()) { //Quando si connette il primo client avvio il timer.
                    timer.start();
                }
            }else
                throw new GameStartedException(); //Se la partita è già iniziata sollevo un'eccezione.
        }
    }


    /**
     * Richiamato da un fake client qundo riceve un messaggio.
     * @param message messaggio ricevuto dal fake client.
     */
    @Override
    public synchronized void notifyFromFakeClient (String message){
        System.out.println(message); //TODO è da rimuovere!
        if(NetworkMessageParser.isDisconnectMessage(message)){ //Controllo se il messaggio ricevuto è una richiesta di disconnessione.
            if(isOpen)// Se la lobby è ancora aperta (quindi la partita non è stata avviata) rimuovo il giocatore
                remove(NetworkMessageParser.getMessageSender(message));
            else { //In caso contrario lo congelo e lo notifio alla fake view.
                freezeFakeClient(NetworkMessageParser.getMessageSender(message));

                if(fakeView != null) //Notifico la fake view del congelamento.
                    fakeView.messageIncoming(NetworkMessageCreator.getFreezeMessage(NetworkMessageParser.getMessageSender(message)));
            }
        }  else { //Se non è un messaggio di disconnessione lo giro direttamente alla fake view.
            if(fakeView != null)
                fakeView.messageIncoming(message);
        }
    }

    /**
     * Il metodo richiama il metodo update di tutti o alcuni fake client contenuti nella lobby.
     * @param message messaggio da passare al vero client tramite il metodo update.
     */

    public void notifyFromFakeView(String message){
        System.out.println(message); //TODO è da rimuovere!

        if(NetworkMessageParser.isFreezeMessage(message)) //Se ricevo un messaggio di congelamento congelo il fake client associato.
            freezeFakeClient(NetworkMessageParser.getMessageInfo(message));

        else if(NetworkMessageParser.isDisconnectMessage(message)){ //Se ricevo un messaggio di disconnessione disconnetto il fake client associato.
            this.remove(NetworkMessageParser.getMessageInfo(message)); //Rimuovo dalla lobby il giocatore indicato nel messaggio.

        }else{ //In tutti gli altri casi devo inviare i messaggi a/ai giocatore/i.

            if(NetworkMessageParser.isBroadcastMessage(message)){ //Se è un messaggio di broadcast lo mando in broadcat.
                sendBroadcast(message);
            }else{ //Se no lo mando privato.
                sendPrivate(message);
            }

            //Se ho mandato un messaggio che segnala il termine di una partita svuto la lobby per pormi nello stato iniziale.
            //Quindi posso accettare nuove connessioni per avviare una nuova partita.
            if(NetworkMessageParser.isWinnerMessage(message))
                clearLobby();
        }
    }


    /**
     * Il metodo viene richiamato allo scadere del tempo dall'oggetto 'SagradaTimer' che si sta osservando.
     */
    @Override
    public void timerUpdate() {

        if(numberOfClient >= 2){ //Se ho due o più giocatori creo la partita.
            createGame();
        }
        else //Se non ci sono abbastanza giocatori fermo il timer e svuoto la lobby. In pratica mi rimetto nelle "condizioni iniziali".
        {
            timer.stop();
            clearLobby();
        }
    }
}
