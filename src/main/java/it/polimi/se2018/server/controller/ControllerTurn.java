package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.events.DisconnectPlayer;
import it.polimi.se2018.server.events.EventMVC;
import it.polimi.se2018.server.events.Freeze;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.timer.ObserverTimer;
import it.polimi.se2018.server.timer.SagradaTimer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe si occupa della gestione dei turni. L'apertura, la chiusura, di ciascun turno con il relativo timer e la gestione del congelamento
 * dei giocatori. Possiede un controllo dei messaggi per cui se il tempo non è acceso il messaggio non passa pee pulire le code dei messaggi tra un turno ed un altro
 * oltre a cambiare i round finiti i turni, triggerando gli update della riserva e della roungrid.
 * @author Kevin Mato
 */
public class ControllerTurn implements ObserverTimer {
    private final Table lobby;
    private final Controller controller;
    private final SagradaTimer sagradaTimer;

    private int round=0;
    private String turnOf=null;
    private String firstPlayer=null;


    private int caller=0;
    private int indexAmongPs;
    private int counterCMod;
    private int numbOfPlayers;

    private ArrayList<String> orderOfTurning = new ArrayList<>();
//gestione delle fasi
    private boolean setting=true;
    private boolean andata=true;
    private boolean timeIsOn=false;

    /**
     * Costruttore della Classe.
     * @param table Tavolo o classe principale del model.
     * @param controller una delle classi controller definita principale per convenzione.
     * @param secondi tempo per un singolo turno.
     */

    public ControllerTurn(Table table,Controller controller,int secondi){
        this.lobby=table;
        this.controller=controller;
        this.numbOfPlayers=lobby.peopleCounter();
        this.indexAmongPs=numbOfPlayers;
        this.counterCMod=numbOfPlayers;
        this.sagradaTimer=new SagradaTimer(secondi);
        sagradaTimer.add(this);
    }

    /**
     * Metodo che ritorna la refence del giocatore turnante.
     * @return reference del player turnante.
     */

    public Player getTurn(){
        try{
            return lobby.callPlayerByName(turnOf);
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
        return null;
    }

    /**Metodo getter di order of turning
     * @return lista dei nomi dei giocatori in ordine di turno.
     */
    public List<String> getOrderOfTurning() {
        return orderOfTurning;
    }

    /**
     * Metdo con i compiti da eseguire a fine di un turno. In base a che turno ci troviamo deciderà se far partire un novo turnante o un nuovo round.
     * @throws Exception lanciata dai metodi setround e setTurn
     */
    private void howToClose() throws Exception {
        if(!andata && turnOf.equals(firstPlayer) ){
            callerModifier();
            lobby.setUpdateDiceToRoundGrid();
            setRound();
        }
        else {
            callerModifier();
            setTurn();
        }
    }

    /**
     * Metodo con i compiti da fare alla fine di un turno tra cui la chiusura del timer, settaggio del valore booleano
     * del tempo finito e segnalazione della fine del turno.
     */
    private void closeTimer(){
        sagradaTimer.stop();
        timeIsOn = false;
        controller.getcChat().notifyObserver(new TimeIsUp(turnOf));
    }

    /**
     * Metodo che fa partire effettivamente il timer del turno e segna il giocatore turnante.
     */
    private void rotation(){
        lobby.rotatingPlayerTurn(caller);
        sagradaTimer.start();
        timeIsOn = true;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che cambia il round e si occupa dell'aggiornamento della riserva e della roundgrid.
     * @throws Exception insieme delle eccezioni lanciate da un possibile errore di callPlayerByname e setTurn;
     */
    private void setRound() throws Exception {
        lobby.newRoundForPlayers();
        firstPlayer = lobby.callPlayerByNumber(caller).getName();
        round = round + 1;

        lobby.newRiserva4Game();

        if(!isGameFinished()) setTurn();


    }

    /**
     * Metodo che occupa del cambio dei turni all'interno di uno stesso round.
     * @throws InvalidHowManyTimes lanciato nel caso in cui un giocatore abbia gioato troppe volte all'interno di uno stesso round.
     */
    private void setTurn() throws InvalidHowManyTimes {
        turnOf = lobby.callPlayerByNumber(caller).getName();

        if(getTurn().canYouPlay()) {//determina se un giocatore è stato disconnesso
            recorder(turnOf);
            getTurn().reductor();
            rotation();
            controller.getcChat().notifyObserver(new UpdateM(null,"turn",turnOf));
            //parte il timer
        }
        else closeTurn();
    }

    /**
     * Metodo che si occupa di lanciare tutte le routine di chiusura di un turno che poi decide se debba iniziare un nuovo turno o un nuovo round.
     * Scatta al finire del tempo del timer.
     */
    private void closeTurn(){
        //azione del timer quando scade il turno posso dividere in due parti
        //o quando messaggi mi dicono che ha già fatto le cose che deve fare da checker
        try{
            if(getTurn().canYouPlay()) {
                closeTimer();

                if (!getTurn().getDidPlayDie() && !getTurn().getDidPlayCard()) {
                    freezer(turnOf);
                }
            }
            howToClose();
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    /**
     * Metodo che si occupa di lanciare tutte le routine di chiusura di un turno che poi decide se debba iniziare un nuovo turno o un nuovo round.
     * Scatta alla richiesta esplicita del giocatore di finire il suo turno.
     * Richiamato da PassTurn.
     */
    private void passCloseTurn(){
        //azione del timer quando scade il turno posso dividere in due parti
        //o quando messaggi mi dicono che ha già fatto le cose che deve fare da checker
        try{
            if(getTurn().canYouPlay()) {
                closeTimer();
            }
            howToClose();
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che si occupa di preparare l'enivironment per i giocatori.
     * @throws InvalidValueException lanciato dal metodo che si occupa con la prima interazione con gli utenti, lanciata solo se il turnante non esiste.
     */
    public void setThePlayers() throws InvalidValueException {
    try{
        lobby.preparePlayers();
    }
    catch(IOException e){
        controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
    }
    anotherPlayer();
}

    /**
     * Metodo che mostra al player il suo obiettivo privato e gli chiede quale delle 4 side assegnate randomicamente a lui vuole scegliere.
     * @throws InvalidValueException lanciata solo se il turnante non esiste. Predisposta nel caso di estensione e per motivi di testing.
     */
    private void anotherPlayer() throws InvalidValueException{
            turnOf = lobby.callPlayerByNumber(caller).getName();
            lobby.setShowPrivate(turnOf);
            getTurn().ask();
            rotation();
    }

    /**
     * Metodo lanciato quando un giocatore ha compiuto la sua scelta della side.
     * Finiti i giocatori a cui chiedere la side, preparata l'ambiente della classe per incominciare la vera e propria partita.
     */
    protected void actualPlayerIsDone(){

        try{

            closeTimer();
            if(getTurn().getMySide()==null){
                lobby.callPlayerByNumber(caller).forgetForever();
                controller.getcChat().notifyObserver(new DisconnectPlayer(turnOf));
            }
            else{

                if(caller == numbOfPlayers-1 ){
                    lobby.setShowEnemiesChoice();
                    resetQualities();
                    setRound();
                }
                else {
                    caller=caller+1;
                    anotherPlayer();
                }

            }
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che si occupa di selezionare correttamente secondo la aticlare specifica di ordine il giocatore durante la partita.
     * Il metodo lavora seleziona il giocatore in base al resto di un indice ripsetto al numero di giocatori.
     * amongps è l'indice che cresce o descre in base che sia andata o ritorno.
     */
    private void callerModifier(){
        if(counterCMod!=1){
            if(andata){
                indexAmongPs=indexAmongPs+1;
                counterCMod=counterCMod-1;

            }
            else {
                indexAmongPs=indexAmongPs-1;
                counterCMod=counterCMod-1;
            }
        }
        else{
            if(andata){
                andata=false;
                indexAmongPs=indexAmongPs+numbOfPlayers;
                counterCMod=numbOfPlayers;
            }
            else {
                andata=true;
                indexAmongPs=numbOfPlayers+round;
                counterCMod=numbOfPlayers;
            }

        }
        caller = (indexAmongPs % numbOfPlayers );
    }

    /**
     * Metodo che prepara l'arraylist da consegnare al controller points per sapere a chi assegnare la vittoria in caso di pareggio.
     * I giocatori compariranno i ordine di turno nel round appena concluso.
     * @param name nome da aggiungere alla lista.
     */
    private void recorder(String name){
        if(!orderOfTurning.contains(name)) orderOfTurning.add(name);
    }

    /**
     * Metodo controlla che il numero di round non vada oltre il decimo determinado così la fine della partita.
     * @return true se finita false altrimenti.
     * @throws Exception lanciato nel caso di problemi all'interno della ricerca del vincitore dentro controller points.
     */
    private boolean isGameFinished() throws Exception {
        if(round>10){controller.finalizeTheGame();
                    return true;}
        return  false;
    }

    /**
     * Metodo checker che controlla che il messaggio possa essere accettato, cosa che avviene solo se il tempo è on e che il giocatore
     * non abbia già fatto le mosse possbili altrimenti verrà  attuato il cambio del turno.
     * @param m evento da controllare
     * @return esito del controllo di validità.
     */
    public boolean messageComingChecking(EventMVC m){
        try{
            if(!timeIsOn) return false;
            else{
                if(lobby.callPlayerByName(m.getPlayer()).getDidPlayDie()&&lobby.callPlayerByName(m.getPlayer()).getDidPlayCard()){
                    passCloseTurn();
                    return false;
                }
                else return true;
            }
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
            return  false;
        }

    }

    /**
     * Metodo che resetta il controller ai valori di default.
     */
    private void resetQualities(){
        round=0;
        turnOf=null;
        firstPlayer=null;

        numbOfPlayers=lobby.peopleCounter();
        caller=0;
        indexAmongPs=numbOfPlayers;
        counterCMod=numbOfPlayers;
        orderOfTurning = new ArrayList<>();

        andata=true;
        timeIsOn=false;
        setting=false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che si occupa del congelamento di un giocatore e della segnalazione al server.
     * @param name giocatore da congelare
     * @throws Exception dalla finalizzazione del gioco
     */
    protected void freezer(String name) throws Exception {
        lobby.callPlayerByName(name).forget();
        controller.getcChat().notifyObserver(new Freeze(name));
        if(lobby.peopleCounter()<2)  controller.finalizeTheGame();
    }
    /**
     * Allo scattare del timer decide come chiudere il turno del giocatore in base che si stia attendendo la scelta di una carta
     * o sia la fine di un turno del gioco.
     */
    public void timerUpdate(){
        if(setting) actualPlayerIsDone();
        else closeTurn();
    }

    /**
     * Metdodo che controlla che la richiesta di passa turno sia effettivamente del turnante e se è così ne chiude il turno.
     * @param m evento di richiesta passaggio turno.
     */
    public void passTurn(PassTurn m){
        if(m.getPlayer().equals(turnOf)){
             passCloseTurn();
        }


    }
}
