/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:45:36 
 * @Last Modified by: mmbatha
 * @Last Modified time: 2019-07-04 10:47:20
 */
package za.co.technoris.swingy.Controllers;

import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Models.Characters.Character;
import za.co.technoris.swingy.Views.Map;

public class MapFactory {

	private static final String LANDING = " landed in quite a pickle!";

	public static Map generateMap(Character hero) {
		int mapSize = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);

		if (mapSize > 19) {
			mapSize = 19;
		}
		Map map = new Map(mapSize);
		map.registerHero(hero);
		LoggerHelper.print(hero.getName() + LANDING);
		map.generateFoes();
		return (map);
	}
}
