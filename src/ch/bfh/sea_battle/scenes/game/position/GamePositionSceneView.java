package ch.bfh.sea_battle.scenes.game.position;

import ch.bfh.sea_battle.utilities.NavigationBar;
import ch.bfh.sea_battle.utilities.ShipView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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

        this.borderPane = new BorderPane();
        this.borderPane.setTop(this.navigationBar);
        this.borderPane.setCenter(this.pane);

        this.scene = new Scene(this.borderPane, 700, 500);
    }

    public void show(Stage applicationStage) {
        applicationStage.setScene(this.scene);

        double cellSize = (this.pane.getHeight() - 20) / 10;

        for (int i = 0; i < 11; i++) {
            Line line = new Line(this.pane.getWidth() - (cellSize * 10) - 10, (i * cellSize) + 10, this.pane.getWidth() - 10, (i * cellSize) + 10);
            line.setStrokeWidth(1);
            line.setStroke(Color.web("000000"));

            Line line2 = new Line(this.pane.getWidth() - (cellSize * i) - 10, 10, this.pane.getWidth() - (cellSize * i) - 10, this.pane.getHeight() - 10);
            line.setStrokeWidth(1);
            line.setStroke(Color.web("000000"));

            this.pane.getChildren().addAll(line, line2);
        }

        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/ship.png"));

        for (int i = 0; i < 5; i++) {
            ShipView shipView = new ShipView();
            shipView.setImage(image);
            shipView.relocate(10, i * 70);
            this.pane.getChildren().add(shipView);
            this.ships.add(shipView);
        }
    }

    public Pane getPane() {
        return this.pane;
    }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }
}