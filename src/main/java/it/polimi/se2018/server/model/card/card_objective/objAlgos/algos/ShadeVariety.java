package it.polimi.se2018.server.model.card.card_objective.objAlgos.algos;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.objAlgos.StrategyAlgorithm;

/*
    Algoritmo8:
    Nome: "Sfumature Diverse"
    Effetto: "Set di dadi di ogni valore ovunque"
    Punti Favore: 5
*/

public class ShadeVariety extends VarietyAlgorithm implements StrategyAlgorithm{

    @Override
    public int use(Player player) throws Exception {
        int favours = 5;
        int[] contNumber=new int[6];
        int tempNumber=0;

        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){

                tempNumber = player.getDiceNumber(i,j);
                switch (tempNumber){

                    case 1: contNumber[0]++;
                        break;
                    case 2: contNumber[1]++;
                        break;
                    case 3: contNumber[2]++;
                        break;
                    case 4: contNumber[3]++;
                        break;
                    case 5: contNumber[4]++;
                        break;
                    case 6: contNumber[5]++;
                        break;
                    default: break;
                }
            }
        }

        if(!asZeroElement(contNumber)) return favours*(sumArray(contNumber)/6);
        else return 0;
    }
}

