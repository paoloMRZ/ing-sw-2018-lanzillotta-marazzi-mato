package it.polimi.se2018.server.model.dice_sachet;


import java.util.ArrayList;
import java.util.Random;

//TODO MODIFICARE UML NON HO USATO ARRAY Per comodità

public class FactoryDice {

    private ArrayList<String> colors;
    private ArrayList<Integer> numbers;

    public FactoryDice(){
        this.colors= new ArrayList<>();
        colors.add("Red");
        colors.add("Azure");
        colors.add("Purple");
        colors.add("Yellow");
        colors.add("Green");

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
    //metodo di supporto per dirmi se il colore scelto è ancora pescabile.
    //
    private String isOkCol(int picked){
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
    private String giveTheColor(){
        Random extractor= new Random();
        int pickedCol= extractor.nextInt(colors.size());
        return isOkCol(pickedCol);
    }

}
