package ch.bfh.sea_battle.utilities;

import javafx.stage.Stage;

public class StageProvider {

    private static StageProvider instance = null;
    private Stage applicationStage = null;

    private StageProvider() {
        // Damit der Singleton nicht von aussen instanziert werden kann.
    }

    public static StageProvider sharedInstance() {
        if(instance == null) {
            instance = new StageProvider();
        }
        return instance;
    }

    public void setApplicationStage(Stage stage) {
        this.applicationStage = stage;
    }

    public Stage getApplicationStage() {
        return  this.applicationStage;
    }
}
