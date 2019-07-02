package za.co.technoris.swingy.Views;

import za.co.technoris.swingy.Models.characters.Character;
import za.co.technoris.swingy.Models.characters.Hero;
import lombok.Getter;
import java.util.Random;

import static za.co.technoris.swingy.Helpers.GlobalHelper.*;


/**
 * Created by mmbatha on 5/2/17.
 */
@Getter
public class Map {
    private int[][] map;
    public int mapSize;
    private Hero hero;
    private int[] prevPosition = new int[] { -1, -1};;

    public Map(int mapSize) {
        this.mapSize = mapSize;
        this.map = new int[mapSize][mapSize];
    }

    public void updateHeroPosition() {
        this.map[prevPosition[0]][prevPosition[1]] = 0;
        prevPosition[0] = hero.getX();
        prevPosition[1] = hero.getY();
        if (this.map[hero.getX()][hero.getY()] == 2) {
            this.map[hero.getX()][hero.getY()] = 8;
        } else {
            this.map[hero.getX()][hero.getY()] = 1;
        }
        if (!isGUI) {
            printMap();
        }
    }

    public void registerHero(Character hero) {
        this.hero = (Hero) hero;
        this.hero.register(this);
        this.hero.setX(mapSize / 2);
        this.hero.setY(mapSize / 2);
        prevPosition[0] = this.hero.getX();
        prevPosition[1] = this.hero.getY();
        this.map[mapSize / 2][mapSize / 2] = 1;
    }

    public void generateFoes() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] != 1) {
                    int random = new Random().nextInt(3);
                    if (random == 0) {
                        map[i][j] = 2;
                    }
                }
            }
        }
        if (!isGUI) {
            printMap();
        }
    }

    private void printMap() {
        for (int[] line : map) {
            for (int col : line) {
                String box = col + " ";
                switch (col) {
                    case 1:
                        System.out.print(ANSI_GREEN + box + ANSI_RESET);
                        break;
                    case 2:
                        System.out.print(ANSI_RED + box + ANSI_RESET);
                        break;
                    case 8:
                        System.out.print(ANSI_YELLOW + box + ANSI_RESET);
                        break;
                    default:
                        System.out.print(box);
                        break;
                }
            }
            System.out.println();
        }
    }
}