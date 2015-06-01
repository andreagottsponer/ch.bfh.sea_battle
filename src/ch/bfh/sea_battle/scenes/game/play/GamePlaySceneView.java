package ch.bfh.sea_battle.scenes.game.play;

import ch.bfh.sea_battle.entities.Ship;
import ch.bfh.sea_battle.model.ConfigurationManager;
import ch.bfh.sea_battle.utilities.GameType;
import ch.bfh.sea_battle.utilities.NavigationBar;
import ch.bfh.sea_battle.utilities.ShipView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;

public class GamePlaySceneView {
    private Scene scene;
    private NavigationBar navigationBar;
    private Pane pane;
    private ArrayList<ImageView> imageViews;
    private ArrayList<ShipView> shipViews;
    private ArrayList<ShipView> revealedShipViews;
    private ArrayList<Label> labels;
    private int width = ConfigurationManager.sharedInstance().getGridWidth();
    private int height = ConfigurationManager.sharedInstance().getGridHeight();
    private int cellSize = ConfigurationManager.sharedInstance().getCellSize();
    private int margin = ConfigurationManager.sharedInstance().getCellMargin();
    private int heightLabels = 30;
    private boolean showDebugLabels = false;
    private GameType gameType;
    private boolean didDrawShips;

    public GamePlaySceneView(GameType gameType) {
        this.imageViews = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.shipViews = new ArrayList<>();
        this.revealedShipViews = new ArrayList<>();
        this.gameType = gameType;
        this.didDrawShips = false;
        this.createScene();
    }

    public void createScene() {
        this.navigationBar = new NavigationBar();
        this.navigationBar.getTitleLabel().setText("");
        this.navigationBar.getRightButton().setText("Quit");

        this.pane = new Pane();
        this.pane.setBackground(new Background(new BackgroundFill(Color.web("0xb5eef5"), CornerRadii.EMPTY, Insets.EMPTY)));

        Label leftLabel = new Label("Your Target");
        leftLabel.setFont(new Font("Arial", 30));
        leftLabel.setAlignment(Pos.CENTER);
        leftLabel.relocate(10, 0);
        this.pane.getChildren().add(leftLabel);

        Label rightLabel = new Label("Your Ships");
        rightLabel.setFont(new Font("Arial", 30));
        rightLabel.setAlignment(Pos.CENTER);
        rightLabel.relocate(this.cellSize * this.width + this.margin * 5, 0);
        this.pane.getChildren().add(rightLabel);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(this.navigationBar);
        borderPane.setCenter(this.pane);

        this.scene = new Scene(borderPane, 2 * (this.width * this.cellSize) + 6 * this.margin, this.height * this.cellSize + 2 * this.margin + 46 + this.heightLabels);
    }

    public void show(Stage applicationStage) {
        applicationStage.setScene(this.scene);
        this.createGridWithX(this.margin);
        this.createGridWithX(5 * this.margin + this.width * this.cellSize);

        Line middleLine = new Line(this.width * this.cellSize + 3 * this.margin, this.margin + this.heightLabels, this.width * this.cellSize + 3 * this.margin, this.height * this.cellSize + this.margin + this.heightLabels);
        middleLine.setStrokeWidth(3);
        middleLine.setStroke(Color.web("002544"));
        this.pane.getChildren().add(middleLine);
    }

    public void createGridWithX(int x) {

        for (int i = 0; i < this.width + 1; i++) {
            Line line = new Line(x, (i * this.cellSize) + this.margin + this.heightLabels, (this.cellSize * this.width) + x, (i * this.cellSize) + this.margin + this.heightLabels);
            line.setStrokeWidth(1);
            line.setStroke(Color.web("002544"));
            this.pane.getChildren().add(line);

        }
        for (int i = 0; i < this.height + 1; i++) {
            Line line = new Line((this.cellSize * i) + x, this.margin + this.heightLabels, (this.cellSize * i) + x, (this.cellSize * this.height) + this.margin + this.heightLabels);
            line.setStrokeWidth(1);
            line.setStroke(Color.web("002544"));
            this.pane.getChildren().add(line);
        }
    }

    public void clickedField(int x, int y, boolean didHit, boolean isBot) {
        Image image;

        if (didHit) {
            image = new Image(getClass().getClassLoader().getResourceAsStream("images/hit.png"), this.cellSize, this.cellSize, true, true);
        } else {
            image = new Image(getClass().getClassLoader().getResourceAsStream("images/miss.png"), this.cellSize, this.cellSize, true, true);
        }

        ImageView imageView = new ImageView();
        imageView.setImage(image);

        if (isBot) {
            imageView.relocate(x * this.cellSize + 5 * this.margin + this.width * this.cellSize, y * this.cellSize + this.margin + this.heightLabels);
        } else {
            imageView.relocate(x * this.cellSize + this.margin, y * this.cellSize + this.margin + this.heightLabels);
        }

        this.pane.getChildren().add(imageView);
        this.imageViews.add(imageView);
    }

    public void refreshInterface(int[][] ownField, int[][] ownShots, ArrayList<Ship> ownShips, int[][] opponentField, int[][] opponentShots, ArrayList<Ship> opponentShips, String title) {
        this.getNavigationBar().getTitleLabel().setText(title);

        if (this.gameType == GameType.SINGLE_PLAYER) {

            if (this.gameType == GameType.SINGLE_PLAYER && !this.didDrawShips) {
                for (Ship ship : ownShips) {
                    this.revealShip(ship, 5 * this.margin + this.width * this.cellSize);
                }

                this.didDrawShips = true;
            }

            for (ImageView imageView : this.imageViews) {
                imageView.toFront();
            }
        } else {
            this.pane.getChildren().removeAll(this.labels);
            this.pane.getChildren().removeAll(this.imageViews);
            this.pane.getChildren().removeAll(this.shipViews);
            this.pane.getChildren().removeAll(this.revealedShipViews);
            this.labels.clear();
            this.imageViews.clear();
            this.shipViews.clear();
            this.revealedShipViews.clear();

            for (Ship ship : ownShips) {
                this.revealShip(ship, 5 * this.margin + this.width * this.cellSize);
            }

            for (Ship ship : opponentShips) {
                if (ship.getDestroyed() == 0) {
                    this.revealShip(ship, 10);
                }
            }

            for (int i = 0; i < opponentField.length; i++) {
                for (int j = 0; j < opponentField[i].length; j++) {
                    if (opponentField[i][j] != 0) {

                        if (this.showDebugLabels) {
                            Label label = new Label(Integer.toString(opponentField[i][j]));
                            label.setFont(new Font("Arial", 36));
                            label.relocate(i * this.cellSize + 20, j * this.cellSize + this.margin + this.heightLabels);
                            this.pane.getChildren().add(label);
                            this.labels.add(label);
                        }
                    }

                    if (ownShots[i][j] == 1) {
                        if (opponentField[i][j] == 0) {
                            this.markOpponentField(false, i, j);
                        } else {
                            this.markOpponentField(true, i, j);
                        }
                    }

                    if (opponentShots[i][j] == 1 && ownField[i][j] != 0) {
                        this.markOwnField(i, j);
                    }
                }
            }
        }
    }

    private void markOpponentField(boolean hit, int i, int j) {
        String imagePath = hit ? "images/hit.png" : "images/miss.png";
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath), this.cellSize, this.cellSize, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.relocate(i * this.cellSize + this.margin, j * this.cellSize + this.margin + this.heightLabels);
        this.pane.getChildren().add(imageView);
        this.imageViews.add(imageView);
    }

    private void markOwnField(int i, int j) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/hit.png"), this.cellSize, this.cellSize, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.relocate(i * this.cellSize + 5 * this.margin + this.width * this.cellSize, j * this.cellSize + this.margin + this.heightLabels);
        this.pane.getChildren().add(imageView);
        this.imageViews.add(imageView);
    }

    public void revealShip(Ship ship, int xCoordinate) {
        ShipView shipView = new ShipView(false, ship.getLength());
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/ship" + ship.getLength() + ".png"), ship.getLength() * cellSize, cellSize, true, true);
        shipView.setImage(image);

        shipView.setRotate(ship.getDirection() * 90);

        if (ship.getLength() % 2 == 0) {
            if (ship.getDirection() % 2 != 0) {
                int differenceX = ship.getLength() == 2 ? this.cellSize : 2 * this.cellSize;
                int differenceY = ship.getLength() == 2 ? 0 : cellSize;
                shipView.relocate(ship.getX() * this.cellSize + xCoordinate + 20 - differenceX, ship.getY() * this.cellSize + this.margin + this.heightLabels + 20 + differenceY);
            } else {
                shipView.relocate(ship.getX() * this.cellSize + xCoordinate, ship.getY() * this.cellSize + this.margin + this.heightLabels);
            }
        } else {
            if (ship.getDirection() % 2 != 0) {
                int difference = ship.getLength() == 3 ? this.cellSize : 2 * this.cellSize;
                shipView.relocate(ship.getX() * this.cellSize + xCoordinate - difference, ship.getY() * this.cellSize + this.margin + this.heightLabels + difference);
            } else {
                shipView.relocate(ship.getX() * this.cellSize + xCoordinate, ship.getY() * this.cellSize + this.margin + this.heightLabels);
            }
        }

        this.pane.getChildren().add(shipView);
        this.shipViews.add(shipView);
    }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }

    public Pane getPane() {
        return this.pane;
    }
}
