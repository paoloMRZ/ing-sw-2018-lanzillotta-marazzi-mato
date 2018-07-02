package it.polimi.se2018.client.cli.controller.states;

import it.polimi.se2018.client.cli.print.scenes.WaitScene;
import it.polimi.se2018.client.message.ClientMessageParser;

public class WaitState implements StateInterface{

    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String NETWORK_CONNECT_MESSAGE = " si è connesso!";
    private static final String NETWORK_DISCONNECT_MESSAGE = " si è disconnesso!";

    private WaitScene waitScene;

    public WaitState(String message){
        this.waitScene = new WaitScene(message);
        waitScene.printScene();
    }


    @Override
    public String handleInput(int request) {
        waitScene.printScene();
        return DEFAULT_MESSAGE;
    }

    @Override
    public void handleNetwork(String request) {

        if(ClientMessageParser.isNewConnectionMessage(request)){
            waitScene.addMessage(ClientMessageParser.getInformationsFromMessage(request).get(0) + NETWORK_CONNECT_MESSAGE);
            waitScene.printScene();
        }

        if(ClientMessageParser.isClientDisconnectedMessage(request)){
            waitScene.addMessage(ClientMessageParser.getInformationsFromMessage(request).get(0) + NETWORK_DISCONNECT_MESSAGE);
            waitScene.printScene();
        }

    }
}
