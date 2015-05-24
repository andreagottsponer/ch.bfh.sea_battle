package ch.bfh.sea_battle.entities;

public class Ship {
    protected int uid;
    protected int length;
    protected int destroyed;
    protected int x, y;
    private int direction;

    public Ship(int uid, int length, int x, int y, int direction) {
        this.uid = uid;
        this.length = length;
        this.x = x;
        this.y = y;
        this.destroyed = length;
        this.direction = direction;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDestroyed() {
        return destroyed;
    }

    public void setDestroyed(int destroyed) {
        this.destroyed = destroyed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return this.direction;
    }
}



