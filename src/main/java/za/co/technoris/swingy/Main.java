/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:18:04 
 * @Last Modified by: mmbatha
 * @Last Modified time: 2019-07-04 11:18:42
 */
package za.co.technoris.swingy;

import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Views.CommandLine.CLI;
import za.co.technoris.swingy.Views.GraphicUser.GUI;

import javax.validation.Validation;

import static za.co.technoris.swingy.Helpers.GlobalHelper.*;

public class Main {
	public static void main(String[] arg) {
		try {
			switch (arg[0]) {
			case "console":
				LoggerHelper.print(ANSI_CYAN + "Welcome to \"SWINGY RPG\"" + ANSI_RESET);
				factory = Validation.buildDefaultValidatorFactory();
				CLI.run();
				break;
			case "gui":
				factory = Validation.buildDefaultValidatorFactory();
				GUI.run();
				break;
			default:
				LoggerHelper.print("Usage: java -jar target/swingy.jar [console/gui]");
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			LoggerHelper.print("Usage: java -jar target/swingy.jar [console/gui]");
		}
	}
}
