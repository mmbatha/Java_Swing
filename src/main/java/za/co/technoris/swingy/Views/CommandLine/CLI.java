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

import static za.co.technoris.swingy.Helpers.GlobalHelper.hero;
import static za.co.technoris.swingy.Helpers.GlobalHelper.isHero;
import static za.co.technoris.swingy.Helpers.GlobalHelper.map;
import static za.co.technoris.swingy.Helpers.GlobalHelper.isGUI;
import static za.co.technoris.swingy.Helpers.GlobalHelper.ANSI_RED;
import static za.co.technoris.swingy.Helpers.GlobalHelper.ANSI_RESET;
import static za.co.technoris.swingy.Helpers.GlobalHelper.ANSI_GREEN;

public class CLI {

	private static final String INVALID_OPTION = " Invalid option. Please try again...";

	public static void run() {
		isGUI = false;
		LoggerHelper.print("------- MODE: Console -------\n");
		PrintHelper.printMenu();

		presentOptions();
	}

	private static void presentOptions() {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.matches("\\s*[0-3]\\s*")) {
				int num = Integer.parseInt(line.trim());
				switch (num) {
				case 0:
					System.exit(0);
					break;
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
					LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + INVALID_OPTION);
					PrintHelper.printMenu();
				}
			} else {
				LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + INVALID_OPTION);
				PrintHelper.printMenu();
			}
		}
	}

	public static void moveHero() {
		PrintHelper.printDirections(hero.getName());
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.matches("[2468]\\s*")) {
				int numMove = Integer.parseInt(line.trim());
				GameManager.moveHero(numMove);
				GameManager.winCondition();
			} else {
				if (line.matches("5\\s*")) {
					LoggerHelper.print("\n- Level: " + hero.getLevel());
					LoggerHelper.print("- XP: " + hero.getXP());
					LoggerHelper.print("- Attack: " + hero.getAttack());
					LoggerHelper.print("- Defense: " + hero.getDefense());
					LoggerHelper.print("- HP: " + hero.getHP());
					LoggerHelper.print("- Weapon: " + hero.getWeapon().getName());
					LoggerHelper.print("- Armor: " + hero.getArmor().getName());
					LoggerHelper.print("- Helm: " + hero.getHelm().getName() + "\n");
				} else if (line.matches("0\\s*")) {
					DatabaseHandler.getInstance().updateHero(hero);
					System.exit(0);
				} else {
					LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + INVALID_OPTION);
				}
			}
			PrintHelper.printDirections(hero.getName());
		}
	}

	private static void selectHero() {
		LoggerHelper.print(ANSI_GREEN + "You can pick:" + ANSI_RESET);
		DatabaseHandler.getInstance().printHeroesFromDB();
		if (!isHero) {
			LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + " No such hero!\n");
			PrintHelper.printMenu();
			return;
		} else {
			LoggerHelper.print(ANSI_GREEN + ">> Type your hero's name and continue your adventure!" + ANSI_RESET);
		}
		isHero = false;

		getSelectedHero();
	}

	private static void getSelectedHero() {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			List<Hero> heroes = DatabaseHandler.getInstance().getFromDB();
			boolean matchedHero = false;
			for (Hero h : heroes) {
				if (h.getName().equals(line.trim())) {
					hero = DatabaseHandler.getInstance().getHeroData(h.getName());
					map = MapFactory.generateMap(hero);
					moveHero();
					matchedHero = true;
				}
			}
			if (!matchedHero) {
				LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + " Invalid option! No such hero! Try again...\n");
			}
		}
	}

	private static void nameHero(HeroTypes heroType) {
		LoggerHelper.print("Enter a name:");
		addNewHero(heroType);
	}

	private static void addNewHero(HeroTypes type) {
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

		selectHeroType();
		PrintHelper.printMenu();
	}

	private static void selectHeroType() {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.matches("\\s*[0-3]\\s*")) {
				int num = Integer.parseInt(line.trim());
				switch (num) {
				case 0:
					System.exit(0);
					break;
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
					LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + " Invalid option! Hero not created");
					PrintHelper.printHeroList();
					break;
				}
				break;
			} else {
				if (line.matches("\\s*see\\s+[0-3]\\s*")) {
					PrintHelper.printHeroDetail(Integer.parseInt(line.split("\\s+")[1]));
				} else {
					LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + INVALID_OPTION);
					PrintHelper.printHeroList();
				}
			}
		}
	}
}
