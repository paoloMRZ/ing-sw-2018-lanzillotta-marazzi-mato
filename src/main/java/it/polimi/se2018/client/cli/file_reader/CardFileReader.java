package it.polimi.se2018.client.cli.file_reader;


import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidParameterException;

/**
 * La classe contiene i metodi per deserializzare le che descrivono le carte di gioco.
 *
 * @author Marazzi Paolo
 */

public class CardFileReader {

    private static final String SIDE_CARD_PATH = "cli/card_schema/";
    private static final String OBJECTIVE_CARD_PATH = "cli/card_objective/";
    private static final String UTENSIL_CARD_PATH = "cli/card_utensil/";

    private ObjectInputStream input;
    private String fileName;

    /**
     * Costruttore della classe.
     * Viene salvato il nome della carta che si vuole deserializzare.
     *
     * @param fileName nome della carta.
     */
    public CardFileReader(String fileName){

            if(fileName != null){
                this.fileName = fileName;
            }else
                throw new InvalidParameterException();
    }

    /**
     * Il metodo deserializza la carta finestra il cui nome è stato indicato nel costruttore della classe.
     * @return carta finestra deserializzata.
     * @throws IOException sollevata in caso di errore di lettura da file.
     * @throws ClassNotFoundException sollevata se la classe deserializzata non è presente nel jar.
     */
    public SideCard readSideCard() throws IOException, ClassNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();
        input = new ObjectInputStream(classLoader.getResourceAsStream( SIDE_CARD_PATH + fileName + ".ser"));

        return (SideCard) input.readObject();
    }


    /**
     * Il metodo deserializza la carta obiettivo il cui nome è stato indicato nel costruttore della classe.
     * @return carta obiettivo deserializzata.
     * @throws IOException sollevata in caso di errore di lettura da file.
     * @throws ClassNotFoundException sollevata se la classe deserializzata non è presente nel jar.
     */
    public ObjectiveCard readObjectiveCard() throws IOException, ClassNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();
        input = new ObjectInputStream(classLoader.getResourceAsStream(OBJECTIVE_CARD_PATH + fileName + ".ser"));

        return (ObjectiveCard) input.readObject();
    }

    /**
     * Il metodo deserializza la carta utensile il cui nome è stato indicato nel costruttore della classe.
     * @return carta utensile deserializzata.
     * @throws IOException sollevata in caso di errore di lettura da file.
     * @throws ClassNotFoundException sollevata se la classe deserializzata non è presente nel jar.
     */
    public UtensilCard readUtensilCard() throws IOException, ClassNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();
        input = new ObjectInputStream(classLoader.getResourceAsStream( UTENSIL_CARD_PATH + fileName + ".ser"));

        return (UtensilCard) input.readObject();
    }

    /**
     * Il metodo chiude lo stream di lettura.
     * @throws IOException viene sollevata se si riscontrano problemi di chiusura dello stream.
     */
    public void close() throws IOException {
        input.close();
    }
}
