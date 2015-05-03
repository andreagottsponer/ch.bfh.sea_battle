package ch.bfh.sea_battle.entities;

import java.util.ArrayList;

public class Bot extends Player {
    private int count;
    private int hit_count;
    private int[] shotx;
    private int[] shoty;

    public Bot(String name) {
        super(name);
        this.count = -1;
        this.hit_count = -1;
    }

    public int shot() {
        ArrayList<Ship> ships = this.field.getShip();

        if(hit_count == -1) {
            //get min ship length
            int max_length = 0;
            for (int i = 0; i < ships.size(); i++) {
                if (ships.get(i).getDestroyed() != 0) {
                    if (max_length == 0) {
                        max_length = ships.get(i).getLength();
                    } else {
                        if (max_length > ships.get(i).getLength()) {
                            max_length = ships.get(i).getLength();
                        }
                    }
                }
            }

            //get x,y from field
            int x = this.field.getX();
            int y = this.field.getY();

            //calculate modulo subgroup
            int subgroup = (x * y) % max_length;
            int max_multipicator = (x * y) / subgroup;

            //get field to shot
            while (true) {
                // create new random multipicator
                int multipicator = 0 + (int) (Math.random() * max_multipicator);
                int field = subgroup * multipicator;

                //get new x,y for shot
                x = field % 100;
                y = field % 10;

                //check for shots
                if(this.shotx[x] != 1 && this.shoty[y] != 1) {
                    break;
                }
            }

            //add shot to array
            this.count++;
            this.shotx[this.count] = 1;
            this.shoty[this.count] = 1;

            if (this.field.shot(x, y)) {
                this.hit_count = this.count;
            }
        }
        else {
            System.out.println("jetzt kill");
        }

        return 1;
    }

    public void setField(Field field) {
        this.field = field;
        this.shotx = new int[field.getX()];
        this.shoty = new int[field.getY()];
    }
}
