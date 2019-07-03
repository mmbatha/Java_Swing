package za.co.technoris.swingy.Models.Characters;

/**
 * Created by mmbatha on 5/3/17.
 */
public class Wolf extends Foe {

    public Wolf(int level) {
        super(level);
        this.name = "Wolf Bite";
        this.type = "Wolf";
        this.attack = 5 + this.level;
        this.defense = 1 + this.level;
        this.hp = 10 + this.level;
    }
}
