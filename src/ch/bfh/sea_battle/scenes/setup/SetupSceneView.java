package ch.bfh.sea_battle.scenes.setup;

import ch.bfh.sea_battle.utilities.GameType;
import ch.bfh.sea_battle.utilities.NavigationBar;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SetupSceneView {
    private Scene scene;
    private NavigationBar navigationBar;
    private BorderPane borderPane;
    private VBox vBox;
    private TextField nameFirstPlayer;
    private TextField nameSecondPlayer;
    private GameType gameType;

    public SetupSceneView(GameType gameType) {
        this.gameType = gameType;
        this.createScene();
    }

    public void createScene() {
        this.navigationBar = new NavigationBar();
        this.navigationBar.getLeftButton().setText("Back");
        this.navigationBar.getTitleLabel().setText("Create Game");
        this.navigationBar.getRightButton().setText("Continue");

        //Todo: In eigene Klasse auslagern
        Label label1 = new Label("Player 1:");
        this.nameFirstPlayer = new TextField();
        HBox hb1 = new HBox();
        hb1.setAlignment(Pos.CENTER);
        hb1.getChildren().addAll(label1, this.nameFirstPlayer);
        hb1.setSpacing(10);

        this.vBox = new VBox();
        this.vBox.getChildren().add(hb1);

        if (this.gameType == GameType.TWO_PLAYER) {
            Label label2 = new Label("Player 2:");
            this.nameSecondPlayer = new TextField();
            HBox hb2 = new HBox();
            hb2.setAlignment(Pos.CENTER);
            hb2.getChildren().addAll(label2, this.nameSecondPlayer);
            hb2.setSpacing(10);
            this.vBox.getChildren().add(hb2);
        }

        this.borderPane = new BorderPane();
        this.borderPane.setTop(this.navigationBar);
        this.borderPane.setCenter(this.vBox);

        this.scene = new Scene(this.borderPane, 700, 500);
    }

    public void show(Stage applicationStage) {
        applicationStage.setTitle("Battle Ship");
        applicationStage.setScene(this.scene);
    }

    public TextField getNameFirstPlayer() {
        return this.nameFirstPlayer;
    }

    public TextField getNameSecondPlayer() {
        return this.nameSecondPlayer;
    }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }
}
