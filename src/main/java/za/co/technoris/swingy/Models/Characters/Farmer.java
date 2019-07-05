/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:58:44 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:58:44 
 */
package za.co.technoris.swingy.Models.Characters;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import za.co.technoris.swingy.Models.Artifacts.*;

public class Farmer extends Hero {

	public Farmer() {
		super();
	}

	public Farmer(String name) {
		super(name);
		this.type = "Farmer";
		this.attack += 7;
		this.defense += 2;
		this.HP += 50;
		suitUp(new Weapon("Rusty hoe", 1), ArtifactsHelper.WEAPON);
		suitUp(new Armor("Overalls", 1), ArtifactsHelper.ARMOR);
		suitUp(new Helm("Straw hat", 1), ArtifactsHelper.HELM);
	}
}
