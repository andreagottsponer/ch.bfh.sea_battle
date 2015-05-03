package ch.bfh.sea_battle.scenes.game.play;

import ch.bfh.sea_battle.utilities.NavigationBar;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GamePlaySceneView {
    private Scene scene;
    private NavigationBar navigationBar;
    private BorderPane borderPane;

    public GamePlaySceneView() {
        this.createScene();
    }

    public void createScene() {
        this.navigationBar = new NavigationBar();
        this.navigationBar.getTitleLabel().setText("");
        this.navigationBar.getRightButton().setText("Quit");

        this.borderPane = new BorderPane();
        this.borderPane.setTop(this.navigationBar);

        this.scene = new Scene(this.borderPane, 700, 500);
    }

    public void show(Stage applicationStage) {
        applicationStage.setScene(this.scene);
    }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }
}
