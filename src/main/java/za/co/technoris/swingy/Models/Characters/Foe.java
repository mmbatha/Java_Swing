package za.co.technoris.swingy.Models.Characters;

import za.co.technoris.swingy.Helpers.LoggerHelper;

/**
 * Created by mmbatha on 5/3/17.
 */
public abstract class Foe extends Character {

    Foe(int level) {
        this.level = level;
    }

    public void attack(Character character) {
        LoggerHelper.print(this.getName() + " is attacking");
        character.defend(this, this.attack);
        if (character.getHp() <= 0) {
            switch (character.getType()) {
                case "Warrior":
                    LoggerHelper.print(this.name + " says: You, a warrior? you must be joking!");
                    break;
                case "Thief":
                    LoggerHelper.print(this.name + " says: Noob!");
                    break;
                case "Wizard":
                    LoggerHelper.print(this.name + " says: You are too weak!");
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