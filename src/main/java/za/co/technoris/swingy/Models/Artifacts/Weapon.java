package za.co.technoris.swingy.Models.Artifacts;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import lombok.Getter;

/**
 * Created by mmbatha on 4/20/17.
 */

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
