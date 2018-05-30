package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Rappresenta l'incapsulamento dell'algoritmo relativo alla carta "Sfumature Diverse" : "Set di dadi di ogni valore ovunque"
 * con Punti Favore 5
 *
 * @author Simone Lanzillotta
 */



public class ShadeVariety extends VarietyAlgorithm implements StrategyAlgorithm {


    /**
     * Metodo che implementa l'algoritmo associato alla carta Obbiettivo
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @return i segnalini guadagnati dal Player su cui si è applicata la carta Obbiettivo
     * @throws Exception viene sollevata in caso di Eccezione generica
     */

    @Override
    public int use(Player player) throws Exception {
        int favours = 5;
        int[] contNumber = new int[6];
        int tempNumber=0;

        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++) {

                if (player.showSelectedCell(i, j).showDice() != null) {
                    tempNumber = player.showSelectedCell(i,j).showDice().getNumber();
                    switch (tempNumber) {

                        case 1:
                            contNumber[0]++;
                            break;
                        case 2:
                            contNumber[1]++;
                            break;
                        case 3:
                            contNumber[2]++;
                            break;
                        case 4:
                            contNumber[3]++;
                            break;
                        case 5:
                            contNumber[4]++;
                            break;
                        case 6:
                            contNumber[5]++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        if(!hasZeroElement(contNumber)) return favours*minValues(contNumber);
        else return 0;
    }
}

