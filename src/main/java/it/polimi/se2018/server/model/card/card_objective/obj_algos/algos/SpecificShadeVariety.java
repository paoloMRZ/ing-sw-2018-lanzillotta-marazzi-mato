package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;


/*
    Algoritmo4:
    Nome: "Sfumature diverse - Colonna"
    Effetto: "Colonne senza sfumature ripetute"
    Punti Favore: 4


    Algoritmo3:
    Nome: "Sfumature diverse - Riga"
    Effetto: "Righe senza sfumature ripetute"
    Punti Favore: 5


    Il costruttore genera quindi l'algoritmo4 se maxRow > maxCol, altrimenti crea l'algoritmo3
*/

public class SpecificShadeVariety extends VarietyAlgorithm implements StrategyAlgorithm {

    private int maxRow = 0;
    private int maxCol = 0;

    //Costruttore
    public SpecificShadeVariety(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
    }


    @Override
    public int use(Player player) throws Exception {

        int[] contArray = new int[6];
        int cont = 0;
        int tempNumber;
        Dice die;

        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {

                if(maxRow>maxCol) die = player.showSelectedCell(j, i).showDice();
                else die = player.showSelectedCell(i, j).showDice();

                if (die != null) {
                    tempNumber = die.getNumber();
                    switch (tempNumber) {
                        case 1:
                            contArray[0]++;
                            break;
                        case 2:
                            contArray[1]++;
                            break;
                        case 3:
                            contArray[2]++;
                            break;
                        case 4:
                            contArray[3]++;
                            break;
                        case 5:
                            contArray[4]++;
                            break;
                        case 6:
                            contArray[5]++;
                            break;
                    }
                    cont = cont + checkArray(contArray,maxRow,maxCol,j);
                }
            }
        }
        if(maxRow>maxCol) return 4*cont;
        else return 5*cont;
    }
}
