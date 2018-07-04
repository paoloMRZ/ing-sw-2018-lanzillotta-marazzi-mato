package it.polimi.se2018.server.model.dice_sachet;


import it.polimi.se2018.server.model.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe che si occupa della creazione/estrazione casuale dei dadi per il gioco.
 * L'estrazione avviene tenedo conto di quanti e che dadi sono già stati messi in gioco.
 * La classe permette il reinserimento di un dado nel sacchetto virtuale del gioco.
 * La classe è la classe factory del patter factory, utilizzato per la creazione dei dadi pensando ad una estensioine del
 * codice o modifica delle regole di estrazione o sul tipo dei dadi.
 * @author Kevin Mato
 */

public class FactoryDice {

    private ArrayList<Color> colors;
    private ArrayList<Integer> numbers;

    /**
     * Costruttore della classe che si prefigge di mettere a disposizione 18 dadi per ogni colore, correlando ogni colore
     * con il loro numero salvato in memoria. dentro delle liste.
     */
    public FactoryDice(){
        this.colors= new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.PURPLE);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);

        this.numbers= new ArrayList<>();
        numbers.add(18);
        numbers.add(18);
        numbers.add(18);
        numbers.add(18);
        numbers.add(18);
    }

    /**
     * Metodo che estrare un nuovo dado con un colore randomico in base alle disponibilità dei colori stessi.
     * @return reference di un dado senza numero.
     */
    public Dice createDice(){
        return new Dice(giveTheColor());
    }

    /**
     * Metodo di supporto per dirmi se il colore scelto è ancora pescabile, prima di assegnarlo ad un dado.
     * Se il colore è o diventa non più pescabile lo rimuove dalla lista dei colori.
     * @param picked indice nella lista dei colori del colore scelto.
     * @return estrazione del colore corretto in base al suo indice.
     */
    private Color isOkCol(int picked){
                    if(numbers.get(picked)>0){

                        numbers.set(picked,(numbers.get(picked)-1));
                        return colors.get(picked);

                    }
                    else {
                        colors.remove(picked);
                        numbers.remove(picked);
                        return giveTheColor();
                    }
    }

    /**
     * estrae un colore correto da assegnare al dado che verrà creato, estraendo prima l'indice del colore
     * randomicamente e poi verificando se quel colore vada bene.
     */
    private Color giveTheColor(){
        Random extractor= new Random();
        int pickedCol= extractor.nextInt(colors.size());
        return isOkCol(pickedCol);
    }

    /**
     * metodo che verrà usato nell'uso di una carta utensile
     * rimettendolo dentro significa che il relativo colore avrà un elemento in più, quindi si incrementa il suo numero,
     * oppure se non era più presente lo si reinserisce con numero 1.
     * @param d reference del dado da reinserire.
     */
    public void reput(Dice d){
        if(d!=null) {
            if(colors.contains(d.getColor())) {
                int picked = colors.indexOf(d.getColor());
                numbers.set(picked, (numbers.get(picked) + 1));
            }
            else{
                colors.add(d.getColor());
                numbers.add(1);
            }
        }
    }

}
