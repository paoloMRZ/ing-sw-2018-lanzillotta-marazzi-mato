package it.polimi.se2018.client.cli.file_reader;


import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidParameterException;

public class CardFileReader {

    private static final String SIDE_CARD_PATH = "cli/card_schema/";
    private static final String OBJECTIVE_CARD_PATH = "cli/card_objective/";
    private static final String UTENSIL_CARD_PATH = "cli/card_utensil/";

    private ObjectInputStream input;
    private String fileName;

    public CardFileReader(String fileName){

            if(fileName != null){
                this.fileName = fileName;
            }else
                throw new InvalidParameterException();
    }

    public SideCard readSideCard() throws IOException, ClassNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();
        input = new ObjectInputStream(classLoader.getResourceAsStream( SIDE_CARD_PATH + fileName + ".ser"));

        return (SideCard) input.readObject();
    }

    public ObjectiveCard readObjectiveCard() throws IOException, ClassNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();
        input = new ObjectInputStream(classLoader.getResourceAsStream(OBJECTIVE_CARD_PATH + fileName + ".ser"));

        return (ObjectiveCard) input.readObject();
    }

    public UtensilCard readUtensilCard() throws IOException, ClassNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();
        input = new ObjectInputStream(classLoader.getResourceAsStream( UTENSIL_CARD_PATH + fileName + ".ser"));

        return (UtensilCard) input.readObject();
    }

    public void close() throws IOException {
        input.close();
    }
}
