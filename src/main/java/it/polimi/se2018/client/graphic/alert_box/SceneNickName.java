package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.cli.Cli;
import it.polimi.se2018.client.connection_handler.ConnectionHandlerRMI;
import it.polimi.se2018.client.connection_handler.ConnectionHandlerSocket;
import it.polimi.se2018.client.graphic.InitWindow;
import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;

import static it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator.*;
import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;


/**
 * Classe SceneNickName che gestisce la creazione della scena relativa alla scelta del NickName da parte dell'utente. Sulla campo immissione somo stati aggiunti dei controlli
 * prettamente collegati all'inserimento, quindi niente a che vedere con il controllo eseguito dal server sulla validità del NickName scelto (ovvero se sia disponibile o
 * esista già un altro giocatore con lo stesso NickName).
 * Il controllo eseguito consiste nell'obbligare il giocatore ad inserire un NickName che conta almeno un carattere (ovvero non può connettersi lasciando vuoto il
 * campo immissione)
 *
 * @author Simone Lanzillotta
 */



public class SceneNickName {

    private static final String ERROR = "Errore";
    private Scene sceneNickname;


    /**
     * Costruttore della classa utilizzato direttamente per la configurazione della scena SceneNickName.
     *
     * @param window Riferimento alla Primary Stage dell'interfaccia Utente
     * @param connectionType Informazioni riguardo la preferenza di connessione scelta dall'Utente
     * @param interfaceType Informazioni riguardo la preferenza di interfaccia scelta dall'Utente
     * @param init Riferimento all'oggetto InitWindow rappresentante dell'interfaccia Utente
     * @param port Informazioni riguardo la preferenza di porta su cui connettersi scelta dall'Utente
     * @param iP Informazioni riguardo l'indirizzo IP di connessione scelta dall'Utente
     * @param sceneLoading Riferimento alla scena SceneLoading per l'effetto Switcher
     * @param sceneConnection Riferimento alla scena SceneConnection per l'effetto Switcher
     */

    public SceneNickName(Stage window, String connectionType, String interfaceType, InitWindow init, int port, String iP, Scene sceneLoading, Scene sceneConnection){

        //Configurazione intestazione per il campo Inserimento del Nickname
        Label labelTitle = setFontStyle(new Label("Scegli il tuo nickName"),25);

        //Configurazione campo Immissione del Nickname
        VBox layout = new VBox(10);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(14);

        TextField textNick = new TextField();
        textNick.setPromptText("nickname");
        textNick.setPrefSize(200,20);
        textNick.setFont(Font.font("Verdana", FontWeight.THIN, 18));

        GridPane.setConstraints(textNick, 0, 0);
        grid.setAlignment(Pos.CENTER);
        grid.getChildren().add(textNick);

        //Configurazione griglia bottoni continue/back e check validation
        ImageView continueButton = getContinueButton(168,71);
        ImageView backButton = getBackButton(138,71);
        HBox labelButton = setInteractLabel(continueButton,backButton,20);

        layout.getChildren().addAll(labelTitle, grid, labelButton);
        layout.setAlignment(Pos.CENTER);

        //Configurazione dell'effetto "Switch Back" per ritornare alla schermata di connessione
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> Platform.runLater(() -> window.setScene(sceneConnection)));

        //fase di Controllo per la valiodità dell'inserimento del NickName
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.runLater(() -> {
                if(isValidInput(textNick.getText(), connectionType, interfaceType, init, port, iP)) {
                    if(interfaceType.equals("Gui")) window.setScene(sceneLoading);
                    else {
                        init.getPrimaryStage().hide();
                        window.close();
                    }
                }
            });
        });


        //Formattazione finale della Finestra
        layout.setBackground(configureBackground("back-init-close", 550,480));
        sceneNickname = new Scene(layout,550,480);
}



    /**
     * Metodo utilizzato per controllare la validità di inserimento da parte dell'Utente del NickName scelto. obbliga quindi l'Utente a inserire un Nick almeno di un
     * carattere (non convalida la connessione fino a quando il campo Inserimento rimane vuoto).
     *
     * @param input NickName inserito dall'utente
     * @param connectionType Informazioni riguardo la preferenza di connessione scelta dall'Utente
     * @param interfaceType Informazioni riguardo la preferenza di interfaccia scelta dall'Utente
     * @param init Riferimento all'oggetto InitWindow rappresentante dell'interfaccia Utente
     * @param port Informazioni riguardo la preferenza di porta su cui connettersi scelta dall'Utente
     * @param iP Informazioni riguardo l'indirizzo IP di connessione scelta dall'Utente
     * @return Booleano del valore TRUE se l'inserimento è valido, altrimenti FALSE
     */

    private static boolean isValidInput(String input, String connectionType, String interfaceType, InitWindow init, int port, String iP) {

        boolean value = false;
        if (input.trim().isEmpty()) AlertValidation.display(ERROR, "Attenzione! Nickname non valido.");
        else {
            try {
                if (connectionType.equals("Socket")) {

                    if(interfaceType.equals("Cli")) {
                        Cli cli = new Cli(input);
                        cli.setConnectionHandler(new ConnectionHandlerSocket(input, cli, iP, port));
                    }
                    else init.setConnectionHandler(new ConnectionHandlerSocket(input, init, iP, port));
                }
                else if (connectionType.equals("Rmi")) {
                    if (interfaceType.equals("Cli")) {
                        Cli cli = new Cli(input);
                        cli.setConnectionHandler(new ConnectionHandlerRMI(input, cli, iP));
                    }
                    else init.setConnectionHandler(new ConnectionHandlerRMI(input, init, iP));
                }
                value = true;
            } catch (InvalidNicknameException e) {
                AlertValidation.display(ERROR, "Attenzione! Nickname già utilizzato!.");
            } catch (GameStartedException e) {
                AlertValidation.display(ERROR, "Attenzione! Partita già in corso!");
            } catch (NotBoundException | IOException e) {
                AlertValidation.display(ERROR, "Attenzione! Errore di rete, riprova!");
            }
        }

        return value;
    }



    /**
     * Metodo Getter per restituire la scena configurata, utiilizzato per l'effetto Switcher
     *
     * @return Riferimento alla scena sceneNickname
     */

    public Scene getSceneNickName() {
        return sceneNickname;
    }
}
