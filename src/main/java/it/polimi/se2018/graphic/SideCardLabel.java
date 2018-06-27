package it.polimi.se2018.graphic;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.polimi.se2018.graphic.Utility.*;


public class SideCardLabel{

    private String nickName;
    private String posX;
    private String posY;
    private AnchorPane anchorPane;
    private GridPane gridPane;
    private ReserveLabel reserve;
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private Group group;
    private ArrayList<String> dicePutHistory = new ArrayList<>();
    private String pathCard;

    //DIMENSIONE DADO SIDE Player: 40x40
    //DIMENSIONE DADO SIDE Enemies: 33x33

    SideCardLabel(String sideCard, String nickName, List<Integer> sizeGrid,  List<Double> positionGrid, List<Integer> sizeSide, List<Double> sizeRect, boolean includeShadowGrid, ReserveLabel reserve){

        this.reserve = reserve;
        this.nickName = nickName;
        this.pathCard = sideCard;

        //Creo la griglia
        setGridSide(sizeGrid.get(0),sizeGrid.get(1), includeShadowGrid);

        //Creo l'immagine di background e Aggiungo la gridPane all'anchorPane
        anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-image: url(cardPack/" + sideCard + ".png); -fx-background-size:" + String.valueOf(sizeSide.get(0)) + " " + String.valueOf(sizeSide.get(1)) + "; -fx-background-position: center; -fx-background-repeat: no-repeat;");
        anchorPane.setPrefSize(sizeSide.get(0),sizeSide.get(1));
        configureAnchorPane(anchorPane,gridPane,positionGrid.get(0),positionGrid.get(1),positionGrid.get(2),positionGrid.get(3));

        if(includeShadowGrid) {
            group = new Group();
            Rectangle rect = new Rectangle(20, 20, sizeRect.get(0), sizeRect.get(1));
            rect.setFill(Color.TRANSPARENT);
            rect.setStroke(Color.RED);
            rect.setStrokeWidth(2d);
            group.getChildren().add(rect);
        }
    }

    private void setGridSide(int width, int height, boolean includeShadowGrid){
        gridPane = new GridPane();
        for(int i=0; i<4; i++){
            for(int j=0;j<5;j++){
                StackPane button = new StackPane();
                button.setAlignment(Pos.CENTER);
                button.setPrefSize(width,height);
                button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");
                if(includeShadowGrid) {
                    cell.put(button, false);
                    button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setStyle("-fx-background-color: rgba(83, 83, 83, 0.5);"));
                    button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setStyle("-fx-background-color: transparent;"));
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        setFocusStyle(cell,button,group);
                        posX = String.valueOf(GridPane.getRowIndex(button));
                        posY = String.valueOf(GridPane.getColumnIndex(button));
                    });
                }
                gridPane.add(button, j, i);
            }
        }

        gridPane.setAlignment(Pos.CENTER);
    }

    public void updateSideAfterPut(List<String> cellUpdate, int requestedWidth, int requestedHeight){
            dicePutHistory.clear();
            int k=1;
            for(int i=0; i<4; i++) {
                for (int j=0; j<5; j++) {
                    if (!cellUpdate.get(k).equals("white0")) {
                        ImageView passed = configureImageView("/diePack/die-",cellUpdate.get(k),".bmp",70,70);
                        dicePutHistory.add(cellUpdate.get(k));
                        passed.setFitWidth(requestedWidth);
                        passed.setFitHeight(requestedHeight);
                        gridPane.add(passed, j, i);
                        GridPane.setHalignment(passed, HPos.CENTER);
                    }
                    k++;
                }
            }
    }


    public void updateSide(List<String> diceInfo, int requestedWidth, int requestedHeight){
        int k=1;
        for(int i=0; i<4; i++) {
            for (int j=0; j<5; j++) {
                if (!diceInfo.get(k).equals("white0")) gridPane.add(configureDieView(diceInfo.get(k),requestedWidth,requestedHeight),j,i);
                k++;
            }
        }
    }

    public SideCardLabel callPlayerSide(String sideCard, String nickName, List<Integer> sizeGrid, List<Double> positionGrid, List<Integer> sizeSide, List<Double> sizeRect, boolean includeShadowGrid, ReserveLabel reserve){
        SideCardLabel callPlayerSide = new SideCardLabel(sideCard, nickName, sizeGrid, positionGrid, sizeSide, sizeRect, includeShadowGrid, reserve);

        Stream<String> streamImageDie = dicePutHistory.stream().filter(s -> (dicePutHistory.indexOf(s) % 2 == 0));
        Stream<String> streamCoordinate = dicePutHistory.stream().filter(s -> (dicePutHistory.indexOf(s) % 2 != 0));

        ArrayList<String> coordinate = (ArrayList<String>)streamCoordinate.collect(Collectors.toList());
        ArrayList<String> imageDie = (ArrayList<String>)streamImageDie.collect(Collectors.toList());

        for (String image: imageDie) {
            String item = coordinate.get(imageDie.indexOf(image));
            String[] coordinates = item.split("-");
            callPlayerSide.getGridPane().add(configureDieView(image,55,55),Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[0]));
        }

        return callPlayerSide;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public String getPosX() {
        return posX;
    }

    public String getPosY() {
        return posY;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPathCard() {
        return pathCard;
    }
}
