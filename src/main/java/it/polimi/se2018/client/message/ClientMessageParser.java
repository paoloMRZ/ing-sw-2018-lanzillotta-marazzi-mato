package it.polimi.se2018.client.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientMessageParser {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String START = "start";
    private static final String UPDATE = "update";
    private static final String NETWORK = "network_message";


    private ClientMessageParser(){}

    /**
     * Il metodo controlla se il messaggio passato è un messaggio di "successo" di una qulasiasi operazione.
     * @param message messaggio da controllare.
     * @return true se è un messaggio di successo, false in caso contrario.
     */
    public static boolean isSuccessMessage(String message){
        return message.split("/")[3].equals(SUCCESS);
    }

    /**
     * Il metodo controlla se il messaggio passato è un messaggio di "errore" di una qulasiasi operazione.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di errore, false in caso contrario.
     */
    public static boolean isErrorMessage(String message){
        return message.split("/")[3].equals(ERROR);
    }

    /**
     * Il metodo controlla se il messaggio passato è un messaggio di tipo START.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di start, false in caso contrario.
     */
    public static boolean isStartMessage(String message){
        return message.split("/")[3].equals(START);
    }

    /**
     * Il metodo controlla se il messaggio passato è un messaggio di tipo UPDATE.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di update, false in caso contrario.
     */
    public static boolean isUpdateMessage(String message){
        return message.split("/")[3].equals(UPDATE);
    }

    /**
     * Il metodo controlla se il messaggio passato è un messaggio di tipo NETWORK.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di network_message, false in caso contrario.
     */
    public static boolean isNetworkMessage(String message){
        return message.split("/")[3].equals(NETWORK);
    }


    /**
     * Il metodo controlla se il messaggio è un messaggio di conferma del posizionamento di un dado.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di conferma del posizionamento, false in caso contrario.
     */
    public static boolean isSuccessPutMessage(String message){
        return message.split("/")[3].equals(SUCCESS) && message.split("/")[4].equals("put") ;
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio di errore del posizionamento di un dado.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di errore del posizionamento, false in caso contrario.
     */
    public static boolean isErrorPutMessage(String message){
        return message.split("/")[3].equals(ERROR) && message.split("/")[4].equals("put") ;
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che notifica un piazzamento non autorizzato. Questo avviene quando
     * un giocatore tenta di piazzare più di un dado per turno.
     *
     * @param message messaggio da conrtollare.
     * @return true se il metodo notifica un piazzamento non autorizzato.
     */
    public static boolean isUnauthorizedPutMessage(String message){
        return message.split("/")[3].equals(ERROR) && message.split("/")[4].equals("unauthorized") ;
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio di conferma dell'attivazione di un utensile.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di conferma dell'attivazione, false in caso contrario.
     */
    public static boolean isSuccessActivateUtensilMessage(String message){
        return message.split("/")[3].equals(SUCCESS) && message.split("/")[4].equals("activate") ;
    }


    /**
     * Il metodo controlla se il messaggio è un messaggio di conferma dell'utilizzo di un utensile.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di conferma dell'utilizzo, false in caso contrario.
     */
    public static boolean isSuccessUseUtensilMessage(String message){
        return message.split("/")[3].equals(SUCCESS) && message.split("/")[4].equals("use") ;
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che indica che l'uso di una carta utensile è terminato ed è andato
     * a buon fine.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio indica l'effetto di una carta utensile è terminato, false in caso contrario.
     */
    public static boolean isUseUtensilEndMessage(String message){
        return message.split("/")[3].equals("utensil") && message.split("/")[4].equals("end");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio di errore riguardante l'uso di un utensile.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di errore, false in caso contrario.
     */
    public static boolean isErrorUseUtensilMessage(String message){
        return message.split("/")[3].equals(ERROR) && message.split("/")[4].equals("use") ;
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio di errore dell'attivazione di un utensile.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di errore dell'attivazione, false in caso contrario.
     */
    public static boolean isErrorActivateUtensilMessage(String message){
        return message.split("/")[3].equals(ERROR) && message.split("/")[4].equals("activate") ;
    }


    /**
     * Il metodo verifica se il messaggio è un messaggio che contiene le carte schema tra cui il giocatore scegliere la propria.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio che invita il giocatore a scegliere una carta schema, false in caso contrario.
     */
    public static boolean isStartChoseSideMessage(String message){
        return message.split("/")[3].equals(START) && message.split("/")[4].equals("chose_side");
    }

    /**
     * Il metodo verifica se il messaggio è un messaggio che trasporta gli obiettivi pubblici estratti a inizio partita.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio trasporta gli obiettivi pubblici, false in caso contrario.
     */
    public static boolean isStartPublicObjectiveMessage(String message){
        return message.split("/")[3].equals(START) && message.split("/")[4].equals("public_objective");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che trasporta l'obiettivo privato di un giocatore.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio trasporta un obiettivo privato, false in caso contrario.
     */
    public static boolean isStartPrivateObjectiveMessage(String message){
        return message.split("/")[3].equals(START) && message.split("/")[4].equals("private_objective");
    }


    /**
     * Il metodo controlla se il messaggio è un messaggio che trasporta le carte utensili estratte ad inizio partita.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio trasporta le carte utensili, false in caso contrario.
     */
    public static boolean isStartUtensilMessage(String message){
        return message.split("/")[3].equals(START) && message.split("/")[4].equals("utensil");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio di inizializzazione riguardo la carta schema scelta da ogni giocatore.
     *
     * @param message messaggio da controllare.
     * @return true se è un messaggio di inizializzazione false in caso contrario.
     */
    public static boolean isStartSideListMessage(String message){
        return message.split("/")[3].equals(START) && message.split("/")[4].equals("side_list");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che notifica il cambio turno.
     *
     * @param message messaggio da controllare
     * @return true se il messaggio notifica il cambio turno, false in caso contrario.
     */
    public static boolean isUpdateTurnMessage(String message){
        return message.split("/")[3].equals(UPDATE) && message.split("/")[4].equals("turn");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che notifica il cambio del round.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio notifica il cambio del round, false in caso conrtario.
     */
    public static boolean isUpdateRoundMessage(String message){
        return message.split("/")[3].equals(UPDATE) && message.split("/")[4].equals("round");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio di aggiornamento della riserva.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio è un messaggio di aggiornamento della riserva, false in caso contrario.
     */
    public static boolean isUpdateReserveMessage(String message){
        return message.split("/")[3].equals(UPDATE) && message.split("/")[4].equals("reserve");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio di aggiornamento della roundgrid.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio è un messaggio di aggiornamento della roundgrid, false in caso contrario.
     */
    public static boolean isUpdateRoundgridMessage(String message){
        return message.split("/")[3].equals(UPDATE) && message.split("/")[4].equals("roundgrid");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio di aggiornamento di una carta schema.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio è un messaggio di aggiornamento di una carta schema, false in caso contrario.
     */
    public static boolean isUpdateSideMessage(String message){
        return message.split("/")[3].equals(UPDATE) && message.split("/")[4].equals("side");
    }


    /**
     * Il metodo controlla se il messaggio è un messaggio che notifica la connessione di un giocatore.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio notifica la connessione di un giocatore, false in caso contrario.
     */
    public static boolean isNewConnectionMessage(String message){
        return message.split("/")[3].equals(NETWORK) && message.split("/")[4].equals("connected");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che notifica la disconnessione di un giocatore.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio notifica la disconnessione di un giocatore, false in caso contrario.
     */
    public static boolean isClientDisconnectedMessage(String message){
        return message.split("/")[3].equals(NETWORK) && message.split("/")[4].equals("disconnected");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che notifica il fallito login al server a causa del nickname
     * già utilizzato da un'altro utente.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio notifica il fallito login, false in caso contrario.
     */
    public static boolean isErrorConnectionInvalidNicknameMessage(String message){
        return message.split("/")[3].equals(NETWORK) && message.split("/")[4].equals("ko_nickname");
    }

    /**
     * Il metodo controlla se il messaggio è un messaggio che notifica il fallito login al server a causa
     * della partita già avviata.
     *
     * @param message messaggio da controllare.
     * @return true se il messaggio notifica il fallito login, false in caso contrario.
     */
    public static boolean isErrorConnectionGameStartedMessage(String message){
        return message.split("/")[3].equals(NETWORK) && message.split("/")[4].equals("ko_gamestarted");
    }




    //Metodo di get delle informazioni dai messaggi.

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
     * Il metodo estrae i dati dal campo "informazioni" del messaggio di tipo "Update roundgrid".
     * E' necessario questo metodo dedicato a causa della particolare struttura del campo "informazioni" di questo
     * tipo di messaggio.
     *
     * @param message messaggio da cui estrarre i dati.
     * @return lista dei dati estratti.
     */
    public static List<List<String>> getInformationsFromUpdateRoundgridMessage(String message){

        List<List<String>> informations = new ArrayList<>();

        for(String info : message.replace("\n", "").split("/")[5].split("&")){
            informations.add(Arrays.asList(info.split(":")));
        }

        return  informations;
    }

    /**
     * Il metodo controlla se il messaggio passato è un messaggio che notifica la fine della partita.
     *
     * @param message messaggio da conrtollare
     * @return true se il metodo notifica la fine della partita.
     */
    public static boolean isWinnerMessage(String message){
        return (message.replace("\n", "").split("/")[4]).equals("winner");
    }

}