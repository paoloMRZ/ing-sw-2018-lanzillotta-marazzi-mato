package it.polimi.se2018.client.connection_handler;

import it.polimi.se2018.server.exceptions.InvalidNicknameException;

import java.io.*;
import java.net.Socket;

/**
 * La classe ha il compito di gestire la connessione tra client e server utilizzando la tecnologia socket.
 * @author  Marazzi Paolo
 */
public class ConnectionHandlerSocket extends  ConnectionHandler implements Runnable {

    private Socket socket;

    /**
     * Costruttore della classe.
     * Oltre a costruire la classe il metodo si collega al server tramite una connessione socket passando il nickname scelto dall'utente.
     * Il server rifuta la connessione si il nickanme scelto è già utilizzato da un'altro utente, in questo caso
     * manda al cliet un messaggio di "chiusura della connessione" che a sua volta solleva un'eccezione InvalidNicknameException.
     *
     * Invece se il nickaname non è già utilizzato al creazione dell'oggetto viene completata e il client risulta connesso al server.
     * @param nickname nickname con cui collegarsi al server.
     * @throws InvalidNicknameException viene sollevata quando il nickanme scelto è già utilizzato da un'altro client connesso al server.
     */
    public ConnectionHandlerSocket(String nickname) throws InvalidNicknameException {

        super();

        try {
            //Mi collego al server.
            socket = new Socket("localhost", 1234);
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Gli mando il nickname che l'utente vuole utilizzare.
            out.write(nickname.concat("\n"));
            out.flush();

            //Attendo la convalida del nickname.
            if(reader.readLine().equals("NO"))
                throw new InvalidNicknameException(); //Nickname non valido.
            else
                System.out.println("[*]Connessione avvenuta."); //Nickname valido.
        } catch (IOException e) {
            System.out.println("[*]ERRORE nel collegamento al server!");
        }
    }

    /**
     * Questo metodo manda un messaggio al server tramite la socket.
     * @param message messaggio da inviare al server.
     */
    @Override
    public void sendToServer(String message) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            out.write(message);
            out.flush();
            System.out.println("[*]Messaggio inviato");
        } catch (IOException e) {
            System.out.println("[*]ERRORE nell'estrazione dello stream dalla socket!");
        }
    }

    /**
     * Loop di lettura della socket. Appena arriva un messaggio dal server lo notifica alla view tramite il metodo contenuto nel padre "ConnectionHandler".
     * Se riceve un messaggio di chiusura della connessione oltre a notificarlo alla view interrompe il loop di lettura.
     */
    @Override
    public void run() {

        boolean isOpen = true;

        System.out.println("[*]Avvio il loop di lettura che attende i messaggi dal server.");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tmp;

            while(isOpen){
                tmp = reader.readLine();
                if(tmp.equals("bye"))
                    isOpen = false;
                super.notifica(tmp);
            }

            System.out.println("[*]Connessione chiusa!\n");

        } catch (IOException e) {
            System.out.println("[*]ERRORE nell'estrazione dello stream dalla socket!");
        }
    }
}
