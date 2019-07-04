/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:41:49 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:41:49 
 */
package za.co.technoris.swingy.Views.CommandLine;

import za.co.technoris.swingy.Helpers.HeroTypes;
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

public class CLI {

	private static final String INVALID_OPTION = " Invalid option. Please try again...";

	public static void run() {
		isGUI = false;
		LoggerHelper.print("------- MODE: Console -------");
		PrintHelper.printMenu();
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.matches("\\s*[1-3]\\s*")) {
				int num = Integer.parseInt(line.trim());
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
					LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + INVALID_OPTION);
					PrintHelper.printMenu();
				}
			} else {
				LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + INVALID_OPTION);
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
			if (line.matches("[1234]\\s*")) {
				int numMove = Integer.parseInt(line.trim());
				GameManager.move(numMove);
				GameManager.winCondition();
			} else {
				if (line.matches("5\\s*")) {
					LoggerHelper.print("- Level: " + hero.getLevel());
					LoggerHelper.print("- XP: " + hero.getXp());
					LoggerHelper.print("- Attack: " + hero.getAttack());
					LoggerHelper.print("- Defense: " + hero.getDefense());
					LoggerHelper.print("- HP: " + hero.getHp());
					LoggerHelper.print("- Weapon: " + hero.getWeapon().getName());
					LoggerHelper.print("- Armor: " + hero.getArmor().getName());
					LoggerHelper.print("- Helm: " + hero.getHelm().getName());
				} else {
					LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + INVALID_OPTION);
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
			return;
		} else {
			LoggerHelper.print(ANSI_GREEN + "Type your hero's name and continue your adventure!" + ANSI_RESET);
		}
		isHero = false;
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			List<Hero> heroes = DatabaseHandler.getInstance().getFromDB();
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
				LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Invalid option! No such hero name");
			}
		}
		scanner.close();
	}

	private static void nameHero(HeroTypes type) {
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
		scanner.close();
	}

	private static void createHero() {
		PrintHelper.printHeroList();
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.matches("\\s*[1-3]\\s*")) {
				int nb = Integer.parseInt(line.trim());
				switch (nb) {
				case 1:
					nameHero(HeroTypes.VILLAIN);
					break;
				case 2:
					nameHero(HeroTypes.FARMER);
					break;
				case 3:
					nameHero(HeroTypes.NERD);
					break;
				default:
					LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + " Invalid option! Hero not created");
					PrintHelper.printHeroList();
					break;
				}
				break;
			} else {
				if (line.matches("\\s*see\\s+[1-3]\\s*")) {
					PrintHelper.printHeroDetail(Integer.parseInt(line.split("\\s+")[1]));
				} else {
					LoggerHelper.print(ANSI_RED + ">" + ANSI_RESET + INVALID_OPTION);
					PrintHelper.printHeroList();
				}
			}
		}
		scanner.close();
		PrintHelper.printMenu();
	}
}
