package it.polimi.se2018.server.model.card.card_schema;

import it.polimi.se2018.server.model.dice_sachet.Dice;

public class Side {

    //OVERVIEW: La classe rappresenta una facciata di una carta schema, cioè una griglia su cui posizionare i dadi, il
    //suo nome ed il numero di segnalini favore ad essa associati.

    private Cell [][] matrix;
    private int favours;
    private String name;
    private boolean isEmpty; //Indica se lo schema contiene dadi. La griglia non potrà mai ritornare vuota.

    /*TODO: definire bene il costruttore. Side non deve possedere nessun metodo addCell, quindi vuol dire che tutte le
    celle che compongono la matrice devono essere passate al momento della creazione, quindi il costruttore dovrebbe
    prevedere anche un terzo parametro, cioè la lista delle celle che compongono la matrice*/

    public Side(String name, int favours){
        this.name = name;
        this.favours = favours; //TODO: effettuare un controllo si favours. In caso di errore sollevare un'eccezione!
        matrix = new Cell[4][5];
        isEmpty = true;
    }





    private boolean areValidcoordinates(int row, int col){
        return row >= 0 && row <= 3 && col >= 0 && col <= 4;
    }

    //Metodo di appoggio per l'inserimento di un nuovo dado in una cella. Il suo scopo è quello di controllare se il dado
    //'d' coincide con l'eventuale dado contenuto in (row, col) per colore o sfumatura.

    private void compareDices(int row, int col, Dice d) throws Exception{
        if (matrix[row][col].showDice().getNumber() == d.getNumber() || matrix[row][col].showDice().getColor().equals(d.getColor())) {
            throw new Exception();
        }
    }

    //Questo metodo effettua il controllo delle celle confinanti alla cella selezionata per l'inserimento.
    //Quando controlla anche le celle ortogonali verifica eventuali uguaglienze nei dadi.

    private void controlNeighbor(int row, int col, Dice d) throws  Exception{
        int counter = 0;

        for(int y = -1; y<=1; y++){
            for(int x = -1; x<=1; x++){
                //Non voglio controllare la cella selezionata dal giocatore per inserire il dado (x=0, y=0)
                if( (x != 0 && y != 0) && areValidcoordinates(row+x, col+y) && matrix[row+x][row+y].showDice() != null){

                    //Se si sta lavorando su una cella ortogonale a quella scelta (questo avviene quando x=0 o y=0) allora controlla anche le caratteristiche di un eventuale dado.
                    if(x == 0 || y == 0){
                        compareDices(row+x, col+y, d);
                    }

                    counter++;
                }
            }
        }

        if( counter == 0)
            throw new Exception();
    }






    //Il metodo preleva un dado dalla cella indicata, questo implica che la cella perde il riferimento al dado prelevato.
    //Se la cella non contiene un dado viene restituito null.
    //La cella in alto a sx ha coordinate (0,0).

    public Dice pick(int row, int col) throws Exception {
        if(areValidcoordinates(row, col))
            return matrix[row][col].pickDice();
        else
            throw new Exception();
    }

    //Il metodo posiziona il dado 'd' nella cella indicata dai parametri (row, col) solo se quest'ultima non è occupata
    //da un'altro dado e se le condizioni con i dadi confinanti sono rispettate.


    public void put(int row, int col, Dice d) throws Exception {

        //Se non è il primo inserimento controlla i vicini.
        if(!isEmpty)
            controlNeighbor(row, col, d);

        //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini.
        if(isEmpty && ( !(row == 0 || row == 3) || !(col == 0 || col == 4)) )
            throw new Exception();

        matrix[row][col].putDice(d);

        isEmpty = false; //Questa istruzione deve sempre essere l'ultima di questo metodo.
    }

    public int getFavours() {
        return favours;
    }

    public String getName() {
        return name;
    }

    //Il metodo restituisce il colore della cella selezionata.

    public String getColor(int row, int col) throws  Exception{
        if(areValidcoordinates(row, col))
            return matrix[row][col].getColor();
        else
            throw new Exception();
    }

    //Il metodo restituisce la sfumatura della cella selezionata.

    public int getNumber(int row, int col) throws  Exception{
        if(row >= 0 && row <= 3 && col >= 0 && col <= 4)
            return matrix[row][col].getNumber();
        else
            throw new Exception();
    }
}


