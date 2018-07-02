package it.polimi.se2018.client.graphic.alert_utensils;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.graphic.ReserveLabel;
import it.polimi.se2018.client.graphic.RoundLabel;
import it.polimi.se2018.client.graphic.SideCardLabel;
import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import it.polimi.se2018.client.graphic.alert_box.AlertValidation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

import static it.polimi.se2018.client.graphic.RoundLabel.*;
import static it.polimi.se2018.client.graphic.Utility.*;
import static it.polimi.se2018.client.graphic.alert_box.AlertInfoCard.*;

public class SelectorContent {

    //Costanti di intestazione delle varie finestra
    private static final String PINZASGROSSATRICE = "Pinza Sgrossatrice";
    private static final String PENNELLOPEREGLOMISE = "Pennello Per Eglomise";
    private static final String ALESATOREPERLALAMINDADIRAME = "Alesatore Per Lamina Di Rame";
    private static final String LATHEKIN = "Lathekin";
    private static final String TAGLIERINACIRCOLARE = "Taglierina Circolare";
    private static final String PENNELLOPERPASTASALDA = "Pennello Per Pasta Salda";
    private static final String TENAGLIAAROTELLE = "Tenaglia A Rotelle";
    private static final String RIGAINSUGHERO = "Riga In Sughero";
    private static final String TAMPONEDIAMANTATO = "Tampone Diamantato";
    private static final String DILUENTEPERPASTASALDA = "Diluente Per Pasta Salda";
    private static final String TAGLIERINAMANUALE = "Taglierina Manuale";
    private static final String TITLERESERVE = "Riserva: ";

    //Elemento che dinamicamente adatta il suo contenuto alla carta Utensile richiesta dal giocatore
    private VBox node;

    //Elementi grafici per la creazione delle scheramate
    private ReserveLabel reserveLabel;
    private ConnectionHandler connectionHandler;
    private String cardSelection;
    private SideCardLabel playerSide;
    private AdapterResolution adapter;
    private SideCardLabel toolSide;


    //UTENSILE 1
    private Boolean firstUse = true;
    private StackPane root;

    //UTENSILE3
    private TextField oldXSecondDie = new TextField();
    private TextField oldYSecondDie = new TextField();
    private TextField newXSecondDie = new TextField();
    private TextField newYSecondDie = new TextField();
    private TextField oldXFirstDie = new TextField();
    private TextField oldYFirstDie = new TextField();
    private TextField newXFirstDie = new TextField();
    private TextField newYFirstDie = new TextField();

    //UTENSILE5
    private Boolean firstClick = true;
    private HBox roundDice;

    //UTENSILE6
    private TextField posX = new TextField();
    private TextField posY = new TextField();


    //UTENSILE11
    private String dieChoose;
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private Group group;




    /**
     * Costruttore della classe.
     *
     * @param reserveLabel Riferimento alla riserva attualmente disponibile nel turno
     * @param connectionHandler Riferimento all'oggetto ConnectionHandler rappresentante del giocatore
     * @param cardSelection Carta selezionata dal giocatore per l'attivazione
     * @param playerSide Riferimento alla carta Side del giocatore che ha attivato la carta Utensile
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    public SelectorContent(ReserveLabel reserveLabel, ConnectionHandler connectionHandler, String cardSelection, SideCardLabel playerSide, AdapterResolution adapterResolution){
        this.reserveLabel = reserveLabel;
        this.connectionHandler = connectionHandler;
        this.cardSelection = cardSelection;
        this.playerSide = playerSide;
        this.adapter = adapterResolution;
        Rectangle rect = new Rectangle(20, 20, 70, 70);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(2d);
        group = new Group(rect);
        toolSide = playerSide.callPlayerSide(playerSide.getPathCard(),connectionHandler.getNickname(),true,true,adapter);
    }




    /**
     * Metodo utilizzato per la creazione dei messaggi che raccolgono la lista di settaggi derivati dall'utilizzo della carta Utensile utilizzata
     *
     * @param cardName Carta selezionata dal giocatore per l'attivazione
     * @param dictionaryUtensils Riferimento alla struttura dati che associa ad ogni eventuale carta Utensile il proprio numero
     * @param keyNameOfCard Chiave per la ricerca del numero della carta Utensile selezionata all'interno del dizionario
     */

    public void configureMessage(String cardName, Map<String, String> dictionaryUtensils, String keyNameOfCard){

        switch (cardName){
            case PINZASGROSSATRICE:

                connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(getInfoData(),reserveLabel.getPos()))));
                break;

            case PENNELLOPEREGLOMISE: case ALESATOREPERLALAMINDADIRAME:

                if(!oldXFirstDie.getText().trim().isEmpty() && !oldYFirstDie.getText().trim().isEmpty() && !newXFirstDie.getText().trim().isEmpty() && !newYFirstDie.getText().trim().isEmpty()) connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(oldXFirstDie.getText(),oldYFirstDie.getText(),newXFirstDie.getText(),newYFirstDie.getText()))));
                else AlertValidation.display("Errore", "Inserisci correttamente le coordinate\ndel dado da spostare!");
                break;

            case LATHEKIN:

                connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(oldXFirstDie.getText(),oldYFirstDie.getText(),newXFirstDie.getText(),newYFirstDie.getText(),oldXSecondDie.getText(),oldYSecondDie.getText(),newXSecondDie.getText(),newYSecondDie.getText()))));
                break;

            case TAGLIERINACIRCOLARE:

                connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(reserveLabel.getPos(), String.valueOf(getRoundNumber()), getDieFromRoundSelected()))));
                break;

            case PENNELLOPERPASTASALDA: case TAMPONEDIAMANTATO: case DILUENTEPERPASTASALDA:

                connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Collections.singletonList(reserveLabel.getPos()))));
                break;

            case TAGLIERINAMANUALE:

                if(!oldXFirstDie.getText().trim().isEmpty() && !oldYFirstDie.getText().trim().isEmpty() && !newXFirstDie.getText().trim().isEmpty() && !newYFirstDie.getText().trim().isEmpty()){
                    if(!oldXSecondDie.getText().trim().isEmpty() && !oldYSecondDie.getText().trim().isEmpty() && !newXSecondDie.getText().trim().isEmpty() && !newYSecondDie.getText().trim().isEmpty())
                        connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(oldXFirstDie.getText(),oldYFirstDie.getText(),newXFirstDie.getText(),newYFirstDie.getText(),oldXSecondDie.getText(),oldYSecondDie.getText(),newXSecondDie.getText(),newYSecondDie.getText()))));

                    else connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(oldXFirstDie.getText(),oldYFirstDie.getText(),newXFirstDie.getText(),newYFirstDie.getText()))));

                }

                 break;

            case RIGAINSUGHERO:

                connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(reserveLabel.getPos(), toolSide.getPosX(), toolSide.getPosY()))));
                break;
        }

    }





    /**
     * Metodo utilizzato per la creazione dei messaggi che raccolgono la lista di settaggi derivati dall'utilizzo delle carte Utensile MultiParametro
     *
     * @param cardName Carta selezionata dal giocatore per l'attivazione
     * @param dictionaryUtensils Riferimento alla struttura dati che associa ad ogni eventuale carta Utensile il proprio numero
     * @param keyNameOfCard Chiave per la ricerca del numero della carta Utensile selezionata all'interno del dizionario
     */

    public void configureMessageBis(String cardName, Map<String, String> dictionaryUtensils, String keyNameOfCard){

        switch (cardName) {
            case DILUENTEPERPASTASALDA:

                connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Collections.singletonList(dieChoose))));
                break;


            case PENNELLOPERPASTASALDA:

                if(!posX.getText().trim().isEmpty() && !posY.getText().trim().isEmpty()) connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList("1",posX.getText(),posY.getText()))));
                else connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList("0",posX.getText(),posY.getText()))));
                break;


        }
    }





    /**
     * Metodo utilizzato poer la configurazione del contenuto delle finestre associate a ciascuna carta Utensile
     *
     * @param cardName Carta selezionata dal giocatore per l'attivazione
     * @return Riferimento al Parent che raccoglie tutti gli elementi grafici necessari per l'utilizzo della carta Utensile selezionata
     */

    public VBox configureNode(String cardName){

        switch (cardName){
            case PINZASGROSSATRICE:
                node = new VBox(15);
                node.setAlignment(Pos.CENTER);
                node.getChildren().addAll(setFontStyle(new Label(TITLERESERVE),30),reserveLabel.callReserve());

                reserveLabel.getTextField().textProperty().addListener((obs, oldText, newText) -> {
                    if(firstUse){
                        root = configureBoxSelection(reserveLabel.getTextField().getText(),70,70);
                        node.getChildren().add(root);
                        firstUse = false;
                    }
                    else{
                        node.getChildren().remove(root);
                        root = configureBoxSelection(reserveLabel.getTextField().getText(),70,70);
                        node.getChildren().add(root);
                    }
                });
                break;

            case PENNELLOPEREGLOMISE: case ALESATOREPERLALAMINDADIRAME:  configureMovingWithoutRestrict(); break;

            case LATHEKIN: configureMovingWithRestrict(); break;

            case TAGLIERINACIRCOLARE:

                node = new VBox(30);
                node.setAlignment(Pos.TOP_CENTER);
                node.getChildren().addAll(setFontStyle(new Label(TITLERESERVE),30),reserveLabel.callReserve(), setFontStyle(new Label("Dadi disponibili nel tracciato: "), 20));
                node.getChildren().add(configureActionOnRound());
                break;

            case PENNELLOPERPASTASALDA:

                node = new VBox(15);
                node.setAlignment(Pos.CENTER);
                node.getChildren().addAll(setFontStyle(new Label(TITLERESERVE), 30),reserveLabel.callReserve());
                break;


            case RIGAINSUGHERO: case TENAGLIAAROTELLE: node = new VBox(20);
                node.setAlignment(Pos.CENTER);
                node.getChildren().addAll(toolSide.getAnchorPane(),
                        setFontStyle(new Label(TITLERESERVE), 20), reserveLabel.callReserve());
                break;

            case TAMPONEDIAMANTATO: case DILUENTEPERPASTASALDA: configureActionOnReserve(); break;


            case TAGLIERINAMANUALE:

                node = new VBox(10);
                node.setAlignment(Pos.CENTER);

                oldXFirstDie.setPrefSize(100,20);
                oldYFirstDie.setPrefSize(100,20);
                oldXSecondDie.setPrefSize(100,20);
                oldYSecondDie.setPrefSize(100,20);

                newXFirstDie.setPrefSize(100,20);
                newYFirstDie.setPrefSize(100,20);
                newXSecondDie.setPrefSize(100,20);
                newYSecondDie.setPrefSize(100,20);

                ArrayList<TextField> coordinateBoxFirstDie = new ArrayList<>(Arrays.asList(oldXFirstDie,oldYFirstDie,newXFirstDie, newYFirstDie));
                ArrayList<TextField> coordinateBoxSecondDie = new ArrayList<>(Arrays.asList(oldXSecondDie, oldYSecondDie, newXSecondDie,newYSecondDie));
                Label labelFirstDie = setFontStyle(new Label("Prima Dado:"),20);
                Label labelSecondDie = setFontStyle(new Label("Secondo Dado:"),20);
                labelSecondDie.setStyle("-fx-text-fill: grey;");
                VBox firstDie = new VBox(labelFirstDie,configureCoordinateInput(15d,10d,coordinateBoxFirstDie,false));
                VBox secondDie = new VBox(labelSecondDie, configureCoordinateInput(15d,10d, coordinateBoxSecondDie,true));

                setListener(coordinateBoxFirstDie,coordinateBoxSecondDie,labelSecondDie);
                HBox labelCoordinate = new HBox(firstDie, secondDie);
                labelCoordinate.setSpacing(20d);
                labelCoordinate.setAlignment(Pos.CENTER);
                node.getChildren().addAll(toolSide.getAnchorPane(), setFontStyle(new Label(TITLERESERVE), 20), reserveLabel.callReserve(),labelCoordinate);
                break;

        }

        return node;
    }





    /**
     * Metodo utilizzato poer la configurazione del contenuto delle finestre associate a ciascuna carta Utensile MultiParametro
     *
     * @param cardName Carta selezionata dal giocatore per l'attivazione
     * @param bisContent Contenuto informativo dell'attivazione MultiParametro
     * @return Riferimento al Parent che raccoglie tutti gli elementi grafici necessari per l'utilizzo della carta Utensile selezionata
     */

    public VBox configureNodeBis(String cardName,String bisContent){

        switch (cardName){

            case PENNELLOPERPASTASALDA:
                node = new VBox(15);
                node.setAlignment(Pos.TOP_CENTER);

                //Configurazione dei campi inserimento coordinate
                posX.setPromptText("Riga");
                posY.setPromptText("Colonna");
                posX.setPrefSize(100,20);
                posY.setPrefSize(100,20);

                VBox posDie = new VBox(setFontStyle(new Label("Posizione desiderata"), 20),posX,posY);
                posDie.setAlignment(Pos.CENTER);
                posDie.setSpacing(15);

                String dieSelected = reserveLabel.getDieName();
                ImageView dieExtract = configureImageView("/diePack/die-", dieSelected.replace(dieSelected.charAt(dieSelected.length()-1), bisContent.charAt(0)),".bmp",60,60);
                VBox imageDie = new VBox(setFontStyle(new Label("Dado estratto"), 20),dieExtract);
                imageDie.setAlignment(Pos.CENTER);
                imageDie.setSpacing(15);


                HBox labelDie = new HBox(20);
                labelDie.setAlignment(Pos.CENTER);
                labelDie.getChildren().addAll(imageDie,posDie);
                node.getChildren().addAll(setFontStyle(new Label("La tua carta Side"), 20),toolSide.getAnchorPane(), labelDie);
                break;


            case DILUENTEPERPASTASALDA:
                node = new VBox(25);
                node.setAlignment(Pos.TOP_CENTER);
                String lowerCase = bisContent.toLowerCase(Locale.ENGLISH);
                ImageView dieExtractItem = configureImageView("/diePack/die-",lowerCase + String.valueOf(1),".bmp" ,70, 70);
                node.getChildren().addAll(setFontStyle(new Label("Hai estratto il dado:"), 25), dieExtractItem);

                VBox selectionLabel = new VBox(15);
                HBox optionDie = new HBox(10);
                for(int i=1; i<7; i++){

                    StackPane button = new StackPane();
                    button.setPrefSize(70,70);
                    button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");
                    String pathDie = lowerCase + String.valueOf(i);
                    ImageView die = shadowEffect(configureImageView("/diePack/die-", pathDie, ".bmp",70,70));
                    button.getChildren().add(die);

                    cell.put(button, false);
                    die.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        if(!cell.get(button)){
                            button.getChildren().add(group);
                            cell.replace(button, false, true);
                        }
                        else {
                            button.getChildren().remove(group);
                            cell.replace(button, true, false);
                        }
                        this.dieChoose = String.valueOf(pathDie.charAt(pathDie.length()-1));
                    });

                    optionDie.getChildren().add(button);
                }

                selectionLabel.getChildren().addAll(setFontStyle(new Label("Scegli il valore:"), 25), optionDie);
                selectionLabel.setAlignment(Pos.CENTER);
                node.getChildren().add(selectionLabel);
                break;

        }

        return node;
    }




    /**
     * Metodo di supporto per la configurazione del contenuto delle finestre associate alla carte Utensili 2 e 3,le quali richiedono che il giocatore interagisca
     * con la carta Side per lo spostamento di un dado
     *
     */

    private void configureMovingWithoutRestrict(){

        node = new VBox(10);
        node.setAlignment(Pos.CENTER);

        ArrayList<TextField> coordinateBox = new ArrayList<>(Arrays.asList(oldXFirstDie,oldYFirstDie,newXFirstDie,newYFirstDie));
        Label labelText = setFontStyle(new Label("Dado: "), 22);
        VBox oldPosition = new VBox(labelText, configureCoordinateInput(15d,10d, coordinateBox,false));
        HBox labelCoordinate = new HBox(oldPosition);
        labelCoordinate.setSpacing(40d);
        labelCoordinate.setAlignment(Pos.CENTER);

        node.getChildren().addAll(toolSide.getAnchorPane(),labelCoordinate);
    }




    /**
     * Metodo di supporto per la configurazione dei campi inserimento coordinate nelle varie finestre dove è richiesto.  In particolare nella carta Utensile 4
     *
     */

    private HBox configureCoordinateInput(Double spacingVBox, Double spacingHBox, ArrayList<TextField> coordonateBox, Boolean isDisabled){

        coordonateBox.get(0).setPromptText("Riga");
        coordonateBox.get(1).setPromptText("Colonna");
        coordonateBox.get(2).setPromptText("Riga");
        coordonateBox.get(3).setPromptText("Colonna");

        if(isDisabled){
            coordonateBox.get(0).setDisable(true);
            coordonateBox.get(1).setDisable(true);
            coordonateBox.get(2).setDisable(true);
            coordonateBox.get(3).setDisable(true);
        }

        HBox coordinateFirstDie = new HBox(spacingHBox);
        coordinateFirstDie.setAlignment(Pos.CENTER);
        ImageView arrow = configureImageView("/iconPack/","icon-arrow-2", ".png", 64,64);

        VBox oldPositionFirstDie = new VBox(spacingVBox);
        oldPositionFirstDie.getChildren().addAll(coordonateBox.get(0), coordonateBox.get(1));

        VBox newPositionFirstDie = new VBox(spacingVBox);
        newPositionFirstDie.getChildren().addAll(coordonateBox.get(2),coordonateBox.get(3));

        coordinateFirstDie.getChildren().addAll(oldPositionFirstDie,arrow,newPositionFirstDie);
        coordinateFirstDie.setAlignment(Pos.CENTER);
        return coordinateFirstDie;
    }




    /**
     * Metodo di supporto per la configurazione del contenuto delle finestre associate alla carte Utensili 4 e 12,le quali richiedono che il giocatore interagisca
     * con la carta Side per lo spostamento di un dado
     *
     */

    private void configureMovingWithRestrict(){
        node = new VBox(10);
        node.setAlignment(Pos.CENTER);

        oldXFirstDie.setPromptText("Riga");
        oldYFirstDie.setPromptText("Colonna");
        newXFirstDie.setPromptText("Riga");
        newYFirstDie.setPromptText("Colonna");

        oldXSecondDie.setPromptText("Riga");
        oldYSecondDie.setPromptText("Colonna");
        newXSecondDie.setPromptText("Riga");
        newYSecondDie.setPromptText("Colonna");

        oldXFirstDie.setPrefSize(100,20);
        oldYFirstDie.setPrefSize(100,20);
        oldXSecondDie.setPrefSize(100,20);
        oldYSecondDie.setPrefSize(100,20);

        newXFirstDie.setPrefSize(100,20);
        newYFirstDie.setPrefSize(100,20);
        newXSecondDie.setPrefSize(100,20);
        newYSecondDie.setPrefSize(100,20);

        ArrayList<TextField> coordinateBoxFirstDie = new ArrayList<>(Arrays.asList(oldXFirstDie,oldYFirstDie,newXFirstDie, newYFirstDie));
        ArrayList<TextField> coordinateBoxSecondDie = new ArrayList<>(Arrays.asList(oldXSecondDie, oldYSecondDie, newXSecondDie,newYSecondDie));
        Label labelFirstDie = setFontStyle(new Label("Prima Dado:"),20);
        VBox firstDie = new VBox(labelFirstDie, configureCoordinateInput(15d,10d, coordinateBoxFirstDie,false));
        Label labelSecondDie = setFontStyle(new Label("Secondo Dado:"),20);
        VBox secondDie = new VBox(labelSecondDie, configureCoordinateInput(15d,10d, coordinateBoxSecondDie,false));


        HBox labelCoordinate = new HBox(firstDie, secondDie);
        labelCoordinate.setSpacing(40d);
        labelCoordinate.setAlignment(Pos.CENTER);


        node.getChildren().addAll(toolSide.getAnchorPane(),labelCoordinate);
    }




    /**
     * Metodo di supporto per la configurazione del contenuto delle finestre associate alla carte Utensili 10 e 11,le quali richiedono che il giocatore interagisca
     * con Riserva attualmente disponibile nel Turno di gioco
     *
     */

    private void configureActionOnReserve(){
        node = new VBox(20);
        node.setAlignment(Pos.CENTER);
        node.getChildren().addAll(setFontStyle(new Label(TITLERESERVE),25),reserveLabel.callReserve());
    }




    /**
     * Metodo di supporto per la configurazione del contenuto della finestra dedicata alla carta Utensile 1. Si tratta di uno Slider che visualizza, partendo da un dado selezionato
     * dalla riserva, le immagini corrispondenti ai dadi con valore decrementato o incrementato di 1 (ove possibile)
     *
     * @param path Nome della risorsa corrispondente al dado di cui si vuole modificare il valore
     * @param prefWidth Larghezza del dado da visualizzare
     * @param prefHight Altezza del dado da visualizzare
     * @return Riferimento al Parent della finesta della carta Utensile 1
     */

    private StackPane configureBoxSelection(String path, int prefWidth, int prefHight) {
        char lastIndex = path.charAt(path.length()-1);
        int increment = 1;
        StackPane rootLabel = new StackPane();
        ImageView dieInit = configureImageDie(path, increment, prefWidth, prefHight, false, false);
        ImageView dieLeft = null;
        if((path.charAt(path.length()-1)!='1')) dieLeft = configureImageDie(path, increment, prefWidth, prefHight, true, false);
        ImageView dieRight = null;
        if((path.charAt(path.length()-1)!='6')) dieRight = configureImageDie(path, increment, prefWidth, prefHight, true, true);
        HBox view1 = new HBox(20);
        HBox view2 = new HBox(20);
        HBox view3 = new HBox(20);
        VBox labelView1 = configureNodeBox("Scegli il tuo valore:",25,25,view1);
        VBox labelView2 = configureNodeBox("Scegli il tuo valore:",25,25,view2);
        VBox labelView3 = configureNodeBox("Scegli il tuo valore:",25,25,view3);
        rootLabel.setStyle("-fx-background-color: transparent;");
        switch (lastIndex) {

            case '1':
                rootLabel.getChildren().add(labelView1);
                view1.getChildren().addAll(setOpacity(setRotation(180d), 0.6),dieInit, shadowEffect(setActionOnImage(labelView2, labelView1, rootLabel, 0d,true,dieRight)));
                view1.setAlignment(Pos.CENTER);

                view2.getChildren().addAll(shadowEffect(setActionOnImage(labelView1, labelView2, rootLabel, 180d,true,dieInit)),dieRight, setOpacity(setRotation(0d), 0.6));
                view2.setAlignment(Pos.CENTER);
                break;

            case '6':
                rootLabel.getChildren().add(labelView2);
                view1.getChildren().addAll(setOpacity(setRotation(180d), 0.6), dieLeft, shadowEffect(setActionOnImage(labelView2, labelView1, rootLabel, 0d,true,dieInit)));
                view1.setAlignment(Pos.CENTER);

                view2.getChildren().addAll(shadowEffect(setActionOnImage(labelView1, labelView2, rootLabel, 180d,true,dieLeft)), dieInit, setOpacity(setRotation(0d), 0.6));
                view2.setAlignment(Pos.CENTER);
                break;


            default:
                rootLabel.getChildren().add(labelView2);
                view1.getChildren().addAll(setOpacity(setRotation(180d),0.6),dieLeft,shadowEffect(setActionOnImage(labelView2,labelView1,rootLabel,0d,true,dieInit)));
                view1.setAlignment(Pos.CENTER);

                view2.getChildren().addAll(shadowEffect(setActionOnImage(labelView1,labelView2,rootLabel,180d,true,dieLeft)),dieInit,shadowEffect(setActionOnImage(labelView3,labelView2,rootLabel,0d,true,dieRight)));
                view2.setAlignment(Pos.CENTER);

                view3.getChildren().addAll(shadowEffect(setActionOnImage(labelView2,labelView3,rootLabel,180d,true,dieInit)),dieRight,setOpacity(setRotation(0d),0.6));
                view3.setAlignment(Pos.CENTER);
                break;
        }

        return rootLabel;
    }




    /**
     * Metodo di supporto per la configurazione del contenuto informativo della finestra dedicata alla carta Utensile 1.
     *
     * @param path Nome della risorsa corrispondente al dado di cui si vuole modificare il valore
     * @param increment Quantitativo da incrementare (valori possibili +1)
     * @param prefWidth Larghezza del dado da visualizzare
     * @param prefHight Altezza del dado da visualizzare
     * @param activateEffect Booleano del valore TRUE se si intende attivare l'effetto di incremento, FALSE se invece si vuole semolicemnte ricreare un'immagine del dado selezionato
     * @param mustBeIncremented Booleano del valore TRUE se il valore 'increment' va sommato, altrimenti FALSE nel caso dovesse essere sottratto
     * @return Riferimento all'elemento grafico contenente l'immagine modificata del dado
     */

    private ImageView configureImageDie(String path, int increment, int prefWidth, int prefHight, boolean activateEffect, boolean mustBeIncremented){
        Image item;
        ImageView die;
        if(activateEffect){
            String dieInfo;
            char lastIndex = path.charAt(path.length()-1);
            if(mustBeIncremented){
                dieInfo = path.replace(lastIndex, String.valueOf(Integer.parseInt(String.valueOf(lastIndex))+increment).charAt(0));
                item = new Image("diePack/die-" + dieInfo + ".bmp", prefWidth,prefHight, false, true);
                die = new ImageView(item);
                die.setUserData("1");

            }
            else {
                char  substitute = String.valueOf(Integer.parseInt(String.valueOf(lastIndex))-increment).charAt(0);
                dieInfo = path.replace(lastIndex, substitute);
                item = new Image("diePack/die-" + dieInfo + ".bmp", prefWidth,prefHight, false, true);
                die = new ImageView(item);
                die.setUserData("0");
            }
        }
        else {
            item =  new Image("diePack/die-" + path + ".bmp", prefWidth,prefHight, false, true);
            die = new ImageView(item);
            die.setUserData("invalidate");
        }


        return die;
    }


    /**
     * Metodo di supporto per la configurazione del contenuto informativo della finestra dedicata alla carta Utensile 1.
     *
     * @param title Intestazione dell'elemento grafico
     * @param sizeTitle Dimensioni dell'intestazione
     * @param space Spacing Verticale tra i vari elementi
     * @param node Riferimento al nodo da inserire nell'elemento grafico
     * @return Riferimento al Parent della finestra
     */

    private VBox configureNodeBox(String title, int sizeTitle, int space, Node node){
        VBox item = new VBox(space);
        item.setAlignment(Pos.CENTER);
        item.getChildren().addAll(setFontStyle(new Label(title),sizeTitle),node);
        return item;
    }




    /**
     * Metodo di supporto per la configurazione del contenuto informativo della finestra dedicata alla carta Utensile 5.
     *
     * @return Riferimento al Parent della finestra
     */

    private VBox configureActionOnRound(){

        ArrayList<HBox> hBoxCollection = (ArrayList<HBox>)RoundLabel.callRoundLable();
        HBox layoutRound = new HBox(5);
        layoutRound.setAlignment(Pos.CENTER);
        VBox root = new VBox(layoutRound);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(40d);
        for(int i=0; i<hBoxCollection.size(); i++){
            StackPane roundButton = new StackPane();
            roundButton.setPrefSize(60,60);
            ImageView roundBack = shadowEffect(configureImageView("/roundPack/","round-" + String.valueOf(i+1), ".png",190,190));
            roundBack.setFitHeight(60);
            roundBack.setFitWidth(60);
            int finalI = i;
            roundBack.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
                if(firstClick) {
                    roundDice = hBoxCollection.get(finalI);
                    root.getChildren().add(roundDice);
                    firstClick = false;
                }
                else {
                    root.getChildren().remove(roundDice);
                    roundDice = hBoxCollection.get(finalI);
                    root.getChildren().add(roundDice);
                }
            });
            roundButton.getChildren().add(roundBack);
            layoutRound.getChildren().add(roundButton);
        }

        return root;
    }




    /**
     * Metodo di supporto per la configurazione del contenuto informativo della finestra dedicata alla carta Utensile 12. Permette di aggiungere un listener per ciascun
     * campo di inserimento delle coordinate, in modo tale che vengano riempiti correttamente.
     *
     * @param listening Collezione di oggetti disponibili ad essere ascoltati
     * @param listened Collezione di oggetti su cui aggiungere un listener
     * @param label Elemento grafico di intestazione
     */

    private void setListener(ArrayList<TextField> listening, ArrayList<TextField> listened, Label label){
        for (TextField text:listening) {
            text.textProperty().addListener((ov, oldTg, newTg) -> {
                if(!oldXFirstDie.getText().trim().isEmpty() && !oldYFirstDie.getText().trim().isEmpty() && !newXFirstDie.getText().trim().isEmpty() && !newYFirstDie.getText().trim().isEmpty()){
                    for (TextField textListened: listened) {
                        textListened.setDisable(false);
                    }
                    label.setStyle("-fx-text-fill: black;");
                }
            });

        }
    }






}
