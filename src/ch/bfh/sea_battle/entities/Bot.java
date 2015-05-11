package ch.bfh.sea_battle.entities;

import java.util.ArrayList;

public class Bot extends Player {
    private Field field;
    private int[][] shots;

    public Bot(String name) {
        super(name);
    }

    public int shot() {
        //get field x,y
        int x = this.field.getX();
        int y = this.field.getY();

        //get ships
        ArrayList<Ship> ships = this.field.getShip();

        int[] ship_length = new int[ships.size()];
        for (int i = 0; i < ships.size(); i++) {
            if(ships.get(i).getDestroyed() != 0) {
                ship_length[i] = 1;
            }
        }

        int[][] fieldcount = new int[x][y];
        for (int ix = 0; ix < x; ix++) {
            for (int iy = 0; iy < y; iy++) {
                //check for ships
                int max_value = 0;
                boolean direction_left = true;
                boolean direction_right = true;
                boolean direction_up = true;
                boolean direction_down = true;

                for(int ilength = 0; ilength <= ship_length.length; ilength++) {
                    //calculate posibilities (right)
                    if(direction_right) {
                        if ((ix + ilength) > x) {
                            direction_right = false;
                        } else {
                            if (ship_length[ilength] == 1) {
                                if (this.shots[ix + ilength][iy] == 0) {
                                    fieldcount[ix][iy]++;
                                    if(fieldcount[ix][iy] > max_value) {
                                        max_value = fieldcount[ix][iy];
                                    }
                                }
                            }
                        }
                    }
                    //calculate posibilities (left)
                    if(direction_left) {
                        if((ix-ilength) < 0) {
                            direction_left = false;
                        }
                        else {
                            if(ship_length[ilength] == 1) {
                                if(this.shots[ix-ilength][iy] == 0) {
                                    fieldcount[ix][iy]++;
                                    if(fieldcount[ix][iy] > max_value) {
                                        max_value = fieldcount[ix][iy];
                                    }
                                }
                            }
                        }
                    }
                    //calculate posibilities (down)
                    if(direction_down) {
                        if((iy+ilength) > y) {
                            direction_down = false;
                        }
                        else {
                            if(ship_length[ilength] == 1) {
                                if(this.shots[ix][iy+ilength] == 0) {
                                    fieldcount[ix][iy]++;
                                    if(fieldcount[ix][iy] > max_value) {
                                        max_value = fieldcount[ix][iy];
                                    }
                                }
                            }
                        }
                    }
                    //calculate posibilities (up)
                    if(direction_up) {
                        if((iy-ilength) < 0) {
                            direction_up = false;
                        }
                        else {
                            if(ship_length[ilength] == 1) {
                                if(this.shots[ix][iy-ilength] == 0) {
                                    fieldcount[ix][iy]++;
                                    if(fieldcount[ix][iy] > max_value) {
                                        max_value = fieldcount[ix][iy];
                                    }
                                }
                            }
                        }
                    }

                    if(!direction_right && !direction_left && !direction_down && !direction_up) {
                        break;
                    }
                }

                // get all coordinates with the value max_value and choose one of these
                int[] coordX = new int[x];
                int[] coordY = new int[y];
            }
        }



        //old version
        /*
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
        */

        return 1;
    }

    public void setField(Field field) {
        this.field = field;
        this.shots = new int[field.getX()][field.getY()];
    }
}
