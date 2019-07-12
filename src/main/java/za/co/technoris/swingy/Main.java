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
			if ("console".equals(arg[0])) {
				LoggerHelper.print(ANSI_CYAN + WELCOME_MSG + ANSI_RESET);
				factory = Validation.buildDefaultValidatorFactory();
				CLI.run();
			} else if ("gui".equals(arg[0])) {
				factory = Validation.buildDefaultValidatorFactory();
				GUI.run();
			} else {
				LoggerHelper.print("Usage: java -jar target/swingy.jar [console/gui]");
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			LoggerHelper.print("Usage: java -jar target/swingy.jar [console/gui]");
		}
	}
}
