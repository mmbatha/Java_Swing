package za.co.technoris.swingy.Controllers;

import za.co.technoris.swingy.Helpers.*;
import za.co.technoris.swingy.Database.DatabaseHandler;
import za.co.technoris.swingy.Models.Artifacts.Armor;
import za.co.technoris.swingy.Models.Artifacts.Helm;
import za.co.technoris.swingy.Models.Artifacts.Weapon;
import za.co.technoris.swingy.Models.Characters.Foe;
import za.co.technoris.swingy.Views.CommandLine.CLI;

import java.util.Random;
import java.util.Scanner;
import static za.co.technoris.swingy.Helpers.GlobalHelper.*;
import static za.co.technoris.swingy.Controllers.CharacterFactory.newFoe;

/**
 * Created by mmbatha on 5/4/17.
 */
public class GameManager {

    private final static int NORTH = 1;
    private final static int EAST = 2;
    private final static int SOUTH = 3;
    private final static int WEST = 4;

    private static int[] prevMove = new int[2];

    public static void winCondition() {
        if (hero.getX() == map.getMapSize() - 1 ||
                hero.getY() == map.getMapSize() - 1 ||
                hero.getX() == 0 || hero.getY() == 0) {
            LoggerHelper.print("You've reached a wall! You live to die another day.");
            map = MapFactory.generateMap(hero);
            if (!isGUI) {
                CLI.moveHero();
            }
        }
    }

    private static void loot() {
        int random = new Random().nextInt(2);

        if (random == 1) {
            LoggerHelper.print("An artifact was dropped!");
            int random2 = new Random().nextInt(4);
            int stats = hero.getLevel() + 1;
            switch (random2) {
                case 0:
                    artifact = new Weapon("Good sword!", stats);
                    LoggerHelper.print(artifact.getName() + " - Attack: " + stats);
                    LoggerHelper.print("If you take this artifact, you will gain " +
                            (((Weapon) artifact).getAttack() - hero.getWeapon().getAttack()) + " attack points");
                    break;
                case 1:
                    artifact = new Armor("Good armor!", stats);
                    LoggerHelper.print(artifact.getName() + " - Defense: " + stats);
                    LoggerHelper.print("If you take this artifact, you will gain " +
                            (((Armor) artifact).getDefense() - hero.getArmor().getDefense()) + " defense points");
                    break;
                case 2:
                    artifact = new Helm("Good helm!", stats);
                    LoggerHelper.print(artifact.getName() + " - Hp: " + stats);
                    LoggerHelper.print("If you take this artifact, you will gain " +
                            (((Helm) artifact).getHp() - hero.getHelm().getHp()) + " hp");
                    break;
                case 3:
                    hero.setHp(hero.getHp() + hero.getLevel() + 1);
                    LoggerHelper.print("You found a health potion! Current health: " + hero.getHp());
                    return;
            }
            if (!isGUI) {
                LoggerHelper.print("Would you like to keep it?");
                LoggerHelper.print("1 - Yes");
                LoggerHelper.print("2 - No");
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.matches("\\s*[1-2]\\s*")) {
                        Integer nb = Integer.parseInt(line.trim());
                        if (nb == 1) {
                            hero.pickUp(artifact, artifact.getType());
                            LoggerHelper.print("<" + artifact.getName() + "> equipped");
                            break;
                        } else if (nb == 2) {
                            break;
                        } else {
                            LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Invalid option");
                        }
                    } else {
                        LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Invalid option");
                    }
                }
                fightPhase = false;
            } else {
                lootOption = true;
            }
        }
    }

    public static void fight(boolean fled) {
        if (fled) {
            LoggerHelper.print("Enemy starts attacking!");
            while (hero.getHp() > 0 && foe.getHp() > 0) {
                foe.attack(hero);
                foe.attack(hero);
                if (hero.getHp() > 0) {
                    hero.attack(foe);
                }
            }
        } else {
            LoggerHelper.print("You start attacking!");
            while (hero.getHp() > 0 && foe.getHp() > 0) {
                hero.attack(foe);
                if (foe.getHp() > 0) {
                    foe.attack(hero);
                }
            }
        }
        if (hero.getHp() <= 0) {
            LoggerHelper.print("Game Over!");
            if (!isGUI) {
                CLI.run();
            }
        } else if (foe.getHp() <= 0) {
            DatabaseHandler.getInstance().updateHero(hero);
            hero.setPosition(0, 0);
            LoggerHelper.print("You win!");
            loot();
        }
    }

    public static void run() {
        int random = new Random().nextInt(2);
        switch (random) {
            case 0:
                LoggerHelper.print("You couldn't escape!");
                fight(true);
                break;
            case 1:
                LoggerHelper.print("You fled from the battle!");
                hero.setPosition(prevMove[0] * -1, prevMove[1] * -1);
                break;
        }
        fightPhase = false;
    }

    private static void fightOrRun() {
        if (!isGUI) {
            PrintHelper.printFightOptions();
        }
        Scanner scanner = new Scanner(System.in);
        if (!isGUI) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.matches("\\s*[1-2]\\s*")) {
                    Integer num = Integer.parseInt(line.trim());
                    switch (num) {
                        case 1:
                            fight(false);
                            return;
                        case 2:
                            run();
                            return;
                    }
                } else {
                    LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Invalid option");
                    PrintHelper.printFightOptions();
                }
            }
        } else {
            encounterPhase = true;
        }
    }

    public static void move(int direction) {
        switch (direction) {
            case NORTH:
                hero.setPosition(-1, 0);
                prevMove[0] = -1;
                prevMove[1] = 0;
                break;
            case EAST:
                hero.setPosition(0, 1);
                prevMove[0] = 0;
                prevMove[1] = 1;
                break;
            case SOUTH:
                hero.setPosition(1, 0);
                prevMove[0] = 1;
                prevMove[1] = 0;
                break;
            case WEST:
                hero.setPosition(0, -1);
                prevMove[0] = 0;
                prevMove[1] = -1;
                break;
        }
        if (map.getMap()[hero.getX()][hero.getY()] == 8) {
            fightPhase = true;
            int randomNum = new Random().nextInt(3);
            foe = (Foe) newFoe((randomNum == 2) ? CharacterTypes.ZOMBIE : CharacterTypes.WOLF, hero);
            LoggerHelper.print("Enemy encounters: \"" + foe.getName() + "\" level " + foe.getLevel() + "!");
            fightOrRun();
        }
    }
}
