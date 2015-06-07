package ch.bfh.sea_battle.scenes.initial;

import ch.bfh.sea_battle.utilities.NavigationBar;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InitialSceneView {
    private Scene scene;
    private Button singlePlayerButton;
    private Button twoPlayerButton;

    public InitialSceneView() {
        this.createScene();
    }

    public void createScene() {
        NavigationBar navigationBar = new NavigationBar();
        navigationBar.getTitleLabel().setText("Choose Mode");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        this.singlePlayerButton = new Button("1 Player vs. Computer");
        this.singlePlayerButton.setPrefWidth(200);
        this.singlePlayerButton.setPrefHeight(40);
        this.twoPlayerButton = new Button("2 Players");
        this.twoPlayerButton.setPrefWidth(200);
        this.twoPlayerButton.setPrefHeight(40);
        vBox.getChildren().addAll(this.singlePlayerButton, this.twoPlayerButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(navigationBar);
        borderPane.setCenter(vBox);

        this.scene = new Scene(borderPane, 420, 466);
    }

    public void show(Stage applicationStage) {
        applicationStage.setScene(this.scene);
    }

    public Button getSinglePlayerButton() {
        return this.singlePlayerButton;
    }

    public Button getTwoPlayerButton() {
        return this.twoPlayerButton;
    }
}
