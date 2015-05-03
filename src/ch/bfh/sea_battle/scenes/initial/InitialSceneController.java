package ch.bfh.sea_battle.scenes.initial;

import ch.bfh.sea_battle.model.DataProvider;
import ch.bfh.sea_battle.scenes.setup.SetupSceneController;
import ch.bfh.sea_battle.utilities.GameType;
import ch.bfh.sea_battle.utilities.StageProvider;

public class InitialSceneController {
    //Model
    private DataProvider dataProvider = DataProvider.sharedInstance();

    //View
    private InitialSceneView view;

    public InitialSceneController() {
        this.view = new InitialSceneView();

        view.getSinglePlayerButton().setOnAction(e -> {
            this.dataProvider.setGameType(GameType.SINGLE_PLAYER);
            new SetupSceneController().show();
        });

        view.getTwoPlayerButton().setOnAction(e -> {
            this.dataProvider.setGameType(GameType.TWO_PLAYER);
            new SetupSceneController().show();
        });
    }

    public void show(){
        this.view.show(StageProvider.sharedInstance().getApplicationStage());
    }
}
