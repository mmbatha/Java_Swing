package za.co.technoris.swingy.Models.Characters;

/**
 * Created by mmbatha on 4/20/17.
 */
public class Villain extends Hero {

    public Villain() {
        super();
    }

    public Villain(String name) {
        super(name);
        this.type = "Villain";
        this.attack += 5;
        this.defense += 3;
        this.hp += 75;
    }
}
