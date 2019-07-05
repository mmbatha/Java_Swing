/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:05:25 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 11:05:25 
 */
package za.co.technoris.swingy.Models.Characters;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import za.co.technoris.swingy.Models.Artifacts.*;

public class Nerd extends Hero {

	public Nerd() {
		super();
	}

	public Nerd(String name) {
		super(name);
		this.type = "Nerd";
		this.attack += 10;
		this.defense += 1;
		this.HP += 25;
		suitUp(new Weapon("Click pen", 1), ArtifactsHelper.WEAPON);
		suitUp(new Armor("Stormtrooper outfit", 1), ArtifactsHelper.ARMOR);
		suitUp(new Helm("Wizard hat", 1), ArtifactsHelper.HELM);
	}
}
