package za.co.technoris.swingy.Controllers;

import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Models.Characters.Character;
import za.co.technoris.swingy.Views.Map;

/**
 * Created by mmbatha on 5/3/17.
 */
public class MapFactory {

    public static Map generateMap(Character hero) {
        int mapSize = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);

        if (mapSize > 19) {
            mapSize = 19;
        }
        Map map = new Map(mapSize);
        map.registerHero(hero);
        LoggerHelper.print(hero.getName() + " arrived in a new hostile environment");
        map.generateFoes();
        return (map);
    }
}
