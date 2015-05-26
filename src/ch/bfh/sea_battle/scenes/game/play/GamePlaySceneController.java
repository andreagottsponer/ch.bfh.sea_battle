package ch.bfh.sea_battle.scenes.game.play;

import ch.bfh.sea_battle.entities.Bot;
import ch.bfh.sea_battle.entities.Player;
import ch.bfh.sea_battle.entities.Ship;
import ch.bfh.sea_battle.model.ConfigurationManager;
import ch.bfh.sea_battle.scenes.game.position.GamePositionSceneController;
import ch.bfh.sea_battle.scenes.initial.InitialSceneController;
import ch.bfh.sea_battle.model.DataProvider;
import ch.bfh.sea_battle.utilities.StageProvider;
import com.sun.javafx.tk.Toolkit;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class GamePlaySceneController {

    //Model
    private DataProvider dataProvider = DataProvider.sharedInstance();

    //View
    private GamePlaySceneView view;

    private Player currentPlayer;
    private Player opponentPlayer;
    private int width = ConfigurationManager.sharedInstance().getGridWidth();
    private int height = ConfigurationManager.sharedInstance().getGridHeight();

    public GamePlaySceneController() {
        this.view = new GamePlaySceneView();
        this.currentPlayer = this.dataProvider.getFirstPlayer();
        this.opponentPlayer = this.dataProvider.getSecondPlayer();

        this.view.getNavigationBar().getRightButton().setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quit Game?");
            alert.setHeaderText("Do you really want to quit this game?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.dataProvider.setFirstPlayer(null);
                this.dataProvider.setSecondPlayer(null);
                new InitialSceneController().show();
            }
        });

        this.view.getPane().setOnMouseClicked(e -> {
            int cellSize = ConfigurationManager.sharedInstance().getCellSize();
            int x = (int) (e.getX() - 10) / cellSize;
            int y = (int) (e.getY() - cellSize) / cellSize;
            this.interpretShot(x, y);
        });
    }

    private void interpretShot(int x, int y) {
        //check x/y if out of bounds
        if (0 > x || x > this.width || 0 > y || y > this.height) {
            return;
        }

        //prevent user from clicking an already clicked field
        if (this.currentPlayer.getShots()[x][y] != 0) {
            return;
        }

        boolean didHit = this.opponentPlayer.getField().getField()[x][y] != 0;
        this.view.clickedField(x, y, didHit);

        if (didHit) {
            int shipId = this.opponentPlayer.getField().getField()[x][y];
            Ship ship = this.opponentPlayer.getField().getShip().get(shipId - 1);

            if (ship.getDestroyed() > 0) {
                ship.setDestroyed(ship.getDestroyed() - 1);
            }

            if (ship.getDestroyed() == 0) {
                this.view.revealShip(ship, 10);
            }

            if (this.checkIfWon()) {
                this.showWonAlert();
                return;
            }
        }

        this.currentPlayer.getShots()[x][y] = 1;

        if (!didHit) {
            this.currentPlayer = this.currentPlayer == this.dataProvider.getFirstPlayer() ? this.dataProvider.getSecondPlayer() : this.dataProvider.getFirstPlayer();
            this.opponentPlayer = this.currentPlayer == this.dataProvider.getFirstPlayer() ? this.dataProvider.getSecondPlayer() : this.dataProvider.getFirstPlayer();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (didHit) {
            alert.setTitle("Hit!");
            alert.setHeaderText(null);
            alert.setContentText("Hit! Another turn for player " + this.currentPlayer.getName() + "!");
        } else {
            alert.setTitle("Hit!");
            alert.setHeaderText(null);
            alert.setContentText("Miss! " + this.currentPlayer.getName() + " has the next turn!");
        }

        alert.showAndWait();
        this.nextTurn();
    }

    private boolean checkIfWon() {
        for (Ship ship : this.opponentPlayer.getField().getShip()) {
            if (ship.getDestroyed() > 0) {
                return false;
            }
        }

        return true;
    }

    private void showWonAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText("Congratulations! " + this.currentPlayer.getName() + " has won this game!");
        alert.setContentText("Would you like to play another game?");

        ButtonType playAgainButton = new ButtonType("Play Again");
        ButtonType quitButton = new ButtonType("Quit");
        alert.getButtonTypes().setAll(playAgainButton, quitButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == playAgainButton) {
            this.dataProvider.getFirstPlayer().reset();
            this.dataProvider.getSecondPlayer().reset();
            new GamePositionSceneController(this.dataProvider.getFirstPlayer()).show();
        } else {
            this.dataProvider.setFirstPlayer(null);
            this.dataProvider.setSecondPlayer(null);
            new InitialSceneController().show();
        }
    }

    private void nextTurn() {
        if (this.currentPlayer instanceof Bot) {
            Bot bot = (Bot)this.currentPlayer;
            int[] result = bot.shot();
            this.interpretShot(result[1], result[0]);
        }

        String title = this.currentPlayer.getName() + ": Your turn!";
        this.view.refreshInterface(this.currentPlayer.getField().getField(), this.currentPlayer.getShots(), this.currentPlayer.getField().getShip(), this.opponentPlayer.getField().getField(), this.opponentPlayer.getShots(), this.opponentPlayer.getField().getShip(), title);
    }

    public void show() {
        Stage applicationStage = StageProvider.sharedInstance().getApplicationStage();
        this.view.show(applicationStage);
        this.nextTurn();
    }
}