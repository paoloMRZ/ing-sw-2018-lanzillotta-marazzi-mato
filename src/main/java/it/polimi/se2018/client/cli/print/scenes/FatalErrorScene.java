package it.polimi.se2018.client.cli.print.scenes;

import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

public class FatalErrorScene {

    private Exception e;

    public FatalErrorScene(Exception e){
        if(e != null)
            this.e = e;
        else
            throw new InvalidParameterException();
    }

    public void printScene(){

        System.out.print(ansi().eraseScreen());

        System.out.print(ansi().cursor(1,1).a("Si è verificato un errore di sistema! Verificare l'integrità dell'archivio JAR.\n\n"));

        System.out.print(ansi().fg(Ansi.Color.RED).a(e.toString()).fgDefault());
    }
}
