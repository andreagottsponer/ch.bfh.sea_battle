package ch.bfh.sea_battle.model;

import ch.bfh.sea_battle.entities.Player;
import ch.bfh.sea_battle.utilities.GameType;

public class DataProvider {
    private static DataProvider instance = null;
    private Player firstPlayer = null;
    private Player secondPlayer = null;
    private GameType gameType = null;

    private DataProvider() {
        // Damit der Singleton nicht von aussen instanziert werden kann.
    }

    public static DataProvider sharedInstance() {
        if(instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getFirstPlayer() {
        return this.firstPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Player getSecondPlayer() {
        return this.secondPlayer;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return this.gameType;
    }
}