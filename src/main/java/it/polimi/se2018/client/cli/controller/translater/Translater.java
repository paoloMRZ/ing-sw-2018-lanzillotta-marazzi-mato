package it.polimi.se2018.client.cli.controller.translater;

import it.polimi.se2018.client.cli.file_reader.CardFileReader;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Translater {

    private Translater(){}

    public static List<DieInfo> fromMessageToDieInfo(List<String> infoFromMessage){
        ArrayList<DieInfo> diceList = new ArrayList<>();

        Ansi.Color dieColor;
        int dieNumber;

        String messageColor;
        String messageNumber;

        for(String info : infoFromMessage){

            messageColor = info.toLowerCase().substring(0,info.length()-1);

            switch (messageColor){ //Faccio il parsing del colore.

                case "white": dieColor = Ansi.Color.WHITE; break;

                case "green": dieColor = Ansi.Color.GREEN; break;

                case "blue": dieColor = Ansi.Color.BLUE; break;

                case "red": dieColor = Ansi.Color.RED; break;

                case "yellow": dieColor = Ansi.Color.YELLOW; break;

                case "purple": dieColor = Ansi.Color.MAGENTA; break;

                default: dieColor = Ansi.Color.WHITE;
            }

            messageNumber = info.substring(info.length()-1, info.length()); //Estraggo il numero dalla stringa.
            dieNumber = Integer.parseInt(messageNumber);

            diceList.add(new DieInfo(dieColor, dieNumber )); //Aggiungo il dado appena estratto alla lista.

        }

        return diceList;
    }

    public static List<SideCard> getSideCardFromName(List<String> cardNames) throws IOException, ClassNotFoundException {
        ArrayList<SideCard> cards = new ArrayList<>();

        CardFileReader reader;

        for(String name : cardNames){
            reader = new CardFileReader(name);
            cards.add(reader.readSideCard());
            reader.close();
        }

        return  cards;
    }

    public static SideCard getSideCardFromName(String cardName) throws IOException, ClassNotFoundException {

        SideCard card;
        CardFileReader reader;
        
        reader = new CardFileReader(cardName);
        card = reader.readSideCard();
        reader.close();


        return card;
    }

    public static List<ObjectiveCard> getObjectiveCardFromName(List<String> cardNames) throws IOException, ClassNotFoundException {

        ArrayList<ObjectiveCard> cards = new ArrayList<>();

        CardFileReader reader;

        for(String name : cardNames){
            reader = new CardFileReader(name);
            cards.add(reader.readObjectiveCard());
            reader.close();
        }

        return  cards;
    }

    public static ObjectiveCard getObjectiveCardFromName(String cardName)  throws IOException, ClassNotFoundException {

        ObjectiveCard card;
        CardFileReader reader;

        reader = new CardFileReader(cardName);
        card = reader.readObjectiveCard();
        reader.close();


        return card;
    }


    public static List<UtensilCard> getUtensilCardFromName(List<String> cardNames) throws IOException, ClassNotFoundException {

        ArrayList<UtensilCard> cards = new ArrayList<>();

        CardFileReader reader;

        for(String name : cardNames){
            reader = new CardFileReader(name);
            cards.add(reader.readUtensilCard());
            reader.close();
        }

        return  cards;
    }
}
