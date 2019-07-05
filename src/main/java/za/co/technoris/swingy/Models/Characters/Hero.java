/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:00:12 
 * @Last Modified by: mmbatha
 * @Last Modified time: 2019-07-04 11:04:25
 */
package za.co.technoris.swingy.Models.Characters;

import za.co.technoris.swingy.Helpers.ArtifactsHelper;
import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Models.Artifacts.Armor;
import za.co.technoris.swingy.Models.Artifacts.Artifact;
import za.co.technoris.swingy.Models.Artifacts.Helm;
import za.co.technoris.swingy.Models.Artifacts.Weapon;
import za.co.technoris.swingy.Views.Map;
import lombok.Getter;
import lombok.Setter;
import java.util.Random;

@Getter
@Setter
public abstract class Hero extends Character {

	private static final String CRITICAL_HIT = "Critical (but stable) hit!";
	private int XP;
	private Map observer;
	protected Weapon weapon;
	protected Armor armor;
	protected Helm helm;

	Hero() {
	}

	Hero(String name) {
		this.name = name;
		this.level = 1;
		this.XP = 0;
	}

	public void register(Map map) {
		observer = map;
	}

	private void updateMap() {
		observer.updateHeroPosition();
	}

	public void setPosition(int x, int y) {
		this.x += x;
		this.y += y;
		updateMap();
	}

	public void attack(Character character) {
		LoggerHelper.print(this.getName() + " attacks!");
		int critical = 0;
		if (this.getType().equals("Farmer")) {
			int random = new Random().nextInt(4);
			if (random == 3) {
				LoggerHelper.print(CRITICAL_HIT);
				critical = this.level * 2;
			}
		}
		character.defend(this, this.attack + critical);
		if (character.getHP() <= 0) {
			int xpEarned = 0;
			String type = this.getType();
			switch (type) {
			case "Villain":
				LoggerHelper.print(this.name + " says: \"Death has claimed another!\"");
				break;
			case "Farmer":
				LoggerHelper.print(this.name + " says: \"Too easy!\"");
				break;
			case "Nerd":
				LoggerHelper.print(this.name + " says: \"Get shwifty!\"");
				break;
			}
			if (character.getType().equals("Zombie")) {
				xpEarned = (int) (Math.ceil((float) this.level / 2) * 750);
				this.XP += xpEarned;
			} else if (character.getType().equals("Wolf")) {
				xpEarned = (int) (Math.ceil((float) this.level / 2) * 500);
				this.XP += xpEarned;
			}
			LoggerHelper.print("You earned " + xpEarned + " XP");
			if (this.XP >= (this.level * 1000 + Math.pow(this.level - 1, 2) * 450)) {
				leveledUp();
			}
		}
	}

	public void defend(Character character, int damage) {
		int realDamage = damage - this.defense;

		if (realDamage <= 0) {
			realDamage = 1;
		}
		this.HP -= realDamage;
		LoggerHelper.print(character.getName() + " dealt " + realDamage + " damage to " + this.name);
		if (this.HP <= 0) {
			LoggerHelper.print(this.name + " died!");
		}
	}

	private void leveledUp() {
		this.level += 1;
		int stats = this.level;

		LoggerHelper.print(this.name + " has leveled up. Current level: " + this.level);
		LoggerHelper.print("- Attack: " + this.attack + " (+" + stats + ")");
		LoggerHelper.print("- Defense: " + this.defense + " (+" + 1 + ")");
		LoggerHelper.print("- HP: " + this.HP + " (+" + stats + ")");
		this.attack += stats;
		this.defense += 1;
		this.HP += stats;
	}

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
