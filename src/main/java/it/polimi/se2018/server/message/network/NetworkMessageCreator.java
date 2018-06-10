package it.polimi.se2018.server.message.network;

public class NetworkMessageCreator {

    private NetworkMessageCreator(){}

    public static String getConnectMessage(String nickname){
        return "/###/!/network/connected/" + nickname + "\n";
    }

    public static String getDisconnectMessage(String nickname){
        return "/###/!/network/disconnected/" + nickname + "\n";
    }

    public static String getConnectionErrorGameStartedMessage(String addressee){
        return "/###/" + addressee + "/network/ko_gamestarted/?\n";
    }

    public static String getConnectionErrorInvalidNickanmeMessage(String addressee){
        return "/###/" + addressee + "/network/ko_nickname/?\n";
    }

}
