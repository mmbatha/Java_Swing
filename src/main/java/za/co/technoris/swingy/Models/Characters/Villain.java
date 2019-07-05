/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:05:40 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 11:05:40 
 */
package za.co.technoris.swingy.Models.Characters;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import za.co.technoris.swingy.Models.Artifacts.*;

public class Villain extends Hero {

	public Villain() {
		super();
	}

	public Villain(String name) {
		super(name);
		this.type = "Villain";
		this.attack += 5;
		this.defense += 3;
		this.HP += 75;
		suitUp(new Weapon("Cat ninja", 1), ArtifactsHelper.WEAPON);
		suitUp(new Armor("Bulletproof suit", 1), ArtifactsHelper.ARMOR);
		suitUp(new Helm("Hair gel", 1), ArtifactsHelper.HELM);
	}
}
