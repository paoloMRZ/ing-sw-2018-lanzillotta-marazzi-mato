package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;


/**
 * Rappresenta l'incapsulamento dell'algoritmo relativo alla carta "Varietà di Colore" : "Set di dadi di ogni colore ovunque"
 * con Punti Favore 4
 *
 * @author Simone Lanzillotta
 */



public class ColorVariety extends VarietyAlgorithm implements StrategyAlgorithm {

    /**
     * Metodo che implementa l'algoritmo associato alla carta Obbiettivo
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @return i segnalini guadagnati dal Player su cui si è applicata la carta Obbiettivo
     * @throws Exception viene sollevata in caso di Eccezione generica
     */


    @Override
    public int use(Player player) throws Exception {
        int favours = 4;
        int[] contColor = new int[5];
        Color tempColor;

        for (int i=0; i<4; i++) {
            for (int j=0; j<5; j++) {
                Dice die = player.showSelectedCell(i,j).showDice();
                if(die != null) {
                    tempColor = die.getColor();
                    switch (tempColor) {
                        case RED:
                            contColor[0]++;
                            break;
                        case YELLOW:
                            contColor[1]++;
                            break;
                        case GREEN:
                            contColor[2]++;
                            break;
                        case PURPLE:
                            contColor[3]++;
                            break;
                        case BLUE:
                            contColor[4]++;
                            break;
                    }
                }
            }
        }
        if (!hasZeroElement(contColor)) return favours*minValues(contColor);
        else return 0;
    }
}