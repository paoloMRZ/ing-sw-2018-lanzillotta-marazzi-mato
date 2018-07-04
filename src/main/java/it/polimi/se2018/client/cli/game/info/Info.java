package it.polimi.se2018.client.cli.game.info;

import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.security.InvalidParameterException;

/**
 * La classe contiene una coppia numero/colore.
 * Il colore è codificato in ansi.
 *
 * @author Marazzi Paolo
 */

public abstract class Info implements Serializable {

    private Ansi.Color color;
    private int number;

    /**
     * Costruttore della classe.
     * Si verifica se i valori inseriti sono accettabili.
     *
     * @param color colore in codifica ansi.
     * @param number numero (della cella o del dado)
     */

    Info(Ansi.Color color, int number){

        if(isAcceptableColor(color) && isAcceptableNumber(number)) {
            this.color = color;
            this.number = number;
        }
        else
            throw new InvalidParameterException();
    }


    /**
     * Il metodo verifica se il colore passato come parametro rientra nella lista dei colori accettabili.
     * @param color colore da controllare
     * @return true se il colore è accettabile.
     */
    private boolean isAcceptableColor(Ansi.Color color){
        return  color == Ansi.Color.RED ||
                color == Ansi.Color.GREEN ||
                color == Ansi.Color.YELLOW ||
                color == Ansi.Color.MAGENTA ||
                color == Ansi.Color.BLUE ||
                color == Ansi.Color.WHITE;
    }

    /**
     * Il metodo verifica se il numero passato come parametro rientra nella lista dei valori accettabili.
     * @param number valore da controllare
     * @return true se il valore è accettabile.
     */
    private boolean isAcceptableNumber(int number) {
        return number >= 0 && number <= 6;
    }

    /**
     * Viene restituito il colore memorizzato.
     * @return colore in codifica ansi.
     */
    public Ansi.Color getColor() {
        return color;
    }

    /**
     * Viene restituito il valore memorizzato.
     * @return valore memorizzato
     */
    public int getNumber() {
        return number;
    }
}
