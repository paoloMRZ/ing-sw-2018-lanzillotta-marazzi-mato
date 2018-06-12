package it.polimi.se2018.server.network.implementation;


import it.polimi.se2018.client.connection_handler.ClientInterface;
import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.fake_client.FakeClientRMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * La classe implementa la gestione delle connessioni che avvengono sull'interfaccia remota esposta dal server.
 */
public class ServerImplementationRMI extends UnicastRemoteObject implements ServerInterface {

    private static final int PORT = 1099;
    private Lobby lobby;

    /**
     * Costruttore della classe.
     *
     * @param lobby sala d'attesa in cui vengono inseriti tutti i client che superano la verifica del nickname.
     * @throws RemoteException viene lanciata se il nickname passato è già utilizzato da un'altro client nella lobby.
     */
    public ServerImplementationRMI(Lobby lobby) throws RemoteException, MalformedURLException {
        super(0);

        LocateRegistry.createRegistry(PORT);
        Naming.rebind("//localhost/MyServer", this);

        if (lobby != null) {
            this.lobby = lobby;
        }
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
    public void add(ClientInterface clientInterface, String nickname) throws RemoteException, InvalidNicknameException, GameStartedException {

        FakeClientRMI fakeClient = new FakeClientRMI(clientInterface, lobby, nickname);

        lobby.add(fakeClient);
    }

    public void close() throws RemoteException, NotBoundException, MalformedURLException {
        Naming.unbind("//localhost/MyServer");
    }
}