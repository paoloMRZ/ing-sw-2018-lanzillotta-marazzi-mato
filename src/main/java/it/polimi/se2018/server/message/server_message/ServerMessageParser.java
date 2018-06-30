package it.polimi.se2018.server.message.server_message;

import java.util.Arrays;
import java.util.List;

public class ServerMessageParser {

    private ServerMessageParser(){}

    /**
     * Il metodo controlla se il messaggio è un messaggio che indica che il client vuole posizionare un dado della riserva
     * sulla griglia.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio che indica che il client vuole posizionare un dado sulla griglia, false in caso
     *         contrario.
     */
    public static boolean isPutMessage(String message){
        return message.split("/")[3].equals("put");
    }


    /**
     * Il metodo controlla se il messaggio è un messaggio di richiesta di attivazione di una carta utensile.
     *
     * @param message messaggio da controllare
     * @return true se il messaggio è un messaggio di attivazione di una carta utensile.
     */
    public static boolean isActivateUtensilMessage(String message){
        return message.split("/")[3].equals("utensil") && message.split("/")[4].equals("activate");
    }


    /**
     * Il metodo controlla se il messaggio è un messaggio che trasporta i parametri necessari per poter utilizzare
     * l'effetto della carta utensile precedentemente attivata.
     *
     * @param message messaggio da controllare
     * @return true se il messaggio è un messaggio che trasporta i parametri necessari per l'uso di una carta utensile.
     */
    public static boolean isUseUtensilMessage(String message){
        return message.split("/")[3].equals("utensil") && message.split("/")[4].equals("use");
    }


    /**
     * Il metodo controlla se il messaggio è un messaggio che indica con quale carta schema un giocatore ha
     * scelto di giocare.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio è un messaggio che specifica con quale carta schema un giocatore ha scelto di giocare.
     */
    public static boolean isSideReplyMessage(String message){
        return message.split("/")[3].equals("start") && message.split("/")[4].equals("side_reply");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che indica che un giocatore ha passato il turno.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio notifica il passaggio del turno da parte di un giocatore.
     */
    public static boolean isPassTurnMessage(String message){
        return message.split("/")[3].equals("update") && message.split("/")[4].equals("turn");
    }

    /**
     * Il metodo restituisce il mittente del messaggio.
     *
     * @param message messaggio da cui estrarre il mittente.
     * @return nickname del mittente.
     */
    public static String getSender(String message){
        return message.split("/")[1];
    }

    /**
     * Il metodo estrae i dati dal campo "informazioni" del messaggio.
     *
     * @param message messaggio da cui estrarre i dati.
     * @return lista dei dati estratti.
     */
    public static List<String> getInformationsFromMessage(String message){
        return Arrays.asList((message.replace("\n", "").split("/")[5]).split("&"));
    }


    /**
     * Il metodo controlla se il messaggio è un messaggio che indica che un giocatore è stato congelato.
     * @param message messaggio da controllare.
     * @return true se il messaggio indica il congelamento di un giocatore.
     */
    public static boolean isFreezeMessage(String message){
        return message.split("/")[4].equals("freeze");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che indica che un giocatore è stato scongelato.
     * @param message messaggio da controllare.
     * @return true se il messaggio indica lo scongelamento di un giocatore.
     */
    public static boolean isUnfreezeMessage(String message){
        return message.split("/")[4].equals("unfreeze");
    }
}
