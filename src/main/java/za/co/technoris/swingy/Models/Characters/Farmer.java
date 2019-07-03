package za.co.technoris.swingy.Models.Characters;

/**
 * Created by mmbatha on 4/20/17.
 */
public class Farmer extends Hero {

    public Farmer() {
        super();
    }

    public Farmer(String name) {
        super(name);
        this.type = "Farmer";
        this.attack += 7;
        this.defense += 2;
        this.hp += 50;
    }
}
