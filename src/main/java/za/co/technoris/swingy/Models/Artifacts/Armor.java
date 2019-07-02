package za.co.technoris.swingy.Models.Artifacts;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import lombok.Getter;

/**
 * Created by mmbatha on 4/20/17.
 */
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
