/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:57:33 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:57:33 
 */
package za.co.technoris.swingy.Models.Artifacts;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import lombok.Getter;

@Getter
public class Weapon extends Artifact {

	private static final long serialVersionUID = 1L;
	private int attack;

	public Weapon(String name, int attack) {
		super(name);
		this.type = ArtifactsHelper.WEAPON;
		this.attack = attack;
	}
}
