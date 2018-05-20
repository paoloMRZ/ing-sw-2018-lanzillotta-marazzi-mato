package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

/**
 * La classe VarietyAlgorithm rappresenta una sorta di " raccoglitore " di metodi utilizzati da buona parte delle classi contenute nel
 * package /algos
 *
 * @author Simone Lanzillotta
 *
 */


public class VarietyAlgorithm {

    /**
     * Metodo di supporto che azzera tutti gli elementi di un Array
     *
     * @param array collezione di interi che si intende azzerare
     */

    public void setZero(int[] array) {
        for (int i = 0; i < array.length; i++) array[i] = 0;
    }


    /**
     * Metodo di supporto che somma tutti gli elementi di un array
     *
     * @param array collezione di interi di cui si vuole calcolare la sommma
     * @return somma degli elementi della collezione in ingresso
     */

    public int sumArray(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum = sum + array[i];
        }
        return sum;
    }


    /**
     * Metodo di supporto che verifica che nella collezione in ingresso non ci siano elementi nulli
     *
     * @param array collezione di interi di cui si vuole controllare gli elementi
     * @return TRUE se è presente un riferimento a null, altrimenti FALSE
     */

    public boolean hasZeroElement(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) return true;
        }
        return false;
    }


    /**
     * Metodo di supporto che scandisce la collezione tramite un foreach per ricercare elementi uguali a 1
     *
     * @param array collezione di interi di cui si vuole controllare gli elementi
     * @return 0 se vi è almeno un elemento maggiore di 1, altrimenti ritorna il numero di elementi uguali a 1
     */

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


    /**
     * Metodo di supporto che attraversa la collezione e restituisce il moltiplicando che verrà utilizzato per moltiplicare il numero di segnalini
     * associati alla carta Obbiettivo ogni volta che l'obbiettivo è stato realizzato
     *
     * @param array collezione di supporto che riporta i riferimenti utilizzati negli algoritmi precedenti
     * @param maxRow limite massimo delle righe della carta Side
     * @param maxCol limite massimo delle colonne della carta Side
     * @param currentCol numero della colonna corrente della carta Side
     * @return il moltiplicando per il calcolo del numero di segnalini favore guadagnati per il raggiungimento dello specifico obbiettivo
     */

    public int checkArray(int[] array, int maxRow, int maxCol, int currentCol) {
        int check = 0;
        if (maxRow > maxCol && currentCol == 3 && parsingArray(array) == 4 || maxRow < maxCol && currentCol == 4 && parsingArray(array) == 5) {
            setZero(array);
            check++;
        }
        return check;
    }

}
