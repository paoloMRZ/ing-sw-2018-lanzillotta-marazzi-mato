package it.polimi.se2018.graphic;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import static it.polimi.se2018.graphic.Utility.*;
import static java.lang.Integer.parseInt;


public class SettingLabel implements ChangeListener<String> {


    private static final String STYLELABEL = "-fx-background-color: transparent;";
    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "/iconPack/";
    private static final int SPACE = 10;
    private HBox settingHBox;
    private HBox nickNameLabel;
    private HBox favoursLabel;
    private HBox actionLabel;
    private HBox turnOfLabel;
    private TextField turnOf = new TextField();
    private TextField favours = new TextField();
    private TextField action = new TextField();

    public SettingLabel(String nickName, String action, String favours, String turnOf){

        this.action.setText(action);
        this.favours.setText(favours);
        this.turnOf.setText(turnOf);

        nickNameLabel = configureNode(SPACE,"icon-sagrada",55,55,nickName,24);
        favoursLabel = configureNode(SPACE,"icon-favours",55,55,favours,24);
        actionLabel = configureNode(SPACE,"icon-mosse",55,55,action,24);
        turnOfLabel = configureNode(SPACE,"icon-player",53,53,turnOf,24);

        settingHBox = new HBox(30);
        settingHBox.setStyle(STYLELABEL);
        settingHBox.getChildren().addAll(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);

        }


    public void changed(ObservableValue observableValue, String oldValue, String newValue){
        try{
            StringProperty textProperty = (StringProperty) observableValue;
            TextField textField = (TextField) textProperty.getBean();

            if(textField == turnOf){
                turnOfLabel = configureNode(SPACE,"icon-player",55,55,newValue,24);
                settingHBox = new HBox(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);
                settingHBox.setSpacing(30d);
                settingHBox.setStyle(STYLELABEL);
            }

            else if(textField == favours){
                favoursLabel = configureNode(SPACE,"icon-favours",55,55,newValue,24);
                settingHBox = new HBox(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);
                settingHBox.setSpacing(30d);
                settingHBox.setStyle(STYLELABEL);
            }

            else if(textField == action){
                actionLabel = configureNode(SPACE,"icon-mosse",55,55,newValue,24);
                settingHBox = new HBox(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);
                settingHBox.setSpacing(30d);
                settingHBox.setStyle(STYLELABEL);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private HBox configureNode(int spaceHBox, String nameResources, int requestedWidth, int requestedHeight, String text, int sizeText){
        HBox node = new HBox(spaceHBox);
        ImageView iconPlayer = configureImageView(SUBDIRECTORY,nameResources,EXTENSION,requestedWidth,requestedHeight);
        iconPlayer.setFitWidth(35);
        iconPlayer.setFitHeight(35);
        Label labelName = setFontStyle(new Label(text),sizeText);
        node.getChildren().addAll(iconPlayer,labelName);
        node.setAlignment(Pos.CENTER);
        return node;
    }

    public HBox getSettingLabel() {
        return settingHBox;
    }

    public void updateTurn(String turnOf){
        changed(this.turnOf.textProperty(),this.turnOf.getText(),turnOf);
    }

    public void updateAction(){
        changed(action.textProperty(),this.action.getText(),String.valueOf(parseInt(this.action.getText())-1));
    }

    public void updateFavours(String updateFavours){
        changed(favours.textProperty(),this.favours.getText(),String.valueOf(parseInt(updateFavours)));
    }
}