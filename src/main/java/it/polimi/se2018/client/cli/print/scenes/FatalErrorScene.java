package it.polimi.se2018.client.cli.print.scenes;

import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe stampa la schermata da visualizzare quando viene sollevata un'eccezione che blocca il corretto funzionamento del gioco.
 */

public class FatalErrorScene {

    private Exception e;

    /**
     * Costruttore della classe.
     *
     * @param e eccezione che ha impedito il corretto svolgimento.
     */
    public FatalErrorScene(Exception e){
        if(e != null)
            this.e = e;
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo stampa la schermata che mostra il messaggio di errore e l'evento che l'ha causato.
     */
    public void printScene(){

        System.out.print(ansi().eraseScreen());

        System.out.print(ansi().cursor(1,1).a("Si è verificato un errore di sistema! Verificare l'integrità dell'archivio JAR.\n\n"));

        System.out.print(ansi().fg(Ansi.Color.RED).a(e.toString()).fgDefault());
    }
}
