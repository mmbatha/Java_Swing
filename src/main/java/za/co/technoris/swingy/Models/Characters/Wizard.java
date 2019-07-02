package za.co.technoris.swingy.Models.Characters;

/**
 * Created by mmbatha on 4/20/17.
 */
public class Wizard extends Hero {

    public Wizard() {
        super();
    }

    public Wizard(String name) {
        super(name);
        this.type = "Wizard";
        this.attack += 10;
        this.defense += 1;
        this.hp += 25;
    }
}
