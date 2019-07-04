/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:50:58 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:50:58 
 */
package za.co.technoris.swingy.Helpers;

import za.co.technoris.swingy.Models.Artifacts.Artifact;
import za.co.technoris.swingy.Models.Characters.Foe;
import za.co.technoris.swingy.Models.Characters.Hero;
import za.co.technoris.swingy.Views.Map;

import javax.swing.*;
import javax.validation.ValidatorFactory;

public class GlobalHelper {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static ValidatorFactory factory;
	public static Boolean isHero = false;
	public static Boolean lootOption = false;
	public static Boolean fightPhase = false;
	public static Boolean encounterPhase = false;
	public static Boolean isGUI = false;
	public static JTextArea jtaLog;
	public static int heroNumber = 0;
	public static Hero hero;
	public static Foe foe;
	public static Artifact artifact;
	public static Map map;
}
