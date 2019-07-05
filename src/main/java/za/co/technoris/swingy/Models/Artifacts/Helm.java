/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:57:19 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:57:19 
 */
package za.co.technoris.swingy.Models.Artifacts;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import lombok.Getter;

@Getter
public class Helm extends Artifact {
	private static final long serialVersionUID = 1L;
	private int HP;

	public Helm(String name, int HP) {
		super(name);
		this.type = ArtifactsHelper.HELM;
		this.HP = HP;
	}
}
