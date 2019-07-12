/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:57:54 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:57:54 
 */
package za.co.technoris.swingy.Models.Characters;

import lombok.Getter;
import lombok.Setter;
import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import za.co.technoris.swingy.Models.Artifacts.Armor;
import za.co.technoris.swingy.Models.Artifacts.Artifact;
import za.co.technoris.swingy.Models.Artifacts.Helm;
import za.co.technoris.swingy.Models.Artifacts.Weapon;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public abstract class Character {

	@NotNull
	@Size(min = 2, max = 20)
	protected String name;

	protected String type;
	protected int attack;
	protected int defense;
	protected int HP;
	protected int level;
	protected int x;
	protected int y;
	protected Weapon weapon;
	protected Armor armor;
	protected Helm helm;

	abstract public void attack(Character character);

	abstract public void defend(Character character, int damage);

	public void suitUp(Artifact artifact, ArtifactsHelper type) {
		switch (type) {
		case WEAPON:
			if (weapon != null) {
				attack -= weapon.getAttack();
			}
			weapon = (Weapon) artifact;
			attack += weapon.getAttack();
			break;
		case ARMOR:
			if (armor != null) {
				defense -= armor.getDefense();
			}
			armor = (Armor) artifact;
			defense += armor.getDefense();
			break;
		case HELM:
			if (helm != null) {
				HP -= helm.getHP();
			}
			helm = (Helm) artifact;
			HP += helm.getHP();
			break;
		}
	}
}
