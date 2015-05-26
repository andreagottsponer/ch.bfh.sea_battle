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
        this.ship = new ArrayList<>();
        field = new int[this.x][this.y];
    }

    public boolean addShip(Ship ship)  {
        this.ship.add(ship.getUid(), ship);
        ship.setUid(ship.getUid() + 1);

        for (int i = 0; i < ship.length; i++) {
            if (ship.getDirection() % 2 == 0) {
                int x = ship.getX() + i;
                int y = ship.getY();
                if (x < 0 || x > 9 || y < 0 || y > 9 || this.field[x][y] != 0) {
                    this.ship.clear();
                    this.field = new int[this.x][this.y];
                    return false;
                }
                this.field[x][y] = ship.getUid();
            } else {
                int x = ship.getX();
                int y = ship.getY() + i;
                if (x < 0 || x > 9 || y < 0 || y > 9 || this.field[x][y] != 0) {
                    this.ship.clear();
                    this.field = new int[this.x][this.y];
                    return false;
                }
                this.field[x][y] = ship.getUid();
            }
        }

        return true;
    }

    public boolean shot(int x, int y) {
        if(this.field[x][y] == 0) {
            return false;
        }

        //decrement ship destroyed -> when equels zero -> ship destroyed
        int shipid = Math.abs(this.field[x][y]);
        int destroyed = this.ship.get(shipid-1).getDestroyed();
        destroyed--;
        this.ship.get(shipid-1).setDestroyed(destroyed);

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

    public void reset() {
        this.field = new int[this.x][this.y];
        this.ship.clear();
    }
}
