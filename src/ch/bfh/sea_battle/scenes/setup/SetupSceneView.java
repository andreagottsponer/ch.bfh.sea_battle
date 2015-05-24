package ch.bfh.sea_battle.scenes.setup;

import ch.bfh.sea_battle.model.ConfigurationManager;
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
    private TextField nameFirstPlayer;
    private TextField nameSecondPlayer;
    private TextField numberOfColumns;
    private TextField numberOfRows;
    private GameType gameType;

    public SetupSceneView(GameType gameType) {
        this.gameType = gameType;
        this.createScene();
    }

    public void createScene() {
        this.navigationBar = new NavigationBar();
        this.navigationBar.getLeftButton().setText("Cancel");
        this.navigationBar.getTitleLabel().setText("Create Game");
        this.navigationBar.getRightButton().setText("Continue");

        VBox vBoxLeft = new VBox();
        vBoxLeft.setSpacing(20);

        VBox vBoxRight = new VBox();
        vBoxRight.setSpacing(10);

        Label labelPlayer1 = new Label("Player 1:");
        this.nameFirstPlayer = new TextField();
        vBoxLeft.getChildren().add(labelPlayer1);
        vBoxRight.getChildren().add(this.nameFirstPlayer);

        if (this.gameType == GameType.TWO_PLAYER) {
            Label labelPlayer2 = new Label("Player 2:");
            this.nameSecondPlayer = new TextField();
            vBoxLeft.getChildren().add(labelPlayer2);
            vBoxRight.getChildren().add(this.nameSecondPlayer);
        }

        Label labelColumns = new Label("Number Columns:");
        this.numberOfColumns = new TextField();
        this.numberOfColumns.setText(Integer.toString(ConfigurationManager.sharedInstance().getGridWidth()));
        vBoxLeft.getChildren().add(labelColumns);
        vBoxRight.getChildren().add(this.numberOfColumns);

        Label labelRows = new Label("Number Rows:");
        this.numberOfRows = new TextField();
        this.numberOfRows.setText(Integer.toString(ConfigurationManager.sharedInstance().getGridHeight()));
        vBoxLeft.getChildren().add(labelRows);
        vBoxRight.getChildren().add(this.numberOfRows);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(this.navigationBar);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(vBoxLeft, vBoxRight);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(hBox);

        borderPane.setCenter(vBox);

        this.scene = new Scene(borderPane, 420, 466);
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

    public TextField getNumberOfColumns() { return this.numberOfColumns; }

    public TextField getNumberOfRows() { return this.numberOfRows; }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }
}
