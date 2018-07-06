package it.polimi.se2018.client.cli.controller.states;

import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe implementa lo stato di disconnessione.
 * L'oggetto Cli si trova in questo stato quando riceve un messaggio che notifica la sua disconnessione dal server.
 *
 * @author Marazzi Paolo
 */
public class DisconnectedState implements StateInterface{


    private static final String DEFAULT_MESSAGE = "NONE";
    private static final String TITLE = "Sei stato disconnesso!";

    /**
     * Costruttore della classe.
     */
    public DisconnectedState(){

        AnsiConsole.out.print(ansi().eraseScreen().cursor(3,5).bold().a(TITLE).boldOff());
    }

    /**
     * Viene ignorato qualsiasi input dell'utente.
     * @param request input dell'utente.
     * @return messaggio di default.
     */
    @Override
    public String handleInput(int request) {
        AnsiConsole.out.print(ansi().eraseScreen().cursor(3,5).bold().a(TITLE).boldOff());
        return DEFAULT_MESSAGE;
    }

    /**
     * Viene ignorato qualsiasi messaggio inviato dal server.
     * @param request messaggio inviato dal server.
     */
    @Override
    public void handleNetwork(String request) {
        //Non è possibile che arrivano messaggi dalla rete.
    }
}
