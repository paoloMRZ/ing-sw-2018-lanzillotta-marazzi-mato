package it.polimi.se2018.graphic.alert_utensils;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.graphic.ReserveLabel;
import it.polimi.se2018.graphic.SideCardLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;

import static it.polimi.se2018.graphic.Utility.setFontStyle;

public class SelectorContent {

    private static final String PINZASGROSSATRICE = "Pinza Sgrossatrice";
    private static final String PENNELLOPEREGLOMISE = "Pennello Per Eglomise";
    private static final String ALESATOREPERLALAMINDADIRAME = "Alesatore Per La Laminda Di Rame";
    private static final String LATHEKIN = "Lathekin";
    private static final String TAGLIERINACIRCOLARE = "Taglierina Circolare";
    private static final String PENNELLOPERPASTASALDA = "Pennello Per Pasta Salda";
    private static final String MARTELLETTO = "Martelletto";
    private static final String TENAGLIAAROTELLE = "Tenaglia A Rotelle";
    private static final String RIGAINSUGHERO = "Riga In Sughero";
    private static final String TAMPONEDIAMANTATO = "Tampone Diamantato";
    private static final String DILUENTEPERPASTASALDA = "Diluente Per Pasta Salda";
    private static final String TAGLIERINAMANUALE = "taglierina Manuale";
    private static final String STRIPCUTTER = "Strip Cutter";

    private String message;
    private VBox node;
    private ReserveLabel reserveLabel;
    private ConnectionHandler connectionHandler;
    private String cardSelection;
    private SideCardLabel playerSide;

    //UTENSILE 2
    private TextField oldX;
    private TextField oldY;
    private TextField newX;
    private TextField newY;


    public SelectorContent(ReserveLabel reserveLabel, ConnectionHandler connectionHandler, String cardselection, SideCardLabel playerSide){
        this.reserveLabel = reserveLabel;
        this.connectionHandler = connectionHandler;
        this.cardSelection = cardselection;
        this.playerSide = playerSide;
    }


    public void configureMessage(String cardName){

        switch (cardName){
            case PINZASGROSSATRICE: connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, new ArrayList<>(Arrays.asList(reserveLabel.getPos()))));
                                    break;
            case PENNELLOPEREGLOMISE: connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, new ArrayList<>(Arrays.asList(oldX.getText(),oldY.getText(),newX.getText(),newY.getText()))));
                                      break;
            case ALESATOREPERLALAMINDADIRAME: connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), cardSelection, new ArrayList<>(Arrays.asList(oldX.getText(),oldY.getText(),newX.getText(),newY.getText()))));
                break;
        }

    }

    public VBox configureNode(String cardName){

        switch (cardName){
            case PINZASGROSSATRICE: node = new VBox(15);
                                    node.setAlignment(Pos.CENTER);
                                    Label answer = setFontStyle(new Label("Scegli il dado di cui vuoi cambiare\n il valore:"), 30);
                                    answer.setTextAlignment(TextAlignment.CENTER);
                                    node.getChildren().addAll(answer,reserveLabel.callReserve());
                                    break;

            case PENNELLOPEREGLOMISE:  configureMovingWithoutRestrict(); break;

            case ALESATOREPERLALAMINDADIRAME:  configureMovingWithoutRestrict(); break;
        }
        return node;
    }



    public void configureMovingWithoutRestrict(){

        node = new VBox(10);
        node.setAlignment(Pos.CENTER);
        Label answer2 = setFontStyle(new Label("Indica nele caselle sottostanti le \ncoordinate del dado che vuoi\n spostare e la sua destinazione:"), 28);
        answer2.setTextAlignment(TextAlignment.CENTER);

        oldX = new TextField();
        oldY = new TextField();
        newX = new TextField();
        newY = new TextField();
        oldX.setPromptText("X");
        oldY.setPromptText("Y");
        newX.setPromptText("X");
        newY.setPromptText("Y");

        VBox oldPosition = new VBox(setFontStyle(new Label("Dado: "),22),oldX,oldY);
        oldPosition.setSpacing(15d);
        oldPosition.setAlignment(Pos.CENTER);

        VBox newPosition = new VBox(setFontStyle(new Label("Nuova Posizione: "),22),newX,newY);
        newPosition.setSpacing(15d);
        newPosition.setAlignment(Pos.CENTER);

        HBox labelCoordinate = new HBox(oldPosition,newPosition);
        labelCoordinate.setSpacing(40d);
        labelCoordinate.setAlignment(Pos.CENTER);

        ArrayList<Integer> sizeGridPlayer = new ArrayList<>(Arrays.asList(65, 65));
        ArrayList<Integer> sizeSidePlayer = new ArrayList<>(Arrays.asList(330, 293));
        ArrayList<Double> positionGridPlayer = new ArrayList<>(Arrays.asList(0d, 0d, 0d, 18d));

        node.getChildren().addAll(answer2,playerSide.callPlayerSide(playerSide.getPathCard(),/*connectionHandler.getNickname()*/"Simone",sizeGridPlayer,positionGridPlayer,sizeSidePlayer,false,reserveLabel).getAnchorPane(),labelCoordinate);
    }
}
