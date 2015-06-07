package ch.bfh.sea_battle.scenes.game.position;

import ch.bfh.sea_battle.entities.Bot;
import ch.bfh.sea_battle.entities.Field;
import ch.bfh.sea_battle.entities.Player;
import ch.bfh.sea_battle.entities.Ship;
import ch.bfh.sea_battle.model.ConfigurationManager;
import ch.bfh.sea_battle.scenes.game.play.GamePlaySceneController;
import ch.bfh.sea_battle.model.DataProvider;
import ch.bfh.sea_battle.scenes.initial.InitialSceneController;
import ch.bfh.sea_battle.utilities.GameType;
import ch.bfh.sea_battle.utilities.ShipView;
import ch.bfh.sea_battle.utilities.StageProvider;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Random;

public class GamePositionSceneController {
    //Model
    private DataProvider dataProvider = DataProvider.sharedInstance();

    //View
    private GamePositionSceneView view;

    private Player currentPlayer;

    public GamePositionSceneController(Player player) {
        this.currentPlayer = player;
        this.view = new GamePositionSceneView();

        this.view.getNavigationBar().getLeftButton().setOnAction(e -> new InitialSceneController().show());

        this.view.getNavigationBar().getRightButton().setOnAction(e -> {

            if (this.dataProvider.getGameType() == GameType.SINGLE_PLAYER) {
                if (!this.saveShipsForPlayer(this.dataProvider.getFirstPlayer())) {
                    this.showIncorrectAlert();
                } else {
                    Field field = generateShipsForBot();
                    this.dataProvider.getSecondPlayer().setField(field);
                    new GamePlaySceneController().show();
                }
            } else if (this.currentPlayer == this.dataProvider.getFirstPlayer()) {
                if (!this.saveShipsForPlayer(this.dataProvider.getFirstPlayer())) {
                    this.showIncorrectAlert();
                } else {
                    new GamePositionSceneController(this.dataProvider.getSecondPlayer()).show();
                }
            } else {
                if (!this.saveShipsForPlayer(this.dataProvider.getSecondPlayer())) {
                    this.showIncorrectAlert();
                } else {
                    new GamePlaySceneController().show();
                }
            }
        });
    }

    private Field generateShipsForBot() {
        int width = ConfigurationManager.sharedInstance().getGridWidth();
        int height = ConfigurationManager.sharedInstance().getGridHeight();

        Field field = new Field(width, height);
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            int direction = rand.nextInt(4);
            int x;
            int y;
            int length;

            do {
                switch (i) {
                    case 2: length = 3; break;
                    case 3: length = 3; break;
                    case 4: length = 4; break;
                    case 5: length = 5; break;
                    default: length = 2; break;
                }

                if (direction % 2 == 0) {
                    x = rand.nextInt(width-length);
                    y = rand.nextInt(height);
                } else {
                    x = rand.nextInt(width);
                    y = rand.nextInt(height - length);
                }
            } while (!field.addShip(new Ship(i, length, x, y, direction)));
        }

        return field;
    }

    private void showIncorrectAlert() {
        this.currentPlayer.getField().getShip().clear();
        this.currentPlayer.getField().setField(new int[this.currentPlayer.getField().getX()][this.currentPlayer.getField().getY()]);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Incorrect Placement!");
        alert.setHeaderText(null);
        alert.setContentText("Please consider that placing ships outside the field or on top of each other is not allowed.");
        alert.showAndWait();
        return;
    }

    public boolean saveShipsForPlayer(Player player) {

        int cellSize = ConfigurationManager.sharedInstance().getCellSize();

        Field botfield = new Field(player.getField().getX(), player.getField().getY());

        for (int i = 0; i < this.view.getShips().size(); i++) {
            ShipView currentShip = this.view.getShips().get(i);
            Bounds boundsInScene = currentShip.localToScene(currentShip.getBoundsInLocal());
            int x = (int)(boundsInScene.getMinX() - 10) / cellSize;
            int y = (int)(boundsInScene.getMinY() - 56) / cellSize;

            //System.out.println("X: " + x + ", Y: " + y);
            if (!player.getField().addShip(new Ship(i, currentShip.getLength(), x, y, currentShip.getDirection()))) {
               return false;
            }

            if(!botfield.addShip(new Ship(i, currentShip.getLength(), x, y, currentShip.getDirection()))) {
                return false;
            }
        }

        if (this.dataProvider.getGameType() == GameType.SINGLE_PLAYER) {
            Bot bot = (Bot) this.dataProvider.getSecondPlayer();

            int[][] bot_field = botfield.getField();
            int[][] tmp_field = new int[botfield.getY()][botfield.getX()];
            for (int ix = 0; ix < botfield.getY(); ix++) {
                for (int iy = 0; iy < botfield.getX(); iy++) {
                    tmp_field[iy][ix] = bot_field[ix][iy];
                }
            }
            botfield.setField(tmp_field);
            bot.setBotField(botfield);
        }

        return true;
    }

    public void show(){
        Stage applicationStage = StageProvider.sharedInstance().getApplicationStage();

        if (this.dataProvider.getGameType() == GameType.TWO_PLAYER && this.currentPlayer == this.dataProvider.getFirstPlayer()) {
            this.view.getNavigationBar().getRightButton().setText("Continue");
        } else {
            this.view.getNavigationBar().getRightButton().setText("Start Game");
        }

        this.view.getNavigationBar().getTitleLabel().setText(this.currentPlayer.getName() + ": Position Your Ships");
        this.view.show(applicationStage);
    }
}