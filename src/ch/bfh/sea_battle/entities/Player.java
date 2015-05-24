package ch.bfh.sea_battle.entities;

import ch.bfh.sea_battle.model.ConfigurationManager;

public class Player {
    protected String name;
    protected Field field;
    protected int[][] shots;
    private int width = ConfigurationManager.sharedInstance().getGridWidth();
    private int height = ConfigurationManager.sharedInstance().getGridHeight();

    public Player(String name) {
        this.name = name;
        this.shots = new int[width][height];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int[][] getShots() {
        return this.shots;
    }

    public void reset() {
        this.shots = new int[width][height];
        this.field.reset();
    }
}