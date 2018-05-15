package it.polimi.se2018.server.model.card.card_schema;

import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;


/**
 * La classe rappresenta una singola cella di una carta schema con le sue possibili restrizioni e
 * l'eventuale dado posizionato su di essa.
 * Si noti che una cella non può cambiare le sue restrizioni durate la partita, quindi queste vengono dichiarate alla
 * nascita della cella e non vengono mai modificate.
 *
 * @author Marazzi Paolo
 */

public class Cell {

    private Dice dice; //Eventuale dado posizionato sulla cella.
    private Color color; //Eventuale restrizione sul colore.
    private int number; //Eventuale restrizione sulla sfumatura.


    /**
     * Costruttore di Default necessario per la lettura da file Json
     *
     */
     public Cell(){
         super();
     }


    /**
     * Costruttore della classe. Crea una cella che non contiene nessun dado.
     *
     * @param color restrizione di colore della cella.
     * @param number restrizione di sfumatura della cella.
     * @throws InvalidShadeValueException viene lanciata se viene passato una restrizione di sfumatura non valida.
     * @throws InvalidColorValueException viene lanciata se viene passato una restrizione di colore non valida.
     */

    public Cell(Color color, int number) throws InvalidShadeValueException, InvalidColorValueException {

        if(number>= 0 && number<=6)
            this.number = number;
        else
            throw new InvalidShadeValueException();

        this.color = color;
        this.dice = null;
    }

    /**
     * Costruttore della classe. Crea una cella contenente una dado.
     *
     * @param color restrizione di colore della cella.
     * @param number restrizione di sfumatura della cella.
     * @param d dado contenuto nella cella.
     * @throws InvalidShadeValueException viene lanciata se viene passato una restrizione di sfumatura non valida.
     * @throws InvalidColorValueException viene lanciata se viene passato una restrizione di colore non valida.
     */
    public Cell(Color color, int number, Dice d) throws InvalidShadeValueException, InvalidColorValueException {

        if(number>= 0 && number<=6)
            this.number = number;
        else
            throw new InvalidShadeValueException();

        this.color = color;

        if(d != null)
            this.dice = new Dice(d.getColor(), d.getNumber());
        else
            throw new NullPointerException();
    }



    /**
     * Il metodo inserisce un dado alla cella controllando le restrizioni di colore e sfumatura.
     *
     * @param d dado da inserire nella cella.
     *
     * @throws NotEmptyCellException lanciata quando una cella è già occupata da un dado.
     * @throws InvalidColorException lanciata quando il dado da inserire non rispetta la restrizione di colore della cella.
     * @throws InvalidShadeException lanciata quando il dado da inserire non rispetta la restrizione di sfumatura della cella.
     */
    public void putDice(Dice d) throws NotEmptyCellException, InvalidColorException, InvalidShadeException {

        //Controlla se la cella è già occupata, se si lancia un'eccezione.
        if(this.dice != null) throw new NotEmptyCellException();

        //Controlla se c'è una restrizione di colore. Se è violata lancia un'eccesione.
        if(this.color != Color.WHITE && d.getColor() != this.color)
            throw new InvalidColorException();

        //Controlla se c'è una restrizione di sfumatura. Se è violata lancia un'eccezione.
        if(this.number != 0 && d.getNumber() != this.number) throw  new InvalidShadeException();

        //Se non è stata sollevata nessuna eccezione posiziona il dado.
        //Dichiaro un nuovo dice con i valori di quello passato per non esporre il riferimento.
        this.dice = new Dice(d.getColor(), d.getNumber());
    }

    /**
     * Il metodo aggiunge un dado alla cella ignorando le restrizini di colore.
     *
     * @param d dado da inserire nella cella.
     *
     * @throws NotEmptyCellException lanciata quando una cella è già occupata da un dado.
     * @throws InvalidShadeException lanciata quando il dado da inserire non rispetta la restrizione di sfumatura della cella.
     */
    public void putDiceIgnoringColor(Dice d) throws NotEmptyCellException, InvalidShadeException {

        //Controlla se la cella è già occupata, se si lancia un'eccezione.
        if(this.dice != null) throw new NotEmptyCellException();

        //Controlla se c'è una restrizione di sfumatura. Se è violata lancia un'eccezione.
        if(this.number != 0 && d.getNumber() != this.number) throw  new InvalidShadeException();

        //Se non è stata sollevata nessuna eccezione posiziona il dado.
        //Dichiaro un nuovo dice con i valori di quello passato per non esporre il riferimento.
        this.dice = new Dice(d.getColor(), d.getNumber());

    }

    /**
     * Il metodo aggiunge un dado alla cella ignorando le restrizini di sfumatura.
     *
     * @param d dado da inserire nella cella.
     *
     * @throws NotEmptyCellException lanciata quando una cella è già occupata da un dado.
     * @throws InvalidColorException lanciata quando il dado da inserire non rispetta la restrizione di colore della cella.
     */
    public void putDiceIgnoringShade(Dice d) throws NotEmptyCellException, InvalidColorException {

        //Controlla se la cella è già occupata, se si lancia un'eccezione.
        if(this.dice != null) throw new NotEmptyCellException();

        //Controlla se c'è una restrizione di colore. Se è violata lancia un'eccesione.
        if(this.color != Color.WHITE && d.getColor() != this.color) throw new InvalidColorException();

        //Se non è stata sollevata nessuna eccezione posiziona il dado.
        //Dichiaro un nuovo dice con i valori di quello passato per non esporre il riferimento.
        this.dice = new Dice(d.getColor(), d.getNumber());
    }

    /**
     * Il metodo preleva il dado dalla cella e lo restituisce (se non c'è nessun dado restituisce null). Questo significa
     * che la cella perde il riferimento al dado.
     * Se nessun dado é contenuto restituisce null.
     *
     * @return Dice dado (eventualmente) contenuto nella cella.
     */
    public Dice pickDice(){
        Dice tmp = this.dice;
        this.dice = null;
        return  tmp;
    }

    /** Il metodo restituisce ( ma non rimuove ) l'eventuale dado contenuto nella cella.
     * Se nessun dado è presente restituisce null.
     *
     * @return Dice dado (eventualmente) contenuto nella cella.
     */
    public Dice showDice(){
        if(this.dice != null)
            return new Dice(this.dice.getColor(), this.dice.getNumber()); //Faccio in questo modo per non esporre l'oggetto privato.
        else
            return null;
    }


    /**
     *Il metodo restituisce la restrizione di colore della cella.
     *
     * @return colore della cella.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Il metodo restituisce la restrizione di sfumatura della cella.
     *
     * @return sfumatura della cella.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Restituisce il colore del dado contenuto nella cella.
     * Se nessun dado è presente restituisce null.
     *
     * @deprecated
     * @return String colore.
     */
    public Color getCellsDiceColor(){
        if(this.dice != null) return dice.getColor();
        else return null;
    }


    /**
     * Restituisce il numero del dado contenuto nella cella.
     * Se nessun dado è presente restituisce null.
     *
     * @deprecated
     * @return int valore.
     */
    public int getCellsDiceNumber(){
        if(this.dice != null) return dice.getNumber();
        else return 0;
    }




}
