package it.polimi.se2018.graphic;

import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;

import static it.polimi.se2018.graphic.Utility.*;

public class RoundLabel{

    private AnchorPane anchorRound;
    private GridPane labelRoundGrid;
    private int column = 0;


    RoundLabel(int widthGrid, int heightGrid){
        configureRoundGrid(widthGrid,heightGrid);
    }

    private void configureRoundGrid(int widthGrid, int heightGrid) {

        //Creo lo anchorePane che accoglierà background (l'immagine del pannello del round) e la griglia posta su di esso
        anchorRound = new AnchorPane();
        anchorRound.setPrefSize(910,90);
        anchorRound.setBackground(Utility.configureBackground("roundgrid",910,90));

        //Creo la griglia
        labelRoundGrid = new GridPane();
        labelRoundGrid.setPrefSize(910, 90);
        labelRoundGrid.setAlignment(Pos.CENTER_RIGHT);
        labelRoundGrid.setHgap(0d);
        labelRoundGrid.setVgap(0d);
        for (int i = 0; i < 10; i++) {
            //Set Griglia invisibile
            StackPane button = new StackPane();
            button.setPrefSize(widthGrid, heightGrid);
            button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");
            labelRoundGrid.add(button, i, 0);
        }
        configureAnchorPane(anchorRound,labelRoundGrid,70d,0d,15d,0d);
    }



    public void proceedRound(List<String> dieInfo){
        ImageView passed = Utility.shadowEffect(Utility.configureImageView("iconPack/icon-divieto",120,70));

        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();
        passed.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> contextMenu.show(passed, Side.BOTTOM, e.getScreenX(),e.getScreenY()));

        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        for (String aDieInfo : dieInfo) {
            hBox.getChildren().addAll(new ImageView(new Image("diePack/die-" + aDieInfo + ".bmp", 50, 50, false, true)));
        }

        MenuItem item = new MenuItem("", hBox);
        contextMenu.getItems().add(item);
        passed.setFitHeight(90d);
        passed.setFitWidth(91d);
        labelRoundGrid.add(passed, 9 - column, 0);
        column++;
    }

    public GridPane getLabelRoundGrid() {
        return labelRoundGrid;
    }

    public AnchorPane getAnchorRound() {
        return anchorRound;
    }
}
