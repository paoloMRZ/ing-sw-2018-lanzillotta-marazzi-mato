package it.polimi.se2018.client.cli.game.info;

import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;

/**
 * La classe descrive un dado e per farlo estende la classe "Info", infatti un dado è rappresentabile come una coppia
 * colore/valore ma con alcune restrizionni aggiuntive.
 *
 * Il colore bianco e il valore 0 solo se usati insieme.
 * La coppia (Bianco,0) non identifica un dado, ma viene usata per indicare che in una determinata posizione non è presente
 * nessun dado.
 *
 * @author Marazzi Paolo.
 */

public class DieInfo extends Info {


    /**
     * Costruttore della classe.
     * Si verifica la combinazione immassa sia accettabile.
     * @param color colore del dado.
     * @param number numero del dado.
     */
    public DieInfo(Ansi.Color color, int number){
        super(color,number);

        if(!isAcceptableCombiantion(color,number))
            throw new InvalidParameterException();
    }

    /**
     * Il metodo verifica che la coppia colore/valore sia accettabile.
     * @param color colore del dado.
     * @param number valore del dado.
     * @return true se la coppia è accettabile.
     */
    private boolean isAcceptableCombiantion(Ansi.Color color, int number){
        return !(color == Ansi.Color.WHITE && number != 0) && !(color != Ansi.Color.WHITE && number == 0);
    }
}
