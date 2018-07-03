package it.polimi.se2018.client.connection_handler;


import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.message.ClientMessageParser;
import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * La classe ha il compito di gestire la connessione tra client e server utilizzando la tecnologia socket.
 * @author  Marazzi Paolo
 */
public class ConnectionHandlerSocket extends  ConnectionHandler implements Runnable {

    private Socket socket;
    private OutputStreamWriter out;
    private BufferedReader reader;


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
    public ConnectionHandlerSocket(String nickname, ConnectionHandlerObserver view, String host, int port) throws InvalidNicknameException, GameStartedException, IOException {

        super(view, nickname);

        String tmp;

        //Mi collego al server.
        socket = new Socket(host, port);
        out = new OutputStreamWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Gli mando il nickname che l'utente vuole utilizzare.
        out.write(nickname.concat("\n"));
        out.flush();


        //Attendo la convalida del nickname.
        tmp = reader.readLine();

        if (ClientMessageParser.isErrorConnectionInvalidNicknameMessage(tmp))
            throw new InvalidNicknameException(); //Nickname non valido.
        else if (ClientMessageParser.isErrorConnectionGameStartedMessage(tmp))
            throw new GameStartedException(); //Partita già iniziata.
        else if (ClientMessageParser.isNewConnectionMessage(tmp)){
            super.notifica(tmp); //Nickname valido.
            (new Thread(this)).start();
        }

    }

    /**
     * Questo metodo manda un messaggio al server tramite la socket.
     * @param message messaggio da inviare al server.
     */
    @Override
    public void sendToServer(String message) {
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            super.notifica(ClientMessageCreator.getServerDisconnectMessage(getNickname()));
        }
    }

    /**
     * Loop di lettura della socket. Appena arriva un messaggio dal server lo notifyFromFakeView alla view tramite il metodo contenuto nel padre "ConnectionHandler".
     * Se riceve un messaggio di chiusura della connessione oltre a notificarlo alla view interrompe il loop di lettura.
     */
    @Override
    public void run() {

        boolean isOpen = true;

        while (isOpen) {
            try {
                String tmp;

                tmp = reader.readLine();

                if (tmp != null)
                    super.notifica(tmp);

            } catch (IOException e) {
                isOpen = false;
            }
        }

        super.notifica(ClientMessageCreator.getServerDisconnectMessage(getNickname()));
    }
}
