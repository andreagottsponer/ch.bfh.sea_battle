package ch.bfh.sea_battle.test;

import ch.bfh.sea_battle.entities.Bot;
import ch.bfh.sea_battle.entities.Field;
import ch.bfh.sea_battle.entities.Ship;
import ch.bfh.sea_battle.model.ConfigurationManager;

/**
 * Created by 1-7353 on 13.05.2015.
 */
public class bottest {
    public static void main(String [ ] args) {
        int fieldX = ConfigurationManager.sharedInstance().getGridWidth();
        int fieldY = ConfigurationManager.sharedInstance().getGridHeight();

        Field field = new Field(fieldX, fieldY);

        Ship ship1 = new Ship(1, 2, 1, 1, 0);
        Ship ship2 = new Ship(2, 3, 2, 2, 0);
        Ship ship3 = new Ship(3, 3, 3, 3, 0);
        Ship ship4 = new Ship(4, 4, 4, 4, 0);
        Ship ship5 = new Ship(5, 5, 5, 5, 0);

        field.addShip(ship1);
        field.addShip(ship2);
        field.addShip(ship3);
        field.addShip(ship4);
        field.addShip(ship5);

        int[][] field1 = new int[fieldX][fieldY];
        for(int ix = 0; ix < fieldX; ix++) {
            for (int iy = 0; iy < fieldY; iy++) {
                field1[ix][iy] = 0;
            }
        }

        field1[1][1] = 1;
        field1[1][2] = 1;

        field1[5][6] = 2;
        field1[6][6] = 2;
        field1[7][6] = 2;

        field1[7][7] = 3;
        field1[7][8] = 3;
        field1[7][9] = 3;

        field1[9][5] = 4;
        field1[9][4] = 4;
        field1[9][3] = 4;
        field1[9][2] = 4;

        field1[2][5] = 5;
        field1[2][6] = 5;
        field1[2][7] = 5;
        field1[2][8] = 5;
        field1[2][9] = 5;

        field.setField(field1);

        Bot bot = new Bot("ein BOT");
        bot.setField(field);

        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
        bot.shot();
    }
}
