/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:43:31 
 * @Last Modified by: mmbatha
 * @Last Modified time: 2019-07-04 11:28:07
 */
package za.co.technoris.swingy.Views.GraphicUser;

import javax.swing.*;
import static za.co.technoris.swingy.Helpers.GlobalHelper.*;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	public static Window window;

	public static void run() {
		isGUI = true;
		window = new Window();
	}
}
