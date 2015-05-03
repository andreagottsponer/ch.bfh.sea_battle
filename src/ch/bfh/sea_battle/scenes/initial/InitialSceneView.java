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
    private NavigationBar navigationBar;
    private BorderPane borderPane;
    private VBox vBox;

    public InitialSceneView() {
        this.createScene();
    }

    public void createScene() {
        this.navigationBar = new NavigationBar();
        this.navigationBar.getTitleLabel().setText("Choose Mode");

        this.vBox = new VBox();
        this.vBox.setAlignment(Pos.CENTER);
        this.singlePlayerButton = new Button("1 Player");
        this.twoPlayerButton = new Button("2 Players");
        this.vBox.getChildren().addAll(this.singlePlayerButton, this.twoPlayerButton);

        this.borderPane = new BorderPane();
        this.borderPane.setTop(this.navigationBar);
        this.borderPane.setCenter(this.vBox);

        this.scene = new Scene(this.borderPane, 700, 500);
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
