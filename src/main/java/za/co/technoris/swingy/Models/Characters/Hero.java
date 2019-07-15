/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:00:12 
 * @Last Modified by: mmbatha
 * @Last Modified time: 2019-07-04 11:04:25
 */
package za.co.technoris.swingy.Models.Characters;

import za.co.technoris.swingy.Helpers.LoggerHelper;
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
		LoggerHelper.print(this.getName() + " attacks...");
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
			switch (this.getType()) {
			case "Villain":
				LoggerHelper.print(this.name + " says: \"I can do this all day!\"");
				break;
			case "Farmer":
				LoggerHelper.print(this.name + " says: \"Oh crop!\"");
				break;
			case "Nerd":
				LoggerHelper.print(this.name + " says: \"I reject your reality and substitute my own!\"");
				break;
			default:
				LoggerHelper.print(this.name + " says: \"OOOOOHHH NOOOOO!! The PAAAAAAAIIIINNNNN!!\"");
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
		LoggerHelper
				.print(character.getName() + " dealt " + realDamage + " points worth of damage to " + this.name + "!!");
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
}
