package it.polimi.se2018.server.message.network_message;

public class NetworkMessageParser {

    private NetworkMessageParser(){}

    public static boolean isDisconnectMessage(String message){
        return message.split("/")[3].equals("network_message") && message.split("/")[4].equals("disconnected");
    }

    public static boolean isTimeoutMessage(String message){
        return message.split("/")[3].equals("network_message") && message.split("/")[4].equals("timeout");
    }

    public static boolean isBroadcastMessage(String message){
        return message.split("/")[2].equals("!");
    }

    public static boolean isFreezeMessage(String message){
        return message.split("/")[4].equals("freeze");
    }

    public static String getMessageSender(String message){
        return message.split("/")[1];
    }

    public static String getMessageAddressee(String message){
        return message.split("/")[2];
    }

    public static String getMessageInfo(String message) {
        return message.split("/")[5];
    }

}