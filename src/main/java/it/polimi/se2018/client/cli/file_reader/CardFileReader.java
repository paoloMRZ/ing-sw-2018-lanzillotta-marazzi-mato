package it.polimi.se2018.client.cli.file_reader;


import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidParameterException;

public class CardFileReader {

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
        input = new ObjectInputStream(classLoader.getResource(fileName + ".ser").openStream()); //TODO Quando verrà trasportato nel repo principale bisogneà aggiungere il path della cartella.

        return (SideCard) input.readObject();
    }

    public ObjectiveCard readObjectiveCard() throws IOException, ClassNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();
        input = new ObjectInputStream(classLoader.getResource(fileName + ".ser").openStream()); //TODO Quando verrà trasportato nel repo principale bisogneà aggiungere il path della cartella.

        return (ObjectiveCard) input.readObject();
    }

    public UtensilCard readUtensilCard() throws IOException, ClassNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();
        input = new ObjectInputStream(classLoader.getResource(fileName + ".ser").openStream()); //TODO Quando verrà trasportato nel repo principale bisogneà aggiungere il path della cartella.

        return (UtensilCard) input.readObject();
    }

    public void close() throws IOException {
        input.close();
    }
}
