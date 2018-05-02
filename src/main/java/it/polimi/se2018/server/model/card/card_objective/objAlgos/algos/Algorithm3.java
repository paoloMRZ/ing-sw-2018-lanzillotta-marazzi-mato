package it.polimi.se2018.server.model.card.card_objective.objAlgos.algos;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.objAlgos.StrategyAlgorithm;

/*
    Algoritmo3:
    Nome: "Sfumature diverse - Riga"
    Effetto: "Righe senza sfumature ripetute"
    Punti Favore: 5
*/

public class Algorithm3 implements StrategyAlgorithm{

    //Metodo di supporto: setta a 0 tutti gli elementi di un array
    public void setZero(int[] array){
        for(int i=0; i<array.length; i++) array[i]=0;
    }

    @Override
    public int use(Player player) throws Exception {

        int favours = 5;
        int[] contNumber = new int[6];
        int cont=0;
        int temp=0;
        int tempNumber;

        for (int i=0; i<4; i++) {
            for (int j=0; j<5; j++) {

                tempNumber = player.getDiceNumber(i,j);
                switch (tempNumber){
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
                if(j==4){
                    int k=0;
                    while(k<6){
                        if(contNumber[k]>1) {
                            setZero(contNumber);
                            temp=0;
                        break;
                    }

                    else if(contNumber[k]==1){
                        temp++;
                        k++;
                    }
                }
                if(temp==5) cont++;
                setZero(contNumber);
                temp=0;
            }
        }
    }
        return cont*favours;
    }
}
