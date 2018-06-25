package it.polimi.se2018.graphic.alert_box;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import static it.polimi.se2018.graphic.Utility.*;

import java.util.ArrayList;
import java.util.List;


public class AlertInfoCard {

    private static final String EXTENSION = ".png";

    private static ArrayList<String> nameCard = new ArrayList<>();
    private static String infoData;

    public static void display(List<String> cardSelection, String path){

        Stage window = new Stage();
        window.setWidth(650);
        window.setHeight(770);

        ImageView back = configureImageView(path, "retro", EXTENSION,700,850);
        back.setOpacity(0.2);

        ArrayList<ImageView> imageCard = new ArrayList<>();
        ArrayList<Label> labelName = new ArrayList<>();

        for (String card: cardSelection) {
            imageCard.add(configureImageView(path, card, EXTENSION,300,450));
            String[] slashSplit = card.split("/");
            String[] stretchSplit = slashSplit[1].split("-");

            String name = stretchSplit[0].substring(0,1).toUpperCase().concat(stretchSplit[0].substring(1));
            for(int i=1; i<stretchSplit.length;i++){
                name = name.concat(" ".concat(stretchSplit[i].substring(0,1).toUpperCase().concat(stretchSplit[i].substring(1))));
            }
            Label item = setFontStyle(new Label(name), 40);
            item.setAlignment(Pos.CENTER);
            nameCard.add(name);
            labelName.add(item);
        }


        HBox view1 = new HBox(30);
        HBox view2 = new HBox(30);
        HBox view3 = new HBox(30);


        VBox labelView1 = new VBox(25);
        labelView1.setAlignment(Pos.CENTER);
        labelView1.getChildren().addAll(labelName.get(0),view1,setActionOnBack(window,180,100));
        VBox labelView2 = new VBox(25);
        labelView2.setAlignment(Pos.CENTER);
        labelView2.getChildren().addAll(labelName.get(1),view2,setActionOnBack(window,180,100));
        VBox labelView3 = new VBox(25);
        labelView3.setAlignment(Pos.CENTER);
        labelView3.getChildren().addAll(labelName.get(2),view3,setActionOnBack(window,180,100));
        StackPane root = new StackPane(back);

        root.getChildren().add(labelView1);



        view1.getChildren().addAll(setOpacity(setRotation(180d),0.6),imageCard.get(0),shadowEffect(setActionOnImage(labelView2,labelView1,root,0d,false,null)));
        view1.setAlignment(Pos.CENTER);

        view2.getChildren().addAll(shadowEffect(setActionOnImage(labelView1,labelView2,root,180d,false,null)),imageCard.get(1),shadowEffect(setActionOnImage(labelView3,labelView2,root,0d,false,null)));
        view2.setAlignment(Pos.CENTER);

        view3.getChildren().addAll(shadowEffect(setActionOnImage(labelView2,labelView3,root,180d,false,null)),imageCard.get(2),setOpacity(setRotation(0d),0.6));
        view3.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();

    }


    public static ImageView setActionOnImage(Node switchWindow, Node actualWindow, StackPane root, Double rotate, Boolean accessUtensil, ImageView dieUtensil){
        ImageView button = new ImageView(new Image("iconPack/icon-next.png",80,80,false,true));
        button.setRotate(rotate);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            root.getChildren().add(switchWindow);
            double width = root.getWidth();
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(switchWindow.translateXProperty(), width),
                    new KeyValue(actualWindow.translateXProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.millis(0.1),
                    new KeyValue(switchWindow.translateXProperty(), 0),
                    new KeyValue(actualWindow.translateXProperty(), -width));

            if(accessUtensil) infoData = dieUtensil.getUserData().toString();
            Timeline slide = new Timeline(start, end);
            slide.setOnFinished(ex -> root.getChildren().remove(actualWindow));
            slide.play();
        });
        return button;
    }

    public static ImageView setOpacity(ImageView node, Double opacity){
        node.setOpacity(opacity);
        return node;
    }

    public static ImageView setRotation(Double grade){
        ImageView button = new ImageView(new Image("iconPack/icon-next.png",80,80,false,true));
        button.setRotate(grade);
        return button;
    }

    private static ImageView setActionOnBack(Stage window,int requestWidth, int requestHeight){
        ImageView backButton = shadowEffect(configureImageView("","button-back", EXTENSION,requestWidth,requestHeight));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());
        return backButton;
    }

    public static ArrayList<String> getNameCard() {
        return nameCard;
    }

    public static String getInfoData() {
        return infoData;
    }
}