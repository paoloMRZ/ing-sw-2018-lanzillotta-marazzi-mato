package it.polimi.se2018.server.message.network_message;

public class NetworkMessageCreator {

    private NetworkMessageCreator(){}

    public static String getConnectMessage(String nickname){
        return "/###/!/network_message/connected/" + nickname + "\n";
    }

    public static String getDisconnectMessage(String nickname){
        return "/###/!/network_message/disconnected/" + nickname + "\n";
    }

    public static String getConnectionErrorGameStartedMessage(String addressee){
        return "/###/" + addressee + "/network_message/ko_gamestarted/?\n";
    }

    public static String getConnectionErrorInvalidNickanmeMessage(String addressee){
        return "/###/" + addressee + "/network_message/ko_nickname/?\n";
    }

    public static String getFreezeMessage(String nickname){
        return "/###/###/network_message/freeze/"+ nickname  +"\n";
    }

    public static String getUnfreezeMessage(String nickname){
        return "/###/###/network_message/unfreeze/"+ nickname  +"\n";
    }

}
