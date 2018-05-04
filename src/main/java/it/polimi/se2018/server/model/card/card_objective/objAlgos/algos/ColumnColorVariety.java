package it.polimi.se2018.server.model.card.card_objective.objAlgos.algos;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.objAlgos.StrategyAlgorithm;

/*
    Algoritmo2:
    Nome: "Colori diversi - Colonna"
    Effetto: "Colonne senza colori ripetuti"
    Punti Favore: 5
*/

public class ColumnColorVariety extends VarietyAlgorithm implements StrategyAlgorithm{

    @Override
    public int use(Player player) throws Exception {

        int favours = 5;
        int[] contColor = new int[5];
        int cont=0;
        int temp=0;
        String tempColor;

        for (int i=0; i<5; i++) {
            for (int j=0; j<4; j++) {

                tempColor = player.getDiceColor(j,i);
                switch (tempColor){
                    case "Rosso":
                        contColor[0]++;
                        break;

                    case "Giallo":
                        contColor[1]++;
                        break;

                    case "Verde":
                        contColor[2]++;
                        break;

                    case "Viola":
                        contColor[3]++;
                        break;

                    case "Blu":
                        contColor[4]++;
                        break;
                }

                if(j==3){
                    int k=0;
                    while(k<5){
                        if(contColor[k]>1) {
                            setZero(contColor);
                            temp=0;
                            break;
                        }
                        else if(contColor[k]==1){
                            temp++;
                            k++;
                        }
                    }
                    if(temp==4) cont++;
                    setZero(contColor);
                    temp=0;
                }
            }
        }
        return cont*favours;
    }
}
