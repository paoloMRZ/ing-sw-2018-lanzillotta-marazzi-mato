package it.polimi.se2018.server.model.card.card_objective.objAlgos.algos;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.objAlgos.StrategyAlgorithm;

/*
    Algoritm14:
    Nome: "Sfumature Blu - Privata"
    Effetto: "Somma dei valori su tutti i dadi blu"
    Punti Favore: #
*/

public class ShadesOfBlue implements StrategyAlgorithm {
    @Override
    public int use(Player player) throws Exception {
        int favours =0;
        String tempColor;
        int tempNumber;
        for (int i=0; i<4; i++) {
            for (int j=0; j<5; j++) {
                tempColor = player.getDiceColor(i,j);
                tempNumber = player.getDiceNumber(i,j);
                if(tempColor=="Blu") favours = favours+tempNumber;
            }
        }
        return favours;
    }
}
