package ch.bfh.sea_battle.entities;

import java.util.ArrayList;

public class Field {
    protected int x;
    protected int y;
    protected int[][] field;
    protected ArrayList<Ship> ship;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addShip(Ship ship)  {
        this.ship.set(ship.getUid(), ship);
    }

    public boolean shot(int x, int y) {
        if(this.field[x][y] == 0) {
            return false;
        }

        //decrement ship destroyed -> when equels zero -> ship destroyed
        int destroyed = this.ship.get(this.field[x][y]).getDestroyed();
        this.ship.get(this.field[x][y]).setDestroyed(destroyed--);

        //set field to -1 * uid -> destroyed
        this.field[x][y] = -1 * this.field[x][y];

        return true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public ArrayList<Ship> getShip() {
        return ship;
    }

    public void setShip(ArrayList<Ship> ship) {
        this.ship = ship;
    }
}
