package it.polimi.se2018.server.model.card.card_schema;

import it.polimi.se2018.server.model.dice_sachet.Dice;

public class Side {

    //OVERVIEW: La classe rappresenta una facciata di una carta schema, cioè una griglia su cui posizionare i dadi, il
    //suo nome ed il numero di segnalini favore ad essa associati.

    private Cell [][] matrix;
    private int favours;
    private String name;
    private boolean isEmpty;    //Indica se lo schema contiene dadi. La griglia non potrà mai ritornare vuota.

    /*
        REALIZZATA LA MODIFICA DEL COSTRUTTORE.
        Sono d'accordo sul fatto che non ci debba essere un metodo del tipo addCell, ma che si debba dare come ingresso anche
        un parametro che indichi come comporre Side.
        Il parametro da dare in ingresso potrebbe essere una Array di Stringhe. Essendo matrix interpretato a livello di memoria
        come un array, le stringhe possono essere interpretate nel modo seguente:

            -> RESTRIZIONE COLORE RESTRIZIONE NUMERO (esempio: Rosso, 3 - Giallo, 6)

        Pertanto l'array passato come parametro indicherà le caratteristiche che, tramite i getter della classe Cell, creano le
        varie celle. In caso sia richiesta una cella senza restrizioni, basterà semplicemente non inserire nessuna stringa all'indice
        corrispondente.

            ESEMPIO:

                Array di stringhe in input
                    Rosso 3                 Cella [1][1] -> Rossa con restrizione di numero 3
                    Giallo 6                Cella [1][2] -> Gialla con restrizione di numero 6
                    NULL                    Cella [1][3] -> Nessuna Restrizione
                    Blu                     Cella [1][4] -> Restrizione di Colore Blu
                    5                       Cella [1][5] -> Restrizione di Numero 5


       In questo modo potremmo anche già definire il modo tramite il quale i giocatori possono utilizzare delle proprie PatternCard
       personalizzate. In un file di testo (che verrà caricato sul server prima dell'inizio della partita) il giocatore scriverà
       in ordine, partendo dall'angolo in alto a sinistra della PatternCard, la lista di Restrizioni che vorrà introdurre nel modo
       che ho illustrato sopra.

       Secondo voi è accettabile come idea?

    */


    //TODO: definire il tipo di eccezioni sollevate

    public Side(String name, int favours, String[] settings) throws Exception{          //settings rappresenta l'array di stringhe per settare Side

        String colorRestriction;
        String numberRestriction;
        int space = 0;
        int i=0;

        //Controlla inizialmente se le dimensione dell'array soddisfano le dimensioni della PatternCard, quindi procede a visitare l'array
        if(settings.length == 20) {
            matrix = new Cell[4][5];

            while(i<20) {
                for (int j=0; j<4; j++) {
                    for (int k=0; k<5; k++) {
                        space = settings[i].indexOf(' ');
                        colorRestriction = settings[i].substring(0, space);                                         //Estrae la restrizione del colore -> Rosso
                        numberRestriction = settings[i].substring(space + 1, space + 2);                            //Estrae la restrizione del numero -> 3
                        matrix[j][k] = new Cell(colorRestriction, Integer.parseInt(numberRestriction));
                        i++;
                    }
                }
            }
        }
        else throw new Exception();

        if(favours > 0)
            this.favours = favours;
        else
            throw new Exception();

        this.name = name;
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



    // I metodi richiedono l'informazione del colore e del numero dell'eventuale dado posizionato sulla cella (row,col)
    public String getCellsDiceColorInformation(int row, int col) throws Exception{
        if(areValidcoordinates(row,col))
            return matrix[row][col].getCellsDiceColor();
        else throw new Exception();
    }

    public int getCellsDiceNumberInformation(int row, int col) throws Exception{
        if(areValidcoordinates(row,col))
            return matrix[row][col].getCellsDiceNumber();
        else throw new Exception();
    }
}


