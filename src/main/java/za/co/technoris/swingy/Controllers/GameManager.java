/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:45:23 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:45:23 
 */
package za.co.technoris.swingy.Controllers;

import za.co.technoris.swingy.Helpers.FoeTypes;
import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Helpers.PrintHelper;
import za.co.technoris.swingy.Database.DatabaseHandler;
import za.co.technoris.swingy.Models.Artifacts.Armor;
import za.co.technoris.swingy.Models.Artifacts.Helm;
import za.co.technoris.swingy.Models.Artifacts.Weapon;
import za.co.technoris.swingy.Models.Characters.Foe;
import za.co.technoris.swingy.Views.CommandLine.CLI;

import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import static za.co.technoris.swingy.Helpers.GlobalHelper.hero;
import static za.co.technoris.swingy.Helpers.GlobalHelper.artifact;
import static za.co.technoris.swingy.Helpers.GlobalHelper.map;
import static za.co.technoris.swingy.Helpers.GlobalHelper.isGUI;
import static za.co.technoris.swingy.Helpers.GlobalHelper.ANSI_RED;
import static za.co.technoris.swingy.Helpers.GlobalHelper.ANSI_RESET;
import static za.co.technoris.swingy.Helpers.GlobalHelper.fightPhase;
import static za.co.technoris.swingy.Helpers.GlobalHelper.lootOption;
import static za.co.technoris.swingy.Helpers.GlobalHelper.foe;
import static za.co.technoris.swingy.Helpers.GlobalHelper.encounterPhase;
import static za.co.technoris.swingy.Helpers.GlobalHelper.foeType;
import static za.co.technoris.swingy.Controllers.CharacterFactory.newFoe;

public class GameManager {

	private static final String GAIN_MESSAGE = "Take this artifact to gain: ";
	private final static int NORTH = 8;
	private final static int EAST = 6;
	private final static int SOUTH = 2;
	private final static int WEST = 4;

	private static int[] prevMove = new int[2];

	public static void winCondition() {
		if (hero.getX() == map.getMapSize() - 1 || hero.getY() == map.getMapSize() - 1 || hero.getX() == 0
				|| hero.getY() == 0) {
			LoggerHelper.print("You've reached a wall! You live to die another day.\n\n----- NEW LEVEL -----\n");
			map = MapFactory.generateMap(hero);
			if (!isGUI) {
				CLI.moveHero();
			}
		}
	}

	private static void loot() {
		int randomNum = new Random().nextInt(2);

		if (randomNum == 1) {
			LoggerHelper.print("An artifact was dropped!");
			int randomNum1 = new Random().nextInt(4);
			int stats = hero.getLevel() + 1;
			switch (randomNum1) {
			case 0:
				artifact = new Weapon(foe.getWeapon().getName(), stats);
				LoggerHelper.print(artifact.getName() + " - Attack: " + stats);
				LoggerHelper.print(GAIN_MESSAGE + (((Weapon) artifact).getAttack() - hero.getWeapon().getAttack())
						+ " attack point(s)");
				break;
			case 1:
				artifact = new Armor(foe.getArmor().getName(), stats);
				LoggerHelper.print(artifact.getName() + " - Defense: " + stats);
				LoggerHelper.print(GAIN_MESSAGE + (((Armor) artifact).getDefense() - hero.getArmor().getDefense())
						+ " defense point(s)");
				break;
			case 2:
				artifact = new Helm(foe.getHelm().getName(), stats);
				LoggerHelper.print(artifact.getName() + " - Health: " + stats);
				LoggerHelper.print(
						GAIN_MESSAGE + (((Helm) artifact).getHP() - hero.getHelm().getHP()) + " health point(s)");
				break;
			case 3:
				hero.setHP(hero.getHP() + hero.getLevel() + 1);
				LoggerHelper.print("You found a health potion! Current health: " + hero.getHP());
				return;
			}
			if (!isGUI) {
				LoggerHelper.print("Would you like to keep it?");
				LoggerHelper.print("1: Yes");
				LoggerHelper.print("2: No");
				Scanner scanner = new Scanner(System.in);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.matches("\\s*[1-2]\\s*")) {
						int num = Integer.parseInt(line.trim());
						if (num == 1) {
							hero.suitUp(artifact, artifact.getType());
							LoggerHelper.print(artifact.getName() + " taken");
							break;
						} else if (num == 2) {
							break;
						} else {
							LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + " Invalid option");
						}
					} else {
						LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + " Invalid option");
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
			LoggerHelper.print(foe.getName() + " starts attacking:");
			while (hero.getHP() > 0 && foe.getHP() > 0) {
				foe.attack(hero);
				foe.attack(hero);
				if (hero.getHP() > 0) {
					hero.attack(foe);
				}
			}
		} else {
			LoggerHelper.print("You start attacking:");
			while (hero.getHP() > 0 && foe.getHP() > 0) {
				hero.attack(foe);
				if (foe.getHP() > 0) {
					foe.attack(hero);
				}
			}
		}
		if (hero.getHP() <= 0) {
			LoggerHelper.print("Game Over!");
			if (!isGUI) {
				CLI.run();
			} else {
				JOptionPane.showMessageDialog(null, "You lost!", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			System.exit(0);
		} else if (foe.getHP() <= 0) {
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
					int num = Integer.parseInt(line.trim());
					switch (num) {
					case 1:
						fight(false);
						return;
					case 2:
						run();
						return;
					}
				} else {
					LoggerHelper.print(ANSI_RED + ">> " + ANSI_RESET + " Invalid option");
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
			if ("Wolf".equals(foeType))
				foe = (Foe) newFoe(FoeTypes.WOLF, hero);
			else if ("Zombie".equals(foeType))
				foe = (Foe) newFoe(FoeTypes.ZOMBIE, hero);
			LoggerHelper
					.print("You're facing a level " + foe.getLevel() + " " + foe.getName() + "! Choose your action...");
			fightOrRun();
		}
	}
}
