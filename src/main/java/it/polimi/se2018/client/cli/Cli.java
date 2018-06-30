package it.polimi.se2018.client.cli;


import it.polimi.se2018.client.connection_handler.ConnectionHandlerObserver;
import it.polimi.se2018.client.cli.controller.translater.Translater;
import it.polimi.se2018.client.cli.controller.states.*;
import it.polimi.se2018.client.cli.controller.stdin_controller.Input;
import it.polimi.se2018.client.cli.controller.stdin_controller.InputObserver;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageParser;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Cli implements InputObserver, ConnectionHandlerObserver {

    private static final String DEFAULT_MESSAGE = "NONE";


    private Game game; //Classe che contiene lo stato della partita dal punto di vista del client.
    private StateInterface state; //Stato in cui si trova il gioco.
    private ConnectionHandler connectionHandler;

    private boolean fatalError;

    public Cli(String myNickname){

        if(myNickname != null) {
            Thread inputListner = new Thread(new Input(this));
            inputListner.start(); //Avvio il thread dedicato all'ascolto.

            game = Game.factoryGame();
            game.setMyNickname(myNickname);

            this.fatalError = false;

        }else
            throw new InvalidParameterException();

    }


    private void manageUpdateSideMessage(String message){

        ArrayList<String> cells = new ArrayList<>(ClientMessageParser.getInformationsFromMessage(message)); //Estraggo le info sulle celle dal messaggio.
        String addressee = cells.remove(0); //Rimuovo la prima posizione perchè contiene il nickname.

        ArrayList<DieInfo> diceOnCard = new ArrayList<>(Translater.fromMessageToDieInfo(cells)); //Converto il messaggio in informazioni utilizzabili per la stampa a schermo.

        if (addressee.equals(game.getMyNickname())) { //Controllo se il messaggio di update è relativo alla mia carta.

            game.setDiceOnMyCard(diceOnCard); //Aggiorno le informazioni sulla mia carta.
            state.handleNetwork(message); //Notifico lo stato del cambiamento in modo che aggiorni la schermata.

        } else { //Se il messaggio di update è sulla carta di un avversario aggiorno il relativo campo in game.
            int enemyIndex = game.getEnemyNicknames().indexOf(addressee); //Estraggo l'indice relativo all'avversario che è stato aggiornato.

            ArrayList<ArrayList<DieInfo>> diceOnEnemysCards = new ArrayList<>(game.getDiceOnEnemysCards()); //Aggiorno lo stato di quell'avversario.
            diceOnEnemysCards.set(enemyIndex, diceOnCard);
            game.setDiceOnEnemysCards(diceOnEnemysCards);

            //NB--> In questo caso non notifico allo stato. LO schermo si aggiornerà quando il giocatore selezionerà
            // da menà l'azione che gli permette di vedere le carte dei suoi avversri.
        }
    }

    private void manageUpdateRoundMessage(String message){

        ArrayList<DieInfo> newRound = new ArrayList<>(Translater.fromMessageToDieInfo(ClientMessageParser.getInformationsFromMessage(message))); //Estraggo dal messaggio il nuovo round da aggiungere alla roundrid.
        ArrayList<ArrayList<DieInfo>> roundGrid = new ArrayList<>(game.getRoundgrid()); //Recupero la roundgrid attuale.
        roundGrid.add(newRound); //Aggiungo il nuovo round alla roundgrid.
        game.setRoundgrid(roundGrid); //Aggiorno game.

        state.handleNetwork(message); //Notifico lo stato dell'aggiornamento.
    }

    private void manageUpdateRoundGridMessage(String message){

        ArrayList<ArrayList<DieInfo>> newRoundGrid = new ArrayList<>();

        //Trasformo ogni round in una lista di info stampabili e l'aggiungo in alla nuova roundgrid.
        for (List<String> round : ClientMessageParser.getInformationsFromUpdateRoundgridMessage(message)) {
            newRoundGrid.add(new ArrayList<>(Translater.fromMessageToDieInfo(round)));
        }

        game.setRoundgrid(newRoundGrid); //Aggiorno game.

        state.handleNetwork(message); //Notifico lo stato attuale dell'aggiornamento, il quale provvederà a stampare a schermo.
    }

    private void manageUpdateReserveMessage(String message){

        //estraggo una lista di dieInfo dal campo informazioni e aggiorno game.

        game.setReserve(Translater.fromMessageToDieInfo(ClientMessageParser.getInformationsFromMessage(message)));
        state.handleNetwork(message); //Notifico lo stato della modifica appena avvenuta.
    }

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
        state = new ChoseSideState(game.getMyNickname(), cards.get(0), cards.get(1), cards.get(2), cards.get(3));
    }

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
    }

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
            game.setUtensils(Translater.getUtensilCardFromName(ClientMessageParser.getInformationsFromMessage(message)));
    }

    private void manageNetworkMessage(String message){

        if (ClientMessageParser.isNewConnectionMessage(message)) {
            if (!ClientMessageParser.getInformationsFromMessage(message).get(0).equals(game.getMyNickname()))
                state.handleNetwork(message);
        }

        if (ClientMessageParser.isClientDisconnectedMessage(message)) {
            if (!ClientMessageParser.getInformationsFromMessage(message).get(0).equals(game.getMyNickname()))
                state.handleNetwork(message);
            //TODO else Se noi siamo stati disconnessi bisogna lanciare una schermata dedicata.
        }
    }

    @Override
    public void InputRequest(int request) {
        if (!fatalError) {

            String response = state.handleInput(request);

            if (!response.equals(DEFAULT_MESSAGE))
                this.connectionHandler.sendToServer(response);

            //TODO fare controllo per lanciare la scena di caricamento.
        }
    }

    @Override
    public void NetworkRequest(String message) {

        if(!fatalError) {

            try {

                if (ClientMessageParser.isSuccessPutMessage(message))
                    state.handleNetwork(message);

                if (ClientMessageParser.isErrorPutMessage(message))
                    state.handleNetwork(message);

                if (ClientMessageParser.isUpdateMessage(message))
                    manageUpdateMessage(message);

                if (ClientMessageParser.isStartMessage(message))
                    manageStartMessage(message);

                if (ClientMessageParser.isNetworkMessage(message))
                    manageNetworkMessage(message);

            } catch (IOException | ClassNotFoundException | NullPointerException e) {
                this.fatalError = true;
                state = new FatalErrorState(e);
            }
        }
    }

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        if(connectionHandler != null)
            this.connectionHandler = connectionHandler;
        else
            throw new InvalidParameterException();
    }
}
