package za.co.technoris.swingy.Models.Characters;

/**
 * Created by mmbatha on 5/3/17.
 */
public class Bat extends Foe {

    public Bat(int level) {
        super(level);
        this.name = "Bat Man";
        this.type = "Bat";
        this.attack = 5 + this.level;
        this.defense = 1 + this.level;
        this.hp = 10 + this.level;
    }
}
