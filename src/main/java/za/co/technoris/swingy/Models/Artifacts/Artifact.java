package za.co.technoris.swingy.Models.Artifacts;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by mmbatha on 5/4/17.
 */
@Getter
public abstract class Artifact implements Serializable {

    private static final long serialVersionUID = 2665009815079060992L;
	String name;
    ArtifactsHelper type;

    Artifact(String name) {
        this.name = name;
    }
}
