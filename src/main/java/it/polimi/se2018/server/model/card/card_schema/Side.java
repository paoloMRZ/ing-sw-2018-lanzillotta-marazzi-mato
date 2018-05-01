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

    public Side(String name, int favours) throws Exception{

        if(favours > 0)
            this.favours = favours;
        else
            throw new Exception();

        this.name = name;
        matrix = new Cell[4][5];
        isEmpty = true;
    }




    //La funzione controlla che la coppia di coordinate non esca dai limiti della matrice.

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
    //Il controllo consiste nel verificare se è presente almeno un dado in una delle celle adiacenti a quella selezionata
    //per l'inserimento.
    //In più, quando si controllano le celle ortogonali si verifica la presenza di eventuali uguaglienze nei dadi.

    private void controlNeighbor(int row, int col, Dice d) throws  Exception {
        int counter = 0; //Conta quanti dadi sono stati trovati nelle celle adiacenti.

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {

                //Non voglio controllare la cella selezionata dal giocatore per inserire il dado (x=0, y=0)
                if ((x != 0 && y != 0) && areValidcoordinates(row + x, col + y) && matrix[row + x][row + y].showDice() != null) {

                    counter++; //E' presente un dado, quindi incremento il contatore.

                    //Se si sta lavorando su una cella ortogonale a quella scelta (questo avviene quando x=0 o y=0) allora controlla anche le caratteristiche di un eventuale dado.
                    if (x == 0 || y == 0) {
                        compareDices(row + x, col + y, d); //Questo metodo lancia un'eccezione se trova un'uguaglianza nei dadi.
                    }
                }
            }
        }

        if (counter == 0) //Se non è stato trovato nessun dado lacio un'eccezione.
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

        if(areValidcoordinates(row,col)) { //Controllo se le coordinate sono valide. Se non lo sono lancio un'eccezione.

            //Se non è il primo inserimento controlla i vicini.
            if (!isEmpty)
                controlNeighbor(row, col, d);

            //Se è il primo inserimento puoi inserire solo nella cornice più esterna. Non è necessario controllare i vicini.
            if (isEmpty && (!(row == 0 || row == 3) || !(col == 0 || col == 4)))
                throw new Exception();

            matrix[row][col].putDice(d);

            isEmpty = false; //Questa istruzione deve sempre essere l'ultima di questo metodo.
        }
        else
            throw new Exception();
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
        if(areValidcoordinates(row,col))
            return matrix[row][col].getNumber();
        else
            throw new Exception();
    }
}


