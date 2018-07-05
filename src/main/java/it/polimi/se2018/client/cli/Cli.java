package it.polimi.se2018.client.cli;


import it.polimi.se2018.client.cli.controller.states.*;
import it.polimi.se2018.client.cli.controller.states.states_utensil.*;
import it.polimi.se2018.client.cli.controller.stdin_controller.Input;
import it.polimi.se2018.client.cli.controller.stdin_controller.InputObserver;
import it.polimi.se2018.client.cli.controller.translater.Translater;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.connection_handler.ConnectionHandlerObserver;
import it.polimi.se2018.client.message.ClientMessageParser;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe, che implementa uno state pattern, si occupa di gestire i messaggi provenienti dalla rete e dallo standard input.
 * I messaggi alcuni messaggi di rete cambiano lo stato della classe (attributo 'state') mentre altri agiscono sullo
 * stato della classe 'Game'.
 *
 * In base allo stato in cui si trova la classe i dati forniti dall'utente su standard input acquisiscono uno specifico significato.
 *
 *
 * ----------------------------------------------------------------------------------------------------------------------------------
 *
 * DIMENSIONI CONSIGLIATE
 *
 * Le dimensioni minime per poter giocare sul terminale sono 140X40.
 * Se dopo aver impostato queste dimensioni le schermate non venissero visualizzate correttamente sarà necessario regolare
 * "manualmente" il proprio terminale aumentando ancora le dimensioni o diminunendo il font (o entrambe le cose).
 *
 * ----------------------------------------------------------------------------------------------------------------------------------
 *
 * @author Marazzi Paolo
 */

public class Cli implements InputObserver, ConnectionHandlerObserver  {

    private static final String DEFAULT_MESSAGE = "NONE";


    private Game game; //Classe che contiene lo stato della partita dal punto di vista del client.
    private StateInterface state; //Stato in cui si trova il gioco.
    private ConnectionHandler connectionHandler;

    private boolean fatalError;
    private boolean endGame;

    /**
     * Costruttore della classe.
     *
     * Viene avviato il thread che si occupa della lettura su sdtin.
     * Si imposta lo "stato di attesa" come stato attivo.
     *
     * @param myNickname Nickname del giocatore
     */
    public Cli(String myNickname){

        AnsiConsole.systemInstall();


        if(myNickname != null) {
            Thread inputListner = new Thread(new Input(this));
            inputListner.start(); //Avvio il thread dedicato all'ascolto dello stdin.

            game = Game.factoryGame();
            game.setMyNickname(myNickname);

            this.fatalError = false;
            this.endGame = false;

            this.state = new WaitState("In attesa di altri giocatori!"); //Imposto lo stato d'attesa.

        }else
            throw new InvalidParameterException();

    }

    /**
     * Il metodo imposta lo stato della classe in base all'utensile che è stato selezionato.
     *
     * @param utensilNumber numero della carta utensile.
     * @param utensilIndex indice dell'utensile nella raccolta delle carte.
     */

    private void utensilActivation(int utensilNumber, int utensilIndex){

        switch (utensilNumber){

            case 1: state = new Utensil1State(utensilIndex); break;

            case 2: state = new Utensil23State(utensilIndex); break;

            case 3: state = new Utensil23State(utensilIndex); break;

            case 4: state = new Utensil4State(utensilIndex); break;

            case 5: state = new Utensil5State(utensilIndex); break;

            case 6: state = new Utensil61011State(utensilIndex); break;

            case 8: state = new Utensil89State(utensilIndex); break;

            case 9: state = new Utensil89State(utensilIndex); break;

            case 7: state = new Utensil7State(utensilIndex); break;

            case 10: state = new Utensil61011State(utensilIndex); break;

            case 11: state = new Utensil61011State(utensilIndex); break;

            case 12: state = new Utensil12State(utensilIndex); break;

            default: //Non è possibile arrivare in questo stato, salvo che per un passaggio errato di parametri. In questo caso non viene eseguita nessuna operazione e lo stato della classe rimane immutato.
        }
    }

    /**
     * Il metodo imposta lo stato della classe per permettere al giocatore di giocare
     * la "seconda fase" della carta utensile da lui scelta.
     * Solo le carta 6 e 11 prevedono una seconda fase.
     * Per seconda fase si intende che il giocatore ha attivato l'utensile e inviato la giocata
     * al server, quest'ultimo risponderà al giocatore fornendogli dei dati sulla base dei quali
     * l'utente effettuerà una seconda azione per terminare la giocata della carta scelta.
     *
     * @param utensilNumber numero dell'utensile da attivare.
     * @param utensilIndex indice dell'utensile nella raccolta delle carte.
     * @param message messaggio inviato dal server che contiene i dati generati dalle azioni fatte in prima fase.
     */

    private void utensilSecondPhaseActivation(int utensilNumber, int utensilIndex, String message){

        if(utensilNumber == 6) {
            int dieValue = Integer.parseInt(ClientMessageParser.getInformationsFromMessage(message).get(2)); //Recupero il nuovo valore del dado.
            game.setSecondPhaseValue(dieValue); //Setto il relativo campo in game.
            state = new Utensil6SecondPhaseState(utensilIndex); //Cambio lo stato.
        }

        if(utensilNumber == 11){
            String color = ClientMessageParser.getInformationsFromMessage(message).get(2).toLowerCase(); //Recupero il colore dal messaggio.
            game.setSecondPhaseColor(Translater.getColorFromText(color)); //Setto il relativo campo in game.
            state = new Utensil11SecondPhaseState(utensilIndex); //Cambio lo stato.
        }
    }


    /**
     * La classe gestisce i messaggi di update ricevuti dal server richiamando i
     * metodi che implementano una gestione più specifica.
     *
     * I messaggi di update sono quei messaggi che aggiornano lo stato del gioco (es: aggiornano lo stato della riserva).
     *
     * @param message messaggio di update da gestire.
     */

    private void manageUpdateMessage(String message){

        if (ClientMessageParser.isUpdateSideMessage(message))
            manageUpdateSideMessage(message);

        if (ClientMessageParser.isUpdateRoundMessage(message))
            manageUpdateRoundMessage(message);

        if (ClientMessageParser.isUpdateRoundgridMessage(message))
            manageUpdateRoundGridMessage(message);

        if (ClientMessageParser.isUpdateReserveMessage(message))
            manageUpdateReserveMessage(message);


        if (ClientMessageParser.isUpdateTurnMessage(message))
            manageUpdateTurnMessage(message);

        if(ClientMessageParser.isUpdatePriceMessage(message))
            manageUpdatePriceMessage(message);

    }

    /**
     * Gestione del messaggio di aggiornamento del costo delle carte utensile.
     * Il metodo modifica i costi delle carte contenute nella classe Game.
     *
     * @param message messaggio da gestire.
     */

    private void manageUpdatePriceMessage(String message){

        int i = 0;

        //Aggiorno il costo di ogni carta utensile sulla base delle informazioni contenute nel messaggio.
        for(String price : ClientMessageParser.getInformationsFromMessage(message)){
            game.setUtensilPrize(i,Integer.parseInt(price));
            i++;
        }
    }


    /**
     * Metodo che gestisce il messaggio di aggiornamento della carta finestra di un giocatore.
     * Vengono estratte dal messaggio le informazioni che descrivono i dadi posizionati sulla carta.
     * Queste informazioni vengono salvate nella classe Game.
     * Notifico lo stato attivo dell'aggiornamento appena avvenuto solo se il messaggio di aggiornamento è relativo
     * alla carta del giocatore e non di un avversario.
     * @param message messaggio di aggiornamento della carta.
     */

    private void manageUpdateSideMessage(String message){

        ArrayList<String> cells = new ArrayList<>(ClientMessageParser.getInformationsFromMessage(message)); //Estraggo le info sulle celle dal messaggio.
        String addressee = cells.remove(0); //Rimuovo la prima posizione perchè contiene il nickname.

        ArrayList<DieInfo> diceOnCard = new ArrayList<>(Translater.fromMessageToDieInfo(cells, true)); //Converto il messaggio in informazioni utilizzabili per la stampa a schermo.

        if (addressee.equals(game.getMyNickname())) { //Controllo se il messaggio di update è relativo alla mia carta.

            game.setDiceOnMyCard(diceOnCard); //Aggiorno le informazioni sulla mia carta.
            state.handleNetwork(message); //Notifico lo stato del cambiamento in modo che aggiorni la schermata.

        } else { //Se il messaggio di update è sulla carta di un avversario aggiorno il relativo campo in game.
            int enemyIndex = game.getEnemyNicknames().indexOf(addressee); //Estraggo l'indice relativo all'avversario che è stato aggiornato.

            ArrayList<ArrayList<DieInfo>> diceOnEnemysCards = new ArrayList<>(game.getDiceOnEnemysCards()); //Aggiorno lo stato di quell'avversario.
            diceOnEnemysCards.set(enemyIndex, diceOnCard);
            game.setDiceOnEnemysCards(diceOnEnemysCards);

            //NB--> In questo caso non notifico allo stato. Lo schermo si aggiornerà quando il giocatore selezionerà
            // da menù l'azione che gli permette di vedere le carte dei suoi avversri.
        }
    }

    /**
     * Viene gestito il messaggio che notifica la fine di un round.
     * Questo messaggio descrive i dadi aggiunti sulla roundgrid per il round appena concluso.
     * Nel metodo vengono estratte le informazioni dal messaggio e salvate nella classe Game.
     * Notifico lo stato attivo dell'aggiornamento appena avvenuto.
     *
     * @param message messaggio di aggiornamento del round.
     */

    private void manageUpdateRoundMessage(String message){

        ArrayList<DieInfo> newRound = new ArrayList<>(Translater.fromMessageToDieInfo(ClientMessageParser.getInformationsFromMessage(message),false)); //Estraggo dal messaggio il nuovo round da aggiungere alla roundrid.
        ArrayList<ArrayList<DieInfo>> roundGrid = new ArrayList<>(game.getRoundgrid()); //Recupero la roundgrid attuale.
        roundGrid.add(newRound); //Aggiungo il nuovo round alla roundgrid.
        game.setRoundgrid(roundGrid); //Aggiorno game.

        state.handleNetwork(message); //Notifico lo stato dell'aggiornamento.
    }

    /**
     * Viene gestito il messaggio di aggiornamento dell'intera roundgrid.
     * Questo messaggio descrive lo stato dell'intera roungri.
     * Nel metodo vengono estratte le informazioni dal messaggio e salvate nella classe Game.
     * Notifico lo stato attivo dell'aggiornamento appena avvenuto.
     *
     * @param message messaggio di aggiornamento della roundgrid.
     */
    private void manageUpdateRoundGridMessage(String message){

        ArrayList<ArrayList<DieInfo>> newRoundGrid = new ArrayList<>();
        ArrayList<List<String>> oldRoundGrid = new ArrayList<>(ClientMessageParser.getInformationsFromUpdateRoundgridMessage(message));
        oldRoundGrid.remove(oldRoundGrid.size()-1); //L'ultima posizione sarà sempre white0 che indica il round corrente. Non sono interessato a questa informazione.

        //Trasformo ogni round in una lista di info stampabili e l'aggiungo alla nuova roundgrid.
        for (List<String> round : oldRoundGrid) {
            newRoundGrid.add(new ArrayList<>(Translater.fromMessageToDieInfo(round,false)));
        }

        game.setRoundgrid(newRoundGrid); //Aggiorno game.

        state.handleNetwork(message); //Notifico lo stato attuale dell'aggiornamento, il quale provvederà a stampare a schermo.
    }

    /**
     * Viene gestito il messaggio di aggiornemento della riserva.
     * Questo messaggio descrive lo stato della riserva.
     * Vengono estratte le informazioni dal messaggio e viene salvato il nuovo stato della roundgrid nella classe Game.
     * Notifico lo stato attivo dell'aggiornamento appena avvenuto.
     *
     * @param message messaggio di aggiornamento della riserva.
     */

    private void manageUpdateReserveMessage(String message){

        //estraggo una lista di dieInfo dal campo informazioni e aggiorno game.

        game.setReserve(Translater.fromMessageToDieInfo(ClientMessageParser.getInformationsFromMessage(message), false));
        state.handleNetwork(message); //Notifico lo stato della modifica appena avvenuta.
    }

    /**
     * Il metodo gestisce il messaggio di aggiornamento del turno.
     * Viene estratto il nickname del giocatore che deve giocare il turno e viene salvato nella classe Game.
     * In base al giocatore che deve giocare il turno ("io" o un avversario) imposto lo stato della classe.
     *
     * @param message messaggio di aggiornamento del turno.
     */

    private void manageUpdateTurnMessage(String message){

        //Estraggo il nickname del giocatore che deve giocare questo turno e aggiorno game.
        String turnOf = ClientMessageParser.getInformationsFromMessage(message).get(0);
        game.setTurnOf(turnOf);

        //Controllo se sono io il giocatore di questo turno e di conseguenza setto lo stato corretto.
        if (turnOf.equals(game.getMyNickname()))
            state = new MyTurnState();
        else
            state = new NotMyTurnState();
    }


    /**
     * Il metodo gestisce il messaggio che notifica a tutti i gicatori quali sono le carta utensili utilizzabili nella partita.
     * Vengono estratte le informazioni dal messaggio e vengono salvate nella classe Game.
     *
     * @param message messaggio da gestire.
     * @throws IOException viene sollevata dal metodo che si occupa della lettura da file delle descrizioni delle carte utensili contenute nel messaggio.
     * @throws ClassNotFoundException viene sollevata dal metodo di deserializzazione se la classe letta da file non è contenuta nel jar.
     */

    private void manageStartUtensilMessage(String message) throws IOException, ClassNotFoundException {

        ArrayList<String> utensils = new ArrayList<>();
        int i = 0;

        //Estrapolo i nomi delgi utensili che si trovano nelle posizioni pari dell'arrayList restituito dal metodo di parsing del messaggio.
        for(String info: ClientMessageParser.getInformationsFromMessage(message)){

            if(i%2==0)
                utensils.add(info); //Aggiungo il nome alla lista di nomi.

            i++;
        }

        game.setUtensils(Translater.getUtensilCardFromName(utensils)); //Traduco i nomi in "utensili stampabili a schermo" che salvo nel campo dedicato dentro l'oggetto game.

    }

    /**
     * Il metodo si occupa di gestire tutti i messaggi di "start" richiamando metodo che eseguono operazioni più specifiche.
     * I messaggi di "start" vengono mandati dal server per inizializzare lo stato della partita.
     *
     * @param message messaggio da gestire.
     * @throws IOException sollevata dai metodi che leggono da file le informzioni su una carta.
     * @throws ClassNotFoundException viene sollevata se la classe deserializzata non è contenuta nel jar.
     */

    private void manageStartMessage(String message) throws IOException, ClassNotFoundException {

        if (ClientMessageParser.isStartChoseSideMessage(message))
            manageChooseSideMessage(message);

        if (ClientMessageParser.isStartSideListMessage(message))
            manageSideListMessage(message);

        if (ClientMessageParser.isStartPrivateObjectiveMessage(message))
            game.setPrivateObjective(Translater.getObjectiveCardFromName(ClientMessageParser.getInformationsFromMessage(message).get(0)));

        if (ClientMessageParser.isStartPublicObjectiveMessage(message))
            game.setPublicObjective(Translater.getObjectiveCardFromName(ClientMessageParser.getInformationsFromMessage(message)));

        if (ClientMessageParser.isStartUtensilMessage(message))
            manageStartUtensilMessage(message);
    }

    /**
     * Il metodo gestisce il messaggio che notifica al giocatore che deve segliere con quale carta giocare la parita.
     * Viene settato uno stato specifico per la scelta della carta.
     *
     * @param message messaggio da gestire.
     * @throws IOException sollevata dal metodo che legge da file le caratteristiche della carta.
     * @throws ClassNotFoundException viene sollevata se la classe deserializzata non è contenuta nel jar.
     */
    private void manageChooseSideMessage(String message) throws IOException, ClassNotFoundException {

        //Recupero le informazioni dal messaggio e rimuovo i segnalini favore associati ad ogni carta (tengo solo i nomi delle carte).
        ArrayList<String> cardNames = new ArrayList<>();
        cardNames.add(ClientMessageParser.getInformationsFromMessage(message).get(0));
        cardNames.add(ClientMessageParser.getInformationsFromMessage(message).get(2));
        cardNames.add(ClientMessageParser.getInformationsFromMessage(message).get(4));
        cardNames.add(ClientMessageParser.getInformationsFromMessage(message).get(6));

        //Passo i nomi al tradutore che li traduce in informazioni stampabili a schermo.
        ArrayList<SideCard> cards = new ArrayList<>(Translater.getSideCardFromName(cardNames));

        //Attivo lo stato che permette al giocatore di scegliere con quale carata giocare.
        state = new ChoseSideState(game.getMyNickname(), cards);
    }

    /**
     * Viene gestito il messaggio che assiocia ad ogni giocatore la carta da lui scelta.
     *
     * @param message messaggio da gestire.
     * @throws IOException sollevata dal metodo che legge da file le caratteristiche della carta.
     * @throws ClassNotFoundException viene sollevata se la classe deserializzata non è contenuta nel jar.
     */

    private void manageSideListMessage(String message) throws IOException, ClassNotFoundException {

        ArrayList<String> enemyNicknames = new ArrayList<>();
        ArrayList<String> enemyCards = new ArrayList<>();

        int pos = 0;

        //Raccolgo nickname e nomi delle carte in due liste differenti.
        for (String info : ClientMessageParser.getInformationsFromMessage(message)) {

            if (pos % 2 == 0) //Nelle posizioni pari si trovano i nickname.
                enemyNicknames.add(info);
            else //Nelle posizioni dispari si trovao i nomi delle carte.
                enemyCards.add(info);

            pos++;
        }

        //Ricavo la posizione del mio nickname nell'elenco dei nick.
        //La mia carta avrà la stessa posizione nell'elenco delle carte.
        pos = enemyNicknames.indexOf(game.getMyNickname());

        //Rimuovo il mio nickname dall'elenco.
        enemyNicknames.remove(pos);

        //Rimuovo dalla lista delle carte la carta in posizione 'pos' dopo di che traduco il nome della carta
        //in informazioni stampabili a schermo e modifico game.
        game.setMyCard(Translater.getSideCardFromName(enemyCards.remove(pos)));

        //Traduco le carte dei miei avversari e aggiorno game.
        game.setEnemyCards(Translater.getSideCardFromName(enemyCards));

        //Aggiorno game con i nickname dei miei avversari.
        game.setEnemyNicknames(enemyNicknames);
    }

    /**
     * Metodo che gestisce tutti i messaggi relativi alle connessioni e disconnessioni dei giocatori.
     * @param message messaggio da gestire.
     */

    private void manageNetworkMessage(String message){

        //Controllo se ho ricevuto un messaggio di connessione relativo ad un altro giocatore. Ignoro i miei messaggi di connessione.
        if (ClientMessageParser.isNewConnectionMessage(message) && !ClientMessageParser.getInformationsFromMessage(message).get(0).equals(game.getMyNickname()))
            state.handleNetwork(message);

        if (ClientMessageParser.isClientDisconnectedMessage(message)) {
            if (!ClientMessageParser.getInformationsFromMessage(message).get(0).equals(game.getMyNickname()))
                state.handleNetwork(message);
            else
                state = new DisconnectedState();
        }
    }

    /**
     * Vengono gestiti tutti i messaggi che notificano che un'azione è andata a buon fine.
     *
     * @param message messaggio da gestire.
     */

    private void manageSuccessMessage(String message){

        if (ClientMessageParser.isSuccessPutMessage(message))
            state.handleNetwork(message);

        if(ClientMessageParser.isSuccessActivateUtensilMessage(message))
            manageActivateUtensil(message);

        if(ClientMessageParser.isSuccessUseUtensilMessage(message))
            manageSuccessUseUtensil(message);
    }

    /**
     * Viene gestito il messaggio che notifica che la carta utensile richiesta può essere giocata.
     * Il metodo setta lo stato dedicato alla carta utensile attivata.
     *
     * @param message messaggio.
     */

    private void manageActivateUtensil(String message){

        int utensilNumber = Integer.parseInt(ClientMessageParser.getInformationsFromMessage(message).get(1)); //Recupero il numero della carta utensile.
        int utensilIndex = Integer.parseInt(ClientMessageParser.getInformationsFromMessage(message).get(0)); //Recupero l'indice della carta utensile.

        utensilActivation(utensilNumber,utensilIndex); //Richiamo il metodo che si occupa di impostare lo stato in base all'utensile che è stato attivato.
    }

    /**
     * Il metodo gestisce il messaggio che indica che l'utilizzo della carta utensile da parte dell'utente è andato a buon fine
     * e che bisogna passare alla seconda fase.
     *
     * @param message messaggio da gestire.
     */

    private void manageSuccessUseUtensil(String message){

        int utensilIndex = Integer.parseInt(ClientMessageParser.getInformationsFromMessage(message).get(0)); //Recupero l'indice della carta utensile.
        int utensilNumber = (game.getUtensils().get(utensilIndex)).getNumber(); //Recupero il numero della carta utensile.

        utensilSecondPhaseActivation(utensilNumber,utensilIndex, message); //Richiamo il metodo che si occupa di impostare lo stato relativo all'utensile che ha superato la prima fase.

    }


    /**
     * Il metodo gestisce il messaggio che indica che l'utilizzo di un utensile si è concluso.
     *
     * @param message messaggio da gestire.
     */

    private void manageEndUtensilMessage(String message){

        int newCardPrize = Integer.parseInt(ClientMessageParser.getInformationsFromMessage(message).get(2)); //Recupero il costo aggiornato della carta.
        int playerFavours = Integer.parseInt(ClientMessageParser.getInformationsFromMessage(message).get(3)); //Recupero i segnali favore rimasti al giocatore.
        int cardIndex = Integer.parseInt(ClientMessageParser.getInformationsFromMessage(message).get(0)); //Recupero l'indice della carta.

        //Resetto gli eventuali salvataggi fatti per la gestione della seconda fase.
        game.setSecondPhaseDie(new DieInfo(Ansi.Color.WHITE,0));
        game.setSecondPhaseColor(Ansi.Color.WHITE);
        game.setSecondPhaseValue(0);

        game.setUtensilPrize(cardIndex, newCardPrize); //Aggiorno il coso carta.

        game.setFavours(playerFavours); //Aggiorno i segnalini favore rimasti al giocatore.

        state = new MyTurnState(); //Cambio lo stato --> Mi ripoto nello stato "MyTurn".

    }

    /**
     * Il metodo gestisce i possibili messaggi che segnalano errori di gioco.
     *
     * @param message messaggio da gestire.
     */

    private void manageErrorMessage(String message){

        if (ClientMessageParser.isErrorPutMessage(message))
            state.handleNetwork(message);

        if(ClientMessageParser.isErrorActivateUtensilMessage(message))
            state.handleNetwork(message);

        if(ClientMessageParser.isErrorUseUtensilMessage(message)){

            int utensilIndex = Integer.parseInt(ClientMessageParser.getInformationsFromMessage(message).get(0)); //Recupero l'indice dell'utensile che ha sollevato l'errore.

            //Se il "dado di seconda fase" in game è diverso da (BIANCO,0) significa che l'errore è stato sollevato in secoda fase.
            if(game.getSecondPhaseDie().getColor() != Ansi.Color.WHITE && game.getSecondPhaseDie().getNumber() != 0){

                //Riattivo lo stato di seconda fase che ha generato errore.
                if(game.getUtensilNumberbyIndex(utensilIndex) == 6)
                    state = new Utensil6SecondPhaseState(utensilIndex);
                else
                    state = new Utensil11SecondPhaseState(utensilIndex);
            }
            else
                utensilActivation(game.getUtensilNumberbyIndex(utensilIndex), utensilIndex); //Riattivo lo stato di prima fase che ha generato errore.
        }

        if(ClientMessageParser.isUnauthorizedPutMessage(message)) //Gestione del messaggio che notifica che la mossa selezionata è già stata fatta in questo turno.
            state.handleNetwork(message);
    }

    /**
     * Metodo che gestisce il messaggio che notifica la fine della partita.
     * Viene settato uno stato dedicato.
     *
     * @param message messaggio da gestire.
     */

    private void manageWinnerMessage(String message){

        ArrayList<String> nicknames = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();

        int i = 0;

        for(String info : ClientMessageParser.getInformationsFromMessage(message)){

            if(i < ClientMessageParser.getInformationsFromMessage(message).size()-1){ //L'ultimo elemento del campo informazioni del messaggio non mi interessa.

                if(i%2==0) //Nei campi pari trovo i nicknem in quelli dispari i punteggi.
                    nicknames.add(info);
                else
                    scores.add(Integer.parseInt(info));
            }

            i++;
        }

        this.endGame = true; //Mi permette di ignorare qualsiasi messaggio proveniente dalla rete.
        state = new EndGameState(nicknames,scores);
    }


    /**
     * Il metodo gestisce i valori immessi su stdin dall'utente.
     * In pratica l'input viene passato allo stato attivo che si occupa di interpretarlo.
     *
     * @param request input immesso dall'utente.
     */

    @Override
    public void inputRequest(int request) {
        if (!fatalError) {

            String response = state.handleInput(request); //Passo allo stato attivo l'input immesso dall'utente.

            if (!response.equals(DEFAULT_MESSAGE)){ //Se l'utente mi restituisce un messaggio diverso dal quellod i default lo invio al server.

                if(response.split("/")[4].equals("side_reply")) //Se l'utente ha appena selezionato la carta con cui giocare la partita lo metto in attesa.
                    state = new WaitState("In attesa della scelta degli altri giocatori!");

                this.connectionHandler.sendToServer(response); //Invio al server.

                if(ClientMessageParser.isClientDisconnectedMessage(response)) { //Se ho appena inviato un messaggio di disconnessione termino il gioco.
                    AnsiConsole.out.print(ansi().eraseScreen());
                    System.exit(0);
                }

            }

        }
    }

    /**
     * Il metodo gestisce i messaggi provenienti dal server riconoscendone la tipologia e richiamando metodi privati che
     * implementano azioni più specifiche.
     *
     * @param message messaggio da gestire.
     */

    @Override
    public void NetworkRequest(String message) {

        if(!endGame && !fatalError && !message.equals("")) {

            try {

                if(ClientMessageParser.isSuccessMessage(message))
                    manageSuccessMessage(message);

                if(ClientMessageParser.isErrorMessage(message))
                    manageErrorMessage(message);

                if(ClientMessageParser.isUseUtensilEndMessage(message))
                    manageEndUtensilMessage(message);

                if (ClientMessageParser.isUpdateMessage(message))
                    manageUpdateMessage(message);

                if (ClientMessageParser.isStartMessage(message))
                    manageStartMessage(message);

                if (ClientMessageParser.isNetworkMessage(message))
                    manageNetworkMessage(message);

                if(ClientMessageParser.isWinnerMessage(message))
                    manageWinnerMessage(message);

            } catch (IOException | NullPointerException | ClassNotFoundException e) {
                this.fatalError = true;
                state = new FatalErrorState(e);
            }
        }
    }

    /**
     * Tramite questo metodo viene settato il connection handler che si deve utilizzare per comunicare col server.
     *
     * @param connectionHandler gestore della connessione da utilizzare.
     */

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        if(connectionHandler != null)
            this.connectionHandler = connectionHandler;
        else
            throw new InvalidParameterException();
    }
}
