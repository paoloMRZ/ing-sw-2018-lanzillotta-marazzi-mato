package it.polimi.se2018.graphic;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
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

import static it.polimi.se2018.graphic.Utility.setFocusStyle;
import static it.polimi.se2018.graphic.Utility.shadowEffect;


public class ReserveLabel{

    private String pos;
    private String dieName;
    private TextField listener = new TextField();
    private HBox reserve;
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private Group group = new Group();
    private ArrayList<String> diceInfo = new ArrayList<>();

    public ReserveLabel(List<String> diceInfo, int prefWidth, int prefHeight, int widthRect, int heightRect){
        this.diceInfo.addAll(diceInfo);
        setReserve(diceInfo,prefWidth,prefHeight,widthRect, heightRect);
    }

    private HBox setReserve(List<String> diceInfo, int prefWidth, int prefHeight,int widthRect, int heightRect) {
        Rectangle rect = new Rectangle(20, 20, widthRect, heightRect);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(3d);
        group.getChildren().add(rect);
        reserve = new HBox(5);
        reserve.setAlignment(Pos.CENTER);
        int num = diceInfo.size();

        for (int i = 0; i < num; i++) {
            String lowerDieInfo = diceInfo.get(i).toLowerCase(Locale.ENGLISH);
            StackPane button = new StackPane();
            button.setPrefSize(prefWidth,prefHeight);
            button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");

            ImageView die = shadowEffect(new ImageView(new Image("/diePack/die-" + lowerDieInfo + ".bmp", prefWidth, prefHeight, false, true)));
            button.getChildren().add(die);

            int finalI = i;
            cell.put(button, false);
            die.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                setFocusStyle(cell,button,group);
                pos = String.valueOf(finalI);
                dieName = diceInfo.get(finalI).toLowerCase();
                listener.setText(dieName);
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

    public HBox callReserve(int prefWidth, int prefHeight){
        return setReserve(this.diceInfo, prefWidth,prefHeight,55,55);
    }

    public String getDieName() {
        return dieName;
    }

    public void updateReserve(int index){
        this.diceInfo.remove(index);
        reserve.getChildren().remove(index);
    }

    public TextField getTextField() {
        return listener;
    }
}
