package ch.bfh.sea_battle.scenes.setup;

import ch.bfh.sea_battle.entities.Bot;
import ch.bfh.sea_battle.entities.Player;
import ch.bfh.sea_battle.scenes.game.position.GamePositionSceneController;
import ch.bfh.sea_battle.scenes.initial.InitialSceneController;
import ch.bfh.sea_battle.model.DataProvider;
import ch.bfh.sea_battle.utilities.GameType;
import ch.bfh.sea_battle.utilities.StageProvider;

public class SetupSceneController {
    //Model
    private DataProvider dataProvider = DataProvider.sharedInstance();

    //View
    private SetupSceneView view;

    public SetupSceneController() {
        this.view = new SetupSceneView(dataProvider.getGameType());

        this.view.getNavigationBar().getLeftButton().setOnAction(e -> new InitialSceneController().show());

        this.view.getNavigationBar().getRightButton().setOnAction(e -> {
            String nameFirstPlayer = (this.view.getNameFirstPlayer().getText() == null || this.view.getNameFirstPlayer().getText().equals("")) ? "Player 1" : this.view.getNameFirstPlayer().getText();
            this.dataProvider.setFirstPlayer(new Player(nameFirstPlayer));

            if (this.dataProvider.getGameType() == GameType.SINGLE_PLAYER) {
                this.dataProvider.setSecondPlayer(new Bot("Computer"));
            } else {
                String nameSecondPlayer = (this.view.getNameSecondPlayer().getText() == null || this.view.getNameSecondPlayer().getText().equals("")) ? "Player 2" : this.view.getNameSecondPlayer().getText();
                this.dataProvider.setSecondPlayer(new Player(nameSecondPlayer));
            }

            new GamePositionSceneController(this.dataProvider.getFirstPlayer()).show();
        });
    }

    public void show(){
        this.view.show(StageProvider.sharedInstance().getApplicationStage());
    }
}
