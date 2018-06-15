package it.polimi.se2018.graphic;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import static it.polimi.se2018.graphic.Utility.*;
import java.util.ArrayList;
import java.util.List;

public class SideEnemyLabel{

    private HBox labelSideEnemy;
    private ArrayList<SideCardLabel> sideEnemy;
    private ArrayList<Integer> sizeGridEnemy = new ArrayList<>();
    private ArrayList<Integer> sizeSideEnemy = new ArrayList<>();
    private ArrayList<Double> positionGridEnemy = new ArrayList<>();

    SideEnemyLabel(List<String> nameOfPlayers, List<String> sideName) {
        sideEnemy = new ArrayList<>();
        sizeGridEnemy.add(55);
        sizeGridEnemy.add(55);
        sizeSideEnemy.add(275);
        sizeSideEnemy.add(233);
        positionGridEnemy.add(0d);
        positionGridEnemy.add(0d);
        positionGridEnemy.add(17d);
        positionGridEnemy.add(17d);
        setSideEnemy(nameOfPlayers,sideName);
    }



    public void setSideEnemy(List<String> nameOfPlayers, List<String> sideName){
        labelSideEnemy = new HBox(5);
        labelSideEnemy.setAlignment(Pos.CENTER);
        for(int i=0; i<nameOfPlayers.size();i++){

            VBox cardEnemy = new VBox(10);
            cardEnemy.setPrefSize(391,395);
            Label labelName = new Label("Player: " + nameOfPlayers.get(i));
            labelName.setAlignment(Pos.CENTER);
            labelName.setFont(Font.font ("Matura MT Script Capitals", 22));

            sideEnemy.add(new SideCardLabel(sideName.get(i), nameOfPlayers.get(i), sizeGridEnemy,positionGridEnemy,sizeSideEnemy, false,null));
            cardEnemy.getChildren().addAll(labelName, sideEnemy.get(i).getAnchorPane());
            cardEnemy.setAlignment(Pos.CENTER);
            labelSideEnemy.getChildren().add(cardEnemy);
        }


        if(nameOfPlayers.size()<3){
            for(int j=0;j<3-nameOfPlayers.size();j++){
                ImageView noPlayer = configureImageView("iconPack/icon-player", 400,400);
                noPlayer.setFitWidth(330);
                noPlayer.setFitHeight(280);
                noPlayer.setOpacity(0.2);
                labelSideEnemy.getChildren().add(noPlayer);
            }
        }

    }

    public HBox getLabelSideEnemy() {
        return labelSideEnemy;
    }

    public void updateSideEnemies(List<String> infoSide){
        String nameOfEnemy = infoSide.remove(0);
        for (SideCardLabel side: sideEnemy) {
            if(side.getNickName().equals(nameOfEnemy)) side.updateSide(infoSide);
        }
    }
}
