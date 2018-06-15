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
    private static final int SPACE = 10;
    private HBox settingLabel;
    private HBox nickNameLabel;
    private HBox favoursLabel;
    private HBox actionLabel;
    private HBox turnOfLabel;
    private TextField turnOf = new TextField();
    private TextField favours = new TextField();
    private TextField action = new TextField();
    private boolean isFirstUse = true;

    public SettingLabel(String nickName, String action, String favours, String turnOf){

        this.action.setText(action);
        this.favours.setText(favours);
        this.turnOf.setText(turnOf);

        nickNameLabel = configureNode(SPACE,"iconPack/icon-sagrada",55,55,nickName,32);
        favoursLabel = configureNode(SPACE,"iconPack/icon-favours",55,55,favours,32);
        actionLabel = configureNode(SPACE,"iconPack/icon-mosse",55,55,action,32);
        turnOfLabel = configureNode(SPACE,"iconPack/icon-player",53,53,turnOf,25);

        settingLabel = new HBox(40);
        settingLabel.setStyle(STYLELABEL);
        settingLabel.getChildren().addAll(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);

        }


    public void changed(ObservableValue observableValue, String oldValue, String newValue){
        try{
            StringProperty textProperty = (StringProperty) observableValue;
            TextField textField = (TextField) textProperty.getBean();

            if(textField == turnOf){
                turnOfLabel = configureNode(SPACE,"iconPack/icon-player",55,55,newValue,28);
                settingLabel = new HBox(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);
                settingLabel.setSpacing(40d);
                settingLabel.setStyle(STYLELABEL);
            }

            else if(textField == favours){
                favoursLabel = configureNode(SPACE,"iconPack/icon-favours",55,55,newValue,32);
                settingLabel = new HBox(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);
                settingLabel.setSpacing(40d);
                settingLabel.setStyle(STYLELABEL);
            }

            else if(textField == action){
                actionLabel = configureNode(SPACE,"iconPack/icon-mosse",55,55,newValue,32);
                settingLabel = new HBox(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);
                settingLabel.setSpacing(40d);
                settingLabel.setStyle(STYLELABEL);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private HBox configureNode(int spaceHBox, String path, int requestedWidth, int requestedHeight, String text, int sixeText){
        HBox node = new HBox(spaceHBox);
        ImageView iconPlayer = configureImageView(path,requestedWidth,requestedHeight);
        Label labelName = setFontStyle(new Label(text),sixeText);
        node.getChildren().addAll(iconPlayer,labelName);
        node.setAlignment(Pos.CENTER);
        return node;
    }

    public HBox getSettingLabel() {
        return settingLabel;
    }

    public void updateTurn(String turnOf){
        changed(this.turnOf.textProperty(),this.turnOf.getText(),turnOf);
    }

    public void updateAction(){
        changed(action.textProperty(),this.action.getText(),String.valueOf(parseInt(this.action.getText())-1));
    }

    public void updateFavours(){
            if(isFirstUse) {
                changed(favours.textProperty(),this.favours.getText(),String.valueOf(parseInt(this.favours.getText())-1));
                isFirstUse = false;
            }
            else  changed(favours.textProperty(),this.favours.getText(),String.valueOf(parseInt(this.favours.getText())-2));
    }
}