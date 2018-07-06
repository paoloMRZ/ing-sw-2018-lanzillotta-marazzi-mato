package it.polimi.se2018.server.model.card.card_schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.*;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.List;

/**
 * La classe rappresenta una carta schema (o finestra), cioè una griglia su cui posizionare i dadi, il
 * suo nome ed il numero di segnalini favore ad essa associati.
 *
 * @author Marazzi Paolo
 * @author Kevin Mato (adattamenti per la comunicazione)
 */
public class Side {
    private String owner;
    private static final int MAX_ROW = 3;
    private static final int MAX_COL = 4;

    @JsonProperty private Cell [][] matrix;
    @JsonProperty private int favours;
    @JsonProperty private String name;
    private int numberOfDice;    //Numero di dadi contenuti nella griglia.


    /**
     * Costruttore di Default necessario per la lettura da file Json
     *
     */
    public Side(){
        super();
    }

    /**
     * Costruttore della classe.
     *
     * @param name nome della carta.
     * @param favours numero di segnalini favore che la carta assegna al giocatore.
     * @param cells celle che compongono la griglia (vetrata) di gioco.
     * @throws InvalidFavoursValueException viene lanciata se si passa un valore minore o uguale a zero di segnlini favore assegnati alla carta.
     * @throws InvalidShadeValueException viene lanciata se una cella contiene un valore errato di sfumatura.
     */

    public Side(String name, int favours, List<Cell> cells) throws InvalidFavoursValueException, InvalidShadeValueException {          //settings rappresenta l'array di stringhe per settare Side

        numberOfDice = 0;
        matrix = new Cell[MAX_ROW +1][MAX_COL +1];
        int k = 0;

        Cell tmp;

        for(int i=0; i<MAX_ROW+1; i++){
            for(int j=0; j<MAX_COL+1; j++){
                tmp = new Cell(cells.get(k).getColor(), cells.get(k).getNumber());
                matrix[i][j] = tmp;

                if(tmp.showDice() != null) //Se la cella passata per costruire la griglia contiene un dado incremento il numero di dadi contenuti nella griglia.
                    numberOfDice++;

                k++;
            }
        }

        if(favours > 0) {
            this.favours = favours;
        }
        else {
            throw new InvalidFavoursValueException();
        }

        this.name = name;
    }

    /**
     * La funzione controlla che la coppia di coordinate non esca dai limiti della matrice.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @return viene restituito true se le coordinate sono valide, in caso contrario false.
     */
    private boolean areValidcoordinates(int row, int col){
        return row >= 0 && row <= MAX_ROW && col >= 0 && col <= MAX_COL;
    }

    /**
     * Il metodo verifica se la cella indicata da (row,col) non appartiene alla cornice più esterna della griglia di gioco.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @return restituisce true se la cella indicata da (row,col) non appartiene alla cornice più esterna della griglia di gioco, in caso contrario restituisce false.
     */
    private boolean areNotEdgeCoordinates(int row, int col){
        return !(row == 0 || row == MAX_ROW) && !(col == 0 || col == MAX_COL);
    }

    /**
     * Metodo di appoggio per l'inserimento di un nuovo dado in una cella. Il suo scopo è quello di controllare se il dado
     * 'd' coincide con l'eventuale dado contenuto in (row, col) per colore o sfumatura.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @param d dado da confrontare.
     * @return restituisce true se il dado passato conincide per colore o per sfumatura al dado contenuto nella cella indicata dalle coordinate.
     */
    private boolean areSimilarDice(int row, int col, Dice d) {
        return matrix[row][col].showDice().getNumber() == d.getNumber() || matrix[row][col].showDice().getColor() == d.getColor();
    }

    /**
     * Questo metodo effettua il controllo delle celle confinanti ortogonalmente alla cella indicata da (row,col)
     * con lo scopo di verificare se vengono rispettate le condizioni imposte dai vicini.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @param d dado da confrontare.
     * @return Se una di delle celle ortogonalmente confinanti posside un dado con sfumatura o colore uguale a quella del dado passato viene restituito true.
     */
    private boolean checkOrtogonalError(int row, int col, Dice d) {

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {

                //Non voglio controllare la cella selezionata dal giocatore per inserire il dado (x=0, y=0)
                //Se si sta lavorando su una cella ortogonale a quella scelta (questo avviene quando x=0 o y=0) allora controlla anche le caratteristiche di un eventuale dado.
                if (    (x*x != y*y) &&
                        areValidcoordinates(row + x, col + y) &&
                        matrix[row + x][col + y].showDice() != null &&
                        areSimilarDice(row + x, col + y, d)   )

                        return true;
            }
        }

        return false;
    }

    /**
     * Il metodo controlla la presenza di dadi confinati alla cella indicata dalle coordinate passate.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @return viene restituito true se è stato trovato almeno un dado in una cella confinante.
     */
    private boolean isThereNeighbor(int row, int col){

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                //Non voglio controllare la cella selezionata dal giocatore per inserire il dado (x=0, y=0)
                if ((x != 0 || y != 0) && areValidcoordinates(row + x, col + y) && matrix[row + x][col + y].showDice() != null)
                    return true; //E' presente un dado.
                }
            }
        return false; //Non ho trovato nessun dado.
    }

    /**
     * Il metodo preleva un dado dalla cella indicata, questo implica che la cella perde il riferimento al dado prelevato.
     * Se la cella non contiene un dado viene restituito null.
     * La cella in alto a sx ha coordinate (0,0).
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @return viene restituito il riferimento al dado prelevato.
     * @throws InvalidCoordinatesException viene sollevata se vengono passate delle coordinate non valide.
     */
    public Dice pick(int row, int col) throws InvalidCoordinatesException {

        if(areValidcoordinates(row, col)) {
            Dice tmp = matrix[row][col].showDice();

            if(tmp != null) //Decremento il numero di dadi solo se restituisco veramente un dado.
                numberOfDice--;

            return matrix[row][col].pickDice();
        }
        else
            throw new InvalidCoordinatesException();
    }

    /**
     * Il metodo posiziona il dado 'd' nella cella indicata dai parametri (row, col) solo se quest'ultima non è occupata
     * da un'altro dado e se le condizioni con i dadi confinanti sono rispettate.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @param d dado da inserire nella cella.
     * @throws InvalidCoordinatesException viene sollevata se le coordinate passate non sono valide.
     * @throws NearDiceInvalidException viene sollevata se il dado da inserire non rispetta le restrizioni imposte dei dadi contenuti nelle celle ortogonalmente confinanti.
     * @throws NoDicesNearException viene sollevata se le cella selezionata per l'inserimento non confina con almeno una cella contente un dado.
     * @throws InvalidShadeException viene sollevata se il dado non rispetta la restrizione di sfumatura imposta dalla cella.
     * @throws NotEmptyCellException viene sollevata se la cella selezionata contiene già un dado.
     * @throws InvalidColorException viene sollevata se il dado non rispetta la restrizione di colore imposta dalla cella.
     */
    public void put(int row, int col, Dice d) throws InvalidCoordinatesException, NearDiceInvalidException, NoDicesNearException, InvalidShadeException, NotEmptyCellException, InvalidColorException {

        if(areValidcoordinates(row,col)) { //Controllo se le coordinate sono valide. Se non lo sono lancio un'eccezione.

            //Se non è il primo inserimento controlla i vicini.
            if (numberOfDice != 0) {
                if (checkOrtogonalError(row, col, d))
                    throw new NearDiceInvalidException();

                if(!isThereNeighbor(row,col))
                    throw new NoDicesNearException();
            }
            else {

                //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini, bastas controllare che le coordinate
                //passate si riferiscano ad una cella della corretta per il primo inserimento.
                if (areNotEdgeCoordinates(row,col)) throw new InvalidCoordinatesException();
            }
            matrix[row][col].putDice(d);
            numberOfDice++; //Questa istruzione deve sempre essere l'ultima di questo metodo.
        }
        else
            throw new InvalidCoordinatesException();
    }

    /**
     * Il metodo inserisce un dado nella cella selezionata ignorando la sua restrizione di colore
     * ma tenendo in considerazione le restrizioni che riguardano i vicini.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @param d dado da inserire nella cella.
     * @throws InvalidCoordinatesException viene sollevata se le coordinate passate non sono valide.
     * @throws NearDiceInvalidException viene sollevata se il dado da inserire non rispetta le restrizioni imposte dei dadi contenuti nelle celle ortogonalmente confinanti.
     * @throws NoDicesNearException viene sollevata se le cella selezionata per l'inserimento non confina con almeno una cella contente un dado.
     * @throws InvalidShadeException viene sollevata se il dado non rispetta la restrizione di sfumatura imposta dalla cella.
     * @throws NotEmptyCellException viene sollevata se la cella selezionata contiene già un dado.
     */
    public void putIgnoringColor(int row, int col, Dice d) throws InvalidCoordinatesException, NearDiceInvalidException, NoDicesNearException, NotEmptyCellException, InvalidShadeException {
        if(areValidcoordinates(row,col)){

            //Se non è il primo inserimento controlla i vicini.
            if (numberOfDice != 0) {
                if (checkOrtogonalError(row, col, d))
                    throw new NearDiceInvalidException();

                if(!isThereNeighbor(row,col))
                    throw new NoDicesNearException();
            }
            else {

                //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini, bastas controllare che le coordinate
                //passate si riferiscano ad una cella della corretta per il primo inserimento.
                if (areNotEdgeCoordinates(row,col))
                    throw new InvalidCoordinatesException();
            }

            matrix[row][col].putDiceIgnoringColor(d);

            numberOfDice++; //Questa istruzione deve sempre essere l'ultima di questo metodo.
        }
        else
            throw new InvalidCoordinatesException();

    }


    /**
     * Il metodo inserisce un dado nella cella selezionata ignorando la sua restrizione di sfumatura
     * ma tenendo in considerazione le restrizioni che riguardano i vicini.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @param d dado da inserire nella cella.
     * @throws InvalidCoordinatesException viene sollevata se le coordinate passate non sono valide.
     * @throws NearDiceInvalidException viene sollevata se il dado da inserire non rispetta le restrizioni imposte dei dadi contenuti nelle celle ortogonalmente confinanti.
     * @throws NoDicesNearException viene sollevata se le cella selezionata per l'inserimento non confina con almeno una cella contente un dado.
     * @throws NotEmptyCellException viene sollevata se la cella selezionata contiene già un dado.
     * @throws InvalidColorException viene sollevata se il dado non rispetta la restrizione di colore imposta dalla cella.
     */
    public void putIgnoringShade(int row, int col, Dice d) throws InvalidCoordinatesException, NearDiceInvalidException, NoDicesNearException, NotEmptyCellException, InvalidColorException {
        if(areValidcoordinates(row,col)){
            //Se non è il primo inserimento controlla i vicini.
            if (numberOfDice != 0) {
                if (checkOrtogonalError(row, col, d))
                    throw new NearDiceInvalidException();

                if(!isThereNeighbor(row,col))
                    throw new NoDicesNearException();
            }
            else{

                //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini, basta controllare che le coordinate
                //passate si riferiscano ad una cella della corretta per il primo inserimento.
                if (areNotEdgeCoordinates(row,col))
                    throw new InvalidCoordinatesException();
            }

            matrix[row][col].putDiceIgnoringShade(d);

            numberOfDice++; //Questa istruzione deve sempre essere l'ultima di questo metodo.
        }
        else
            throw new InvalidCoordinatesException();


    }

    /**
     * Il metodo inserisce un dado in una cella che non possiede dadi confinanti ma rispettando le sue restrizioni di colore e sfumatura.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @param d dado da inserire nella cella.
     * @throws InvalidCoordinatesException viene sollevata se le coordinate passate non sono valide.
     * @throws InvalidShadeException viene sollevata se il dado non rispetta la restrizione di sfumatura imposta dalla cella.
     * @throws NotEmptyCellException viene sollevata se la cella selezionata contiene già un dado.
     * @throws InvalidColorException viene sollevata se il dado non rispetta la restrizione di colore imposta dalla cella.
     */
    public void putWithoutDicesNear(int row, int col, Dice d) throws InvalidCoordinatesException, InvalidShadeException, NotEmptyCellException, InvalidColorException {
        if(areValidcoordinates(row, col)){
            if(numberOfDice != 0 && isThereNeighbor(row,col))
                throw new InvalidCoordinatesException();
            else{

                //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini, basta controllare che le coordinate
                //passate si riferiscano ad una cella della corretta per il primo inserimento.
                if (numberOfDice == 0 && areNotEdgeCoordinates(row,col))
                    throw new InvalidCoordinatesException();
            }

            matrix[row][col].putDice(d);
            numberOfDice++; //Questa istruzione deve sempre essere l'ultima di questo metodo.
        }
        else
            throw new InvalidCoordinatesException();

    }

    /**
     * Il metodo restituisce il numero di segnalini favore associati alla carta.
     *
     * @return numero di segnalini favore.
     */
    public int getFavours() {
        return favours;
    }

    /**
     * Il metodo restituisce il nome della carta.
     * @return nome della carta.
     */
    public String getName() {
        return name;
    }

    /**
     * Il metodo restituisce il colore della cella selezionata.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @return colore della cella.
     * @throws InvalidCoordinatesException viene sollevata se le coordinate passate non sono valide.
     */
    public Color getColor(int row, int col) throws InvalidCoordinatesException{
        if(areValidcoordinates(row, col))
            return matrix[row][col].getColor();
        else
            throw new InvalidCoordinatesException();
    }

    /**
     * Il metodo restituisce la sfumatura della cella selezionata.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @return la sfumatura della cella.
     * @throws InvalidCoordinatesException viene sollevata se le coordinate passate non sono valide.
     */
    public int getNumber(int row, int col) throws InvalidCoordinatesException{
        if(areValidcoordinates(row,col))
            return matrix[row][col].getNumber();
        else
            throw new InvalidCoordinatesException();
    }



    /**
     *
     * Il metodo restituisce una copia del riferimento alla cella indicata dalle coordinate (row, col). Poichè una cella può avere
     * eventualmente un dado associato, creo anche la copia del riferimento al dado stesso.
     *
     * @param row coordinata relativa alla riga.
     * @param col coordinata relativa alla colonna.
     * @throws InvalidCoordinatesException viene sollevata se le coordinate passate non sono valide.
     */
    public Cell showCell(int row, int col) throws InvalidCoordinatesException, InvalidShadeValueException {
        if(areValidcoordinates(row,col)) {
            if(matrix[row][col].showDice()!=null)
                return new Cell(matrix[row][col].getColor(), matrix[row][col].getNumber(), matrix[row][col].showDice());
            else
                return new Cell(matrix[row][col].getColor(), matrix[row][col].getNumber());
        }
        else
            throw new InvalidCoordinatesException();
    }

//////////////////////////////////////////////////////////
///////////////////////////Comunicazione//////////////////
//////////////////////////////////////////////////////////

    /**
     * Metodo che assegna un proprietario alla carta schema
     * @param name nome del proprietario.
     */
    public void setOwner(String name){
        owner=name;
    }
    /**
     * Metodo che crea l'evento di aggiornamento della carta schema.
     * @return evento contenete la rappresentazione.
     */
    private UpdateM createResponse(){
        String content = this.toString();

        return new UpdateM(null,"Side", content);
    }
    /**
     * override del toString di Object, serve a creare una rappresentazione customizzata secondo protocollo
     * della classe.
     * @return stringa di rappresentazione della classe.
     */
    public String toString() {
        String message = owner;
        for (int riga=0;riga<4;riga++)
        {
            for (int colo=0;colo<5;colo++) {
                Dice die= matrix[riga][colo].showDice();
                if (die != null)
                    message = message.concat("&" + die.getColor().toString().toLowerCase() + die.getNumber());
                else
                    message = message.concat("&white0");
            }
          }

            return message;
    }
    /**
     * Metodo designato dalla classe tavolo che triggera la generazione di un aggiornamento della classe.
     * Il tavolo recuperare gli eventi di update grazie questi metodi.
     * @return l'evento di aggiornamento.
     */
    public UpdateM setUpdate(){
        return createResponse();
    }


}


