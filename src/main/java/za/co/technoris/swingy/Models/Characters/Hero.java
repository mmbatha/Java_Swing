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


/**
 * Created by mmbatha on 4/20/17.
 */

@Getter
@Setter
public abstract class Hero extends Character {

    private int xp;
    private Map observer;
    protected Weapon weapon;
    protected Armor armor;
    protected Helm helm;


    Hero() {
    }

    Hero(String name) {
        this.name = name;
        this.level = 1;
        this.xp = 0;
        pickUp(new Weapon("Rusted dagger", 1), ArtifactsHelper.WEAPON);
        pickUp(new Armor("Ripped shirt", 1), ArtifactsHelper.ARMOR);
        pickUp(new Helm("Old hat", 1), ArtifactsHelper.HELM);
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
        LoggerHelper.print(this.getName() + " is attacking");
        int critical = 0;
        if (this.getType().equals("Thief")) {
            int random = new Random().nextInt(4);
            if (random == 3) {
                LoggerHelper.print("Critical hit !");
                critical = this.level * 2;
            }
        }
        character.defend(this, this.attack + critical);
        if (character.getHp() <= 0) {
            int xpEarned = 0;
            String type = this.getType();
            switch (type) {
                case "Warrior":
                    LoggerHelper.print(this.name + " says: Die !");
                    break;
                case "Thief":
                    LoggerHelper.print(this.name + " says: Too easy !");
                    break;
                case "Wizard":
                    LoggerHelper.print(this.name + " says: Burn !");
                    break;
            }
            if (character.getType().equals("Rat")) {
                xpEarned = (int) (Math.ceil((float)this.level / 2) * 750);
                this.xp += xpEarned;
            } else if (character.getType().equals("Bat")) {
                xpEarned = (int) (Math.ceil((float)this.level / 2) * 500);
                this.xp += xpEarned;
            }
            LoggerHelper.print("You earned " + xpEarned + " xp");
            if (this.xp >= (this.level * 1000 + Math.pow(this.level - 1, 2) * 450)) {
                leveledUp();
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
        if (this.hp <= 0) {
            LoggerHelper.print(this.name + " died");
        }
    }

    private void leveledUp() {
        this.level += 1;
        int stats = this.level;

        LoggerHelper.print(this.name + " has leveled up. Current level: " + this.level);
        LoggerHelper.print("- Attack: " + this.attack + " (+" + stats + ")");
        LoggerHelper.print("- Defense: " + this.defense + " (+" + 1 + ")");
        LoggerHelper.print("- Hp: " + this.hp + " (+" + stats + ")");
        this.attack += stats;
        this.defense += 1;
        this.hp += stats;
    }

    public void pickUp(Artifact artifact, ArtifactsHelper type) {
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
                    hp -= helm.getHp();
                }
                helm = (Helm) artifact;
                hp += helm.getHp();
                break;
        }
    }
}
