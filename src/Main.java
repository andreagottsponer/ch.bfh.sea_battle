import ch.bfh.sea_battle.scenes.initial.InitialSceneController;
import ch.bfh.sea_battle.utilities.StageProvider;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageProvider.sharedInstance().setApplicationStage(primaryStage);
        primaryStage.setTitle("Battle Ship");
        InitialSceneController controller = new InitialSceneController();
        controller.show();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
