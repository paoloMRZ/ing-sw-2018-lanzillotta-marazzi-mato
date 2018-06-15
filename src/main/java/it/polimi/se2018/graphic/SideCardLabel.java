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


    public void updateSideAfterPut(List<String> dicePutInfo){
            dicePutHistory.addAll(dicePutInfo);
            dicePutHistory.add(posX + "-" + posY);
            reserve.updateReserve(Integer.parseInt(dicePutInfo.get(0)));
            ImageView passed = new ImageView(new Image("diePack/die-blue2"+ reserve.getDieName() + ".bmp",70,70,false,true));
            passed.setFitHeight(70);
            passed.setFitHeight(70);
            gridPane.add(passed, Integer.parseInt(dicePutInfo.get(1)), Integer.parseInt(dicePutInfo.get(2)));
            GridPane.setHalignment(passed, HPos.CENTER);
    }


    public void updateSide(List<String> diceInfo){
        int k=0;
        for(int i=0; i<4; i++) {
            for (int j=0; j<5; j++) {
                if (!diceInfo.get(k).equals("white0")) {
                    ImageView die = new ImageView(new Image("diePack/die-" + diceInfo.get(k) + ".bmp", 40, 40, false, true));
                    gridPane.add(die,j,i);
                }
                k++;
            }
        }
    }


    SideCardLabel(String sideCard, String nickName, List<Integer> sizeGrid,  List<Double> positionGrid, List<Integer> sizeSide, boolean includeShadowGrid, ReserveLabel reserve){

        this.reserve = reserve;
        this.nickName = nickName;
        this.pathCard = sideCard;

        //Creo la griglia
        setGridSide(sizeGrid.get(0),sizeGrid.get(1), includeShadowGrid);

        //Creo l'immagine di background e Aggiungo la gridPane all'anchorPane
        anchorPane = new AnchorPane();
        anchorPane.setBackground(Utility.configureBackground("cardPack/" + sideCard, sizeSide.get(0),sizeSide.get(1)));
        anchorPane.setPrefSize(sizeSide.get(0),sizeSide.get(1));
        configureAnchorPane(anchorPane,gridPane,positionGrid.get(0),positionGrid.get(1),positionGrid.get(2),positionGrid.get(3));

        group = new Group();
        Rectangle rect = new Rectangle(20, 20, 87, 87);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(5d);
        group.getChildren().add(rect);
    }


    public void setGridSide(int width, int height, boolean includeShadowGrid){
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
                        if(!cell.get(button)){
                            button.getChildren().add(group);
                            cell.replace(button, false, true);
                        }
                        else {
                            button.getChildren().remove(group);
                            cell.replace(button, true, false);
                        }

                        posX = String.valueOf(GridPane.getRowIndex(button));
                        posY = String.valueOf(GridPane.getColumnIndex(button));
                    });

                }

                gridPane.add(button, j, i);
            }
        }

        gridPane.setAlignment(Pos.CENTER);
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

    public SideCardLabel callPlayerSide(String sideCard, String nickName, List<Integer> sizeGrid, List<Double> positionGrid, List<Integer> sizeSide, boolean includeShadowGrid, ReserveLabel reserve){
        SideCardLabel callPlayerSide = new SideCardLabel(sideCard, nickName,  sizeGrid, positionGrid,  sizeSide, includeShadowGrid, reserve);

        Stream<String> streamImageDie = dicePutHistory.stream().filter(s -> (dicePutHistory.indexOf(s) % 2 == 0));
        Stream<String> streamCoordinate = dicePutHistory.stream().filter(s -> (dicePutHistory.indexOf(s) % 2 != 0));

        ArrayList<String> coordinate = (ArrayList<String>)streamCoordinate.collect(Collectors.toList());
        ArrayList<String> imageDie = (ArrayList<String>)streamImageDie.collect(Collectors.toList());

        for (String image: imageDie) {
            ImageView die = new ImageView(new Image("diePack/die-" + image + ".bmp", 55, 55, false, true));
            String item = coordinate.get(imageDie.indexOf(image));
            String[] coordinates = item.split("-");
            callPlayerSide.getGridPane().add(die,Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[0]));

        }

        return callPlayerSide;
    }
}
