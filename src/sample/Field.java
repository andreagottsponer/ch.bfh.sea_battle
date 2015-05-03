package sample;

import java.util.ArrayList;

/**
 * Created by 1-7353 on 27.04.2015.
 */
public class Field {
    protected int x;
    protected int y;
    protected int[][] field;
    protected Player player;
    protected ArrayList<Ship> ship;

    public Field(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Ship> getShip() {
        return ship;
    }

    public void setShip(ArrayList<Ship> ship) {
        this.ship = ship;
    }
}
