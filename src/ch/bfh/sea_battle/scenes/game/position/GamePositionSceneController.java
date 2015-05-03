package ch.bfh.sea_battle.scenes.game.position;

import ch.bfh.sea_battle.entities.Field;
import ch.bfh.sea_battle.entities.Player;
import ch.bfh.sea_battle.scenes.game.play.GamePlaySceneController;
import ch.bfh.sea_battle.model.DataProvider;
import ch.bfh.sea_battle.scenes.setup.SetupSceneController;
import ch.bfh.sea_battle.utilities.GameType;
import ch.bfh.sea_battle.utilities.StageProvider;
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

        this.view.getNavigationBar().getLeftButton().setOnAction(e -> {
            if (this.dataProvider.getGameType() == GameType.TWO_PLAYER && this.currentPlayer == this.dataProvider.getSecondPlayer()) {
                new GamePositionSceneController(this.dataProvider.getFirstPlayer()).show();
            } else {
                new SetupSceneController().show();
            }
        });

        this.view.getNavigationBar().getRightButton().setOnAction(e -> {

            //todo: Zuerst überprüfen, ob alle Schiffe gesetzt wurden

            if (this.dataProvider.getGameType() == GameType.SINGLE_PLAYER) {
                this.dataProvider.getFirstPlayer().setField(new Field(10, 10));
                //todo: setup ships for bot
                this.dataProvider.getSecondPlayer().setField(new Field(10, 10));
                new GamePlaySceneController().show();
            } else if (this.currentPlayer == this.dataProvider.getFirstPlayer()) {
                this.dataProvider.getFirstPlayer().setField(new Field(10, 10));
                new GamePositionSceneController(this.dataProvider.getSecondPlayer()).show();
            } else {
                this.dataProvider.getSecondPlayer().setField(new Field(10, 10));
                new GamePlaySceneController().show();
            }
        });
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
