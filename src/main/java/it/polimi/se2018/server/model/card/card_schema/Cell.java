package it.polimi.se2018.server.model.card.card_schema;

import it.polimi.se2018.server.model.dice_sachet.Dice;

public class Cell {

    //OVERVIEW: La classe rappresenta una singola cella di una carta schema con le sue possibili restrizioni e
    //l'eventuale dado posizionato su di essa.
    //Si noti che una cella non può cambiare le sue restrizioni durate la partita, quindi queste vengono dichiarate alla
    //nascita della cella e non vengono mai modificate.

    private Dice dice; //Eventuale dado posizionato sulla cella.
    private String color; //Eventuale restrizione sul colore.
    private int number; //Eventuale restrizione sulla sfumatura.

    //Costruttore.
    public Cell(String color, int number) throws Exception{
        if(number>= 0 && number<=6)
            this.number = number;
        else
            throw new Exception();

        this.color = color;
        this.dice = null;
    }

    public String getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    //Il metodo preleva il dado dalla cella e lo restituisce (se non c'è nessun dado restituisce null). Questo significa
    //che la cella perde il riferimento al dado.

    public Dice pickDice(){
        Dice tmp = this.dice;
        this.dice = null;
        return  tmp;
    }

    //TODO Dobbiamo decidere cosa fare con le eccezioni. Per ora lancio un'eccezione generica (che è una cosa sbagliata).

    public void putDice(Dice d) throws Exception{

        //Controlla se la cella è già occupata, se si lancia un'eccezione.
        if(this.dice != null) throw new Exception();

        //Controlla se c'è una restrizione di colore. Se è violata lancia un'eccesione.
        if(this.color != "withe" && d.getColor() != this.color) throw new Exception();

        //Controlla se c'è una restrizione di sfumatura. Se è violata lancia un'eccezione.
        if(this.number != 0 && d.getNumber() != this.number) throw  new Exception();

        //Se non è stata sollevata nessuna eccezione posiziona il dado.
        //Dichiaro un nuovo dice con i valori di quello passato per non esporre il riferimento.
        this.dice = new Dice(d.getColor(), d.getNumber());
    }


    /*
        REALIZZATA MODIFICA DEL METODO showDice()

        Nell'algoritmo 1 e 2 delle carte obbiettivo, ho utilizzato inizialmente i metodi getter di Side per il controllo dei colori, ma
        successivamente mi sono accorto che il metodo non funziona correttamente in presenza di celle prive di colore.

        Pertanto un modo univoco di controllo è quello di verificare che tutti i dadi disposti sulla riga abbiano colori diversi, però
        nella classe Cell non ho visto metodi getter per questo tipo di utilizzo ma solo showDice(). Visto che mi sembrava eccessivo creare
        una raffica di nuovi oggetti Dice solo per estrarre l'informazione del colore, ho inserito ilmetodo getColorDice().

        Secondo me è più comodo, però non elimino il metodo così ne discutiamo insieme.

     */


    public String getCellsDiceColor(){
        if(this.dice != null) return dice.getColor();
        else return null;
    }

    public int getCellsDiceNumber(){
        if(this.dice != null) return dice.getNumber();
        else return 0;
    }

    //Il metodo restituisce ( ma non rimuove ) l'eventuale dado contenuto nella cella. Se nessun dado è presente
    //restituisce null.

    public Dice showDice(){
        if(this.dice != null)
            return new Dice(this.dice.getColor(), this.dice.getNumber()); //Faccio in questo modo per non esporre l'oggetto privato.
        else
            return null;
    }
}
