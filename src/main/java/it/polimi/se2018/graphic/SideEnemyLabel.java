package it.polimi.se2018.graphic;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import static it.polimi.se2018.graphic.Utility.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SideEnemyLabel{


    private HBox labelSideEnemy;
    private ArrayList<SideCardLabel> sideEnemy;
    private ArrayList<Integer> sizeGridEnemy;
    private ArrayList<Integer> sizeSideEnemy;
    private ArrayList<Double> positionGridEnemy;

    SideEnemyLabel(List<String> nameOfPlayers, List<String> sideName) {
        sideEnemy = new ArrayList<>();
        sizeGridEnemy = new ArrayList<>(Arrays.asList(39,39));
        sizeSideEnemy = new ArrayList<>(Arrays.asList(200,170));
        positionGridEnemy = new ArrayList<>(Arrays.asList(0d,0d,4d,12d));
        setSideEnemy(nameOfPlayers,sideName);
    }

    private void setSideEnemy(List<String> nameOfPlayers, List<String> sideName){
        labelSideEnemy = new HBox(0);
        labelSideEnemy.setAlignment(Pos.CENTER);
        for(int i=0; i<nameOfPlayers.size();i++){

            VBox cardEnemy = new VBox(5);
            cardEnemy.setPrefSize(391,395);
            Label labelName = setFontStyle(new Label("Player: " + nameOfPlayers.get(i)), 18);
            labelName.setAlignment(Pos.CENTER);
            sideEnemy.add(new SideCardLabel(sideName.get(i), nameOfPlayers.get(i),sizeGridEnemy,positionGridEnemy,sizeSideEnemy, null,false,null));
            cardEnemy.getChildren().addAll(labelName, sideEnemy.get(i).getAnchorPane());
            cardEnemy.setAlignment(Pos.CENTER);
            labelSideEnemy.getChildren().add(cardEnemy);
        }


        if(nameOfPlayers.size()<3){
            for(int j=0;j<3-nameOfPlayers.size();j++){
                ImageView noPlayer = configureImageView("/iconPack/","icon-player", ".png", 400,400);
                noPlayer.setFitWidth(250);
                noPlayer.setFitHeight(220);
                noPlayer.setOpacity(0.2);
                labelSideEnemy.getChildren().add(noPlayer);
            }
        }

    }

    public HBox getLabelSideEnemy() {
        return labelSideEnemy;
    }

    public void updateSideEnemies(List<String> infoSide){
        String nameOfEnemy = infoSide.get(0);
        for (SideCardLabel side: sideEnemy) {
            if(side.getNickName().equals(nameOfEnemy)) side.updateSide(infoSide,33,33);
        }
    }
}
