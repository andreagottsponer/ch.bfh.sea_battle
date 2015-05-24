package ch.bfh.sea_battle.scenes.setup;

import ch.bfh.sea_battle.entities.Bot;
import ch.bfh.sea_battle.entities.Field;
import ch.bfh.sea_battle.entities.Player;
import ch.bfh.sea_battle.model.ConfigurationManager;
import ch.bfh.sea_battle.scenes.game.position.GamePositionSceneController;
import ch.bfh.sea_battle.scenes.initial.InitialSceneController;
import ch.bfh.sea_battle.model.DataProvider;
import ch.bfh.sea_battle.utilities.GameType;
import ch.bfh.sea_battle.utilities.StageProvider;
import javafx.scene.control.Alert;

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
            int numberOfColumns;
            int numberOfRows;

            try {
                numberOfColumns = Integer.parseInt(this.view.getNumberOfColumns().getText());
                numberOfRows = Integer.parseInt(this.view.getNumberOfRows().getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("Number of colums and rows must be valid numbers.");
                alert.showAndWait();
                return;
            }

            if (numberOfColumns < 7 || numberOfColumns > 20 || numberOfRows < 7 || numberOfRows > 20) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("Number of colums and rows must be between 8 and 20.");
                alert.showAndWait();
                return;
            }

            ConfigurationManager.sharedInstance().setGridWidth(numberOfColumns);
            ConfigurationManager.sharedInstance().setGridHeight(numberOfRows);

            int width = ConfigurationManager.sharedInstance().getGridWidth();
            int height = ConfigurationManager.sharedInstance().getGridHeight();

            Player player1 = new Player(nameFirstPlayer);
            Field field1 = new Field(width, height);
            player1.setField(field1);
            this.dataProvider.setFirstPlayer(player1);

            if (this.dataProvider.getGameType() == GameType.SINGLE_PLAYER) {
                this.dataProvider.setSecondPlayer(new Bot("Computer"));
            } else {
                String nameSecondPlayer = (this.view.getNameSecondPlayer().getText() == null || this.view.getNameSecondPlayer().getText().equals("")) ? "Player 2" : this.view.getNameSecondPlayer().getText();
                Player player2 = new Player(nameSecondPlayer);
                Field field2 = new Field(width, height);
                player2.setField(field2);
                this.dataProvider.setSecondPlayer(player2);
            }

            new GamePositionSceneController(this.dataProvider.getFirstPlayer()).show();
        });
    }

    public void show(){
        this.view.show(StageProvider.sharedInstance().getApplicationStage());
    }
}
