package za.co.technoris.swingy.Models.Artifacts;


import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import lombok.Getter;

/**
 * Created by mmbatha on 4/20/17.
 */
@Getter
public class Helm extends Artifact {
    private static final long serialVersionUID = 1L;
	private int hp;

    public Helm(String name, int hp) {
        super(name);
        this.type = ArtifactsHelper.HELM;
        this.hp = hp;
    }
}
