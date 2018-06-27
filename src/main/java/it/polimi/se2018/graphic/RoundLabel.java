package it.polimi.se2018.graphic;

import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static it.polimi.se2018.graphic.Utility.*;

public class RoundLabel{

    private AnchorPane anchorRound;
    private GridPane labelRoundGrid;
    private int column = 0;
    private static ArrayList<HBox> roundHistory= new ArrayList<>();
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private Group group;
    private static String dieFromRoundSelected;
    private static int roundNumber = 0;


    RoundLabel(int widthGrid, int heightGrid, int widthImageGrid, int heightImageGrid, List<Double> positiongrid){
        configureRoundGrid(widthGrid,heightGrid, widthImageGrid, heightImageGrid, positiongrid);
        Rectangle rect = new Rectangle(20, 20, 55, 55);
        group = new Group(rect);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(2d);
    }

    private void configureRoundGrid(int widthGrid, int heightGrid, int widthImageGrid, int heightImageGrid,List<Double> positiongrid) {

        //Creo l'anchorPane che accoglierà background (l'immagine del pannello del round) e la griglia posta su di esso
        anchorRound = new AnchorPane();
        anchorRound.setStyle("-fx-background-size: 700; -fx-background-image: url(roundgrid.png); -fx-background-position: center; -fx-background-repeat: no-repeat;");

        //Creo la griglia
        labelRoundGrid = new GridPane();
        labelRoundGrid.setAlignment(Pos.CENTER_RIGHT);
        labelRoundGrid.setHgap(0d);
        labelRoundGrid.setVgap(0d);
        for (int i = 0; i < 10; i++) {
            StackPane button = new StackPane();
            button.setPrefSize(widthGrid, heightGrid);
            button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");
            labelRoundGrid.add(button, i, 0);
        }
        configureAnchorPane(anchorRound,labelRoundGrid,positiongrid.get(0),positiongrid.get(1),positiongrid.get(2),positiongrid.get(3));
    }


    public void proceedRound(List<String> dieInfo, int widthImage, int heightImage){
        ImageView passed = shadowEffect(configureImageView("/iconPack/", "icon-divieto",".png",widthImage,heightImage));

        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();
        passed.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> contextMenu.show(passed, Side.BOTTOM, e.getScreenX(),e.getScreenY()));

        HBox hBox = new HBox(5);
        HBox hBoxHistory = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        hBoxHistory.setAlignment(Pos.CENTER);
        for (String aDieInfo : dieInfo) {
            String lowerDieInfo = aDieInfo.toLowerCase(Locale.ENGLISH);
            hBox.getChildren().addAll(new ImageView(new Image("/diePack/die-" + lowerDieInfo + ".bmp", 40, 40, false, true)));

            ImageView elementHistory = shadowEffect(new ImageView(new Image("/diePack/die-" + lowerDieInfo + ".bmp", 55, 55, false, true)));
            StackPane button = new StackPane(elementHistory);
            cell.put(button, false);
            elementHistory.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        if(!cell.get(button)){
                            button.getChildren().add(group);
                            cell.replace(button, false, true);
                        }
                        else {
                            button.getChildren().remove(group);
                            cell.replace(button, true, false);
                        }

                        dieFromRoundSelected = String.valueOf(dieInfo.indexOf(aDieInfo));

            });
            hBoxHistory.getChildren().addAll(button);
        }

        roundHistory.add(hBoxHistory);
        roundNumber++;
        MenuItem item = new MenuItem("", hBox);
        contextMenu.getItems().add(item);
        passed.setFitHeight(60d);
        passed.setFitWidth(60d);
        labelRoundGrid.add(passed, 9 - column, 0);
        column++;
    }

    public GridPane getLabelRoundGrid() {
        return labelRoundGrid;
    }

    public AnchorPane getAnchorRound() {
        return anchorRound;
    }

    public static List<HBox> callRoundLable(){
        return roundHistory;
    }

    public static String getDieFromRoundSelected() {
        return dieFromRoundSelected;
    }

    public static int getRoundNumber() {
        return roundNumber;
    }
}
