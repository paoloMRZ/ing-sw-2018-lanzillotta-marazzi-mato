package it.polimi.se2018.server.model.card.card_schema;

import it.polimi.se2018.server.exceptions.*;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
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
    public Cell(String color, int number) throws InvalidShadeValueException {

        if(number>= 0 && number<=6)
            this.number = number;
        else
            throw new InvalidShadeValueException();

        this.color = color;
        this.dice = null;
    }

    public Cell(){
        super();
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

    //Il metodo inserisce un dado alla cella controllando le restrizioni di colore e sfumatura.
    public void putDice(Dice d) throws InvalidCellException {

        //Controlla se la cella è già occupata, se si lancia un'eccezione.
        if(this.dice != null) throw new NotEmptyCellException();

        //Controlla se c'è una restrizione di colore. Se è violata lancia un'eccesione.
        if(!this.color.equals("white") && !d.getColor().equals(this.color)) throw new InvalidColorException();

        //Controlla se c'è una restrizione di sfumatura. Se è violata lancia un'eccezione.
        if(this.number != 0 && d.getNumber() != this.number) throw  new InvalidShadeException();

        //Se non è stata sollevata nessuna eccezione posiziona il dado.
        //Dichiaro un nuovo dice con i valori di quello passato per non esporre il riferimento.
        this.dice = new Dice(d.getColor(), d.getNumber());
    }


    //Il metodo aggiunge un dado alla cella ignorando le restrizini di colore.
    public void putDiceIgnoringColor(Dice d) throws InvalidCellException {

        //Controlla se la cella è già occupata, se si lancia un'eccezione.
        if(this.dice != null) throw new NotEmptyCellException();

        //Controlla se c'è una restrizione di sfumatura. Se è violata lancia un'eccezione.
        if(this.number != 0 && d.getNumber() != this.number) throw  new InvalidShadeException();

        //Se non è stata sollevata nessuna eccezione posiziona il dado.
        //Dichiaro un nuovo dice con i valori di quello passato per non esporre il riferimento.
        this.dice = new Dice(d.getColor(), d.getNumber());

    }

    public void putDiceIgnoringShade(Dice d) throws InvalidCellException{

        //Controlla se la cella è già occupata, se si lancia un'eccezione.
        if(this.dice != null) throw new NotEmptyCellException();

        //Controlla se c'è una restrizione di colore. Se è violata lancia un'eccesione.
        if(!this.color.equals("withe") && !d.getColor().equals(this.color)) throw new InvalidColorException();

        //Se non è stata sollevata nessuna eccezione posiziona il dado.
        //Dichiaro un nuovo dice con i valori di quello passato per non esporre il riferimento.
        this.dice = new Dice(d.getColor(), d.getNumber());
    }

    //Il metodo restituisce ( ma non rimuove ) l'eventuale dado contenuto nella cella. Se nessun dado è presente
    //restituisce null.
    public Dice showDice(){
        if(this.dice != null)
            return new Dice(this.dice.getColor(), this.dice.getNumber()); //Faccio in questo modo per non esporre l'oggetto privato.
        else
            return null;
    }




    //Costruttore che prende in ingresso anche un dice
    public Cell(String color, int number, Dice d) throws InvalidShadeValueException {

        if(number>= 0 && number<=6)
            this.number = number;
        else
            throw new InvalidShadeValueException();

        this.color = color;
        this.dice = d;
    }


    public String getCellsDiceColor(){
        if(this.dice != null) return dice.getColor();
        else return null;
    }

    public int getCellsDiceNumber(){
        if(this.dice != null) return dice.getNumber();
        else return 0;
    }




}
