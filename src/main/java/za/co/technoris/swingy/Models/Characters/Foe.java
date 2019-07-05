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
		LoggerHelper.print(this.getName() + " attacks!");
		character.defend(this, this.attack);
		if (character.getHP() <= 0) {
			switch (character.getType()) {
			case "Villain":
				LoggerHelper.print(this.name + " says: \"I can do this all day!\"");
				break;
			case "Farmer":
				LoggerHelper.print(this.name + " says: \"Oh crop!\"");
				break;
			case "Nerd":
				LoggerHelper.print(this.name + " says: \"I reject your reality and substitute my own!\"");
				break;
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
		if (HP <= 0) {
			LoggerHelper.print(this.name + " died");
		}
	}
}
