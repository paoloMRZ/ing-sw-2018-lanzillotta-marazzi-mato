package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;

/*
    Algoritmo4:
    Nome: "Sfumature diverse - Colonna"
    Effetto: "Colonne senza sfumature ripetute"
    Punti Favore: 4
*/

public class ColumnShadeVariety extends VarietyAlgorithm implements StrategyAlgorithm{

    @Override
    public int use(Player player) throws Exception {

        int favours = 4;
        int[] contNumber = new int[6];
        int cont=0;
        int temp=0;
        int tempNumber;

        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {

                if (player.showSelectedCell(i, j).showDice() != null) {
                    tempNumber = player.getDiceNumber(j, i);
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

                    }

                    if (j == 3) {
                        int k = 0;
                        while (k < 6) {
                            if (contNumber[k] > 1) {
                                setZero(contNumber);
                                temp = 0;
                                break;
                            } else if (contNumber[k] == 1) {
                                temp++;
                                k++;
                            }
                        }
                        if (temp == 4) cont++;
                        setZero(contNumber);
                        temp = 0;
                    }
                }
            }
        }
        return cont*favours;
    }
}
