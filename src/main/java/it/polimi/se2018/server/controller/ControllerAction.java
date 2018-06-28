package it.polimi.se2018.server.controller;


import it.polimi.se2018.server.events.HookMessage;
import it.polimi.se2018.server.events.SimpleMove;
import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;


import java.util.ArrayList;

/**
 * Classe adibita alle modifiche dirette al model, insieme di metodi richiamati dal Controller per "azioni" dirette
 * sulle sotto istanze del tavolo.
 * @author Kevin Mato
 */
public class ControllerAction {
    private final Table lobby;

    private final Controller controller;

    /**
     * Costruttore della classe di azioni
     * @param LOBBY riferimento alla classe principale del model
     * @param controller riferimento alla classe principale dei controller
     */
    public ControllerAction(Table LOBBY,Controller controller){
        this.lobby=LOBBY;
        this.controller=controller;
    }

///////////////////////////////////////////////////////////////////////

    /**
     * Metodo utilizzato per compiere un semplice piazzamento di un dado dato sulla carta schema del giocatore
     * di nome "name".
     * @param name nome del giocatore padrone dello schema
     * @param d riferimento del dado dato preso o da riserva o da un altro punto dello schema
     * @param row la riga dove porre il dado
     * @param col colonna dove porre il dado
     * @throws InvalidValueException
     * @throws InvalidCellException
     */
    public void workOnSide(String name,Dice d, int row, int col)throws InvalidValueException, InvalidCellException {
        lobby.callPlayerByName(name).putDice(d,row,col);
    }

    /**
     * Metodo che effettua lo spostamento di un dado all'interno di uno schema. Ignorando reatrizioni di colore.
     * @param name nome del giocatore padrone dello schema
     * @param oldRow riga precedente
     * @param oldCol colonna precedente
     * @param newRow riga nuova
     * @param newCol colonna nuova
     * @throws InvalidValueException
     * @throws InvalidCellException
     */
    public void workOnSideIgnoreColor(String name,int oldRow, int oldCol,int newRow, int newCol)throws InvalidValueException, InvalidCellException{
        lobby.callPlayerByName(name).putDiceIgnoreColor(oldRow,oldCol,newRow,newCol);
    }

    /**
     * Metodo che effettua lo spostamento di un dado all'interno di uno schema. Ignorando restrizioni di valore.
     * @param name nome del giocatore padrone dello schema
     * @param oldRow riga precedente
     * @param oldCol colonna precedente
     * @param newRow riga nuova
     * @param newCol colonna nuova
     * @throws InvalidValueException
     * @throws InvalidCellException
     */
    public void workOnSideIgnoreValue(String name,int oldRow, int oldCol,int newRow, int newCol)throws InvalidValueException, InvalidCellException{
        lobby.callPlayerByName(name).putDiceIgnoreValue(oldRow,oldCol,newRow,newCol);
    }

    /**
     * Metodo che posiziona un dado della riserva in una cella senza vicini.
     * @param name nome del giocatore padrone dello schema
     * @param die indice del dado nella riserva
     * @param row riga dove posizionare
     * @param col colonna dove posizionare
     * @throws InvalidValueException
     * @throws InvalidShadeException
     * @throws NotEmptyCellException
     * @throws InvalidColorException
     * @throws InvalidSomethingWasNotDoneGood
     */
    public void putNoNeighbours(String name, int die, int row, int col) throws InvalidValueException, InvalidShadeException, NotEmptyCellException, InvalidColorException, InvalidSomethingWasNotDoneGood {
        Dice tmp= pickFromReserve(die);
        lobby.callPlayerByName(name).putWithoutDicesNear(row,col,tmp);
    }

    /**
     * Metodo che mostra una copia della riserva contenuta nel tavolo.
     * @return Copia della riserva del tavolo
     */
    public Reserve getReserve(){
        return lobby.getReserve();
    }

    /**
     * Metodo che appende il dado tenuto in sospeso nel model durante l'uso in più fasi di una carta utensile.
     */
    public void putBackInReserve(){
        Reserve toStoreAgain=lobby.getReserve();
        toStoreAgain.put(controller.getHoldingADiceMoveInProgress());
        lobby.setReserve(toStoreAgain);
    }

    /**
     *Overloading del metodo putBackInReserve che in questo caso appende alla riserva attuale nel model
     * un dado a mia scelto passandogli il riferimento di tale
     * @param D riferimento del dado da inserire
     */
    public void putBackInReserve(Dice D){
        Reserve toStoreAgain=lobby.getReserve();
        toStoreAgain.put(D);
        lobby.setReserve(toStoreAgain);
    }

    /**
     * Metodo che  pesca un dado dalla riserva in base all'indice che gli passiamo.
     * @param whichOne indice del dado scleto nell'elenco della riserva.
     * @return riferimento al dado ritornato in risposta dal model.
     * @throws InvalidValueException
     * @throws InvalidSomethingWasNotDoneGood
     */
    public Dice pickFromReserve(int whichOne) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        try {
            return lobby.pickFromReserve(whichOne);
        }
        catch(IndexOutOfBoundsException  e){
            throw new InvalidValueException();
        }
        catch(NullPointerException e){
            throw new InvalidSomethingWasNotDoneGood();
        }
    }

    /**
     * Metodo che segna che il player nel attuale turno ha attivato una carta utensile e gli scala il prezzo giusto.
     * @param name nome del giocatore che ha attivato la carta.
     * @param price prezzo da scalare al giocatore.
     * @throws InvalidValueException
     */
    public void playerActivatedCard(String name,int price)throws InvalidValueException{
        lobby.callPlayerByName(name).setDidPlayCard(price);
    }

    /**
     * Metodo che sostituisce la riserva attuale nel model con la riserva che gli passo.
     * @param reserve
     */
    public void resettingReserve(Reserve reserve){
        lobby.setReserve(reserve);
    }

    /**
     * Metodo che pesca un dado dal RoundGrid in base a due coordinare.
     *
     * @param onGrid casella del round.
     * @param inGrid dentro la lista di dadi nella casella di dadi
     * @return
     * @throws InvalidValueException
     */
    public Dice takeFromGrid(int onGrid,int inGrid) throws InvalidValueException {
        Dice d= lobby.getRoundGrid().pick(onGrid,inGrid);
        lobby.setUpdateRoundGrid();
        return d;
    }

    /**
     * Metodo che appende un dado alla lista di dadi presente in una certa casella di round.
     *
     * @param onGrid indice della casella di round.
     * @param d riferimento del dado da appendere.
     * @throws InvalidValueException
     */
    public void putOnGrid(int onGrid,Dice d) throws InvalidValueException {
        lobby.getRoundGrid().put(onGrid,d);
        lobby.setUpdateRoundGrid();
    }

    /**
     * Metodo che mostra una copia del dado presente in una certa cella dello schema, per una sola analisi.
     * @param name nome del proprietario dello schema
     * @param row coordinata riga del dado
     * @param col coordinata colonna del dado
     * @return riferimento della copia del dado
     * @throws InvalidValueException
     */
    public Dice takeALookToDie(String name, int row, int col) throws InvalidValueException {
        return lobby.callPlayerByName(name).showSelectedCell(row,col).showDice();
    }

    /**
     * Metodo che mostra una copia del dado presente in una certa cella del roundgrid, per una sola analisi.
     * @param onBox coord. della cella round.
     * @param inBox coord. nella lista della cella round.
     * @return riferimento a copia del dado.
     * @throws InvalidValueException
     */
    public Dice takeALookToDieInGrid(int onBox, int inBox) throws InvalidValueException {
        return lobby.getRoundGrid().show(onBox,inBox);
    }


    /**
     * Metodo che richiede il reinserimento di un dado e l'estrazione di uno nuovo.
     * @param die riferimento al dado da reinserire.
     * @return riferimento al dado estratto dal sacchetto.
     */
    public Dice extractDieAgain(Dice die){
        lobby.getDiceSachet().reput(die);
        return lobby.getDiceSachet().getDiceFromSachet();
    }

    /**
     * Metodo che ritorna semplicemente quanti giocatori ci sono nella partita attuale.
     * @return numero di giocatori.
     */
    public int peopleCounter(){
        return lobby.peopleCounter();
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che esegue un piazzamento semplice di un dado, da trarre dalla riserva, riponenedolo sulla carta
     * schema del giocatore che invia il messaggio.
     * @param move messaggio contenente le info per il piazzamento.
     */
    public void simpleMove(SimpleMove move){
        try{
            String dude = move.getPlayer();
            lobby.setHoldingResDie(pickFromReserve(move.getDiceIndex()));
            ArrayList<Integer> coords = move.getCoord();
            int row = coords.get(0);
            int col = coords.get(1);

            workOnSide(dude, lobby.getHoldingResDie(), row, col);
            lobby.cleanHoldingResDie();
            lobby.callPlayerByName(dude).setDidPlayDie();
            //success yeeeeaaaaaaa
            lobby.responder().notifyObserver(new SuccessSimpleMove(move.getDiceIndex(),row,col,dude));
        }
        catch(InvalidValueException | InvalidCellException e){
            putBackInReserve(lobby.getHoldingResDie());
            lobby.cleanHoldingResDie();
            String destination=controller.getTurn().getName();
            ArrayList<Integer> coords = move.getCoord();
            int row = coords.get(0);
            int col = coords.get(1);
            lobby.responder().notifyObserver(new ErrorSelection(move.getDiceIndex(),row,col,destination));
        } catch (InvalidSomethingWasNotDoneGood invalidSomethingWasNotDoneGood) {
            invalidSomethingWasNotDoneGood.printStackTrace();
        }
    }

    /**
     * Metodo che richiede  messaggi di aggiornamento su alcune parti del model.
     * @param m messaggio con una stringa nella quale sono indicati gli elementi da aggiornare.
     */
    public void refresher(UpdateReq m){
        try{
        lobby.refresh(m);
        controller.getTurn().refresh(m);}
        catch (Exception e ){
            lobby.responder().notifyObserver(new
                    ErrorSomethingNotGood(e));
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metedo che si occupa di agganciare il notificatore del MODEL con la FAKEVIEW.
     * @param message contiene la reference della fakeview chat
     */
    public void hook(HookMessage message){
        lobby.responder().register(message.getObserver());
    }

}
