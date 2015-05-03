package ch.bfh.sea_battle.scenes.game.play;

import ch.bfh.sea_battle.scenes.initial.InitialSceneController;
import ch.bfh.sea_battle.model.DataProvider;
import ch.bfh.sea_battle.utilities.StageProvider;
import javafx.stage.Stage;

public class GamePlaySceneController {
    //Model
    private DataProvider dataProvider = DataProvider.sharedInstance();

    //View
    private GamePlaySceneView view;

    public GamePlaySceneController() {
        this.view = new GamePlaySceneView();
        this.view.getNavigationBar().getRightButton().setOnAction(e -> {

            //Todo: First, present an alert to make sure the user wants to quit!

            this.dataProvider.setFirstPlayer(null);
            this.dataProvider.setSecondPlayer(null);
            new InitialSceneController().show();
        });
    }

    public void show() {
        Stage applicationStage = StageProvider.sharedInstance().getApplicationStage();
        view.getNavigationBar().getTitleLabel().setText(this.dataProvider.getFirstPlayer().getName() + ": Your Turn!");
        this.view.show(applicationStage);
    }
}
