/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:59:09 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:59:09 
 */
package za.co.technoris.swingy.Models.Characters;

import za.co.technoris.swingy.Helpers.LoggerHelper;

public abstract class Foe extends Character {

	Foe(int level) {
		this.level = level;
	}

	public void attack(Character character) {
		LoggerHelper.print(this.getName() + " is attacking");
		character.defend(this, this.attack);
		if (character.getHp() <= 0) {
			switch (character.getType()) {
			case "Villain":
				LoggerHelper.print(this.name + " says: You, a villain? Killmonger you are NOT!");
				break;
			case "Farmer":
				LoggerHelper.print(this.name + " says: Oh crop!");
				break;
			case "Nerd":
				LoggerHelper.print(this.name + " says: I reject your reality and substitute my own!");
				break;
			}
		}
	}

	public void defend(Character character, int damage) {
		int realDamage = damage - this.defense;

		if (realDamage <= 0) {
			realDamage = 1;
		}
		this.hp -= realDamage;
		LoggerHelper.print(character.getName() + " dealt " + realDamage + " damage to " + this.name);
		if (hp <= 0) {
			LoggerHelper.print(this.name + " died");
		}
	}
}
