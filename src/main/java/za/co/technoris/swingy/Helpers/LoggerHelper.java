package za.co.technoris.swingy.Helpers;


import static za.co.technoris.swingy.Helpers.GlobalHelper.isGUI;

/**
 * Created by mmbatha on 5/11/17.
 */
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
