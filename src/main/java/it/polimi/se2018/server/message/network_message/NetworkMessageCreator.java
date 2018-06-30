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


    /**
     * Il metodo costruisce il messaggio utilizzato dal client per notificare al server la propria disconnessione.
     * Questo messaggio viene usato dal fake client per richiedere la disconnessione quando si solleva un'eccezone di rete.
     *
     * @param sender nickname del client che ha sollevato l'eccezione.
     * @return messaggio di disconnessione.
     */

    public static String getClientDisconnectMessage(String sender){
        return "/" + sender + "/###/network_message/disconnected/?\n";
    }

}
