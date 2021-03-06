package ch.bfh.sea_battle.entities;

import ch.bfh.sea_battle.model.ConfigurationManager;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Player {
    protected int[][] botshots;
    protected Field botfield;

    public Bot(String name) {
        super(name);
    }

    public int[] shot() {

        int[] result = new int[2];

        //get field x,y
        int x = this.botfield.getX();
        int y = this.botfield.getY();

        //get opponent field
        int[][] PlayerField = this.botfield.getField();

        //get available ships
        ArrayList<Ship> ships = this.botfield.getShip();

        int[] ship_length = new int[ships.size()];

        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).getDestroyed() != 0) {
                ship_length[i] = 1;
            } else {
                ship_length[i] = 0;
            }
        }

        //init max value
        int max_value = 0;

        //check for ships
        int[][] fieldcheck = new int[x][y];
        for (int ix = 0; ix < x; ix++) {
            for (int iy = 0; iy < y; iy++) {
                //set std value
                fieldcheck[ix][iy] = 0;

                //check for ships
                boolean direction_left = true;
                boolean direction_right = true;
                boolean direction_up = true;
                boolean direction_down = true;

                //check if it's already been shooted
                if(this.botshots[ix][iy] == 0) {
                    for(int ilength = 1; ilength < ship_length.length; ilength++) {
                        //check for right shots
                        if(direction_right) {
                            if((ix + ilength) >= x || this.botshots[ix + ilength][iy] == 0 || this.botshots[ix + ilength][iy] == 1 || this.botshots[ix + ilength][iy] == 3) {
                                direction_right = false;
                            }
                            else {
                                if(this.botshots[ix + ilength][iy] == 2) {
                                    fieldcheck[ix][iy]++;
                                    if (fieldcheck[ix][iy] > max_value) {
                                        max_value = fieldcheck[ix][iy];
                                    }
                                }
                            }
                        }
                        //check for left shots
                        if(direction_left) {
                            if((ix - ilength) < 0 || this.botshots[ix - ilength][iy] == 0 || this.botshots[ix - ilength][iy] == 1 || this.botshots[ix - ilength][iy] == 3) {
                                direction_left = false;
                            }
                            else {
                                if (this.shots[ix - ilength][iy] == 2) {
                                    fieldcheck[ix][iy]++;
                                    if (fieldcheck[ix][iy] > max_value) {
                                        max_value = fieldcheck[ix][iy];
                                    }
                                }
                            }
                        }
                        //check for down shots
                        if(direction_down) {
                            if((iy + ilength) >= y || this.botshots[ix][iy + ilength] == 0 || this.botshots[ix][iy + ilength] == 1 || this.botshots[ix][iy + ilength] == 3) {
                                direction_down = false;
                            }
                            else {
                                if (this.botshots[ix][iy + ilength] == 2) {
                                    fieldcheck[ix][iy]++;
                                    if (fieldcheck[ix][iy] > max_value) {
                                        max_value = fieldcheck[ix][iy];
                                    }
                                }
                            }
                        }
                        //check for up shots
                        if(direction_up) {
                            if((iy - ilength) < 0 || this.botshots[ix][iy - ilength] == 0 || this.botshots[ix][iy - ilength] == 1 || this.botshots[ix][iy - ilength] == 3) {
                                direction_up = false;
                            }
                            else {
                                if (this.botshots[ix][iy - ilength] == 2) {
                                    fieldcheck[ix][iy]++;
                                    if (fieldcheck[ix][iy] > max_value) {
                                        max_value = fieldcheck[ix][iy];
                                    }
                                }
                            }
                        }

                        if(!direction_right && !direction_left && !direction_down && ! direction_up) {
                            break;
                        }
                    }
                }
            }
        }

        if(max_value > 0) {
            // get all coordinates with the value max_value and choose one of these
            int[] coordX = new int[x * y];
            int[] coordY = new int[x * y];

            int i = 0;
            for (int ix = 0; ix < x; ix++) {
                for (int iy = 0; iy < y; iy++) {
                    if (fieldcheck[ix][iy] == max_value) {
                        coordX[i] = ix;
                        coordY[i] = iy;
                        i++;
                    }
                }
            }

            //set randomfactor
            Random r = new Random();
            int rand = r.nextInt(i - 0) + 0;

            int shotX = coordX[rand];
            int shotY = coordY[rand];

            result[0] = shotX;
            result[1] = shotY;

            System.out.println("2:--------");
            System.out.println(shotX);
            System.out.println(shotY);

            int shipid = Math.abs(PlayerField[shotX][shotY]);
            if (this.botfield.shot(shotX, shotY)) {
                //check, if ship was destoyed
                if(shipid > 0) {
                    //ship destoyed -> set as destroyed
                    if(ships.get(shipid-1).getDestroyed() <= 0) {
                        boolean endgame = true;
                        for (int ix = 0; ix < x; ix++) {
                            for (int iy = 0; iy < y; iy++) {
                                if(Math.abs(PlayerField[ix][iy]) == shipid) {
                                    this.botshots[ix][iy] = 3;
                                }
                            }
                        }
                    }
                    //Mark as hit
                    else {
                        this.botshots[shotX][shotY] = 2;
                    }
                }
            } else {
                this.botshots[shotX][shotY] = 1;
            }
        }
        else {
            //init max value
            max_value = 0;

            int[][] fieldcount = new int[x][y];
            for (int ix = 0; ix < x; ix++) {
                for (int iy = 0; iy < y; iy++) {
                    //set std value
                    fieldcount[ix][iy] = 0;

                    //check for ships
                    boolean direction_left = true;
                    boolean direction_right = true;
                    boolean direction_up = true;
                    boolean direction_down = true;

                    //check if it's already been shooted
                    if(this.botshots[ix][iy] == 0) {
                        //check for possibilities
                        for (int ilength = 0; ilength < ship_length.length; ilength++) {
                            //calculate posibilities (right)
                            if (direction_right) {
                                if ((ix + ilength) >= x) {
                                    direction_right = false;
                                } else {
                                    if (ship_length[ilength] == 1) {
                                        if (this.botshots[ix + ilength][iy] == 0) {
                                            fieldcount[ix][iy]++;
                                            if (fieldcount[ix][iy] > max_value) {
                                                max_value = fieldcount[ix][iy];
                                            }
                                        }
                                    }
                                }
                            }
                            //calculate posibilities (left)
                            if (direction_left) {
                                if ((ix - ilength) < 0) {
                                    direction_left = false;
                                } else {
                                    if (ship_length[ilength] == 1) {
                                        if (this.botshots[ix - ilength][iy] == 0) {
                                            fieldcount[ix][iy]++;
                                            if (fieldcount[ix][iy] > max_value) {
                                                max_value = fieldcount[ix][iy];
                                            }
                                        }
                                    }
                                }
                            }
                            //calculate posibilities (down)
                            if (direction_down) {
                                if ((iy + ilength) >= y) {
                                    direction_down = false;
                                } else {
                                    if (ship_length[ilength] == 1) {
                                        if (this.botshots[ix][iy + ilength] == 0) {
                                            fieldcount[ix][iy]++;
                                            if (fieldcount[ix][iy] > max_value) {
                                                max_value = fieldcount[ix][iy];
                                            }
                                        }
                                    }
                                }
                            }
                            //calculate posibilities (up)
                            if (direction_up) {
                                if ((iy - ilength) < 0) {
                                    direction_up = false;
                                } else {
                                    if (ship_length[ilength] == 1) {
                                        if (this.botshots[ix][iy - ilength] == 0) {
                                            fieldcount[ix][iy]++;
                                            if (fieldcount[ix][iy] > max_value) {
                                                max_value = fieldcount[ix][iy];
                                            }
                                        }
                                    }
                                }
                            }

                            if (!direction_right && !direction_left && !direction_down && !direction_up) {
                                break;
                            }
                        }
                    }
                }
            }

            // get all coordinates with the value max_value and choose one of these
            int[] coordX = new int[x * y];
            int[] coordY = new int[x * y];

            int i = 0;
            for (int ix = 0; ix < x; ix++) {
                for (int iy = 0; iy < y; iy++) {
                    if (fieldcount[ix][iy] == max_value) {
                        coordX[i] = ix;
                        coordY[i] = iy;
                        i++;
                    }
                }
            }

            //set randomfactor
            Random r = new Random();
            int rand = r.nextInt(i - 0) + 0;

            int shotX = coordX[rand];
            int shotY = coordY[rand];

            result[0] = shotX;
            result[1] = shotY;

            System.out.println("1:--------");
            System.out.println(shotX);
            System.out.println(shotY);

            if (this.botfield.shot(shotX, shotY)) {
                this.botshots[shotX][shotY] = 2;
            } else {
                this.botshots[shotX][shotY] = 1;
            }
        }

        for (int ix = 0; ix < x; ix++) {
            for (int iy = 0; iy < y; iy++) {
                System.out.print(this.botshots[ix][iy]+" ");
            }
            System.out.println("");
        }

        return result;
    }

    public void setBotField(Field field) {
        this.botfield = field;
        this.botshots = new int[field.getX()][field.getY()];
    }
}
