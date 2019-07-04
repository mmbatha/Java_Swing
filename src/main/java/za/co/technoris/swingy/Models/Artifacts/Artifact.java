/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:57:06 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:57:06 
 */
package za.co.technoris.swingy.Models.Artifacts;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class Artifact implements Serializable {

	private static final long serialVersionUID = 2665009815079060992L;
	String name;
	ArtifactsHelper type;

	Artifact(String name) {
		this.name = name;
	}
}
