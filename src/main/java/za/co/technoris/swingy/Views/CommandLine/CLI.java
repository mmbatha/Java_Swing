package za.co.technoris.swingy.Views.CommandLine;

import za.co.technoris.swingy.Helpers.CharacterTypes;
import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Helpers.PrintHelper;
import za.co.technoris.swingy.Controllers.CharacterFactory;
import za.co.technoris.swingy.Controllers.MapFactory;
import za.co.technoris.swingy.Controllers.GameManager;
import za.co.technoris.swingy.Database.DatabaseHandler;
import za.co.technoris.swingy.Models.Characters.Hero;
import za.co.technoris.swingy.Views.GraphicUser.GUI;

import java.util.List;
import java.util.Scanner;

import static za.co.technoris.swingy.Helpers.GlobalHelper.*;

/**
 * Created by mmbatha on 5/3/17.
 */
public class CLI {

    public static void run() {
        isGUI = false;
        LoggerHelper.print("------- MODE: Console -------");
        PrintHelper.printMenu();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.matches("\\s*[1-3]\\s*")) {
                Integer num = Integer.parseInt(line.trim());
                switch (num) {
                    case 1:
                        createHero();
                        break;
                    case 2:
                        selectHero();
                        break;
                    case 3:
                        GUI.run();
                        return;
                    default:
                        LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Invalid option. Please try again...");
                        PrintHelper.printMenu();
                }
            } else {
                LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Invalid option. Please try again...");
                PrintHelper.printMenu();
            }
        }
        scanner.close();
    }

    public static void moveHero() {
        PrintHelper.printDirections();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.matches("\\s*[1-4]\\s*")) {
                Integer nb = Integer.parseInt(line.trim());
                GameManager.move(nb);
                GameManager.winCondition();
            } else {
                if (line.matches("\\s*5\\s*")) {
                    LoggerHelper.print("- Level: " + hero.getLevel());
                    LoggerHelper.print("- Xp: " + hero.getXp());
                    LoggerHelper.print("- Attack: " + hero.getAttack());
                    LoggerHelper.print("- Defense: " + hero.getDefense());
                    LoggerHelper.print("- Hp: " + hero.getHp());
                    LoggerHelper.print("- Weapon: " + hero.getWeapon().getName());
                    LoggerHelper.print("- Armor: " + hero.getArmor().getName());
                    LoggerHelper.print("- Helm: " + hero.getHelm().getName());
                } else {
                    LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Incorrect choice.");
                }
            }
            PrintHelper.printDirections();
        }
        scanner.close();
    }

    private static void selectHero() {
        LoggerHelper.print(ANSI_GREEN + "You can pick:" + ANSI_RESET);
        DatabaseHandler.getInstance().printDB();
        if (!isHero) {
            LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " No saved hero");
            PrintHelper.printMenu();
            return ;
        } else {
            LoggerHelper.print(ANSI_GREEN + "Choose your hero and continue your adventure !" + ANSI_RESET);
        }
        isHero = false;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            List<Hero> heroes = DatabaseHandler.getInstance().getDB();
            boolean matchedHero = false;
            for (Hero hero : heroes) {
                if (hero.getName().equals(line.trim())) {
                    hero = DatabaseHandler.getInstance().getHeroData(hero.getName());
                    map = MapFactory.generateMap(hero);
                    moveHero();
                    matchedHero = true;
                }
            }
            if (!matchedHero) {
                LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Incorrect choice. No such hero name");
            }
        }
    }

    private static void nameHero(CharacterTypes type) {
        LoggerHelper.print("Enter a name:");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (CharacterFactory.newHero(line, type) != null) {
                DatabaseHandler.getInstance().insertHero((Hero) CharacterFactory.newHero(line.trim(), type));
                break;
            } else {
                LoggerHelper.print("Enter a name:");
            }
        }
    }

    private static void createHero() {
        PrintHelper.printHeroList();
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String arg = in.nextLine();
            if (arg.matches("\\s*[1-3]\\s*")) {
                Integer nb = Integer.parseInt(arg.trim());
                switch (nb) {
                    case 1:
                        nameHero(CharacterTypes.WARRIOR);
                        break;
                    case 2:
                        nameHero(CharacterTypes.THIEF);
                        break;
                    case 3:
                        nameHero(CharacterTypes.WIZARD);
                        break;
                    default:
                        LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Incorrect choice. Hero not created");
                        PrintHelper.printHeroList();
                        break;
                }
                break;
            } else {
                if (arg.matches("\\s*see\\s+[1-3]\\s*")) {
                    PrintHelper.printHeroDetail(Integer.parseInt(arg.split("\\s+")[1]));
                } else {
                    LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Incorrect choice.");
                    PrintHelper.printHeroList();
                }
            }
        }
        PrintHelper.printMenu();
    }
}
