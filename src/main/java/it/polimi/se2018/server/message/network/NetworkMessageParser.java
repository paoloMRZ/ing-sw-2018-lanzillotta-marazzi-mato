package it.polimi.se2018.server.message.network;

public class NetworkMessageParser {

    private NetworkMessageParser(){}

    public static boolean isDisconnectMessage(String message){
        return message.split("/")[3].equals("network") && message.split("/")[4].equals("disconnected");
    }

    public static boolean isTimeoutMessage(String message){
        return message.split("/")[3].equals("network") && message.split("/")[4].equals("timeout");
    }

    public static String getMessageSender(String message){
        return message.split("/")[1];
    }

    public static String getMessageAddressee(String message){
        return message.split("/")[2];
    }

    public static String getMessageInfo(String message){
        return message.split("/")[5];
    }
}