package it.polimi.se2018.server.network.implementation;


import it.polimi.se2018.client.connection_handler.ClientInterface;
import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia remota esposta dal server per permettere ai client di aggiungersi alla lobby tramite tramite RMI.
 *
 * @author Marazzi Paolo
 */
public interface ServerInterface extends Remote {
    /**
     * Il metodo permette ad un client di aggiungersi alla lobby.
     * @param clientInterface interfaccia remota del client che si vuole aggiungere alla lobby.
     * @param nickname nickname scelto dal client.
     * @throws RemoteException
     * @throws InvalidNicknameException viene sollevata se il nickname scelto non è valido o è già utilizzato da un altro client presente nella lobby.
     */
    void add(ClientInterface clientInterface, String nickname) throws RemoteException, InvalidNicknameException, GameStartedException;
}
