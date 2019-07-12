/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:06:53 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 11:06:53 
 */
package za.co.technoris.swingy.Models.Characters;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import za.co.technoris.swingy.Models.Artifacts.Armor;
import za.co.technoris.swingy.Models.Artifacts.Helm;
import za.co.technoris.swingy.Models.Artifacts.Weapon;

public class Zombie extends Foe {

	public Zombie(int level) {
		super(level);
		this.name = "Zombie";
		this.type = "Zombie";
		this.attack = 7 + this.level;
		this.defense = 2 + this.level;
		this.HP = 20 + this.level;
		suitUp(new Weapon("Zombie bite", 3), ArtifactsHelper.WEAPON);
		suitUp(new Armor("Ragged suit", -1), ArtifactsHelper.ARMOR);
		suitUp(new Helm("Corpse skin", 1), ArtifactsHelper.HELM);
	}
}
