/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:05:50 
 * @Last Modified by: mmbatha
 * @Last Modified time: 2019-07-04 11:07:06
 */
package za.co.technoris.swingy.Models.Characters;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import za.co.technoris.swingy.Models.Artifacts.Armor;
import za.co.technoris.swingy.Models.Artifacts.Helm;
import za.co.technoris.swingy.Models.Artifacts.Weapon;

public class Wolf extends Foe {

	public Wolf(int level) {
		super(level);
		this.name = "Wolf";
		this.type = "Wolf";
		this.attack = 5 + this.level;
		this.defense = 1 + this.level;
		this.HP = 10 + this.level;
		suitUp(new Weapon("Wolf bite", 2), ArtifactsHelper.WEAPON);
		suitUp(new Armor("Fur coat", 1), ArtifactsHelper.ARMOR);
		suitUp(new Helm("Doggy clothes", -1), ArtifactsHelper.HELM);
	}
}
