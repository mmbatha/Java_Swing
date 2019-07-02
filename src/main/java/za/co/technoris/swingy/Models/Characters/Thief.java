package za.co.technoris.swingy.Models.Characters;

/**
 * Created by mmbatha on 4/20/17.
 */
public class Thief extends Hero {

    public Thief() {
        super();
    }

    public Thief(String name) {
        super(name);
        this.type = "Thief";
        this.attack += 7;
        this.defense += 2;
        this.hp += 50;
    }
}
