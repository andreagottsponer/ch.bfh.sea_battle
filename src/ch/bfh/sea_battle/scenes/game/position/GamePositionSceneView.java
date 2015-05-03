package ch.bfh.sea_battle.scenes.game.position;

import ch.bfh.sea_battle.utilities.NavigationBar;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GamePositionSceneView {
    private Scene scene;
    private NavigationBar navigationBar;
    private BorderPane borderPane;
    private StackPane shipParking;
    private StackPane shipPlacing;
    private ImageView testShip;

    public GamePositionSceneView() {
        this.createScene();
    }

    public void createScene() {
        this.navigationBar = new NavigationBar();
        this.navigationBar.getLeftButton().setText("Back");
        this.navigationBar.getTitleLabel().setText("");
        this.navigationBar.getRightButton().setText("");

        this.testShip = new ImageView();
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/ship.png"));
        this.testShip.setImage(image);
        this.shipParking.getChildren().add(this.testShip);

        this.shipParking = new StackPane();
        this.shipParking.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.shipParking.setPrefWidth(200);


        this.shipPlacing = new StackPane();
        this.shipPlacing.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.shipPlacing.setPrefWidth(500);

        this.borderPane = new BorderPane();
        this.borderPane.setTop(this.navigationBar);
        this.borderPane.setLeft(this.shipParking);
        this.borderPane.setCenter(this.shipPlacing);

        this.scene = new Scene(this.borderPane, 700, 500);
    }

    public void show(Stage applicationStage) {
        applicationStage.setScene(this.scene);
    }

    public StackPane getShipParking() {
        return this.shipParking;
    }

    public StackPane getShipPlacing() {
        return this.shipPlacing;
    }

    public ImageView getTestShip() {
        return this.getTestShip();
    }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }
}
