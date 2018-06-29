package it.polimi.se2018.graphic.alert_utensils;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.graphic.ReserveLabel;
import it.polimi.se2018.graphic.RoundLabel;
import it.polimi.se2018.graphic.SideCardLabel;
import it.polimi.se2018.graphic.adapterGUI.AdapterResolution;
import it.polimi.se2018.graphic.alert_box.AlertValidation;
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

import static it.polimi.se2018.graphic.RoundLabel.*;
import static it.polimi.se2018.graphic.Utility.*;
import static it.polimi.se2018.graphic.alert_box.AlertInfoCard.*;

public class SelectorContent {

    private static final String PINZASGROSSATRICE = "Pinza Sgrossatrice";
    private static final String PENNELLOPEREGLOMISE = "Pennello Per Eglomise";
    private static final String ALESATOREPERLALAMINDADIRAME = "Alesatore Per Lamina Di Rame";
    private static final String LATHEKIN = "Lathekin";
    private static final String TAGLIERINACIRCOLARE = "Taglierina Circolare";
    private static final String PENNELLOPERPASTASALDA = "Pennello Per Pasta Salda";
    private static final String MARTELLETTO = "Martelletto";
    private static final String TENAGLIAAROTELLE = "Tenaglia A Rotelle";
    private static final String RIGAINSUGHERO = "Riga In Sughero";
    private static final String TAMPONEDIAMANTATO = "Tampone Diamantato";
    private static final String DILUENTEPERPASTASALDA = "Diluente Per Pasta Salda";
    private static final String TAGLIERINAMANUALE = "Taglierina Manuale";
    private static final String DILUENTEPERPASTASALDABIS = "Diluente Per Pasta Salda Bis";
    private static final String TITLERESERVE = "Riserva: ";


    private VBox node;
    private ReserveLabel reserveLabel;
    private ConnectionHandler connectionHandler;
    private String cardSelection;
    private SideCardLabel playerSide;
    private AdapterResolution adapter;


    //UTENSILE 1
    private Boolean firstUse = true;
    private StackPane root;

    //UTENSILE 2
    private TextField oldX = new TextField();
    private TextField oldY = new TextField();
    private TextField newX = new TextField();
    private TextField newY = new TextField();

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

    //UTENSILE11
    private static String dieExtract;
    private String dieChoose;
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private Group group;


    public SelectorContent(ReserveLabel reserveLabel, ConnectionHandler connectionHandler, String cardselection, SideCardLabel playerSide, AdapterResolution adapterResolution){
        this.reserveLabel = reserveLabel;
        this.connectionHandler = connectionHandler;
        this.cardSelection = cardselection;
        this.playerSide = playerSide;
        this.adapter = adapterResolution;
        Rectangle rect = new Rectangle(20, 20, 70, 70);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(2d);
        group = new Group(rect);
    }



    public void configureMessage(String cardName, Map<String, String> dictionaryUtensils, String keyNameOfCard){

        switch (cardName){
            case PINZASGROSSATRICE:
                if(!oldX.getText().trim().isEmpty() && !oldY.getText().trim().isEmpty() && !newX.getText().trim().isEmpty() && !newY.getText().trim().isEmpty()) connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(getInfoData(),reserveLabel.getPos()))));
                else AlertValidation.display("Errore", "Inserisci correttamente le coordinate\ndel dado da spostare!");
                break;

            case PENNELLOPEREGLOMISE: case ALESATOREPERLALAMINDADIRAME:

                connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(oldX.getText(),oldY.getText(),newX.getText(),newY.getText()))));
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

            case DILUENTEPERPASTASALDABIS:

                connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Collections.singletonList(dieChoose))));
                break;

            case TAGLIERINAMANUALE:

                if(!oldXFirstDie.getText().trim().isEmpty() && !oldYFirstDie.getText().trim().isEmpty() && !newXFirstDie.getText().trim().isEmpty() && !newYFirstDie.getText().trim().isEmpty()){
                    if(!oldXSecondDie.getText().trim().isEmpty() && !oldYSecondDie.getText().trim().isEmpty() && !newXSecondDie.getText().trim().isEmpty() && !newYSecondDie.getText().trim().isEmpty())
                        connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(oldXFirstDie.getText(),oldYFirstDie.getText(),newXFirstDie.getText(),newYFirstDie.getText(),oldXSecondDie.getText(),oldYSecondDie.getText(),newXSecondDie.getText(),newYSecondDie.getText()))));

                    else connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, dictionaryUtensils.get(keyNameOfCard), new ArrayList<>(Arrays.asList(oldXFirstDie.getText(),oldYFirstDie.getText(),newXFirstDie.getText(),newYFirstDie.getText()))));

                }

                 break;
        }

    }

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
                node.setAlignment(Pos.TOP_CENTER);
                node.getChildren().addAll(setFontStyle(new Label(TITLERESERVE), 30),reserveLabel.callReserve());
                break;

            case RIGAINSUGHERO: case TENAGLIAAROTELLE: node = new VBox(20);
                node.setAlignment(Pos.CENTER);
                node.getChildren().addAll(playerSide.callPlayerSide(playerSide.getPathCard(), connectionHandler.getNickname(),true,adapter).getAnchorPane(),
                        setFontStyle(new Label(TITLERESERVE), 20), reserveLabel.callReserve());
                break;

            case TAMPONEDIAMANTATO: case DILUENTEPERPASTASALDA: configureActionOnReserve(); break;

            case DILUENTEPERPASTASALDABIS:
                node = new VBox(25);
                node.setAlignment(Pos.TOP_CENTER);
                ImageView dieExtractItem = configureDieView(dieExtract + String.valueOf(1) ,70, 70);
                node.getChildren().addAll(setFontStyle(new Label("Hai estratto il dado:"), 25), dieExtractItem);

                VBox selectionLabel = new VBox(15);
                HBox optionDie = new HBox(10);
                for(int i=1; i<7; i++){

                    StackPane button = new StackPane();
                    button.setPrefSize(70,70);
                    button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");
                    String pathDie = dieExtract + String.valueOf(i);
                    ImageView die = shadowEffect(configureImageView("/diePack/", "die-" + pathDie, ".bmp",70,70));
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
                        this.dieChoose = pathDie;
                    });

                    optionDie.getChildren().add(button);
                }

                selectionLabel.getChildren().addAll(setFontStyle(new Label("Scegli il valore:"), 25), optionDie);
                selectionLabel.setAlignment(Pos.CENTER);
                node.getChildren().add(selectionLabel);
                break;


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
                node.getChildren().addAll(playerSide.callPlayerSide(playerSide.getPathCard(), connectionHandler.getNickname(),true,adapter).getAnchorPane(),
                        setFontStyle(new Label(TITLERESERVE), 20), reserveLabel.callReserve(),labelCoordinate);
                break;

        }

        return node;
    }






    //METODI CONFIGURAZIONE PER LE CARTE UTENSILI 2
    private void configureMovingWithoutRestrict(){

        node = new VBox(10);
        node.setAlignment(Pos.CENTER);

        ArrayList<TextField> coordinateBox = new ArrayList<>(Arrays.asList(oldX,oldY,newX,newY));
        Label labelText = setFontStyle(new Label("Dado: "), 22);
        VBox oldPosition = new VBox(labelText, configureCoordinateInput(15d,10d, coordinateBox,false));
        HBox labelCoordinate = new HBox(oldPosition);
        labelCoordinate.setSpacing(40d);
        labelCoordinate.setAlignment(Pos.CENTER);

        node.getChildren().addAll(playerSide.callPlayerSide(playerSide.getPathCard(), connectionHandler.getNickname(),false,adapter).getAnchorPane(),labelCoordinate);
    }

    private void configureMovingWithRestrict(){
        node = new VBox(10);
        node.setAlignment(Pos.CENTER);

        oldXFirstDie.setPromptText("X");
        oldYFirstDie.setPromptText("Y");
        newXFirstDie.setPromptText("X");
        newYFirstDie.setPromptText("Y");

        oldXSecondDie.setPromptText("X");
        oldYSecondDie.setPromptText("Y");
        newXSecondDie.setPromptText("X");
        newYSecondDie.setPromptText("Y");

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


        node.getChildren().addAll(playerSide.callPlayerSide(playerSide.getPathCard(),connectionHandler.getNickname(),true,adapter).getAnchorPane(),labelCoordinate);
    }

    private void configureActionOnReserve(){
        node = new VBox(20);
        node.setAlignment(Pos.CENTER);
        node.getChildren().addAll(setFontStyle(new Label(TITLERESERVE),25),reserveLabel.callReserve());
    }

    private HBox configureCoordinateInput(Double spacingVBox, Double spacingHBox, ArrayList<TextField> coordonateBox, Boolean isDisabled){

        coordonateBox.get(0).setPromptText("X");
        coordonateBox.get(1).setPromptText("Y");
        coordonateBox.get(2).setPromptText("X");
        coordonateBox.get(3).setPromptText("Y");

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


    //METODI CONFIGURAZIONE PER LA CARTA UTENSILE 1
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

    private StackPane configureBoxSelection(String path, int prefWidth, int prefhight) {
        char lastIndex = path.charAt(path.length()-1);
        int increment = 1;
        StackPane rootLabel = new StackPane();
        ImageView dieInit = configureImageDie(path, increment, prefWidth, prefhight, false, false);
        ImageView dieLeft = null;
        if((path.charAt(path.length()-1)!='1')) dieLeft = configureImageDie(path, increment, prefWidth, prefhight, true, false);
        ImageView dieRight = null;
        if((path.charAt(path.length()-1)!='6')) dieRight = configureImageDie(path, increment, prefWidth, prefhight, true, true);
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

    private VBox configureNodeBox(String title, int sizeTitle, int space, Node node){
        VBox item = new VBox(space);
        item.setAlignment(Pos.CENTER);
        item.getChildren().addAll(setFontStyle(new Label(title),sizeTitle),node);
        return item;
    }


    //METODI CONFIGURAZIONE PER LA CARTA UTENSILE 5
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


    //METODI CONFIGURAZIONE PER LA CARTA UTENSILE 11
    public static void setDieChoose(String dieExtract) {
        SelectorContent.dieExtract = dieExtract;
    }


    //METODI CONFIGURAZIONE PER LA CARTA UTENSILE 12
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
