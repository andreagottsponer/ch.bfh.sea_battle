package ch.bfh.sea_battle.scenes.game.position;

import ch.bfh.sea_battle.entities.Field;
import ch.bfh.sea_battle.entities.Player;
import ch.bfh.sea_battle.scenes.game.play.GamePlaySceneController;
import ch.bfh.sea_battle.model.DataProvider;
import ch.bfh.sea_battle.scenes.setup.SetupSceneController;
import ch.bfh.sea_battle.utilities.GameType;
import ch.bfh.sea_battle.utilities.StageProvider;
import javafx.geometry.Insets;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
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

        this.view.getShipParking().setOnDragDetected(e -> {
            /* drag was detected, start drag-and-drop gesture*/
            System.out.println("onDragDetected");

            /* allow any transfer mode */
            Dragboard db = this.view.getShipParking().startDragAndDrop(TransferMode.ANY);

            /* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString("Teststring");
            db.setContent(content);

            e.consume();
        });

        this.view.getShipParking().setOnDragOver(e -> {
            /* data is dragged over the target */
            System.out.println("onDragOver parking");

            e.consume();
        });

        this.view.getShipPlacing().setOnDragOver(e -> {
            /* data is dragged over the target */
            System.out.println("onDragOver placing");

            e.consume();
        });

        this.view.getShipPlacing().setOnDragEntered(e -> {
            /* the drag-and-drop gesture entered the target */
            System.out.println("onDragEntered");
            this.view.getShipPlacing().setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            e.consume();
        });

        this.view.getShipPlacing().setOnDragExited(e -> {
            /* mouse moved away, remove the graphical cues */
            System.out.println("onDragExited");
            this.view.getShipPlacing().setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            e.consume();
        });

        this.view.getShipPlacing().setOnDragDropped(e -> {
            /* data dropped */
            System.out.println("onDragDropped");

            /* let the source know whether the string was successfully
             * transferred and used */
            //event.setDropCompleted(success);

            e.consume();
        });

        this.view.getShipParking().setOnDragDone(e -> {
         /* the drag-and-drop gesture ended */
            System.out.println("onDragDone");

            e.consume();
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
