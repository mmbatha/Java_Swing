/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:52:50 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:52:50 
 */
package za.co.technoris.swingy.Helpers;

import static za.co.technoris.swingy.Helpers.GlobalHelper.isGUI;

public class LoggerHelper {

	static public void print(String content) {
		if (!isGUI) {
			System.out.println(content);
		} else {
			if (!GlobalHelper.fightPhase) {
				GlobalHelper.jtaLog.setText(content + "\n");
			} else {
				GlobalHelper.jtaLog.append(content + "\n");
			}
		}
	}
}
