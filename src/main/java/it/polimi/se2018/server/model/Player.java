package it.polimi.se2018.server.model;

import it.polimi.se2018.server.events.responses.AskPlayer;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.*;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;
import java.util.List;

/**
 * la classe Player è una rappresentazione nel model del client (giocatore) partecipante alla sessione di gioco, identificato da un NOME,
 * un SIDE, un OBJECTIVE (privato) e dai segnalini FAVORE.
 *
 * @author Simone Lanzillotta
 * @author Kevin Mato
 */


public class Player {

    private final NotifyModel notifier;
    private List<Side> mySideSelection;
    private final String name;
    private Side mySide=null;
    private final Objective myObjective;
    private int favours = 0;
    private boolean isMyTurn=false;
    private boolean canIPlay=true;
    private boolean canIRenter=true;
    private int howManyTurns;
    private boolean didPlayDie;
    private boolean didPlayCard;


    /**
     *      * Costruttore della classe Player. Si noti che che Player non è completamente identificato al momento della creazione, infatti
     * l'associazione della carta Side viene demandata in un secondo momento tramite il metodo setMySide.
     *
     * @param objective riferimento alla carta Obbietivo (privato) associata al Player
     * @param nomine  riferimento al nome del giocatore
     * @param notifier riferimento del comunicatore del model.
     */
    public Player(Objective objective, String nomine,NotifyModel notifier) {
        this.notifier=notifier;
        this.myObjective = objective;
        this.name=nomine;
        this.howManyTurns=2;
        this.didPlayCard=false;
        this.didPlayDie=false;
    }


    /**
     * Metodo che restituisce il nome del giocatore
     *
     * @return nome del giocatore
     */

    public String getName(){
        return name;
    }


    /**
     * Metodo che restituisce il riferimento ai segnalini Favore del giocatore
     *
     * @return riferimento al'attributo favours
     */

    public int getFavours() {
        return favours;
    }


    /**
     * Metodo che restituisce il riferimento alla carta Side del giocatore
     *
     * @return riferimento alla carta Side
     */

    public Side getMySide() {
        return mySide;
    }

    /**
     * Metodo che restituisce il riferimento alla carta Obbiettivo Privata del giocatore
     *
     * @return riferimento alla carta Obbiettivo Privata
     */

    public Objective getMyObjective() {
        return myObjective;
    }

    /**
     * Metodo che restituisce la restrizione di colore della cella selezionata tramite le sue coordinate nella carta Side del giocatore
     *
     * @param row riferimento alla riga di selezione per la Cella interessata
     * @param col rierimento alla colonna di selezione per la Cella interessata
     * @return restrizione di colore della cella individuata tramite le coordinate (row,col)
     * @throws InvalidCoordinatesException viene lanciata le coordinate passate non sono valide
     */

    public Color getColorCell(int row, int col) throws InvalidCoordinatesException {
        return mySide.getColor(row,col);
    }


    /**
     * Metodo che restituisce la restrizione di sfumatura della cella selezionata tramite le sue coordinate nella carta Side del giocatore
     *
     * @param row riferimento alla riga di selezione per la Cella interessata
     * @param col rierimento alla colonna di selezione per la Cella interessata
     * @return restrizione di sfumatura della cella individuata tramite le coordinate (row,col)
     * @throws InvalidCoordinatesException viene lanciata le coordinate passate non sono valide
     */

    public int getNumberCell(int row, int col) throws InvalidCoordinatesException {
        return mySide.getNumber(row,col);
    }


    /**
     * Metodo che restituisce il numero azioni che il giocatore può ancora eseguire durante il suo turno
     *
     * @return numero di azioni eseguibile dal giocatore
     */

    public int getHowManyTurns(){
        return howManyTurns;
    }


    /**
     * Metodo che mostra se sia stata già effettuata l'azione del giocatore "Posiziona Dado"
     *
     * @return TRUE se il giocatore ha già eseguito l'azione, altrimenti FALSE
     */

    public boolean getDidPlayDie(){
        return didPlayDie;
    }


    /**
     * Metodo che mostra se sia stata già effettuata l'azione del giocatore "Gioca carta Utensile"
     *
     * @return TRUE se il giocatore ha già eseguito l'azione, altrimenti FALSE
     */

    public boolean getDidPlayCard(){
        return didPlayCard;
    }


    /**
     * Metodo che imposta il numero di segnalini favore del giocatore in base alla difficoltà della propria carta Side scelta
     *
     */

    public void setFavours(){
        favours = mySide.getFavours();
    }
    /**
     * Metodo che reimposta il numero di segnalini favore del giocatore in base al costo della crta utensile attivata
     *
     * @param cost segnalini favore associate alla carta Side
     */
    public void resetFavours(int cost){favours= favours-cost;}

    /**
     * Metodo di aggiornamento del parametro didPlayCard, se ha giocato una carta utensile.
     *@param price prezzo della carta che ha appena attivato
     */

    public void setDidPlayCard(int price){
        didPlayCard= !didPlayCard;
        favours= favours - price;
    }


    /**
     * Metodo di aggiornamento del parametro didPlayDie, cioè se ha già giocato un piazzamento.
     *
     */

    public void setDidPlayDie(){
        didPlayDie=!didPlayDie;
    }


    /**
     * Metodo che permette il posizionamento dei dadi sulla Side del giocatore
     *
     * @param die riferimento al dado che si intende posizionare sulla carta Side
     * @param row riferimento alla riga di posizionamento interessata
     * @param col riferimento alla colonna di posizionamento interessata
     * @throws InvalidCellException viene lanciata quando la cella selezionata è già occupata da un altro Dado
     * @throws InvalidCoordinatesException viene lanciata quando le coordinate inserite non sono valide
     */

    public void putDice(Dice die, int row, int col) throws InvalidCellException, InvalidCoordinatesException {
        if(die!=null) {
            this.mySide.put(row, col, die);
            launchCommunication(mySide.setUpdate());
        }
    }


    /**
     * Metodo che mostra la Cella selezionata tramite le sue coordinate nella carta Side
     *
     * @param row riferimento alla riga di posizionamento interessata
     * @param col riferimento alla colonna di posizionamento interessata
     * @return restituisce il riferimento a una copia dell'oggetto Cell selezionato tramite le coordinate (row,col)
     * @throws InvalidShadeValueException viene lanciata quando il valore di sfumatura della Cella non è valido
     * @throws InvalidCoordinatesException viene lanciata quando le coordinate inserite non sono valide
     */


    public Cell showSelectedCell(int row, int col) throws InvalidCoordinatesException, InvalidShadeValueException {
        return mySide.showCell(row,col);
    }


    /**
     * Metodo di aggiornamento del paramentro howManyTurns, cioè numero di turni in un round.
     *
     * @throws InvalidHowManyTimes viene lanciata quando il parametro howManyTurns assume un valore negativo
     */

    public void reductor()throws InvalidHowManyTimes {
        if(howManyTurns>0){
            howManyTurns=howManyTurns-1;
            didPlayCard=false;
            didPlayDie=false;
        }
        else throw new InvalidHowManyTimes();
    }

    /**
     * Metodo che toogla l'attributo di turno attivo del giocatore, grazie al quale l'istanza sa se le è èermesso giocare.
     */
    public void setIsMyTurner(){
        isMyTurn= !isMyTurn;
    }

    /**
     * Metodo che ritorna se è possbile giocatore al giocatore
     * @return true se può giocare, false altrimenti.
     */
    public boolean getIsMyTurn(){
        return isMyTurn;
    }

    /**
     * Metodo che imposta i valori dei parametri howManyTurns, didPlayCard e didPlayDie. Si noti che il metodo verrà richiamato non
     * appena il giocatore inizierà un nuovo turno, e non durante.
     *
     */

    public void restoreValues(){
        this.howManyTurns=2;
        this.didPlayCard=false;
        this.didPlayDie=false;
    }


    /**
     * Metodo che permette il posizionamento di un Dado senza considerare la vincolazione della Cella riguardo il colore, comprese quelle forzate
     * delle celle adiacenti. Si noti che il metodo seleziona un Dado già posizionato sulla carta Side, quindi va inteso come uno spostamento
     *
     * @param oldRow riferimento alla riga di partenza da cui prelevare il Dado
     * @param oldCol riferimento alla colonna di partenza da cui prelevare il Dado
     * @param newRow riferimento alla riga di destinazione da cui prelevare il Dado
     * @param newCol riferimento alla riga di destinazione da cui prelevare il Dado
     * @throws InvalidValueException viene lanciata quando vengono passati valori non validi
     * @throws NoDicesNearException viene lanciata quando la Cella individuata da (oldRow, oldCol) non ha Celle adiacenti con un Dado
     * @throws NotEmptyCellException viene lanciata quando al Cella individuata da (newRow, newCol) ha gia un Dado posizionatovi
     * @throws InvalidShadeException viene lanciata quando il valore di sfumatura della Cella non è valido
     * @throws NearDiceInvalidException viene lanciata quando al Cella individuata da (newRow, newCol) non ha Celle adiacenti con un Dado
     */

    public void putDiceIgnoreColor(int oldRow, int oldCol,int newRow, int newCol) throws InvalidValueException, NoDicesNearException, NotEmptyCellException, InvalidShadeException, NearDiceInvalidException, InvalidColorException {
        Dice d=mySide.pick(oldRow,oldCol);
        if(d==null) throw new InvalidValueException();
        try{
            mySide.putIgnoringColor(newRow,  newCol,  d  );
        }
        catch(Exception e){
            mySide.put(oldRow,oldCol,d);
            throw e;
        }
        launchCommunication(mySide.setUpdate());
    }


    /**
     * Metodo che permette il posizionamento di un Dado senza considerare la vincolazione della Cella riguardo la sfumatura, comprese quelle forzate
     * delle celle adiacenti. Si noti che il metodo seleziona un Dado già posizionato sulla carta Side, quindi va inteso come uno spostamento

     * @param oldRow riferimento alla riga di partenza da cui prelevare il Dado
     * @param oldCol riferimento alla colonna di partenza da cui prelevare il Dado
     * @param newRow riferimento alla riga di destinazione da cui prelevare il Dado
     * @param newCol riferimento alla riga di destinazione da cui prelevare il Dado
     * @throws InvalidValueException viene lanciata quando vengono passati valori non validi
     * @throws NoDicesNearException viene lanciata quando la Cella individuata da (oldRow, oldCol) non ha Celle adiacenti con un Dado
     * @throws NotEmptyCellException viene lanciata quando al Cella individuata da (newRow, newCol) ha gia un Dado posizionatovi
     * @throws NearDiceInvalidException viene lanciata quando al Cella individuata da (newRow, newCol) non ha Celle adiacenti con un Dado
     * @throws NearDiceInvalidException viene lanciata quando non si hanno dadi vicini
     * @throws InvalidShadeException viene lanciata quando la sfumatura non è rispettata
     */

    public void putDiceIgnoreValue(int oldRow, int oldCol,int newRow, int newCol) throws InvalidValueException, NoDicesNearException, NotEmptyCellException, InvalidColorException, NearDiceInvalidException, InvalidShadeException {
        Dice d=mySide.pick(oldRow,oldCol);
        if(d==null) throw  new InvalidValueException();
        try {
            mySide.putIgnoringShade(newRow, newCol, d);
        }
        catch(Exception e){
            mySide.put(oldRow,oldCol,d);
            throw e;
        }
        launchCommunication(mySide.setUpdate());
    }


    /**
     * Metodo che assegna al giocatore la carta Side scelta e assegna un proprietario alla carta
     *
     * @param pos riferimento alla posizione della carta selezionata all'interno della collezione di carte Side distribuite
     */

    public void setMySide(int pos){
        mySide = mySideSelection.get(pos);
        mySide.setOwner(name);
        setFavours();
    }


    /**
     * Metodo che assegna al giocatore le carte Side da cui dovrà selezionare la propria
     *
     * @param mySideSelection riferimento alla collezione di carte Side distribuite al giocatore
     */

    public void setSideSelection(List<Side> mySideSelection){
        this.mySideSelection = mySideSelection;
    }


    /**
     * Metodo che permette il posizionamento di un Dado ignorando la vincolazione sulle adiacenze
     *
     * @param row riferimento alla riga di posizionamento interessata
     * @param col riferimento alla colonna di posizionamento interessata
     * @param die riferimento al dado che si intende posizionare sulla carta Side
     * @throws InvalidColorException viene lanciata quando il valore di colore della Cella non è valido
     * @throws NotEmptyCellException viene lanciata quando la Cella individuata da (row, col) ha gia un Dado posizionatovi
     * @throws InvalidShadeException viene lanciata quando il valore di sfumatura della Cella non è valido
     * @throws InvalidCoordinatesException viene lanciata quando le coordinate inserite non sono valide
     */

    public void putWithoutDicesNear(int row, int col, Dice die) throws InvalidColorException, NotEmptyCellException, InvalidShadeException, InvalidCoordinatesException {
        if(die!=null){
        mySide.putWithoutDicesNear(row,col,die);
        launchCommunication(mySide.setUpdate());
        }
    }
    public Dice sidePick(int row, int col) throws InvalidCoordinatesException {
        Dice dado = mySide.pick(row,col);
        launchCommunication(mySide.setUpdate());
        return dado;
    }
//////////////////////////////////////////////////////////
///////////////////////////Comunicazione//////////////////
//////////////////////////////////////////////////////////
    /**
     * Metodo che lancia un evento di aggiornamento della sezione di model visibile dall'utente.
     * @param m messaggio contenente i nuovi dati.
     */
    private void launchCommunication(UpdateM m){
        if(m!=null) notifier.notifyObserver(m);
    }

    /**
     * Metodo che permette di forzare il lancio di un messaggio aggiornamento della side.
     */
    public void setUpdateSide(){
        launchCommunication(mySide.setUpdate());
    }

    /**
     * Metodo che setta l'attributo canIPlay a false e così il giocatore viene virtualmente
     * congelato saltando il turno fino al suo scongelamento.
     */
    public void forget(){
        canIPlay=false;
    }

    /**
     * Metodo per cui un giocatore viene disconnesso virtualmente dalla partita, settando l'attributo canIRenter
     * e viene congelato
     */
    public void forgetForever(){
        canIRenter=false;
        forget();
    }

    /**
     * Metodo per lo scongelamento del giocatore.
     */
    public void remember(){
        if(!canIPlay && canIRenter) canIPlay =true;
    }

    /**
     * Metodo che ritorna se è possibile per un giocatore giocare il suo turno.
     * @return true se può esehire il suo turno, false altrimenti.
     */
    public boolean canYouPlay(){
        return canIPlay;
    }

    /**
     * Metodo che lancia il messaggio di start con tutte le side da cui il giocatore può scegliere.
     */
    public void ask(){
        notifier.notifyObserver(askingMessage());
    }


     /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio di start contente
     * le carte schema da cui il player può scgeliere.
     * Il metodo è da considerarsi un toString del deck di sides e segue la convenzione del protocollo
     * di gioco.
     * @return messaggio contenente le carte schema del giocatore.
     */
    private AskPlayer askingMessage(){
        ArrayList<String> ret=new ArrayList<>(4);
        for(Side s: mySideSelection){
            ret.add(s.getName());
            ret.add(String.valueOf(s.getFavours()));
        }

        return new AskPlayer(name,"",ret);
    }
}
