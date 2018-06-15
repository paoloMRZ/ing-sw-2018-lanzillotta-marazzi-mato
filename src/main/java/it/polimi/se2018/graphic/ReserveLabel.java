package it.polimi.se2018.graphic;

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

import static it.polimi.se2018.graphic.Utility.shadowEffect;


public class ReserveLabel{

    private String pos;
    private String dieName;
    private HBox reserve;
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private Group group;
    private ArrayList<String> diceInfo = new ArrayList<>();

    ReserveLabel(List<String> diceInfo){
        group = new Group();
        Rectangle rect = new Rectangle(20, 20, 70, 70);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(3d);
        this.diceInfo.addAll(diceInfo);
        setReserve(diceInfo);
    }

    public HBox setReserve(List<String> diceInfo) {

        reserve = new HBox(5);
        reserve.setAlignment(Pos.CENTER);
        int num = diceInfo.size();

        for (int i = 0; i < num; i++) {
            StackPane button = new StackPane();
            button.setPrefSize(70,70);
            button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");

            ImageView die = shadowEffect(new ImageView(new Image("diePack/die-" + diceInfo.get(i) + ".bmp", 68, 68, false, true)));
            button.getChildren().add(die);

            int finalI = i;
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
                pos = String.valueOf(finalI);
                dieName = diceInfo.get(finalI);
            });

            reserve.getChildren().add(button);
        }

        return reserve;
    }

    public String getPos() {
        return pos;
    }

    public HBox getHBox() {
        return reserve;
    }

    public HBox callReserve(){
        return setReserve(this.diceInfo);
    }

    public String getDieName() {
        return dieName;
    }

    public void updateReserve(int index){
        this.diceInfo.remove(index);
        reserve.getChildren().remove(index);
    }
}
