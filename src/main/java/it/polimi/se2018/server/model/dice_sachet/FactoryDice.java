package it.polimi.se2018.server.model.dice_sachet;


import it.polimi.se2018.server.model.Color;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.Random;

//TODO MODIFICARE UML NON HO USATO ARRAY Per comodità

public class FactoryDice {

    private ArrayList<Color> colors;
    private ArrayList<Integer> numbers;

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

    public Dice createDice(){
        return new Dice(giveTheColor());
    }

    //Metodo di supporto per dirmi se il colore scelto è ancora pescabile.
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

    //estrae un colore correto da assegnare al dado che verrà creato
    private Color giveTheColor(){
        Random extractor= new Random();
        int pickedCol= extractor.nextInt(colors.size());
        return isOkCol(pickedCol);
    }


    //metodo che verrà usato nell'uso di una carta utensile
    //rimettendolo dentro significa che il relativo colore avrà un elemnto in più
    public void reput(Dice D){
        int picked= colors.indexOf(D.getColor());
        numbers.set(picked,(numbers.get(picked)+1));
    }

}
