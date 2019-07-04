/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:56:30 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:56:30 
 */
package za.co.technoris.swingy.Models.Artifacts;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import lombok.Getter;

@Getter
public class Armor extends Artifact {

	private static final long serialVersionUID = 1L;
	private int defense;

	public Armor(String name, int defense) {
		super(name);
		this.type = ArtifactsHelper.ARMOR;
		this.defense = defense;
	}
}
