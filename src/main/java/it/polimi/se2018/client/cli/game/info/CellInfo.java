package it.polimi.se2018.client.cli.game.info;

import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;

/**
 * La classe rappresenta una cella della classe finestra.
 * Estende la classe 'Info' perchè è rappresentabile come una coppia colore/valore, ma con alcune restrizione aggiuntive
 * sulle possibili combinazioni dei due parametri.
 *
 * Se una cella è colorata non può contenere un valore diverso da 0 ---> Cella con restrizione di colore.
 * Se una cella possiede un valore diverso da 0 il suo colore può essere solo bianco ---> Cella con restrizione di valore.
 * La combinzione (Binaco,0) indica una cella senza restrizioni.
 *
 * @author Marazzi Paolo
 */

public class CellInfo extends Info {

    /**
     * Costruttore della classe.
     * Viene verificata la validità della coppia (colore,valore) inserita.
     * @param color colore della cella.
     * @param number numero della cella.
     */
    public CellInfo(Ansi.Color color, int number){

        super(color, number);

        if(!isAcceptableCombination(color, number))
            throw new InvalidParameterException();
    }

    /**
     * Il metodo verifica se la combinazione (colore,valore) è vaida.
     * @param color colore in codifica ansi.
     * @param number sfumatura della cella.
     * @return true se la combinazione è valida.
     */
    private boolean isAcceptableCombination(Ansi.Color color, int number){
        return !(color != Ansi.Color.WHITE && number != 0);
    }
}
