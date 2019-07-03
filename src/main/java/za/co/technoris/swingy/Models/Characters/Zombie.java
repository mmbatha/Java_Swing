package za.co.technoris.swingy.Models.Characters;

/**
 * Created by mmbatha on 5/3/17.
 */
public class Zombie extends Foe {

    public Zombie(int level) {
        super(level);
        this.name = "Zombie Bite";
        this.type = "Zombie";
        this.attack = 7 + this.level;
        this.defense = 2 + this.level;
        this.hp = 20 + this.level;
    }
}
