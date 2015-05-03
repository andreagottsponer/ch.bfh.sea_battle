package ch.bfh.sea_battle.scenes.game.position;

import ch.bfh.sea_battle.utilities.NavigationBar;
import ch.bfh.sea_battle.utilities.ShipView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GamePositionSceneView {
    private Scene scene;
    private NavigationBar navigationBar;
    private BorderPane borderPane;
    private Pane pane;
    private ArrayList<ShipView> ships;

    public GamePositionSceneView() {
        this.createScene();
    }

    public void createScene() {
        this.navigationBar = new NavigationBar();
        this.navigationBar.getLeftButton().setText("Back");
        this.navigationBar.getTitleLabel().setText("");
        this.navigationBar.getRightButton().setText("");

        this.pane = new Pane();
        this.pane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        this.ships = new ArrayList<ShipView>();
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/ship.png"));

        for (int i = 0; i < 5; i++) {
            ShipView shipView = new ShipView();
            shipView.setImage(image);
            shipView.relocate(10, i * 80);
            this.pane.getChildren().add(shipView);
            this.ships.add(shipView);
        }

        this.borderPane = new BorderPane();
        this.borderPane.setTop(this.navigationBar);
        this.borderPane.setCenter(this.pane);

        this.scene = new Scene(this.borderPane, 700, 500);
    }

    public void show(Stage applicationStage) {
        applicationStage.setScene(this.scene);
    }

    public Pane getPane() {
        return this.pane;
    }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }
}
