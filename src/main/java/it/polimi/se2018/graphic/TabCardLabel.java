package it.polimi.se2018.graphic;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class TabCardLabel {

    private TabPane tabPane;
    private Tab utensilsTab;
    private Tab objectiveTab;
    private BorderPane borderPane;
    private Group root;


    public TabCardLabel() {
        tabPane = new TabPane();
        borderPane = new BorderPane();
        root = new Group();
        utensilsTab = new Tab();
        objectiveTab = new Tab();
        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
    }

    public void configureTabUtensils(AnchorPane cardUtensils){
        configureTab(utensilsTab, "Carte Utensili", "iconPack/icon-utensils.png", cardUtensils);

    }

    public void configureTabObjective(AnchorPane cardObjective){
        configureTab(objectiveTab, "Obbiettvi Pubblici", "iconPack/icon-objective.png", cardObjective);
    }

    private void configureTab(Tab tab, String title, String iconPath, AnchorPane content) {
        double imageWidth = 40.0;

        ImageView imageView = new ImageView(new Image(iconPath));
        imageView.setFitHeight(imageWidth);
        imageView.setFitWidth(imageWidth);

        Label label = new Label(title);
        label.setFont(Font.font ("Matura MT Script Capitals", 23));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setStyle("-fx-background-color: transparent;");

        HBox contentTab = new HBox(10);
        contentTab.setAlignment(Pos.CENTER);
        contentTab.getChildren().addAll(imageView, label);
        contentTab.setStyle("-fx-background-color: transparent;");

        tab.setGraphic(contentTab);
        tab.setStyle("-fx-background-color: white; -fx-pref-width: 311; -fx-pref-height: 50; -fx-alignment: center; -fx-focus-color: gray; -fx-faint-focus-color: transparent;");
        tab.setClosable(false);
        tab.setContent(content);
        tabPane.getTabs().add(tab);

    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
