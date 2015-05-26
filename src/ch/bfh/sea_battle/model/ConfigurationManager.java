package ch.bfh.sea_battle.model;

public class ConfigurationManager {
    private static ConfigurationManager instance = null;
    private int gridWidth = 10;
    private int gridHeight = 10;

    private ConfigurationManager() {
        // Damit der Singleton nicht von aussen instanziert werden kann.
    }

    public static ConfigurationManager sharedInstance() {
        if(instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public int getCellSize() {
        return 40;
    }

    public int getGridHeight() {
        return this.gridWidth;
    }

    public int getGridWidth() {
        return this.gridHeight;
    }

    public void setGridWidth(int width) {
        this.gridWidth = width;
    }

    public void setGridHeight(int height) {
        this.gridHeight = height;
    }

    public int getCellMargin() {
        return 10;
    }
}
