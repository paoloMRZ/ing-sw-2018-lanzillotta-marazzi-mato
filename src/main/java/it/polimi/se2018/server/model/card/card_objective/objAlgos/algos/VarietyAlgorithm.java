package it.polimi.se2018.server.model.card.card_objective.objAlgos.algos;

 /*
    Questa classe rappresenta  una sorta di raccolta per tutti i metodi comuni agli algoritmi cosiddetti Variety.
    Gli algoritmi dovranno estenderla e quindi sarà proprio a questi demandato il contratto di implementare il
    metodo use.

 */

public class VarietyAlgorithm{

    //Metodo di supporto: setta a 0 tutti gli elementi di un array
    public void setZero(int[] array){
        for(int i=0; i<array.length; i++) array[i]=0;
    }

    //Metodo di supporto: somma tutti gli elementi di un array
    public int sumArray(int[] array){
        int sum=0;
        for(int i=0; i<array.length; i++) {
            sum = sum+array[i];
        }
        return sum;
    }

    //Metodo di supporto: verifica che nell'array non ci siano elementi nulli
    public boolean asZeroElement(int[] array){
        for(int i=0; i<array.length; i++) {
            if(array[i]==0) return true;
        }
        return false;
    }

}
