package ch.bfh.sea_battle.entities;

public class Ship {
    protected int uid;
    protected int length;
    protected int destroyed;

    public Ship(int uid, int length) {
        this.uid = uid;
        this.length = length;
        this.destroyed = length;
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
}
