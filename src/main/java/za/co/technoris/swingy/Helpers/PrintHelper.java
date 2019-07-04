/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:53:26 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:53:26 
 */
package za.co.technoris.swingy.Helpers;

public class PrintHelper {

	public static void printMenu() {
		LoggerHelper.print("1 - Create a new hero\n" + "2 - Select a hero\n" + "3 - Switch to GUI view");
	}

	public static void printHeroList() {
		System.out.println("Hero types:\n" + "1 - Villain (turned)\n" + "2 - Farmer\n" + "3 - Nerd");
	}

	public static void printHeroDetail(int input) {
		switch (input) {
		case 1:
			LoggerHelper.print("Villain - Level: 0\n" + "- Attack: 3\n" + "- Defense: 5\n" + "- Health: 8");
			break;
		case 2:
			LoggerHelper.print("Farmer - Level: 0\n" + "- Attack: 4\n" + "- Defense: 3\n" + "- Health: 5");
			break;
		case 3:
			LoggerHelper.print("Nerd - Level: 0\n" + "- Attack: 5\n" + "- Defense: 2\n" + "- Health: 3");
			break;
		}
	}

	public static void printDirections() {
		LoggerHelper.print("Please type a direction:\n" + "8: North\n" + "6: East\n" + "2: South\n" + "4: West\n"
				+ "5: See hero stats");
	}

	public static void printFightOptions() {
		LoggerHelper.print("1 - Fight\n" + "2 - Run");
	}
}
