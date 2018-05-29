package it.polimi.se2018.server.network.implementation;


import it.polimi.se2018.client.connection_handler.ClientInterface;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.fake_client.FakeClientRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * La classe implementa la gestione delle connessioni che avvengono sull'interfaccia remota esposta dal server.
 */
public class ServerImplementationRMI extends UnicastRemoteObject implements ServerInterface {

    private Lobby lobby;

    /**
     * Costruttore della classe.
     *
     * @param lobby sala d'attesa in cui vengono inseriti tutti i client che superano la verifica del nickname.
     * @throws RemoteException viene lanciata se il nickname passato è già utilizzato da un'altro client nella lobby.
     */
    public ServerImplementationRMI(Lobby lobby) throws RemoteException {
        super(0);

        if (lobby != null)
            this.lobby = lobby;
    }

    /**
     * Il metodo accetta l'interfaccia remota ed il nickname del client che si vuole aggiungere alla lobby, crea un fake client associato e tenta l'inserimento
     * nella stanza (lobby). Se il nickname non è valido viene sollevata un'eccezione.
     *
     * @param clientInterface interfaccia remota del client che vuole essere aggiunto alla lobby.
     * @param nickname        nickname del client che si vuole aggiungere alla lobby.
     * @throws RemoteException          eccezione di RMI.
     * @throws InvalidNicknameException viene sollevata se il nickname scelto non è disponibile.
     */
    @Override
    public void add(ClientInterface clientInterface, String nickname) throws RemoteException, InvalidNicknameException {

        System.out.println("\n[*]Ho ricevuto una nuova connessione da RMI.");
        FakeClientRMI fakeClient = new FakeClientRMI(clientInterface, lobby, nickname);
        try {
            lobby.add(fakeClient);
            System.out.println("\n[*]Ho aggiunto il client alla lobby\n");
        }catch (InvalidNicknameException e){
            System.out.println("[*]Connessione rifiutata");
            throw new InvalidNicknameException();
        }
    }
}