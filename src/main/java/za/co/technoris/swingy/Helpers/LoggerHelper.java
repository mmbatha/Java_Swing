/*
 * @Author: mmbatha
 * @Date: 2019-07-04 10:52:50
 * @Last Modified by:   mmbatha
 * @Last Modified time: 2019-07-04 10:52:50
 */
package za.co.technoris.swingy.Helpers;

import static za.co.technoris.swingy.Helpers.GlobalHelper.isGUI;
import static za.co.technoris.swingy.Helpers.GlobalHelper.jtaLog;
import static za.co.technoris.swingy.Helpers.GlobalHelper.fightPhase;

public class LoggerHelper {

	static public void print(String content) {
		if (!isGUI) {
			System.out.println(content);
		} else {
			if (!fightPhase) {
				jtaLog.setText(content + "\n");
			} else {
				jtaLog.append(content + "\n");
			}
		}
	}

	public static void printForInput(String content) {
		if (!isGUI) {
			System.out.println(content + "\n>> ");
		} else {
			if (!fightPhase) {
				jtaLog.setText(content + "\n");
			} else {
				jtaLog.append(content + "\n");
			}
		}
	}
}
