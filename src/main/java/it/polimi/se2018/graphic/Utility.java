package it.polimi.se2018.graphic;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


public class Utility {

    private static DropShadow shadow = new DropShadow();
    private static String font = "Matura MT Script Capitals";

    public static ImageView shadowEffect(ImageView button) {
        //Effetto ombra
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(shadow));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));
        return button;
    }

    //Per sole immagini PNG
    public static ImageView configureImageView(String path, int requestedWidth, int requestedHeight){
        return new ImageView(new Image(path + ".png",requestedWidth,requestedHeight,false,true));
    }

    //Per sole immagini PNG
    public static Background configureBackground(String path, int requestesWidth, int requestedHeight){
        return new Background(new BackgroundImage(new Image(path + ".png",requestesWidth,requestedHeight,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
    }

    public static void configureAnchorPane(AnchorPane anchorPane, Node node, Double right, Double top, Double left, Double bottom){
        anchorPane.getChildren().add(node);
        AnchorPane.setRightAnchor(node,right);
        AnchorPane.setTopAnchor(node,top);
        AnchorPane.setLeftAnchor(node,left);
        AnchorPane.setBottomAnchor(node,bottom);
    }

    public static VBox configureVBox(Label label, Node hBox, int spacingVBox){
        VBox vBox = new VBox(spacingVBox);
        vBox.getChildren().addAll(label,hBox);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    public static Label setFontStyle(Label label, int size){
        label.setFont(Font.font(font, size));
        return label;
    }

}
