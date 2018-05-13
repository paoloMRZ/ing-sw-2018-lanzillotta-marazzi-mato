package it.polimi.se2018.server.model.card.card_schema;

import it.polimi.se2018.server.exceptions.*;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.*;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.List;

public class Side {

    //OVERVIEW: La classe rappresenta una facciata di una carta schema, cioè una griglia su cui posizionare i dadi, il
    //suo nome ed il numero di segnalini favore ad essa associati.

    private static final int MAX_ROW = 3;
    private static final int MAX_COL = 4;

    private Cell [][] matrix;
    private int favours;
    private String name;
    private boolean isEmpty;    //Indica se lo schema contiene dadi. La griglia non potrà mai ritornare vuota.


    public Side(String name, int favours, List<Cell> cells) throws InvalidFavoursValueException, InvalidColorValueException, InvalidShadeValueException {          //settings rappresenta l'array di stringhe per settare Side

        matrix = new Cell[MAX_ROW +1][MAX_COL +1];
        int k = 0;

        for(int i=0; i<MAX_ROW+1; i++){
            for(int j=0; j<MAX_COL+1; j++){
                matrix[i][j] = new Cell(cells.get(k).getColor(), cells.get(k).getNumber());
                k++;
            }
        }

        if(favours > 0)
            this.favours = favours;
        else
            throw new InvalidFavoursValueException();

        this.name = name;
        isEmpty = true;
    }

    //La funzione controlla che la coppia di coordinate non esca dai limiti della matrice.

    private boolean areValidcoordinates(int row, int col){
        return row >= 0 && row <= MAX_ROW && col >= 0 && col <= MAX_COL;
    }

    //Metodo di appoggio per l'inserimento di un nuovo dado in una cella. Il suo scopo è quello di controllare se il dado
    //'d' coincide con l'eventuale dado contenuto in (row, col) per colore o sfumatura.

    private boolean areSimilarDice(int row, int col, Dice d) {
        return matrix[row][col].showDice().getNumber() == d.getNumber() || matrix[row][col].showDice().getColor().equals(d.getColor());
    }

    //Questo metodo effettua il controllo delle celle confinanti ortogonalmente alla cella selezionata per l'inserimento.
    //Se una di queste celle posside un dado con sfumatura o colore uguale a quella del dado passato viene restituito false.

    private boolean checkOrtogonalNeighbor(int row, int col, Dice d) {

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {

                //Non voglio controllare la cella selezionata dal giocatore per inserire il dado (x=0, y=0)
                //Se si sta lavorando su una cella ortogonale a quella scelta (questo avviene quando x=0 o y=0) allora controlla anche le caratteristiche di un eventuale dado.
                if (    (x*x != y*y) &&
                        areValidcoordinates(row + x, col + y) &&
                        matrix[row + x][col + y].showDice() != null &&
                        areSimilarDice(row + x, col + y, d)   )

                        return false;
            }
        }

        return true;
    }

    //Il metodo ritorna true se trova un dado confinante alla cella passata.

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


    //Il metodo preleva un dado dalla cella indicata, questo implica che la cella perde il riferimento al dado prelevato.
    //Se la cella non contiene un dado viene restituito null.
    //La cella in alto a sx ha coordinate (0,0).

    public Dice pick(int row, int col) throws InvalidCoordinatesException {
        if(areValidcoordinates(row, col))
            return matrix[row][col].pickDice();
        else
            throw new InvalidCoordinatesException();
    }

    //Il metodo posiziona il dado 'd' nella cella indicata dai parametri (row, col) solo se quest'ultima non è occupata
    //da un'altro dado e se le condizioni con i dadi confinanti sono rispettate.

    public void put(int row, int col, Dice d) throws InvalidCoordinatesException, NearDiceInvalidException, NoDicesNearException, InvalidShadeException, NotEmptyCellException, InvalidColorException {

        if(areValidcoordinates(row,col)) { //Controllo se le coordinate sono valide. Se non lo sono lancio un'eccezione.

            //Se non è il primo inserimento controlla i vicini.
            if (!isEmpty) {
                if (!checkOrtogonalNeighbor(row, col, d))
                    throw new NearDiceInvalidException();

                if(!isThereNeighbor(row,col))
                    throw new NoDicesNearException();
            }
            else {

                //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini, bastas controllare che le coordinate
                //passate si riferiscano ad una cella della corretta per il primo inserimento.
                if (!(row == 0 || row == MAX_ROW) && !(col == 0 || col == MAX_COL)) throw new InvalidCoordinatesException();
            }
            matrix[row][col].putDice(d);
            isEmpty = false; //Questa istruzione deve sempre essere l'ultima di questo metodo.
        }
        else
            throw new InvalidCoordinatesException();
    }

    //Il metodo inserisce un dado nella cella selezionata ignorando le restrizioni di colore della cella,
    //ma tenendo in considerazione le restrizioni che riguardano i vicini.

    public void putIgnoringColor(int row, int col, Dice d) throws InvalidCoordinatesException, NearDiceInvalidException, NoDicesNearException, NotEmptyCellException, InvalidShadeException {
        if(areValidcoordinates(row,col)){

            //Se non è il primo inserimento controlla i vicini.
            if (!isEmpty) {
                if (!checkOrtogonalNeighbor(row, col, d))
                    throw new NearDiceInvalidException();

                if(!isThereNeighbor(row,col))
                    throw new NoDicesNearException();
            }
            else {

                //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini, bastas controllare che le coordinate
                //passate si riferiscano ad una cella della corretta per il primo inserimento.
                if (!(row == 0 || row == MAX_ROW) && !(col == 0 || col == MAX_COL))
                    throw new InvalidCoordinatesException();
            }
            matrix[row][col].putDiceIgnoringColor(d);

            isEmpty = false; //Questa istruzione deve sempre essere l'ultima di questo metodo.
        }
        else
            throw new InvalidCoordinatesException();

    }

    //Il metodo inserisce un dado nella cella selezionata ignorando le restrizioni di sfumatura della cella,
    //ma tenendo in considerazione le restrizioni che riguardano i vicini.

    public void putIgnoringShade(int row, int col, Dice d) throws InvalidCoordinatesException, NearDiceInvalidException, NoDicesNearException, NotEmptyCellException, InvalidColorException {
        if(areValidcoordinates(row,col)){
            //Se non è il primo inserimento controlla i vicini.
            if (!isEmpty) {
                if (!checkOrtogonalNeighbor(row, col, d))
                    throw new NearDiceInvalidException();

                if(!isThereNeighbor(row,col))
                    throw new NoDicesNearException();
            }
            else{

                //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini, basta controllare che le coordinate
                //passate si riferiscano ad una cella della corretta per il primo inserimento.
                if (!(row == 0 || row == MAX_ROW) && !(col == 0 || col == MAX_COL))
                    throw new InvalidCoordinatesException();
            }

            matrix[row][col].putDiceIgnoringShade(d);

            isEmpty = false; //Questa istruzione deve sempre essere l'ultima di questo metodo.
        }
        else
            throw new InvalidCoordinatesException();


    }

    //Il metodo inserisce un dado rispettando le condizioni di colore e sfumatura in una cella che non possiede dadi confinanti.

    public void putWithoutDicesNear(int row, int col, Dice d) throws InvalidCoordinatesException, InvalidShadeException, NotEmptyCellException, InvalidColorException {
        if(areValidcoordinates(row, col)){
            if(!isEmpty && isThereNeighbor(row,col))
                throw new InvalidCoordinatesException();
            else{

                //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini, basta controllare che le coordinate
                //passate si riferiscano ad una cella della corretta per il primo inserimento.
                if (isEmpty && !(row == 0 || row == MAX_ROW) && !(col == 0 || col == MAX_COL))
                    throw new InvalidCoordinatesException();
            }

            matrix[row][col].putDice(d);
            isEmpty = false; //Questa istruzione deve sempre essere l'ultima di questo metodo.
        }
        else
            throw new InvalidCoordinatesException();

    }

    public int getFavours() {
        return favours;
    }

    public String getName() {
        return name;
    }


    //Il metodo restituisce il colore della cella selezionata.
    public String getColor(int row, int col) throws InvalidCoordinatesException{
        if(areValidcoordinates(row, col))
            return matrix[row][col].getColor();
        else
            throw new InvalidCoordinatesException();
    }

    //Il metodo restituisce la sfumatura della cella selezionata.
    public int getNumber(int row, int col) throws InvalidCoordinatesException{
        if(areValidcoordinates(row,col))
            return matrix[row][col].getNumber();
        else
            throw new InvalidCoordinatesException();
    }


    //Il metodo restituisce una copia del riferimento alla cella corrispondente alle coordinate. Poichè una cella può avere
    //eventualmente un dado associato, creo anche la copia del riferimento al dado stesso

    public Cell showCell(int row, int col) throws InvalidCoordinatesException, InvalidColorValueException, InvalidShadeValueException {
        if(areValidcoordinates(row,col)) {
            if(matrix[row][col].showDice()!=null)
                return new Cell(matrix[row][col].getColor(), matrix[row][col].getNumber(), matrix[row][col].showDice());
            else
                return new Cell(matrix[row][col].getColor(), matrix[row][col].getNumber());
        }
        else
            throw new InvalidCoordinatesException();
    }

    // I metodi restituiscono l'informazione del colore e del numero dell'eventuale dado posizionato sulla cella (row,col)
    public String getCellsDiceColorInformation(int row, int col) throws InvalidCoordinatesException{
        if(areValidcoordinates(row,col))
            return matrix[row][col].getCellsDiceColor();
        else throw new InvalidCoordinatesException();
    }

    public int getCellsDiceNumberInformation(int row, int col) throws InvalidCoordinatesException{
        if(areValidcoordinates(row,col))
            return matrix[row][col].getCellsDiceNumber();
        else throw new InvalidCoordinatesException();
    }
}


