package ch.bfh.sea_battle.entities;

import ch.bfh.sea_battle.model.ConfigurationManager;

import java.util.ArrayList;

public class Field {
    protected int x;
    protected int y;
    protected int[][] field;
    protected ArrayList<Ship> ship;
    private int width = ConfigurationManager.sharedInstance().getGridWidth();
    private int height = ConfigurationManager.sharedInstance().getGridHeight();

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.ship = new ArrayList<>();
        field = new int[this.x][this.y];
    }

    public boolean addShip(Ship ship)  {
        boolean isShipPlacable = isShipPlacable(ship);

        if (isShipPlacable) {
            for (int i = 0; i < ship.length; ++i) {
                int x = ship.getX();
                int y = ship.getY();

                if (ship.getDirection() % 2 == 0)  // horizontal
                    x += i;
                else  // vertical
                    y += i;

                this.field[x][y] = ship.getUid() + 1;
            }

            this.ship.add(ship.getUid(), ship);
            ship.setUid(ship.getUid() + 1);
        }

        return isShipPlacable;
    }

    private boolean isShipPlacable(Ship ship) {
        //can we place the ship?
        for (int i = 0; i < ship.length; ++i) {
            int x = ship.getX();
            int y = ship.getY();

            if (ship.getDirection() % 2 == 0)  //horizontal
                x += i;
            else  //vertical
                y += i;

            //indices valid?
            final boolean validIndices = (x >= 0 && x < this.width && y >= 0 && y < this.height);

            if (validIndices) {
                //is the field empty?
                if (this.field[x][y] != 0) return false;
            } else {
                return false;
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
