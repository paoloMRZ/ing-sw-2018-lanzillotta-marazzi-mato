package it.polimi.se2018.client.cli.controller.states;



import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.print.scenes.ChoseSideScene;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.message.ClientMessageParser;

import java.security.InvalidParameterException;

public class ChoseSideState implements StateInterface {

    //La segunte stringa viene restituita quando la gestione di una richiesta non genera messaggi da mandare al server.
    private static final String DEFAULT_MESSAGE = "NONE";
    private static final String NETWORK_MESSAGE = " si è disconnesso!";
    private static final String ERROR_MESSAGE = "ERRORE: Inserisci un valore valido!";


    private ChoseSideScene choseSideScene;
    private String myNickanme;

    public ChoseSideState(String myNickname, SideCard card1, SideCard card2, SideCard card3, SideCard card4){

        if(myNickname != null && card1 != null && card2 != null && card3 != null && card4 != null){

            this.myNickanme = myNickname;

            choseSideScene = new ChoseSideScene(card1,card2,card3,card4);
            choseSideScene.printScene(); //Stampo la scena.

        }else
            throw new InvalidParameterException();
    }

    @Override
    public String handleInput(int request) { //Gestione delle richieste da stdin.

        if(request >= 1 && request <= 4) //Se ricevo un numero rta 1 e 4 vuol dire che il giocatore ha scelto una carta.
            //Restituisco il messaggio da mandare al server per notificarlo della scelta fatta.
            //Il client passa un numero tra 1 e 4, ma il server si aspetta un numero tra 0 e 3, per questo faccio request-1.
            return ClientMessageCreator.getSideReplyMessage(myNickanme,String.valueOf(request-1));

        else{//Se non ho ricevuto una richiesta non valida lo notifico a schermo ristampando la scena con un messaggio di errore.
            choseSideScene.addMessage(ERROR_MESSAGE);
            choseSideScene.printScene();
            return DEFAULT_MESSAGE; //Nel caso in cui non si riceve una richiesta valida non si costruisce nessun messaggio e si passa al controllore della grafica la stringa di default.
        }
    }

    @Override
    public void handleNetwork(String request) { //In questo stato gestisco solo i messaggi di disconnessione degli altri giocatori.

        if(ClientMessageParser.isClientDisconnectedMessage(request)){ //Se ho ricevuto la notifica di disconnessione di un giocatore avversario lo stampo a schermo.
            choseSideScene.addMessage(ClientMessageParser.getInformationsFromMessage(request).get(0) + NETWORK_MESSAGE); //Aggiungo il messaggio di disconnessione tra gli elementi stampabili.
            choseSideScene.printScene(); //Ristampo la scena.
        }
    }

}
