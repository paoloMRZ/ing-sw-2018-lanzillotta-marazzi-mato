package it.polimi.se2018.graphic;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.graphic.adapterGUI.AdapterResolution;
import it.polimi.se2018.graphic.alert_box.AlertValidation;
import it.polimi.se2018.graphic.alert_utensils.AlertCardUtensils;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import static it.polimi.se2018.graphic.Utility.*;


/**
 * Classe ButtonGameLabel utilizzata per la configurazione dei bottoni interattivi tramite i quali il giocatore può effettuare le seguenti azioni:
 *  -> Piazzare un dado
 *  -> Passare il turno
 *  -> Utilizzare una carta Utensile
 *
 * @author Simone Lanzillotta
 */




public class ButtonGameLabel {

    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "";

    private VBox labelButtonGame;
    private ImageView buttonGame;
    private ImageView buttonUtensils;
    private ImageView buttonTurn;
    private AlertCardUtensils alertCardUtensils;


    /**
     * Costruttore della classe ButtonGameLabel
     *
     * @param connectionHandler Riferimento all'oggetto ConnectionHandler rappresentante del giocatore
     * @param reserve Riferimento alla riserva disponibile nel turno
     * @param playerSide Riferimento alla carta Side associata al giocatore
     * @param cardUtensils Riferimento alla collezione di carte Utensili disponibile nella partita corrente
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    public ButtonGameLabel(ConnectionHandler connectionHandler, ReserveLabel reserve, SideCardLabel playerSide, CardCreatorLabel cardUtensils, AdapterResolution adapterResolution){

        ArrayList<Integer> sizeButton = (ArrayList<Integer>)adapterResolution.getButtonLabelSize();
        alertCardUtensils = new AlertCardUtensils(cardUtensils,connectionHandler,reserve,playerSide,adapterResolution);


        //Configuro il bottone Azione per il piazzamento del dado
        buttonGame = shadowEffect(configureImageView(SUBDIRECTORY,"button-game-die", EXTENSION,352, 104));
        buttonGame.setFitWidth(sizeButton.get(0));
        buttonGame.setFitHeight(sizeButton.get(1));
        buttonGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(reserve.getPos() != null) connectionHandler.sendToServer(ClientMessageCreator.getPutDiceMessage(connectionHandler.getNickname(), reserve.getPos(), playerSide.getPosX(), playerSide.getPosY()));
            else AlertValidation.display("Errore", "Non hai selezionato nella riserva\nil dado da inserire!");
        });


        //Configuro il bottone Azione per le carte Utensili
        buttonUtensils = shadowEffect(configureImageView(SUBDIRECTORY,"button-game-utensil",EXTENSION, 352, 104));
        buttonUtensils.setFitWidth(sizeButton.get(0));
        buttonUtensils.setFitHeight(sizeButton.get(1));
        buttonUtensils.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> alertCardUtensils.display("Sagrada", "Scegli al carta che vuoi attivare"));


        //Configuro il bottone Azione per il passa Turno
        buttonTurn = shadowEffect(configureImageView(SUBDIRECTORY,"button-game-turn",EXTENSION, 352, 104));
        buttonTurn.setFitWidth(sizeButton.get(0));
        buttonTurn.setFitHeight(sizeButton.get(1));
        buttonTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            connectionHandler.sendToServer(ClientMessageCreator.getPassTurnMessage(connectionHandler.getNickname()));
        });


        //Configurazione Finale
        labelButtonGame = new VBox(sizeButton.get(2));
        labelButtonGame.setAlignment(Pos.CENTER);
        HBox labelButton = new HBox(sizeButton.get(3));
        labelButton.setAlignment(Pos.CENTER);
        labelButton.getChildren().addAll(buttonGame, buttonUtensils);
        labelButtonGame.getChildren().addAll(buttonTurn, labelButton);
    }



    /**
     * Metodo getter che rende disponibile il layout finale dell'elemento grafico
     *
     * @return Riferimento all'oggetto labelButtonGame
     */

    public VBox getLabelButtonGame() {
        return labelButtonGame;
    }



    /**
     * Metodo getter che rende disponibile la schermata di interazione con le carte Utensili
     *
     * @return Riferimento all'oggetto alertCardUtensils
     */

    public AlertCardUtensils getAlertCardUtensils() {
        return alertCardUtensils;
    }



    /**
     * Metodo che viene richiamato ogni qualvolta si presenta la situazione in cui il giocatore non è ik turnante. Disabilita i bottoni impedendo solo
     * le azioni di interazioni con il model (Passa Turno, Piazza Dado e Usa Utensile). Rimangono invece disponibili le interazioni con l'interfaccia
     * grafica (Visualizzazione dei dadi residui nella RoundGrid, ZoomIn delle carte Utensili e Obbiettivo Pubblico disponibili)
     *
     * @param nickname Nome del giocatore
     * @param turnOf Nome del giocatore turnante
     */

    public void checkPermission(String nickname, String turnOf){

            if (!nickname.equals(turnOf)) {
                buttonGame.setDisable(true);
                buttonUtensils.setDisable(true);
                buttonTurn.setDisable(true);
                buttonGame.setOpacity(0.7);
                buttonUtensils.setOpacity(0.7);
                buttonTurn.setOpacity(0.7);
            } else {
                buttonGame.setDisable(false);
                buttonUtensils.setDisable(false);
                buttonTurn.setDisable(false);
                buttonGame.setOpacity(1.0);
                buttonUtensils.setOpacity(1.0);
                buttonTurn.setOpacity(1.0);
            }
    }
}
