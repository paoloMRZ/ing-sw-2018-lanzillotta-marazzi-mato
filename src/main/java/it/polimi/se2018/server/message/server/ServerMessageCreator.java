package it.polimi.se2018.server.message.server;

import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.List;

/**
 * La classe è una raccolta di metodi per la creazione dei messaggi che il server invia al client.
 *
 * @author Marazzi Paolo
 */

public class ServerMessageCreator {

    private ServerMessageCreator() {
    }

    /**
     * Il messaggio restituito dal metodo indica che la put effettuata dal client è andata a buon fine.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice che rappresenta il dado della riserva scelto per l'inserimento.
     * @param row       riga della cella su cui è stato effettuato l'inserimento.
     * @param col       colonna della cella su cui è stato effettuato l'inserimento.
     * @return messaggio.
     */
    public static String getPutDieSuccesMessage(String addressee, String index, String row, String col) {
        return "/###/" + addressee + "/success/put/" + index + "&" + row + "&" + col + "\n";
    }


    /**
     * Il messaggio restituito dal metodo indica che la put effettuata dal client non è andata a buon fine.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice che rappresenta il dado della riserva scelto per l'inserimento.
     * @param row       riga della cella su cui è stato effettuato l'inserimento.
     * @param col       colonna della cella su cui è stato effettuato l'inserimento.
     * @return messaggio.
     */
    public static String getPutDieErrorMessage(String addressee, String index, String row, String col) {
        return "/###/" + addressee + "/error/put/" + index + "&" + row + "&" + col + "\n";
    }

    //Messaggi relativi all'uso degli utensili.

    /**
     * Il messaggio restituito dal metodo indica che l'attivazione della carta utensile scelta dal client è andata a
     * buon fine.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice che identifica la carta utensile scelta dal client.
     * @param price     nuovo prezzo della carta utensile che è stata attivata.
     * @return messaggio.
     */
    public static String getActivateUtensilSuccessMessage(String addressee, String index, String price) {
        return "/###/" + addressee + "/success/utensil/" + index + "&" + price + "\n";
    }


    /**
     * Il messaggio restituito dal metodo indica che l'attivazione della carta utensile scelta dal client è andata a
     * buon fine.
     * Questo metodo prende in ingresso un parametro in più, infatti le carte utensili 1, 6, 10, 11 devono passare un parametro
     * al client per permettergli di utilizzare l'effetto della carta.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice che identifica la carta utensile scelta dal client.
     * @param price     nuovo prezzo della carta utensile che è stata attivata.
     * @param param     parametro da passare al client per permettergli di usare la carta.
     * @return messaggio.
     */
    public static String getActivateUtensilSuccessMessage(String addressee, String index, String price, String param) {
        return "/###/" + addressee + "/success/utensil/" + index + "&" + price + "&" + param + "\n";
    }

    /**
     * Il messaggio restituito dal metodo indica che l'attivazione della carta utensile scelta dal client è andata a
     * buon fine.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice che identifica la carta utensile scelta dal client.
     * @return messaggio.
     */
    public static String getUseUtensilErrorMessage(String addressee, String index) {
        return "/###/" + addressee + "/error/utensil/" + index + "\n";
    }

    //Messaggi relativi all'avvio della partita.


    /**
     * Il metodo restituisce il messaggio da mandare al client a inizio partita per permettergli di scegliere
     * con quale carta schema giocare.
     *
     * @param addressee destinatario del messaggio.
     * @param cardname  lista dei nomi delle carte tra cui il giocatore può scegliere.
     * @return messaggio.
     */
    public static String getChoseSideMessage(String addressee, List<String> cardname) {
        String message = "/###/" + addressee + "/start/chose_side/";

        for (int i = 0; i < 4; i++) {
            if (i == 0)
                message = message.concat(cardname.get(i));
            else
                message = message.concat("&" + cardname.get(i));
        }

        message = message.concat("\n");

        return message;
    }


    public static String getUseUtensilEndMessage(String addressee, String index){
        return "/###/" + addressee + "/utensil/end/" + index + "\n";
    }

    /**
     * Il metodo restiruisce il messaggio da mendare ai client per aggiornarli riguardo la carta schema scelta da ogni giocatore.
     *
     * Attenzione: l'elenco dei nickname e l'elenco dei nomi devono essere ordinati. Il nome della carta scleta dal nickname
     * in posizione zero si deve trovare anch'essa in posizione zero nella lista dei nomi delle carte, e così via.
     *
     * @param players elenco dei nickname dei giocatori.
     * @param cardNames elenco dei nomi delle carte side scelte.
     * @return messaggio.
     */
    public static String getSideListMessage(List<String> players, List<String> cardNames){

        String message = "/###/!/start/side_list/";
        int i = 0;

        for (String nickname :players){
            if(i == 0)
                message = message.concat(nickname + "&" + cardNames.get(i));
            else
                message =  message.concat("&" + nickname + "&" + cardNames.get(i));

            i++;
        }

        return message.concat("\n");
    }

    /**
     * Il metodo restituisce il messaggio da mandare ai client ad inizio partita per informarli su quali obiettivi pubblici
     * sono stati estratti.
     *
     * @param cardname lista dei nomi degli obiettivi pubblici.
     * @return messaggio.
     */
    public static String getPublicObjectiveMessage(List<String> cardname) {
        String message = "/###/!/start/public_objective/";

        for (int i = 0; i < 3; i++) {
            if (i == 0)
                message = message.concat(cardname.get(i));
            else
                message = message.concat("&" + cardname.get(i));
        }

        message = message.concat("\n");

        return message;
    }

    /**
     * Il metodo restituisce il messaggio da mandare ai client ad inizio partita per informarli su quali carte utensili
     * sono state estratte.
     *
     * @param cardname lista dei nomi deelle carte utensili.
     * @return messaggio.
     */
    public static String getSendUtensilsMessage(List<String> cardname) {
        String message = "/###/!/start/utensil/";

        for (int i = 0; i < 3; i++) {
            if (i == 0)
                message = message.concat(cardname.get(i));
            else
                message = message.concat("&" + cardname.get(i));
        }

        message = message.concat("\n");

        return message;
    }

    /**
     * Il metodo restituisce il messaggio da mandare al client ad inizio partita per informarlo su quale sia il suo obiettivo privato.
     *
     * @param addressee nickname del destinatario del messaggio.
     * @param cardName  nome dell'obiettivo privato.
     * @return messaggio.
     */
    public static String getPrivateObjectiveMesage(String addressee, String cardName) {
        return "/" + addressee + "/###/start/private_objective/" + cardName + "\n";
    }


    //Messaggi relativi all'aggiornamento della partita.


    /**
     * Il metodo restiuisce il messaggio da mandare ai client per informarli dell'inizio di un nuovo round.
     *
     * @param nickname nickaname del giocatore che deve giocare il primo turno del round.
     * @param diceList lista dei dadi rimasti nella riserva che devono essere aggiunti alla roundgrid.
     * @return messaggio.
     */
    public static String getUpdateRoundMessage(String nickname, List<Dice> diceList) {
        String message =  "/###/!/update/round/" + nickname;

        for(Dice die : diceList)
            message = message.concat("&" + die.getColor().toString().toLowerCase() + die.getNumber());

        return message.concat("\n");
    }


    /**
     * Il metodo restiuisce il messaggio da mandare ai client per informarli dell'inizio di un nuovo turno.
     *
     * @param nickname nickaname del giocatore che deve giocare il turno.
     * @return messaggio.
     */
    public static String getUpdateTurnMessage(String nickname) {
        return "/###/!/update/turn/" + nickname + "\n";
    }

    /**
     * Il metodo restituisce il messaggio da inviare ai client per aggiornarli sullo stato della riserva.
     *
     * @param dice lista dei dadi della riserva.
     * @return messaggio.
     */
    public static String getUpdateReserveMessage(List<Dice> dice) {
        String message = "/###/!/update/reserve/";

        for (Dice die : dice) {
            if (dice.indexOf(die) == 0)
                message = message.concat(die.getColor().toString().toLowerCase() + die.getNumber());
            else
                message = message.concat("&" + die.getColor().toString().toLowerCase() + die.getNumber());
        }

        message = message.concat("\n");

        return message;
    }

    /**
     * Il metodo restituisce il messaggio da inviare ai client per aggiornarli sullo stato della roundgrid.
     *
     * @param dice dadi contenuti nella roundgrid. Ad ogni slot della roundgrid corrisponde una lista di dadi.
     * @return messaggio.
     */
    public static String getUpdeteRoundgridMessage(List<List<Dice>> dice) {
        String message = "/###/!/update/roundgrid/";

        for (List<Dice> diceList : dice) {

            if (dice.indexOf(diceList) != 0)
                message = message.concat("&");

            for (Dice die : diceList) {
                if (diceList.indexOf(die) == 0)
                    message = message.concat(die.getColor().toString().toLowerCase() + die.getNumber());
                else
                    message = message.concat(":" + die.getColor().toString().toLowerCase() + die.getNumber());
            }
        }

        message = message.concat("\n");

        return message;
    }

    /**
     * Il metodo restituisce il messaggio da mandare ai client per aggiornarli sullo stato di una carta schema.
     *
     * @param nickname proprietario della carta schema descritta dal messaggio.
     * @param dice dadi (in ordine di riga) contenuti nella carta schema. Ad una casella vuota corrisponde un "dado" bianco
     *             di valore 0.
     * @return messaggio.
     */
    public static String getUpdateSideMessage(String nickname, List<Dice> dice) {
        String message = "/###/!/update/side/" + nickname;

        for (Dice die : dice) {
            if (die != null)
                message = message.concat("&" + die.getColor().toString().toLowerCase() + die.getNumber());
            else
                message = message.concat("&white0");
        }

        message = message.concat("\n");

        return message;
    }



    //Messaggio relativo alla rete.

    /**
     * Il metodo restiutisce il messaggio da mandare al gestore delle connessioni per richiedere il congelamento di un client.
     *
     * @param addressee nickname del destinatario del messaggio, cioè il client da congelare
     * @return messaggio.
     */
    public static String getTimeoutMessage(String addressee) {
        return "/###/" + addressee + "/network/timeout/?\n";
    }

}