package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;


/**
 * Rappresenta l'incapsulamento dell'algoritmo relativo alle carte della tipologia ShadesOf che, a seconda del colore in ingresso,
 * istanzia una delle seguenti tipologia di carta:
 *
 * "Sfumature Rosse - Privata" : "Somma dei valori su tutti i dadi rossi" ,  Punti Favore #
 * "Sfumature Gialle - Privata" : "Somma dei valori su tutti i dadi gialli",  Punti Favore #
 * "Sfumature Blu - Privata" : "Somma dei valori su tutti i dadi blu",  Punti Favore #
 * "Sfumature Verdi - Privata" : "Somma dei valori su tutti i dadi verdi",  Punti Favore #
 * "Sfumature Viola - Privata" : "Somma dei valori su tutti i dadi viola",  Punti Favore #
 *
 * @author Simone Lanzillotta
 */



public class ShadesOfCard implements StrategyAlgorithm{

    private Color color;

    /**
     * Costruttore della classe che, a seconda del valore in ingresso (colore), istanzia una tra le cinque tipologie
     * di carte sopra citate.
     *
     * @param color colore in ingresso su cui si basa il calcolo dei punti favore
     */

    public ShadesOfCard(Color color){
            this.color = color;
        }


    /**
     * Metodo che implementa l'algoritmo associato alla carta Obbiettivo
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @return i segnalini guadagnati dal Player su cui si è applicata la carta Obbiettivo
     * @throws Exception viene sollevata in caso di Eccezione generica
     */

    @Override
    public int use(Player player) throws Exception {
        int favours =0;
        Color tempColor;
        int tempNumber;
        for (int i=0; i<4; i++) {
            for (int j=0; j<5; j++) {
                Dice die = player.showSelectedCell(i,j).showDice();
                if (die!= null) {
                    tempColor = die.getColor();
                    tempNumber = die.getNumber();
                    if (tempColor ==  color) favours = favours + tempNumber;
                }
            }
        }
        return favours;
    }
}
