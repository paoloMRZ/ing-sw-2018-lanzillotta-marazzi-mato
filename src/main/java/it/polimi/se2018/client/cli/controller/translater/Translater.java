package it.polimi.se2018.client.cli.controller.translater;

import it.polimi.se2018.client.cli.file_reader.CardFileReader;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe traduce informazioni sotto forma di testo nelle corrispondenti informazioni "stampabili a schermo".
 *
 * @author Marazzi Paolo
 */

public class Translater {

    private Translater(){}

    /**
     * Restituisce il colore in codifica ansi associato al colore passato come stringa.
     * @param textColor colore da tradurre
     * @return colore in codifica ansi.
     */
    public static Ansi.Color getColorFromText(String textColor){

        switch (textColor){ //Faccio il parsing del colore.

            case "white": return Ansi.Color.WHITE;

            case "green": return Ansi.Color.GREEN;

            case "blue": return  Ansi.Color.BLUE;

            case "red": return Ansi.Color.RED;

            case "yellow": return Ansi.Color.YELLOW;

            case "purple": return Ansi.Color.MAGENTA;

            default: return Ansi.Color.WHITE;
        }
    }

    /**
     * Il metodo traduce una lista di dadi descritti come stringa in una lista di dadi in codifica ansi e numero.
     * @param infoFromMessage stringhe da tradurre.
     * @param acceptWhite indica se il colore bianco è accettabile o se deve generare una lista vuota.
     * @return lista di dadi (ansi + numero)
     */
    public static List<DieInfo> fromMessageToDieInfo(List<String> infoFromMessage, boolean acceptWhite){
        ArrayList<DieInfo> diceList = new ArrayList<>();

        Ansi.Color dieColor;
        int dieNumber;

        String messageColor;
        String messageNumber;

        for(String info : infoFromMessage){

            messageColor = info.toLowerCase().substring(0,info.length()-1); //estrapolo il colore dal messaggio.

            dieColor = getColorFromText(messageColor); //Recupero il colore in codifica ansi.

            messageNumber = info.substring(info.length()-1, info.length()); //Estraggo il numero dalla stringa.
            dieNumber = Integer.parseInt(messageNumber);

            if(dieColor == Ansi.Color.WHITE && !acceptWhite) { //Se trovo bianco ed il booleano mi indica che non è accettabile creo una lista vuota e mi fermo.
                diceList = new ArrayList<>(); //Creo lista vuota.
                break;
            }

            diceList.add(new DieInfo(dieColor, dieNumber )); //Aggiungo il dado appena estratto alla lista.

        }

        return diceList;
    }

    /**
     * Il metodo restituisce una lista di oggetti che descrivono le carte finestra.
     * @param cardNames nomi delle carte di cui si vogliono ottenere le informazioni
     * @return lista delle carte.
     * @throws IOException sollevata in caso di errore di lettura da file.
     * @throws ClassNotFoundException sollevata se la classe deserializzata non è presente nel jar.
     */
    public static List<SideCard> getSideCardFromName(List<String> cardNames) throws IOException, ClassNotFoundException {
        ArrayList<SideCard> cards = new ArrayList<>();

        for(String name : cardNames){
            cards.add(getSideCardFromName(name));
        }

        return  cards;
    }

    /**
     * Il metodo restituisce l'oggetto che descrive la carta finestra idicata dal nome.
     * @param cardName nome della carta di cui si vogliono ottenere le informzioni.
     * @return oggetto che descrive una carta finestra.
     * @throws IOException sollevata in caso di errore di lettura da file.
     * @throws ClassNotFoundException sollevata se la classe deserializzata non è presente nel jar.
     */
    public static SideCard getSideCardFromName(String cardName) throws IOException, ClassNotFoundException {

        SideCard card;
        CardFileReader reader;

        reader = new CardFileReader(cardName);
        card = reader.readSideCard();
        reader.close();


        return card;
    }

    /**
     * Il metodo restituisce la drescrizione delle carte assoviate ai nomi passati tramite il parametro del metodo.
     * @param cardNames nomi delle carte per cui si vogliono ottenere informazioni.
     * @return lista composta dall'oggetto descrittore di una carta.
     * @throws IOException sollevata in caso di errore di lettura da file.
     * @throws ClassNotFoundException sollevata se la classe deserializzata non è presente nel jar.
     */

    public static List<ObjectiveCard> getObjectiveCardFromName(List<String> cardNames) throws IOException, ClassNotFoundException {

        ArrayList<ObjectiveCard> cards = new ArrayList<>();

        for(String name : cardNames){
            cards.add(getObjectiveCardFromName(name));
        }

        return  cards;
    }

    /**
     * Il metodo restituisce la descrizione della carta obiettivo sulla base del nome.
     * @param cardName nome della carta di cui si vuole ottenere una descrizione.
     * @return oggetto che contiene la descrizione di una carta obiettivo.
     * @throws IOException sollevata in caso di errore di lettura da file.
     * @throws ClassNotFoundException sollevata se la classe deserializzata non è presente nel jar.
     */
    public static ObjectiveCard getObjectiveCardFromName(String cardName)  throws IOException, ClassNotFoundException {

        ObjectiveCard card;
        CardFileReader reader;

        reader = new CardFileReader(cardName);
        card = reader.readObjectiveCard();
        reader.close();


        return card;
    }


    /**
     * Il metodo restituisce le informazioni delle carte utensili specificate.
     * @param cardNames lista dei nomi delle carte di cui si vuole ottenre una descrizione.
     * @return lista di oggetti che descrivono una carta utensile.
     * @throws IOException sollevata in caso di errore di lettura da file.
     * @throws ClassNotFoundException sollevata se la classe deserializzata non è presente nel jar.
     */
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
