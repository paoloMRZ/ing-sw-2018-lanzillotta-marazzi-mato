package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;

/*
    Algoritm10:
    Nome: "Varietà di Colore"
    Effetto: "Set di dadi di ogni colore ovunque"
    Punti Favore: 4
*/

public class ColorVariety extends VarietyAlgorithm implements StrategyAlgorithm {

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
        if (!hasZeroElement(contColor)) return favours * (sumArray(contColor) / 5);
        else return 0;
    }
}