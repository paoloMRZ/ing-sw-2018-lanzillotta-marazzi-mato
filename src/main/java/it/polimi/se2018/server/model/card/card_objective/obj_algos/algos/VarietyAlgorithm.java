package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

 /*
    Questa classe rappresenta  una sorta di raccolta per tutti i metodi comuni agli algoritmi cosiddetti Variety.
    Gli algoritmi dovranno estenderla e quindi sarà proprio a questi demandato il contratto di implementare il
    metodo use.

 */


public class VarietyAlgorithm {

    //Metodo di supporto: setta a 0 tutti gli elementi di un array
    public void setZero(int[] array) {
        for (int i = 0; i < array.length; i++) array[i] = 0;
    }


    //Metodo di supporto: somma tutti gli elementi di un array
    public int sumArray(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum = sum + array[i];
        }
        return sum;
    }


    //Metodo di supporto: verifica che nell'array non ci siano elementi nulli
    public boolean hasZeroElement(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) return true;
        }
        return false;
    }


    //Metodo di supporto che scandisce l'array tramite un foreach e ritorna il numero di valori diversi all'interno della riga/colonna
    public int parsingArray(int[] array){
        int temp = 0;
        for (int elem: array){
            if (elem>1) {
                setZero(array);
                break;
            } else if (elem==1) temp++;
        }
        return temp;
    }


    //Metodo di supporto che attraversa l'array e, a seconda della tipologia di ricerca (se per riga o se per colonna), ritorna il
    //moltiplicando dei punti corrispondenti
    public int checkArray(int[] array, int maxRow, int maxCol, int currentCol) {
        int check = 0;
        if (maxRow > maxCol && currentCol == 3 && parsingArray(array)==4) {
                setZero(array);
                check++;
        }

        else if (maxRow < maxCol && currentCol == 4 && parsingArray(array)==5) {
                setZero(array);
                check++;
        }
        return check;
    }

}
