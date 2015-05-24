package ch.bfh.sea_battle.scenes.game.position;

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
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Incorrect Placement!");
                    alert.setHeaderText(null);
                    alert.setContentText("Please consider that placing ships outside the field or on top of each other is not allowed.");
                    alert.showAndWait();
                    return;
                }

                int width = ConfigurationManager.sharedInstance().getGridWidth();
                int height = ConfigurationManager.sharedInstance().getGridHeight();

                //setup ships for bot
                Field field = new Field(width, height);

                //todo: randomize creation of bot ships
                Ship ship1 = new Ship(0, 3, 1, 1, 0);
                Ship ship2 = new Ship(1, 3, 2, 2, 0);
                Ship ship3 = new Ship(2, 3, 3, 3, 0);
                Ship ship4 = new Ship(3, 3, 4, 4, 0);
                Ship ship5 = new Ship(4, 3, 5, 5, 0);

                field.addShip(ship1);
                field.addShip(ship2);
                field.addShip(ship3);
                field.addShip(ship4);
                field.addShip(ship5);

                this.dataProvider.getSecondPlayer().setField(field);

                new GamePlaySceneController().show();

            } else if (this.currentPlayer == this.dataProvider.getFirstPlayer()) {
                this.saveShipsForPlayer(this.dataProvider.getFirstPlayer());
                new GamePositionSceneController(this.dataProvider.getSecondPlayer()).show();
            } else {
                this.saveShipsForPlayer(this.dataProvider.getSecondPlayer());
                new GamePlaySceneController().show();
            }
        });
    }

    public boolean saveShipsForPlayer(Player player) {

        int cellSize = ConfigurationManager.sharedInstance().getCellSize();

        for (int i = 0; i < this.view.getShips().size(); i++) {
            ShipView currentShip = this.view.getShips().get(i);
            Bounds boundsInScene = currentShip.localToScene(currentShip.getBoundsInLocal());
            int x = (int)(boundsInScene.getMinX() - 10) / cellSize;
            int y = (int)(boundsInScene.getMinY() - 56) / cellSize;

            //System.out.println("X: " + x + ", Y: " + y);
            if (!player.getField().addShip(new Ship(i, currentShip.getLength(), x, y, currentShip.getDirection()))) {
               return false;
            }
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