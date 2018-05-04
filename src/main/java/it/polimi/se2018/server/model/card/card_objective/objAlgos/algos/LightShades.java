package it.polimi.se2018.server.model.card.card_objective.objAlgos.algos;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.objAlgos.StrategyAlgorithm;

/*
    Algoritmo5:
    Nome: "Sfumature Chiare"
    Effetto: "Set di 1 & 2 ovunque"
    Punti Favore: 2
*/

public class LightShades implements StrategyAlgorithm{

    @Override
    public int use(Player player) throws Exception {
        int favours = 2;
        int contOne=0;
        int contTwo=0;
        int tempNumber=0;

        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){

                tempNumber = player.getDiceNumber(i,j);
                switch (tempNumber){

                    case 1: contOne++;
                        break;
                    case 2: contTwo++;
                        break;

                    default: break;
                }
            }
        }

        if(contOne>contTwo && contTwo!=0) return favours*contTwo;
        else if(contTwo>contOne && contOne!=0) return favours*contOne;
        else if(contOne==contTwo) return favours*contOne;
        else return 0;
    }
}
