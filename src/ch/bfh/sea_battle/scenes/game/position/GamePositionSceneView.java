package ch.bfh.sea_battle.scenes.game.position;

import ch.bfh.sea_battle.model.ConfigurationManager;
import ch.bfh.sea_battle.utilities.NavigationBar;
import ch.bfh.sea_battle.utilities.ShipView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GamePositionSceneView {
    private Scene scene;
    private NavigationBar navigationBar;
    private Pane pane;
    private ArrayList<ShipView> ships;
    private int cellSize = ConfigurationManager.sharedInstance().getCellSize();
    private int width = ConfigurationManager.sharedInstance().getGridWidth();
    private int height = ConfigurationManager.sharedInstance().getGridHeight();
    private int margin = ConfigurationManager.sharedInstance().getCellMargin();

    public GamePositionSceneView() {
        this.createScene();
    }

    public void createScene() {
        this.navigationBar = new NavigationBar();
        this.navigationBar.getLeftButton().setText("Cancel");
        this.navigationBar.getTitleLabel().setText("");
        this.navigationBar.getRightButton().setText("");

        this.pane = new Pane();
        this.pane.setBackground(new Background(new BackgroundFill(Color.web("0xb5eef5"), CornerRadii.EMPTY, Insets.EMPTY)));

        this.ships = new ArrayList<ShipView>();

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(this.navigationBar);
        borderPane.setCenter(this.pane);

        this.scene = new Scene(borderPane, this.width * this.cellSize + 2 * this.margin, this.height * this.cellSize + 2 * this.margin + 46);
    }

    public void show(Stage applicationStage) {
        applicationStage.setScene(this.scene);

        for (int i = 0; i < this.width + 1; i++) {
            Line line = new Line(this.margin, (i * this.cellSize) + this.margin, (this.cellSize * this.width) + this.margin, (i * this.cellSize) + this.margin);
            line.setStrokeWidth(1);
            line.setStroke(Color.web("002544"));
            this.pane.getChildren().add(line);

        }
        for (int i = 0; i < this.height + 1; i++) {
            Line line = new Line((this.cellSize * i) + this.margin, this.margin, (this.cellSize * i) + this.margin, (this.cellSize * this.height) + this.margin);
            line.setStrokeWidth(1);
            line.setStroke(Color.web("002544"));
            this.pane.getChildren().add(line);
        }

        this.addShipView(0, 2, this.cellSize);
        this.addShipView(1, 2, this.cellSize);
        this.addShipView(2, 3, this.cellSize);
        this.addShipView(3, 3, this.cellSize);
        this.addShipView(4, 4, this.cellSize);
        this.addShipView(5, 5, this.cellSize);
    }

    public void addShipView(int id, int size, double cellSize) {
        ShipView shipView = new ShipView(true, size);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/ship" + size + ".png"), size * cellSize, cellSize, true, true);
        shipView.setImage(image);
        shipView.relocate(10, id * cellSize + 10);
        this.pane.getChildren().add(shipView);
        this.ships.add(shipView);
    }

    public Pane getPane() {
        return this.pane;
    }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }

    public ArrayList<ShipView> getShips() {
        return this.ships;
    }
}